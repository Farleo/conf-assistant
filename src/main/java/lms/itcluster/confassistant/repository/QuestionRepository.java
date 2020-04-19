package lms.itcluster.confassistant.repository;

import lms.itcluster.confassistant.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    Question findByQuestion(String question);

    Question findBySelected(boolean selected);
}
