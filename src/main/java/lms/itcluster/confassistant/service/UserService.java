package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.dto.RoleDTO;
import lms.itcluster.confassistant.dto.UserDTO;
import lms.itcluster.confassistant.entity.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    UserDTO findById(long id);

    User findByEmail(String email);

    User createNewUserAsGuest(UserDTO userForm);
    
    void deleteUser(long id);

    List<UserDTO> getAllUsers();

    void updateUser(UserDTO userDTO);

    void completeGuestRegistration (UserDTO userForm);

    UserDTO getUserDTOById(long id);

    void addNewUserByAdmin(String email, String password, String firstName, String lastName, Set<String> roles);
}
