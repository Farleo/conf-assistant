package lms.itcluster.confassistant.repository;

import lms.itcluster.confassistant.entity.Roles;
import lms.itcluster.confassistant.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);

    List<User> findAllByRoles(Roles roles);
}
