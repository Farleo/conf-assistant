package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.dto.EditTopicDTO;
import lms.itcluster.confassistant.dto.SimpleTopicDTO;
import lms.itcluster.confassistant.dto.TopicDTO;
import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.exception.TopicNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TopicService {

    Topic findByName (String id);

    Topic findById (Long id) throws TopicNotFoundException;

    TopicDTO getTopicDTOById(Long id) throws TopicNotFoundException;
    
    SimpleTopicDTO getSimpleTopicDTOById(Long id) throws TopicNotFoundException;

    void updateMainTopicData(EditTopicDTO editTopicDTO, MultipartFile photo) throws IOException, TopicNotFoundException;

    void updateTopicInfo(TopicDTO topicDTO) throws TopicNotFoundException;

    List<TopicDTO> getAllTopicForCurrentSpeaker(Long userId);

    boolean enableOrDisableQuestion(Long topicID);
    
    List<TopicDTO> findAllTopicByStreamId(Long streamId);

    void deleteTopic(Long topicId);

    void updateTopic(SimpleTopicDTO simpleTopicDTO, MultipartFile photo) throws IOException, TopicNotFoundException;

    void createTopic(SimpleTopicDTO simpleTopicDTO, MultipartFile photo) throws IOException, TopicNotFoundException;
}
