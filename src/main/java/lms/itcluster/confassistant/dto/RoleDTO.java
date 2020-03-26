package lms.itcluster.confassistant.dto;

import java.io.Serializable;
import java.util.Set;

public class RoleDTO implements Serializable {

private long rolesId;
private String role;
private String description;
private Set<UserDTO> userSet;

public RoleDTO() {
}

public RoleDTO(long rolesId, String role, String description, Set<UserDTO> userSet) {
	this.rolesId = rolesId;
	this.role = role;
	this.description = description;
	this.userSet = userSet;
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

public Set<UserDTO> getUserSet() {
	return userSet;
}

public void setUserSet(Set<UserDTO> userSet) {
	this.userSet = userSet;
}
}
