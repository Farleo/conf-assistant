package lms.itcluster.confassistant.repository;

import lms.itcluster.confassistant.entity.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreamRepository extends JpaRepository<Stream, Long> {
    Stream findByName(String name);
}
