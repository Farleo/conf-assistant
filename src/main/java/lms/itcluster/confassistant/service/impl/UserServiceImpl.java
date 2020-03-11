package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.entity.Guest;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.model.CurrentGuest;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.repository.GuestRepository;
import lms.itcluster.confassistant.repository.UserRepository;
import lms.itcluster.confassistant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GuestRepository guestRepository;


    @Override
    public User findById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public Guest findByEmail(String email) {
        return guestRepository.findByEmail(email);
    }

    @Override
    public List<Conference> getAllConferences() {
        List<User> usersList = userRepository.findAll();
        List<Conference> conferencesList = new ArrayList<>();
        for (User user : usersList) {
            conferencesList.addAll(user.getConferenceList());
        }
        conferencesList.sort(Comparator.comparing(Conference::getBeginDate));
        return conferencesList;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Guest guest = guestRepository.findByEmail(username);
        if (guest != null) {
            return new CurrentGuest(guest);
        } else {
            User user = userRepository.findByEmail(username);
            if (user != null) {
                return new CurrentUser(user);
            } else {
                throw new UsernameNotFoundException("Profile not found by email " + username);
            }
        }
    }
}
