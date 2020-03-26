package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.dto.QuestionDTO;
import lms.itcluster.confassistant.dto.UserDTO;
import lms.itcluster.confassistant.entity.Question;
import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.mapper.Mapper;
import lms.itcluster.confassistant.repository.QuestionRepository;
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
    @Qualifier("questionMapper")
    private Mapper<Question, QuestionDTO> mapper;

    @Autowired
    @Qualifier("userLoginMapper")
    private Mapper<User, UserDTO> userMapper;

    @Override
    public Question save(QuestionDTO questionDTO, UserDTO userDTO, Topic currentTopic) {
        Question question = new Question();
        question.setQuestion(questionDTO.getQuestion());
        question.setTopic(currentTopic);
        question.setUser(userMapper.toEntity(userDTO));
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

    @Override
    public List<QuestionDTO> getSortedQuestionDTOList(Topic topic) {
        List<QuestionDTO> dtoList = new ArrayList<>();
        topic.getQuestionList().forEach(question -> dtoList.add(mapper.toDto(question)));
        dtoList.sort((o1, o2) -> o2.getRating() - o1.getRating());
        return dtoList;
    }
}
