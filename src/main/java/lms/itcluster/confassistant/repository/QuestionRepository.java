package lms.itcluster.confassistant.repository;

import lms.itcluster.confassistant.entity.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {

    Question findByQuestion(String question);
}
