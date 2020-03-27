package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.dto.QuestionDTO;
import lms.itcluster.confassistant.entity.Question;
import java.util.List;

public interface QuestionService {

    QuestionDTO save(QuestionDTO questionDTO);

    Question findByName(String name);

    Question findById(long id);

    List<QuestionDTO> getSortedQuestionDTOList(Long id);
}
