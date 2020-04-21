package lms.itcluster.confassistant.repository;

import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Topic findByName(String name);

    List<Topic> findAllBySpeaker(User speaker);

    @Transactional
    @Query("select t from Topic t where t.stream.streamId=:streamId")
    List<Topic> findAllByStreamId(@Param("streamId")Long streamId);

    List<Topic> findAllByIsAllowedQuestion(boolean allowedQuestion);
}
