package lms.itcluster.confassistant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "roles_id", unique = true, nullable = false)
    private long rolesId;

    @Column(name = "role", nullable = false, length = 15, unique = true)
    private String role;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<User> userSet;

    public Roles(String role, String description, Set<User> userSet) {
        this.role = role;
        this.description = description;
        this.userSet = userSet;
    }

    public Roles() {
    }

    public long getRolesId() {
        return rolesId;
    }

    public void setRolesId(long rolesId) {
        this.rolesId = rolesId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Roles roles = (Roles) o;
        return rolesId == roles.rolesId &&
                Objects.equals(role, roles.role) &&
                Objects.equals(description, roles.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rolesId, role, description);
    }

    @Override
    public String toString() {
        return "Roles{" +
                "rolesId=" + rolesId +
                ", role='" + role + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
