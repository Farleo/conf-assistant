package lms.itcluster.confassistant.form;

import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.entity.User;

import java.io.Serializable;
import java.util.List;

public class ManageUsersForm implements Serializable {
private List<User> userList;

public ManageUsersForm (List<User> userList) {
	super();
	this.userList = userList;
}

public ManageUsersForm () {
}

public List<User> getUserList() {
	return userList;
}

public void setUserList(List<User> userList) {
	this.userList = userList;
}
}
