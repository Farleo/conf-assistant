package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.component.CheckDataAccess;
import lms.itcluster.confassistant.dto.EditTopicDTO;
import lms.itcluster.confassistant.dto.SimpleTopicDTO;
import lms.itcluster.confassistant.dto.TopicDTO;
import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.exception.NoSuchEntityException;
import lms.itcluster.confassistant.mapper.Mapper;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.repository.TopicRepository;
import lms.itcluster.confassistant.repository.UserRepository;
import lms.itcluster.confassistant.service.ImageStorageService;
import lms.itcluster.confassistant.service.TopicService;
import lms.itcluster.confassistant.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageStorageService imageStorageService;

    @Autowired
    private CheckDataAccess checkEditAccess;

    @Autowired
    @Qualifier("topicMapper")
    private Mapper<Topic, TopicDTO> mapper;

    @Autowired
    @Qualifier("simpleTopicMapper")
    private Mapper<Topic, SimpleTopicDTO> simpleTopicMapper;

    @Autowired
    @Qualifier("editTopicMapper")
    private Mapper<Topic, EditTopicDTO> editTopicMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Topic findById(Long id) {
        return topicRepository.findById(id).orElseThrow(() -> new NoSuchEntityException("Topic with id - %d not found." + id));
    }

    @Override
    public Topic findByName(String name) {
        return topicRepository.findByName(name);
    }

    @Override
    public TopicDTO getTopicDTOById(Long id, CurrentUser currentUser) {
        return mapper.toDto(findById(id));
    }

    @Override
    public TopicDTO getTopicDTOWithQuestionManageAccess(Long id, CurrentUser currentUser) {
        Topic topic = findById(id);
        checkEditAccess.canCurrentUserManageTopic(currentUser, topic);
        return mapper.toDto(findById(id));
    }

    @Override
    public EditTopicDTO getEditTopicDTOById(Long id, CurrentUser currentUser) {
        Topic topic = findById(id);
        checkEditAccess.canCurrentUserEditTopic(currentUser, topic);
        return editTopicMapper.toDto(topic);
    }

    @Override
    public SimpleTopicDTO getSimpleTopicDTOById(Long id) {
        return simpleTopicMapper.toDto(findById(id));
    }

    @Override
    @Transactional
    public void updateMainTopicData(EditTopicDTO editTopicDTO, byte[] photo, String originalFileName) throws IOException {
        Topic updatedData = editTopicMapper.toEntity(editTopicDTO);

        Optional<String> newCoverPhoto = imageStorageService.saveAndReturnImageLink(photo, originalFileName);
        updatedData.setCoverPhoto(newCoverPhoto.orElse(updatedData.getCoverPhoto()));

        topicRepository.save(updatedData);
    }

    @Override
    public boolean enableOrDisableQuestion(Long topicId, CurrentUser currentUser) {
        Topic topic = findById(topicId);
        checkEditAccess.canCurrentUserManageTopic(currentUser, topic);
        boolean result;
        if (topic.isAllowedQuestion()) {
            topic.setAllowedQuestion(false);
            result = false;
        } else {
            topic.setAllowedQuestion(true);
            result = true;
        }
        topicRepository.save(topic);
        return result;
    }

    @Override
    public List<TopicDTO> findAllTopicByStreamId(Long streamId) {
        List<Topic> topicList = topicRepository.findAllByStreamId(streamId);
        return topicList.stream().map(t -> mapper.toDto(t)).collect(Collectors.toList());
    }

    @Override
    public void deleteTopic(Long topicId) {
        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
        if (optionalTopic.isPresent()) {
            Topic topic = optionalTopic.get();
            topicRepository.delete(topic);
        }
    }


    @Override
    @Transactional
    public void updateTopic(SimpleTopicDTO simpleTopicDTO, byte[] photo, String originalPhotoName) throws IOException {
        Topic topic = simpleTopicMapper.toEntity(simpleTopicDTO);

        Optional<String> newCoverPhoto = imageStorageService.saveAndReturnImageLink(photo, originalPhotoName);
        topic.setCoverPhoto(newCoverPhoto.orElse(topic.getCoverPhoto()));

        topic.setSpeaker(userRepository.findById(simpleTopicDTO.getSpeakerId()).get());
        topicRepository.save(topic);
    }

    @Override
    @Transactional
    public void createTopic(SimpleTopicDTO simpleTopicDTO, byte[] photo, String originalPhotoName) throws IOException {
        Topic topic = simpleTopicMapper.toEntity(simpleTopicDTO);

        Optional<String> newCoverPhotoPath = imageStorageService.saveAndReturnImageLink(photo, originalPhotoName);
        topic.setCoverPhoto(newCoverPhotoPath.orElse(null));

        topic.setSpeaker(userRepository.findById(simpleTopicDTO.getSpeakerId()).get());
        topicRepository.save(topic);
    }

    @Override
    public boolean isQuestionAllowed(Long topicId) {
        return findById(topicId).isAllowedQuestion();
    }

    @Scheduled(cron = "0 59 23 * * *")
    @Transactional
    public void removeDisableAllowedQuestion() {
        List<Topic> topics = topicRepository.findAllByIsAllowedQuestion(true);
        for (Topic topic : topics) {
            topic.setAllowedQuestion(false);
        }
        topicRepository.saveAll(topics);
    }
}
