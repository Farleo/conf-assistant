package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.dto.*;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.model.CurrentUser;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    User findById(Long id);

    UserDTO getUserDtoById(Long id);

    void createNewUserAsGuest(SignUpDTO signUpDTO);
    
    void deleteUser(long id);
    
    List<UserDTO> getAllUsers();

    void updateUser(UserDTO userDTO, MultipartFile photo) throws IOException;

    void addNewUserByAdmin(UserDTO userDTO, MultipartFile photo) throws IOException;

    SpeakerDTO getSpeakerById(Long id);

    void updateSpeaker(EditProfileDTO editProfileDTO, MultipartFile photo) throws IOException;

    boolean createActivationCodeForConfirmEmail(EditContactsDTO editContactsDTO);

    UserDTO completeRegistration (String code, CurrentUser currentUser);

    UserDTO findByActivationCodeAndSaveIfValid(String code, CurrentUser currentUser);

    boolean updatePassword(EditPasswordDTO editPasswordDTO);

    EditProfileDTO getEditProfileDto (Long userId);
}
