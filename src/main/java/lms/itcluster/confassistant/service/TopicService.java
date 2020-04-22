package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.dto.EditTopicDTO;
import lms.itcluster.confassistant.dto.SimpleTopicDTO;
import lms.itcluster.confassistant.dto.TopicDTO;
import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.model.CurrentUser;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TopicService {

    Topic findById(Long id);

    Topic findByName (String id);

    TopicDTO getTopicDTOById(Long id, CurrentUser currentUser);

    TopicDTO getTopicDTOWithQuestionManageAccess(Long id, CurrentUser currentUser);

    EditTopicDTO getEditTopicDTOById(Long id, CurrentUser currentUser);
    
    SimpleTopicDTO getSimpleTopicDTOById(Long id);

    void updateMainTopicData(EditTopicDTO editTopicDTO, MultipartFile photo) throws IOException;

    boolean enableOrDisableQuestion(Long topicID);
    
    List<TopicDTO> findAllTopicByStreamId(Long streamId);

    void deleteTopic(Long topicId);

    void updateTopic(SimpleTopicDTO simpleTopicDTO, MultipartFile photo) throws IOException;

    void createTopic(SimpleTopicDTO simpleTopicDTO, MultipartFile photo) throws IOException;
}
