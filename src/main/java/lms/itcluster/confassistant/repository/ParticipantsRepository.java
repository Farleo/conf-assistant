package lms.itcluster.confassistant.repository;

import lms.itcluster.confassistant.entity.Participants;
import lms.itcluster.confassistant.entity.ParticipantsKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantsRepository extends CrudRepository<Participants, Long> {

}
