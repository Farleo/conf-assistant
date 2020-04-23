package lms.itcluster.confassistant.component;

import lms.itcluster.confassistant.entity.Stream;
import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.model.CurrentUser;

public interface CheckEditAccess {
    boolean canCurrentUserEditTopic(CurrentUser currentUser, Topic topic);

    boolean canManageQuestion(CurrentUser currentUser, Topic topic);

    boolean isCurrentUserPresentAtConference(Long userId, Long confId);

    boolean isActiveTopic(Topic topic);

    boolean isBeginTopic(Topic topic);
}
