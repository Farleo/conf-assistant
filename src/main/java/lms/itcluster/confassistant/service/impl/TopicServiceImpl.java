package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.annotation.TopicDataFieldGroup;
import lms.itcluster.confassistant.annotation.TopicDataInfo;
import lms.itcluster.confassistant.dto.EditTopicDTO;
import lms.itcluster.confassistant.dto.TopicDTO;
import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.exception.TopicNotFoundException;
import lms.itcluster.confassistant.mapper.Mapper;
import lms.itcluster.confassistant.repository.TopicRepository;
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
        Topic topicDb = findTopicById(editTopicDTO.getTopicId());
        Topic updatedData = editTopicMapper.toEntity(editTopicDTO);

        String oldCoverPhotoPath = topicDb.getCoverPhoto();

        if (!photo.isEmpty()) {
            String newCoverPhotoPath = imageStorageService.saveAndReturnImageLink(photo);
            topicDb.setCoverPhoto(newCoverPhotoPath);
        }

        updateTopicData(topicDb, updatedData, TopicDataFieldGroup.class);

        topicRepository.save(topicDb);
        removeCoverPhotoIfTransactionSuccess(oldCoverPhotoPath);
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
    public void updateTopicInfo(EditTopicDTO editTopicDTO) throws TopicNotFoundException {
        Topic topic = findTopicById(editTopicDTO.getTopicId());
        Topic updatedData = editTopicMapper.toEntity(editTopicDTO);

        updateTopicData(topic, updatedData, TopicDataInfo.class);
        topicRepository.save(topic);
    }

    private <T extends Annotation> void updateTopicData (Topic topicDb, Topic updateData, Class<T> annotationClass) {
        DataUtil.copyFields(updateData, topicDb, annotationClass);
    }

}
