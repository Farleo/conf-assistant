package lms.itcluster.confassistant.repository;

import lms.itcluster.confassistant.entity.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long> {
	@Transactional
	@Query("select c from Conference c where c.owner.userId=:ownerId and c.isDeleted=false ")
	List<Conference> findAllByOwnerId (@Param("ownerId") Long ownerId);

	Conference findByName (String name);

	Conference findByAlias(String alias);
}
