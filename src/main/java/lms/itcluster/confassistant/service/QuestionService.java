package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.dto.QuestionDTO;
import lms.itcluster.confassistant.entity.Question;
import lms.itcluster.confassistant.model.CurrentUser;

import java.util.List;

public interface QuestionService {

    boolean saveQuestion(QuestionDTO questionDTO, CurrentUser currentUser);

    Question findByName(String name);

    Question findById(long id);

    List<QuestionDTO> getSortedQuestionDTOListByRating(Long id);

    List<QuestionDTO> getSortedQuestionDTOListByDate(Long id);

    boolean selectNextQuestion(List<QuestionDTO> questionDTOList, Long questionId);

    boolean like(Long questionId, Long userId);

    boolean sendQuestionToSpeaker(Long topicId, CurrentUser currentUser);
}
