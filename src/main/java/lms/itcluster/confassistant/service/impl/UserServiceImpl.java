package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.dto.UserDTO;
import lms.itcluster.confassistant.entity.Roles;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.exception.UserAlreadyExistException;
import lms.itcluster.confassistant.mapper.Mapper;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.repository.RolesRepository;
import lms.itcluster.confassistant.repository.UserRepository;
import lms.itcluster.confassistant.service.UserService;
import lms.itcluster.confassistant.util.SecurityUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    @Qualifier("userLoginMapper")
    private Mapper<User, UserDTO> mapper;


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
    public UserDTO createNewUserAsGuest(UserDTO userForm) {
        if (userRepository.findByEmail(userForm.getEmail()) != null) {
            throw new UserAlreadyExistException("User with this email is already exist: " + userForm.getEmail());
        }
        User user = mapper.toEntity(userForm);
        user.setRoles(Collections.singleton(rolesRepository.findByRole("User")));
        user = userRepository.save(user);
        SecurityUtil.authenticate(user);
        return mapper.toDto(user);
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
            BeanUtils.copyProperties(realUser, dbUser, "userId");
            User existingUserEmailFromDb = userRepository.findByEmail(realUser.getEmail());
            if (existingUserEmailFromDb != null && userDTO.getUserId() != existingUserEmailFromDb.getUserId()) {
                throw new UserAlreadyExistException("User with this email is already exist: " + realUser.getEmail());
            }
            userRepository.save(realUser);
        }
    }


    @Override
    public void completeGuestRegistration(UserDTO userForm) {
        User dbUser = userRepository.findById(userForm.getUserId()).get();
        User user = mapper.toEntity(userForm);
        BeanUtils.copyProperties(user, dbUser, "userId", "password", "email", "roles");
        userRepository.save(dbUser);
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
    public UserDTO getUserDTOById(long id) {
        return mapper.toDto(userRepository.findById(id).orElse(null));
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
}
