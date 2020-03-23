package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.form.UserForm;

import java.util.List;

public interface UserService {

    User findById(long id);

    User findByEmail(String email);

    User createNewUserAsGuest(UserForm userForm);
    
    User deleteUser(long id);

    List<User> getAllUsers();

    void update(User user);

    User addNewUserByAdmin(User user) throws Exception;

    void completeGuestRegistration (UserForm userForm);
}
