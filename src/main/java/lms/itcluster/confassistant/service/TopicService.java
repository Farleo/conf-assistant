package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.dto.QuestionDTO;
import lms.itcluster.confassistant.dto.TopicDTO;
import lms.itcluster.confassistant.entity.Question;
import lms.itcluster.confassistant.entity.Topic;

import java.util.List;

public interface TopicService {

    Topic findByName (String id);

    Topic findById (Long id);

    TopicDTO getTopicDTOBuId(Long id);
}
