package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.entity.Roles;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.form.UserForm;

import java.util.List;
import java.util.Set;

public interface UserService {

    User findById(long id);

    User findByEmail(String email);

    User createNewUser(UserForm userForm);


}
