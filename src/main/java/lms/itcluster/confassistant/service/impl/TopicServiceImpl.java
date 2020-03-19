package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.entity.Question;
import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.repository.TopicRepository;
import lms.itcluster.confassistant.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public Topic findByName(String name) {
        return topicRepository.findByName(name);
    }

    @Override
    public Topic findById(long id) {
        return topicRepository.findById(id).get();
    }

    @Override
    public List<Question> getSortedQuestionList(Topic topic) {
        topic.getQuestionList().sort((o1, o2) -> o2.getRating() - o1.getRating());
        return topic.getQuestionList();
    }
}
