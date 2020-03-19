package lms.itcluster.confassistant.repository;

import lms.itcluster.confassistant.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import java.util.List;
import java.util.Set;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {

    Roles findByRole(String role);
}
