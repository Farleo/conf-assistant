package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.dto.*;
import lms.itcluster.confassistant.entity.*;
import lms.itcluster.confassistant.exception.NoSuchEntityException;
import lms.itcluster.confassistant.mapper.Mapper;
import lms.itcluster.confassistant.model.Constant;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.repository.ConferenceRepository;
import lms.itcluster.confassistant.repository.TopicRepository;
import lms.itcluster.confassistant.repository.UserRepository;
import lms.itcluster.confassistant.service.*;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.MINUTES;

@Log4j
@Service
public class ConferenceServiceImpl implements ConferenceService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Autowired
    private ImageStorageService imageStorageService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    @Qualifier("simpleConferenceMapper")
    private Mapper<Conference, ConferenceDTO> simpleMapper;

    @Autowired
    @Qualifier("conferenceMapper")
    private Mapper<Conference, ConferenceDTO> mapper;

    @Autowired
    @Qualifier("topicMapper")
    private Mapper<Topic, TopicDTO> topicMapper;

    @Autowired
    @Qualifier("streamMapper")
    private Mapper<Stream, StreamDTO> streamMapper;


    private List<Conference> getAllConferences() {
        return conferenceRepository.findAll();
    }

    @Override
    public Conference findById(Long id) {
        return conferenceRepository.findById(id).filter(conference -> !conference.getDeleted()).orElseThrow(() -> {
            log.error(String.format("Conference with id - %d not found", id));
            return new NoSuchEntityException(String.format("Conference with id - %d not found", id));
        });
    }

    @Override
    public ConferenceDTO getConferenceDTOById(Long id) {
        return mapper.toDto(findById(id));
    }

    @Override
    public ConferenceDTO getConferenceDTObyAlias(String alias) {
        Conference conference = conferenceRepository.findByAlias(alias);
        if (conference!=null&&!conference.getDeleted()) {
            return simpleMapper.toDto(conference);
        }
        return null;
    }
    
    @Override
    public ListConferenceDTO getListConferencesDTO() {
        return new ListConferenceDTO(getAllConferencesDTO());
    }

    @Override
    public ListConferenceDTO getAllConferenceDTOForCurrentSpeaker(CurrentUser currentUser) {
        User user = userService.findById(currentUser.getId());
        List<ConferenceDTO> list = new ArrayList<>();
        for (Participants participants : user.getParticipants()) {
            if (participants.getParticipantsKey().getParticipantType().getName().equals(Constant.SPEAKER)) {
                list.add(simpleMapper.toDto(participants.getParticipantsKey().getConference()));
            }
        }
        deleteAllUnnecessaryTopicDTO(list, currentUser);
        return new ListConferenceDTO(list);
    }

    private void deleteAllUnnecessaryTopicDTO(List<ConferenceDTO> list, CurrentUser currentUser) {
        for (ConferenceDTO conferenceDTO : list) {
            conferenceDTO.getStreamList().removeIf(streamDTO -> {
                streamDTO.getTopicList().removeIf(topicDTO -> !topicDTO.getSpeakerDTO().getUserId().equals(currentUser.getId()));
                return streamDTO.getTopicList().isEmpty();
            });
        }
    }

    @Override
    public ListConferenceDTO getAllConferenceDTOForCurrentModerator(CurrentUser currentUser) {
        User user = userService.findById(currentUser.getId());
        List<ConferenceDTO> list = new ArrayList<>();
        for (Participants participants : user.getParticipants()) {
            if (participants.getParticipantsKey().getParticipantType().getName().equals(Constant.MODERATOR)) {
                list.add(simpleMapper.toDto(participants.getParticipantsKey().getConference()));
            }
        }
        deleteAllUnnecessaryStreamDTO(list, currentUser);
        return new ListConferenceDTO(list);
    }

    private void deleteAllUnnecessaryStreamDTO(List<ConferenceDTO> list, CurrentUser currentUser) {
        for (ConferenceDTO conferenceDTO : list) {
            conferenceDTO.getStreamList().removeIf(nextDTO -> nextDTO.getModerator() != currentUser.getId());
        }
    }


    @Override
    public ListConferenceDTO getConferencesDTOByOwnerId(Long id) {
        List<Conference> conferenceList = conferenceRepository.findAllByOwnerId(id);
        List<ConferenceDTO> dtos = conferenceList.stream().map(c -> simpleMapper.toDto(c)).collect(Collectors.toList());
        return new ListConferenceDTO(dtos);
    }

    @Transactional
    @Override
    public void addNewConference(ConferenceDTO conferenceDTO, byte[] photo, String originalPhotoName) throws IOException {
        Conference conference = mapper.toEntity(conferenceDTO);

        Optional<String> newCoverPhoto = imageStorageService.saveAndReturnImageLink(photo, originalPhotoName);
        conference.setCoverPhoto(newCoverPhoto.orElse(null));

        conferenceRepository.save(conference);
    }

    @Transactional
    @Override
    public void updateConference(ConferenceDTO conferenceDTO, byte[] photo, String originalPhotoName) throws IOException {
        Conference conference = mapper.toEntity(conferenceDTO);

        Optional<String> newCoverPhoto = imageStorageService.saveAndReturnImageLink(photo, originalPhotoName);
        conference.setCoverPhoto(newCoverPhoto.orElse(conference.getCoverPhoto()));

        conferenceRepository.save(conference);
    }

    @Override
    public void deleteConference(Long confId) {
        Optional<Conference> conferenceOptional = conferenceRepository.findById(confId);
        if(conferenceOptional.isPresent()){
            Conference conference = conferenceOptional.get();
            conference.setDeleted(true);
            conferenceRepository.save(conference);
        }
    }

    @Override
    public List<ConferenceDTO> getAllConferencesDTO() {
        List<ConferenceDTO> list = new ArrayList<>();
        for (Conference conference : conferenceRepository.findAll().stream().filter(f -> !f.getDeleted()).collect(Collectors.toList())) {
            list.add(mapper.toDto(conference));
        }
        return list;
    }

    @Override
    public ScheduleConferenceDTO getConferenceForSchedule(Long confId) {
        Conference conference = findById(confId);
        ScheduleConferenceDTO scheduleConferenceDTO = new ScheduleConferenceDTO();

        BeanUtils.copyProperties(conference, scheduleConferenceDTO); //Copy property from entity into Dto

        Map<LocalDate, List<StreamDTO>> schedule = new TreeMap<>(); //Create map with date and events for that date

        List<Topic> topics = new ArrayList<>();
        List<StreamDTO> streamsDto = new ArrayList<>();

        for (Stream stream : conference.getStreamList()) {
            topics.addAll(stream.getTopicList());                   //Get all topics from current conference
            streamsDto.add(streamMapper.toDto(stream));                //Convert all streams from current conference into Dto
        }

        for (Topic topic : topics) {                                                        //Init schedule map
            if (schedule.containsKey(topic.getDate())) {                                    //If map already contains topic date
                for (StreamDTO streamDTO : schedule.get(topic.getDate())) {
                    if (streamDTO.getStreamId().equals(topic.getStream().getStreamId())) {  //find the stream that belongs to the topic
                        streamDTO.getTopicList().add(topicMapper.toDto(topic));             //convert topic to Dto and add him to stream
                    }
                }
            } else {                                                                        //if map does not contains topic date
                List<StreamDTO> newList = new ArrayList<>();                                //for every date create new list StreamDto with empty TopicList
                for (StreamDTO originDto : streamsDto) {
                    StreamDTO newDto = new StreamDTO();
                    BeanUtils.copyProperties(originDto, newDto);
                    newDto.setTopicList(new ArrayList<>());
                    if (originDto.getStreamId().equals(topic.getStream().getStreamId())) {  //if stream contains topic, add topic to new list StreamDto
                        newDto.getTopicList().add(topicMapper.toDto(topic));
                    }
                    newList.add(newDto);
                }
                schedule.put(topic.getDate(), newList);                                     //put new date and new list StreamDto in schedule
            }
        }

        scheduleConferenceDTO.setSchedule(schedule);                                        //add schedule to scheduleConferenceDto
        initDataForUI(scheduleConferenceDTO);                                               //init data for UI

        return scheduleConferenceDTO;
    }


    private void initDataForUI(ScheduleConferenceDTO conferenceDTOS) {
        for (Map.Entry<LocalDate, List<StreamDTO>> entry : conferenceDTOS.getSchedule().entrySet()) {
            for (StreamDTO streamDTO : entry.getValue()) {
                List<TopicDTO> listTopic = streamDTO.getTopicList();
                listTopic.sort(Comparator.comparing(TopicDTO::getBeginTime));
                LocalTime startTime = LocalTime.of(8, 0, 0);        //Min time on schedule graphic
                for (TopicDTO topicDTO : listTopic) {
                    LocalTime beginTime = topicDTO.getBeginTime();
                    LocalTime finishTime = topicDTO.getFinishTime();
                    double begin = (MINUTES.between(startTime, beginTime)) / 5.0 * 0.5;     //distance from previous block to begin current block
                    double finish = (MINUTES.between(beginTime, finishTime)) / 5.0 * 0.5;   //distance from begin current block to end of current block
                    topicDTO.setBackDown(begin);
                    topicDTO.setBodySize(finish);
                    startTime = finishTime;
                }
            }
        }
    }



    @Override
    public boolean registerCurrentUserForConference(Long confId, Long userId) {
        Conference conference = findById(confId);
        for (Participants participants : conference.getParticipants()) {
            if (participants.getParticipantsKey().getUser().getUserId().equals(userId)) {
                return false;
            }
        }
        UserDTO user = userService.getUserDtoById(userId);
        participantService.addParticipant(user, conference);
        emailService.sendMessage(user.getEmail(), "Registration on Conference: " + conference.getName(), "You have been successfully registered on Conference: " + conference.getName());
        return true;
    }

    @Override
    public ConferenceDTO getConferenceDTOByTopicId(Long topicId) {
        Topic topic = topicService.findById(topicId);
        return getConferenceDTOById(topic.getStream().getConference().getConferenceId());
    }

}
