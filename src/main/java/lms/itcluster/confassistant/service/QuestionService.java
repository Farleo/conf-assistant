package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.dto.QuestionDTO;
import lms.itcluster.confassistant.entity.Question;
import lms.itcluster.confassistant.model.CurrentUser;

import java.util.List;

public interface QuestionService {

    boolean saveQuestion(QuestionDTO questionDTO, CurrentUser currentUser);

    Question findByName(String name);

    Question findById(long id);

    List<QuestionDTO> getSortedQuestionDTOListOrderBy(Long topicId, String orderBy, CurrentUser currentUser);

    boolean selectNextQuestion(List<QuestionDTO> questionDTOList, Long questionId);

    boolean like(Long questionId, CurrentUser currentUser);

    boolean sendQuestionToSpeaker(Long topicId, CurrentUser currentUser);


}
