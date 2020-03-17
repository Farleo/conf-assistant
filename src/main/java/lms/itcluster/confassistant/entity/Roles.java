package lms.itcluster.confassistant.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class Roles {
@Id
@GeneratedValue(strategy= GenerationType.IDENTITY)
@Column(name = "role_id", unique = true, nullable = false)
private long roleId;

@Column(name = "name", nullable = false, unique = true)
private String name;

@Column(name = "description", nullable = false)
private String description;

@ManyToMany(mappedBy = "roles")
private List<User> users = new ArrayList<>();

public long getRoleId() {
	return roleId;
}

public void setRoleId(long roleId) {
	this.roleId = roleId;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public List<User> getUsers() {
	return users;
}

public void setUsers(List<User> users) {
	this.users = users;
}
}
