package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.entity.Roles;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.form.UserForm;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.repository.RolesRepository;
import lms.itcluster.confassistant.repository.UserRepository;
import lms.itcluster.confassistant.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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



@Override
public User findById(long id) {
    return userRepository.findById(id).get();
}

@Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User createNewUser(UserForm userForm) {
        User user = new User();
        user.setEmail(userForm.getEmail());
        //user.setPassword(userForm.getPassword());
        Roles guestRole = rolesRepository.findByRole("Guest");
        user.setRoles(Collections.singleton(guestRole));
        userRepository.save(user);
        return user;
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
    if (dbUser.isPresent() && user.getRoles()!=null){
        User realUser = dbUser.get();
        BeanUtils.copyProperties(user, realUser, "userId");
        userRepository.save(realUser);
    }
    
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
}
