package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.dto.UserDTO;
import lms.itcluster.confassistant.entity.User;

import java.util.List;

public interface UserService {

    User findById(long id);

    User findByEmail(String email);

    User createNewUserAsGuest(UserDTO userForm);
    
    User deleteUser(long id);

    List<User> getAllUsers();

    void updateUser(User user);

    User addNewUserByAdmin(User user);

    void completeGuestRegistration (UserDTO userForm);

    UserDTO getUserDTOById(long id);
}
