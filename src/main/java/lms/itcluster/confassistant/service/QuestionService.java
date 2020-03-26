package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.dto.QuestionDTO;
import lms.itcluster.confassistant.dto.UserDTO;
import lms.itcluster.confassistant.entity.Question;
import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.entity.User;

import java.util.List;

public interface QuestionService {
    Question save(QuestionDTO questionDTO, UserDTO userDTO, Topic currentTopic);

    Question findByName(String name);

    Question findById(long id);

    List<QuestionDTO> getSortedQuestionDTOList(Topic topic);
}
