package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.dto.*;
import lms.itcluster.confassistant.entity.*;
import lms.itcluster.confassistant.exception.NoSuchConferenceException;
import lms.itcluster.confassistant.mapper.Mapper;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.repository.ConferenceRepository;
import lms.itcluster.confassistant.repository.StreamRepository;
import lms.itcluster.confassistant.repository.TopicRepository;
import lms.itcluster.confassistant.repository.UserRepository;
import lms.itcluster.confassistant.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
public class ConferenceServiceImpl implements ConferenceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceServiceImpl.class);

    @Autowired
    private StreamRepository streamRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private StreamService streamService;

    @Autowired
    private TopicRepository topicRepository;

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


    @Override
    public List<Conference> getAllConferences() {
        return conferenceRepository.findAll();
    }

    @Override
    public Conference findById(Long id) {
        Optional<Long> optionalId = Optional.ofNullable(id);

        return conferenceRepository.findById(
                optionalId.orElseThrow(() -> {
                    LOGGER.error("ConfId is null");
                    return new NullPointerException("Conf id is null");
                }))
                .orElseThrow(() -> {
                    LOGGER.error("Conference with id {} not found", id);
                    return new NoSuchConferenceException(String.format("Conference with id %d not found", id));
                });
    }

    @Override
    public ListConferenceDTO getListConferencesDTO() {
        return new ListConferenceDTO(getAllConferencesDTO());
    }

    @Override
    public ListConferenceDTO getAllConferenceDTOForCurrentModerator(CurrentUser currentUser) {
        List<ConferenceDTO> list = new ArrayList<>();
        User user = userRepository.findById(currentUser.getId()).get();
        for (Participants participants : user.getParticipants()) {
            if (participants.getParticipantsKey().getParticipantType().getName().equals("moder")) {
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
    public ConferenceDTO getConferenceDTOById(Long id) {
        return mapper.toDto(conferenceRepository.findById(id).get());
    }

    @Override
    public ListConferenceDTO getConferencesDTOByOwnerId(Long id) {
        List<Conference> conferenceList = conferenceRepository.findAllByOwnerId(id);
        List<ConferenceDTO> dtos = conferenceList.stream().map(c -> simpleMapper.toDto(c)).collect(Collectors.toList());
        return new ListConferenceDTO(dtos);
    }

    @Transactional
    @Override
    public void addNewConference(ConferenceDTO conferenceDTO, MultipartFile photo) throws IOException {
        Conference conference = mapper.toEntity(conferenceDTO);
        String oldCoverPhotoPath = null;
        if (!photo.isEmpty()) {
            String newCoverPhotoPath = imageStorageService.saveAndReturnImageLink(photo);
            oldCoverPhotoPath = conference.getCoverPhoto();
            conference.setCoverPhoto(newCoverPhotoPath);
        }
        conferenceRepository.save(conference);
        if (oldCoverPhotoPath != null) {
            removeCoverPhotoIfTransactionSuccess(oldCoverPhotoPath);
        }
    }

    @Transactional
    @Override
    public void updateConference(ConferenceDTO conferenceDTO, MultipartFile photo) throws IOException {
        Conference conference = mapper.toEntity(conferenceDTO);
        String oldCoverPhotoPath = null;
        if (!photo.isEmpty()) {
            String newCoverPhotoPath = imageStorageService.saveAndReturnImageLink(photo);
            oldCoverPhotoPath = conference.getCoverPhoto();
            conference.setCoverPhoto(newCoverPhotoPath);
        }
        conferenceRepository.save(conference);
        if (oldCoverPhotoPath != null) {
            removeCoverPhotoIfTransactionSuccess(oldCoverPhotoPath);
        }

    }

    @Override
    public void deleteConference(Long confId) {
        Optional<Conference> conferenceOptional = conferenceRepository.findById(confId);
        conferenceRepository.delete(conferenceOptional.get());
    }

    private void removeCoverPhotoIfTransactionSuccess(final String oldCoverPhoto) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                imageStorageService.removeOldImage(oldCoverPhoto);
            }
        });
    }

    @Override
    public List<ConferenceDTO> getAllConferencesDTO() {
        List<ConferenceDTO> list = new ArrayList<>();
        for (Conference conference : conferenceRepository.findAll()) {
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
    public boolean isCurrentUserPresentAtConference(Long userId, Long confId) {
        Conference conference = conferenceRepository.findById(confId).get();
        for (Participants participants : conference.getParticipants()) {
            if (participants.getParticipantsKey().getUser().getUserId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean registerCurrentUserForConference(Long confId, Long userId) {
        Conference conference = conferenceRepository.findById(confId).get();
        for (Participants participants : conference.getParticipants()) {
            if (participants.getParticipantsKey().getUser().getUserId().equals(userId)) {
                return false;
            }
        }
        UserDTO user = userService.findById(userId);
        participantService.addParticipant(user, conference);
        emailService.sendMessage(user.getEmail(), "Registration on Conference: " + conference.getName(), "You have been successfully registered on Conference: " + conference.getName());
        return true;
    }

    @Override
    public Long getConfIdByTopicId(Long topicId) {
        Topic topic = topicRepository.findById(topicId).get();
        Conference conference = conferenceRepository.findById(topic.getStream().getConference().getConferenceId()).get();
        return conference.getConferenceId();
    }

}
