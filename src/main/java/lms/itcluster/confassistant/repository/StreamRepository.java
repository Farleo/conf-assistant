package lms.itcluster.confassistant.repository;

import lms.itcluster.confassistant.entity.Stream;
import lms.itcluster.confassistant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StreamRepository extends JpaRepository<Stream, Long> {

    Stream findByName(String name);

    List<Stream> findAllByModerator_UserIdAndConference_ConferenceId(Long userId, Long conferenceId);

    List<Stream> findAllByModerator(User moderator);

    @Transactional
    @Query("select s from Stream s where s.conference.conferenceId=:confId")
    List<Stream> findAllByConferenceId (@Param("confId") Long confId);
}
