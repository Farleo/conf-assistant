package lms.itcluster.confassistant.repository;

import lms.itcluster.confassistant.entity.Topic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends CrudRepository<Topic, Long> {

    Topic findByName(String name);
}
