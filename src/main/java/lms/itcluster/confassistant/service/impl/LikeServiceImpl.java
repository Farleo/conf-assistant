package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.dto.QuestionDTO;
import lms.itcluster.confassistant.entity.Question;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.mapper.Mapper;
import lms.itcluster.confassistant.repository.QuestionRepository;
import lms.itcluster.confassistant.repository.UserRepository;
import lms.itcluster.confassistant.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public boolean like(Long questionId, Long userId) {
        Question question = questionRepository.findById(questionId).get();
        User user = userRepository.findById(userId).get();
        if (question.getLikesSet().contains(user)) {
            return unLike(user, question);
        }
        return addLike(user, question);
    }

    private boolean unLike(User user, Question question) {
        user.getLikes().remove(question);
        question.getLikesSet().remove(user);
        userRepository.save(user);
        questionRepository.save(question);
        return false;
    }

    private boolean addLike(User user, Question question) {
        user.getLikes().add(question);
        question.getLikesSet().add(user);
        userRepository.save(user);
        questionRepository.save(question);
        return true;
    }
}
