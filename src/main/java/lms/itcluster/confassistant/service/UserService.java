package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.dto.*;
import lms.itcluster.confassistant.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    UserDTO findById(long id);

    User findByEmail(String email);

    void createNewUserAsGuest(SignUpDTO signUpDTO);
    
    void deleteUser(long id);
    
    List<UserDTO> getAllUsers();

    void updateUser(UserDTO userDTO);

    void completeGuestRegistration (EditProfileDTO editProfileDTO);

    EditProfileDTO getGuestProfileDTOById(long id);

    void addNewUserByAdmin(UserDTO userDTO);

    SpeakerDTO getSpeakerById(Long id);

    void updateSpeaker(EditProfileDTO editProfileDTO, MultipartFile photo) throws IOException;

    UserDTO findByActivationCode(String code, Long currentUserId);

    boolean updateUserEmail(EditContactsDTO editContactsDTO);

    void updateEmail(UserDTO userDTO);

    UserDTO findByCode(String code, Long currentUserId);

    boolean updatePassword(EditPasswordDTO editPasswordDTO);
}
