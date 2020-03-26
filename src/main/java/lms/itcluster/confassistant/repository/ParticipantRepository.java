package lms.itcluster.confassistant.repository;

import lms.itcluster.confassistant.entity.Participants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participants, Long> {
}
