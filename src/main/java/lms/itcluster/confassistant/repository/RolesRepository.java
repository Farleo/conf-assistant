package lms.itcluster.confassistant.repository;

import lms.itcluster.confassistant.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {

    Roles findByRole(String role);
}
