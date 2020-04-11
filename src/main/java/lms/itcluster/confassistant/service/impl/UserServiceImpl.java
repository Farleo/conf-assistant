package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.dto.*;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.exception.UserAlreadyExistException;
import lms.itcluster.confassistant.mapper.Mapper;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.repository.UserRepository;
import lms.itcluster.confassistant.service.EmailService;
import lms.itcluster.confassistant.service.ImageStorageService;
import lms.itcluster.confassistant.service.UserService;
import lms.itcluster.confassistant.util.SecurityUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private ImageStorageService imageStorageService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("userLoginMapper")
    private Mapper<User, UserDTO> mapper;

    @Autowired
    @Qualifier("speakerMapper")
    private Mapper<User, SpeakerDTO> speakerMapper;

    @Autowired
    @Qualifier("signUpMapper")
    private Mapper<User, SignUpDTO> signUpMapper;

    @Autowired
    @Qualifier("editProfileMapper")
    private Mapper<User, EditProfileDTO> editProfileMapper;

    @Autowired
    @Qualifier("editContactMapper")
    private Mapper<User, EditContactsDTO> editContactsMapper;

    @Autowired
    private EmailService emailService;


    @Override
    public UserDTO findById(long id) {
        User user = userRepository.findById(id).get();
        UserDTO userDTO = mapper.toDto(user);
        return userDTO;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public void createNewUserAsGuest(SignUpDTO signUpDTO) {
        User user = signUpMapper.toEntity(signUpDTO);
        user = userRepository.save(user);
        authenticateUserIfTransactionSuccess(user);
        String link = "Please follow the link - https://conf-assistant.azurewebsites.net/active/" + user.getActiveCode();
        emailService.sendSimpleMessage(user.getEmail(), "Active Profile on Conference Assistant", link);
    }

    private void authenticateUserIfTransactionSuccess(final User user) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                SecurityUtil.authenticate(user);
            }
        });
    }

    @Override
    public void deleteUser(long id) {
        Optional<User> user = userRepository.findById(id);
        userRepository.delete(user.get());
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        Type listType = new TypeToken<List<UserDTO>>() {
        }.getType();
        ModelMapper modelMapper = new ModelMapper();
        List<UserDTO> userDTOS = modelMapper.map(users, listType);
        return userDTOS;
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        Optional<User> dbUser = Optional.of(userRepository.findById(userDTO.getUserId()).get());
        if (dbUser.isPresent()) {
            User realUser = mapper.toEntity(userDTO);
            User existingUserEmailFromDb = userRepository.findByEmail(realUser.getEmail());
            if (existingUserEmailFromDb != null && userDTO.getUserId() != existingUserEmailFromDb.getUserId()) {
                throw new UserAlreadyExistException("User with this email is already exist: " + realUser.getEmail());
            }
            userRepository.save(realUser);
        }
    }

    @Override
    public void completeGuestRegistration(EditProfileDTO editProfileDTO) {
        User user = editProfileMapper.toEntity(editProfileDTO);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user != null) {
            return new CurrentUser(user);
        } else {
            throw new UsernameNotFoundException("Profile not found by email " + username);
        }
    }

    @Override
    public EditProfileDTO getGuestProfileDTOById(long id) {
        return editProfileMapper.toDto(userRepository.findById(id).orElse(null));
    }

    @Override
    public void addNewUserByAdmin(UserDTO userDTO) {
        User existingUserFromDb = userRepository.findByEmail(userDTO.getEmail());
        if (existingUserFromDb != null) {
            throw new UserAlreadyExistException("User with this email is already exist: " + userDTO.getEmail());
        }
        User user = mapper.toEntity(userDTO);
        userRepository.save(user);
    }

    @Override
    public SpeakerDTO getSpeakerById(Long id) {
        return speakerMapper.toDto(userRepository.findById(id).get());
    }

    @Override
    @Transactional
    public void updateSpeaker(EditProfileDTO editProfileDTO, MultipartFile photo) throws IOException {
        User speaker = editProfileMapper.toEntity(editProfileDTO);

        String oldProfilePhotoPath = null;

        if (!photo.isEmpty()) {
            String newProfilePhotoPath = imageStorageService.saveAndReturnImageLink(photo);
            oldProfilePhotoPath = speaker.getPhoto();
            speaker.setPhoto(newProfilePhotoPath);
        }

        userRepository.save(speaker);
        if (oldProfilePhotoPath != null) {
            removeCoverPhotoIfTransactionSuccess(oldProfilePhotoPath);
        }
    }



    private void removeCoverPhotoIfTransactionSuccess(final String oldCoverPhoto) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                imageStorageService.removeOldImage(oldCoverPhoto);
            }
        });
    }

    @Override
    @Transactional
    public UserDTO findByActivationCode(String code) {
        User user = userRepository.findByActiveCode(code);
        if (user == null) {
            return null;
        }
        user.setActive(true);
        user.setActiveCode(null);
        userRepository.save(user);
        authenticateUserIfTransactionSuccess(user);
        return mapper.toDto(user);
    }

    @Override
    public boolean updateUserEmail(EditContactsDTO editContactsDTO) {
        User user = userRepository.findById(editContactsDTO.getId()).get();
        long n = Math.round(100000 + Math.random() * 900000);
        user.setActiveCode(String.valueOf(n));
        String link = "Please enter current number to change email address - " + user.getActiveCode();
        emailService.sendSimpleMessage(user.getEmail(), "Active Profile on Conference Assistant", link);
        userRepository.save(user);
        return true;
    }

}
