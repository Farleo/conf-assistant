package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.dto.EditTopicDTO;
import lms.itcluster.confassistant.dto.SimpleTopicDTO;
import lms.itcluster.confassistant.dto.TopicDTO;
import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.exception.NoSuchTopicException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TopicService {

    Topic findById(Long id);

    Topic findByName (String id);

    TopicDTO getTopicDTOById(Long id);

    EditTopicDTO getEditTopicDTOById(Long id);
    
    SimpleTopicDTO getSimpleTopicDTOById(Long id);

    void updateMainTopicData(EditTopicDTO editTopicDTO, MultipartFile photo) throws IOException;

    void updateTopicInfo(TopicDTO topicDTO);

    List<TopicDTO> getAllTopicForCurrentSpeaker(Long userId);

    boolean enableOrDisableQuestion(Long topicID);
    
    List<TopicDTO> findAllTopicByStreamId(Long streamId);

    void deleteTopic(Long topicId);

    void updateTopic(SimpleTopicDTO simpleTopicDTO, MultipartFile photo) throws IOException;

    void createTopic(SimpleTopicDTO simpleTopicDTO, MultipartFile photo) throws IOException;
}
