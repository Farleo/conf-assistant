package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.dto.QuestionDTO;
import lms.itcluster.confassistant.entity.Question;
import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.repository.QuestionRepository;
import lms.itcluster.confassistant.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Question save(QuestionDTO questionDTO, User user, Topic currentTopic) {
        Question question = new Question();
        question.setQuestion(questionDTO.getQuestion());
        question.setTopic(currentTopic);
        question.setUser(user);
        return questionRepository.save(question);
    }

    @Override
    public Question findByName(String name) {
        return questionRepository.findByQuestion(name);
    }

    @Override
    public Question findById(long id) {
        return questionRepository.findById(id).get();
    }
}
