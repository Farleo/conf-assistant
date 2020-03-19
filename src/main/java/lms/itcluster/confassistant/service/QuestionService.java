package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.dto.QuestionDTO;
import lms.itcluster.confassistant.entity.Question;
import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.entity.User;

public interface QuestionService {
    Question save(QuestionDTO questionDTO, User user, Topic currentTopic);

    Question findByName(String name);

    Question findById(long id);


}
