package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.entity.User;

import java.util.List;

public interface UserService {

    User findById(long id);

    User findByEmail(String email);

    List<Conference> getAllConferences();
}
