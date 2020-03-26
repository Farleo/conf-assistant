package lms.itcluster.confassistant.repository;

import lms.itcluster.confassistant.entity.ParticipantType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantsTypeRepository extends CrudRepository<ParticipantType, Long> {
	ParticipantType findByName (String name);
}
