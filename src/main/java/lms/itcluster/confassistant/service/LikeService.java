package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.dto.UserDTO;
import lms.itcluster.confassistant.entity.Question;
import lms.itcluster.confassistant.entity.User;

public interface LikeService {
    boolean like (UserDTO userDTO, Question question);
}
