package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.dto.*;
import lms.itcluster.confassistant.entity.*;
import lms.itcluster.confassistant.mapper.Mapper;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.repository.ConferenceRepository;
import lms.itcluster.confassistant.repository.StreamRepository;
import lms.itcluster.confassistant.repository.TopicRepository;
import lms.itcluster.confassistant.repository.UserRepository;
import lms.itcluster.confassistant.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


    @Override
    public List<Conference> getAllConferences() {
        return conferenceRepository.findAll();
    }

    @Override
    public Conference findById(long id) {
        return conferenceRepository.findById(id).get();
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
    public Page<ScheduleConferenceDTO> getConferencesForSchedule(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<ScheduleConferenceDTO> conferenceDTOS = new ArrayList<>();
        List<Conference> conferences = conferenceRepository.findAll();
        List<Topic> topics = topicRepository.findAll();

        for (Conference conference : conferences) {
            Map<LocalDate, List<StreamDTO>> schedule = new HashMap<>();
            List<StreamDTO> streams = streamService.getAllStreamDtoByConference(conference);
            for (StreamDTO streamDTO : streams) {
                streamDTO.setTopicList(new ArrayList<>());
            }
            for (Topic topic : topics) {
                if (topic.getStream().getConference().getConferenceId().equals(conference.getConferenceId())) {
                    if (schedule.containsKey(topic.getDate())) {
                        List<StreamDTO> streamDTOS = schedule.get(topic.getDate());
                        for (StreamDTO streamDTO : streamDTOS) {
                            if (streamDTO.getStreamId().equals(topic.getStream().getStreamId())) {
                                TopicDTO topicDTO = new TopicDTO();
                                topicDTO.setDate(topic.getDate());
                                topicDTO.setActive(topic.isActive());
                                topicDTO.setTopicId(topic.getTopicId());
                                topicDTO.setName(topic.getName());
                                topicDTO.setBeginTime(topic.getBeginTime());
                                topicDTO.setFinishTime(topic.getFinishTime());
                                streamDTO.getTopicList().add(topicDTO);
                            }
                        }
                    } else {
                        List<StreamDTO> dtoList = new ArrayList<>();
                        for (StreamDTO dto : streams) {
                            StreamDTO streamDTO = new StreamDTO();
                            streamDTO.setModerator(dto.getModerator());
                            streamDTO.setConference(dto.getConference());
                            streamDTO.setName(dto.getName());
                            streamDTO.setStreamId(dto.getStreamId());
                            streamDTO.setTopicList(new ArrayList<>());
                            if (dto.getStreamId().equals(topic.getStream().getStreamId())) {
                                TopicDTO topicDTO = new TopicDTO();
                                topicDTO.setDate(topic.getDate());
                                topicDTO.setActive(topic.isActive());
                                topicDTO.setTopicId(topic.getTopicId());
                                topicDTO.setName(topic.getName());
                                topicDTO.setBeginTime(topic.getBeginTime());
                                topicDTO.setFinishTime(topic.getFinishTime());
                                streamDTO.getTopicList().add(topicDTO);
                            }
                            dtoList.add(streamDTO);
                        }
                        schedule.put(topic.getDate(), dtoList);
                    }
                }
            }
            ScheduleConferenceDTO scheduleConferenceDTO = new ScheduleConferenceDTO();
            scheduleConferenceDTO.setConferenceId(conference.getConferenceId());
            scheduleConferenceDTO.setName(conference.getName());
            scheduleConferenceDTO.setAlias(conference.getAlias());
            scheduleConferenceDTO.setVenue(conference.getVenue());
            scheduleConferenceDTO.setBeginDate(conference.getBeginDate());
            scheduleConferenceDTO.setFinishDate(conference.getFinishDate());
            scheduleConferenceDTO.setSchedule(schedule);
            conferenceDTOS.add(scheduleConferenceDTO);
        }
        completeTopic(conferenceDTOS);
        if (conferenceDTOS.size() < startItem) {
            conferenceDTOS = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, conferenceDTOS.size());
            conferenceDTOS = conferenceDTOS.subList(startItem, toIndex);
        }
        return new PageImpl<>(conferenceDTOS, PageRequest.of(currentPage, pageSize), conferences.size());
    }

    @Override
    public Long getConfIdByTopicId(Long topicId) {
        Topic topic = topicRepository.findById(topicId).get();
        Conference conference = conferenceRepository.findById(topic.getStream().getConference().getConferenceId()).get();
        return conference.getConferenceId();
    }



    private void completeTopic(List<ScheduleConferenceDTO> conferenceDTOS) {
        for (ScheduleConferenceDTO schedule : conferenceDTOS) {
            for (Map.Entry<LocalDate, List<StreamDTO>> entry : schedule.getSchedule().entrySet()) {
                for (StreamDTO streamDTO : entry.getValue()) {
                    List<TopicDTO> listTopic = streamDTO.getTopicList();
                    listTopic.sort(Comparator.comparing(TopicDTO::getBeginTime));
                    LocalTime startTime = LocalTime.of(8, 0, 0);
                    for (TopicDTO topicDTO : listTopic) {
                        LocalTime beginTime = topicDTO.getBeginTime();
                        LocalTime finishTime = topicDTO.getFinishTime();
                        double begin;
                        double finish;
                        begin = (MINUTES.between(startTime, beginTime)) / 5.0 * 0.5;
                        finish = (MINUTES.between(beginTime, finishTime)) / 5.0 * 0.5;
                        topicDTO.setBackdown(begin);
                        topicDTO.setBodySize(finish);
                        startTime = finishTime;
                    }
                }
            }
        }
    }

    @Override
    public boolean isCurrentUserPresentAtTopicConference(Long userId, Long topicId) {
        Topic topic = topicRepository.findById(topicId).get();
        Conference conference = topic.getStream().getConference();
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

}
