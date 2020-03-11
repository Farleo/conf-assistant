package lms.itcluster.confassistant.repository;

import lms.itcluster.confassistant.entity.Guest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends CrudRepository<Guest, Integer> {
    Guest findByEmail(String email);
}
