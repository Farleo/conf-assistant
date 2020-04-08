package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.dto.RoleDTO;
import lms.itcluster.confassistant.dto.SpeakerDTO;
import lms.itcluster.confassistant.dto.UserDTO;
import lms.itcluster.confassistant.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface UserService {

    UserDTO findById(long id);

    User findByEmail(String email);

    UserDTO createNewUserAsGuest(UserDTO userForm);
    
    void deleteUser(long id);

    List<UserDTO> getAllUsers();

    void updateUser(UserDTO userDTO);

    void completeGuestRegistration (UserDTO userForm);

    UserDTO getUserDTOById(long id);

    void addNewUserByAdmin(UserDTO userDTO);

    SpeakerDTO getSpeakerById(Long id);

    void updateSpeaker(SpeakerDTO speakerDTO, MultipartFile photo) throws IOException;
}
