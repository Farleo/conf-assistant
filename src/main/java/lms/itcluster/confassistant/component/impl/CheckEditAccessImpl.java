package lms.itcluster.confassistant.component.impl;

import lms.itcluster.confassistant.component.CheckEditAccess;
import lms.itcluster.confassistant.entity.Stream;
import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.util.SecurityUtil;
import org.springframework.stereotype.Component;

@Component
public class CheckEditAccessImpl implements CheckEditAccess {
    @Override
    public boolean canCurrentUserEditTopic(CurrentUser currentUser, Topic topic) {
        if (SecurityUtil.userHasConfOwnerRole(currentUser)) {
            return topic.getStream().getConference().getOwner().getUserId().equals(currentUser.getId());
        }
        if (SecurityUtil.userHasConfModerRole(currentUser)) {
            return topic.getStream().getModerator().getUserId().equals(currentUser.getId());
        }
        if (SecurityUtil.userHasConfSpeakerRole(currentUser)) {
            return topic.getSpeaker().getUserId().equals(currentUser.getId());
        }
        return SecurityUtil.userHasAdminRole(currentUser);
    }

    @Override
    public boolean canManageQuestion(CurrentUser currentUser, Topic topic) {
        if (SecurityUtil.userHasConfOwnerRole(currentUser)) {
            return topic.getStream().getConference().getOwner().getUserId().equals(currentUser.getId());
        }
        if (SecurityUtil.userHasConfModerRole(currentUser)) {
            return topic.getStream().getModerator().getUserId().equals(currentUser.getId());
        }
        return SecurityUtil.userHasAdminRole(currentUser);
    }
}
