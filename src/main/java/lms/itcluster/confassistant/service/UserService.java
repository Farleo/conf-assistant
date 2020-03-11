package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.entity.Guest;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.repository.UserRepository;

import java.util.List;

public interface UserService {

    User findById(int id);

    Guest findByEmail(String email);

    List<Conference> getAllConferences();
}
