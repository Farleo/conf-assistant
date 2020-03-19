package lms.itcluster.confassistant.repository;

import lms.itcluster.confassistant.entity.ParticipantType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParicipantTypeRepository extends JpaRepository<ParticipantType, Long> {
}
