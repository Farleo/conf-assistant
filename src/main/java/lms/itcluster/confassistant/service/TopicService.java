package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.entity.Question;
import lms.itcluster.confassistant.entity.Topic;

import java.util.List;

public interface TopicService {

    Topic findByName (String id);

    Topic findById (long id);

    List<Question> getSortedQuestionList(Topic topic);
}
