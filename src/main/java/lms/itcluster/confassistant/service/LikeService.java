package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.entity.Question;
import lms.itcluster.confassistant.entity.User;

public interface LikeService {
    boolean like (User user, Question question);
}
