package lms.itcluster.confassistant.form;

import lms.itcluster.confassistant.entity.Roles;
import lms.itcluster.confassistant.entity.User;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class EditUserForm implements Serializable {
private User user;
private List<Roles> roles;

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

public List<Roles> getRoles() {
	return roles;
}

public void setRoles(List<Roles> roles) {
	this.roles = roles;
}

public EditUserForm(User user, List<Roles> roles) {
	this.user = user;
	this.roles = roles;
}

public boolean hasRole(Roles role) {
	return this.user.getRoles().contains(role);
}

public void assignRole(Roles role) {
	Set<Roles> roles = this.user.getRoles();
	if (!roles.contains(role)) {
		roles.add(role);
		this.user.setRoles(roles);
	}
}
}