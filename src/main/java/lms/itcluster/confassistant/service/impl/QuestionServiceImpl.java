package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.dto.QuestionDTO;
import lms.itcluster.confassistant.entity.Question;
import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.mapper.Mapper;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.repository.QuestionRepository;
import lms.itcluster.confassistant.repository.TopicRepository;
import lms.itcluster.confassistant.repository.UserRepository;
import lms.itcluster.confassistant.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    @Qualifier("questionMapper")
    private Mapper<Question, QuestionDTO> mapper;

    @Override
    public QuestionDTO save(QuestionDTO questionDTO) {
        Question newQuestion = mapper.toEntity(questionDTO);
        return mapper.toDto(questionRepository.save(newQuestion));
    }

    @Override
    public Question findByName(String name) {
        return questionRepository.findByQuestion(name);
    }

    @Override
    public Question findById(long id) {
        return questionRepository.findById(id).get();
    }

    @Override
    public List<QuestionDTO> getSortedQuestionDTOList(Long id) {
        Topic topic = topicRepository.findById(id).get();
        List<QuestionDTO> dtoList = new ArrayList<>();
        topic.getQuestionList().forEach(question -> dtoList.add(mapper.toDto(question)));
        dtoList.sort((o1, o2) -> o2.getRating() - o1.getRating());
        return dtoList;
    }
}
