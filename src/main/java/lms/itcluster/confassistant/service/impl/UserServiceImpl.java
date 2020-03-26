package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.dto.UserDTO;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.exception.UserAlreadyExistException;
import lms.itcluster.confassistant.mapper.Mapper;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.repository.RolesRepository;
import lms.itcluster.confassistant.repository.UserRepository;
import lms.itcluster.confassistant.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public User findById(long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User createNewUserAsGuest(UserDTO userForm) {
        if (userRepository.findByEmail(userForm.getEmail()) != null) {
            throw new UserAlreadyExistException("User with this email is already exist: " + userForm.getEmail());
        }
        User user = mapper.toEntity(userForm);
        user.setRoles(Collections.singleton(rolesRepository.findByRole("Guest")));
        return userRepository.save(user);
    }

    @Override
    public User deleteUser(long id) {
        userRepository.delete(findById(id));
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void update(User user) {
        Optional<User> dbUser = userRepository.findById(user.getUserId());
        if (dbUser.isPresent()){
            User realUser = dbUser.get();
            BeanUtils.copyProperties(user, realUser, "userId");
            userRepository.save(realUser);
        }
    }

    @Override
    public User addNewUserByAdmin(User user) throws Exception{
        User existingUserFromDb = userRepository.findByEmail(user.getEmail());
        if(existingUserFromDb!=null){
            throw new Exception("User with this email is already registered: " + user.getEmail());
        }
        return userRepository.save(user);
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
}
