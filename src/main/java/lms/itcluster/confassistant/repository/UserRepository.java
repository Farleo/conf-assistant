package lms.itcluster.confassistant.repository;

import lms.itcluster.confassistant.entity.Roles;
import lms.itcluster.confassistant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    
    List<User> findAllByRoles(Roles roles);
    
    List<User> findAll();

    User findByActiveCode(String activationCode);

    void deleteUserByIsActiveAndCreatedLessThan(Boolean active, LocalDate created);
}
