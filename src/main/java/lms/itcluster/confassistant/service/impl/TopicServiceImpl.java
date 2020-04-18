package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.dto.EditTopicDTO;
import lms.itcluster.confassistant.dto.TopicDTO;
import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.exception.TopicNotFoundException;
import lms.itcluster.confassistant.mapper.Mapper;
import lms.itcluster.confassistant.repository.TopicRepository;
import lms.itcluster.confassistant.repository.UserRepository;
import lms.itcluster.confassistant.service.ImageStorageService;
import lms.itcluster.confassistant.service.TopicService;
import lms.itcluster.confassistant.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ImageStorageService imageStorageService;

    @Autowired
    @Qualifier("topicMapper")
    private Mapper<Topic, TopicDTO> mapper;

    @Autowired
    @Qualifier("editTopicMapper")
    private Mapper<Topic, EditTopicDTO> editTopicMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Topic findByName(String name) {
        return topicRepository.findByName(name);
    }

    @Override
    public Topic findById(Long id) throws TopicNotFoundException {
        return findTopicById(id);
    }

    @Override
    public TopicDTO getTopicDTOById(Long id) throws TopicNotFoundException {
        return mapper.toDto(findTopicById(id));
    }

    private Topic findTopicById (Long id) throws TopicNotFoundException {
        if (id != null) {
            return topicRepository.findById(id).orElseThrow(() -> new TopicNotFoundException("Topic not found: id = " + id));
        }
        else {
            throw new NullPointerException("Can't find Topic. TopicId is null");
        }
    }

    @Override
    @Transactional
    public void updateMainTopicData(EditTopicDTO editTopicDTO, MultipartFile photo) throws IOException, TopicNotFoundException {
        Topic updatedData = editTopicMapper.toEntity(editTopicDTO);

        String oldCoverPhotoPath = null;

        if (!photo.isEmpty()) {
            String newCoverPhotoPath = imageStorageService.saveAndReturnImageLink(photo);
            oldCoverPhotoPath = updatedData.getCoverPhoto();
            updatedData.setCoverPhoto(newCoverPhotoPath);
        }

        topicRepository.save(updatedData);
        if (oldCoverPhotoPath != null) {
            removeCoverPhotoIfTransactionSuccess(oldCoverPhotoPath);
        }
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
    @Transactional
    public void updateTopicInfo(TopicDTO topicDTO) throws TopicNotFoundException {
        Topic topic = findTopicById(topicDTO.getTopicId());
        topic.setInfo(topicDTO.getInfo());
        topicRepository.save(topic);
    }

    @Override
    public List<TopicDTO> getAllTopicForCurrentSpeaker(Long userId) {
        List<TopicDTO> list = new ArrayList<>();
        User user = userRepository.findById(userId).get();
        for (Topic topic : topicRepository.findAllBySpeaker(user)) {
            list.add(mapper.toDto(topic));
        }
        return list;
    }

    @Override
    public boolean enableOrDisableQuestion(Long topicID) {
        Topic topic = topicRepository.findById(topicID).get();
        boolean result;
        if (topic.isActive()) {
            topic.setActive(false);
            result = false;
        } else {
            topic.setActive(true);
            result = true;
        }
        topicRepository.save(topic);
        return result;
    }
}
