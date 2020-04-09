package lms.itcluster.confassistant.repository;

import lms.itcluster.confassistant.entity.Participants;
import lms.itcluster.confassistant.entity.ParticipantsKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participants, ParticipantsKey> {
	
	@Transactional
	@Query("select p from Participants p where p.participantsKey.user.userId=:userId and p.participantsKey.conference.conferenceId=:confId")
	List<Participants> findByUserIdAndConfId(@Param("userId") Long userId, @Param("confId") Long confId);
	
	@Transactional
	@Modifying
	@Query("delete from Participants p where p.participantsKey.user.userId=:userId and p.participantsKey.conference.conferenceId=:confId")
	void deleteAllByUserIdAndConfId(@Param("userId") Long userId, @Param("confId") Long confId);

	@Transactional
	@Query("select p.participantsKey.conference.conferenceId from Participants p where p.participantsKey.user.userId=:userId and p.participantsKey.participantType.name=:typeName")
	List<Long> findByUserIdAndTypeName(@Param("userId") Long userId, @Param("typeName") String typeName);

	@Transactional
	@Query("select p from Participants p where p.participantsKey.conference.conferenceId=:confId and p.participantsKey.participantType.name=:typeName")
	List<Participants> findAllUserByConfIdAndTypeName(@Param("confId") Long confId, @Param("typeName") String typeName);
}
