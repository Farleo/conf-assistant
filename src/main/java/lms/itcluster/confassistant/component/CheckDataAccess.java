package lms.itcluster.confassistant.component;

import lms.itcluster.confassistant.entity.Stream;
import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.model.CurrentUser;

public interface CheckDataAccess {
    boolean canManageConference (CurrentUser currentUser, Long confId);

    void canCurrentUserEditTopic(CurrentUser currentUser, Topic topic);

    void canCurrentUserManageTopic(CurrentUser currentUser, Topic topic);

    void isUserWithActivationCodeIsCurrentUser(User user, CurrentUser currentUser);

    void isCurrentUserRegisteredAtConference(CurrentUser currentUser, Long confId);

    void isAllowedQuestionAtTopic(Topic topic);

    boolean userPresentAtConference(CurrentUser currentUser, Long confId);

    boolean isActiveTopic(Topic topic);

    boolean isBeginTopic(Topic topic);
}
