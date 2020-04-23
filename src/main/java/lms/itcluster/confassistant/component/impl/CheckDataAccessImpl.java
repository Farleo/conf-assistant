package lms.itcluster.confassistant.component.impl;

import lms.itcluster.confassistant.component.CheckDataAccess;
import lms.itcluster.confassistant.entity.*;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.service.ConferenceService;
import lms.itcluster.confassistant.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CheckDataAccessImpl implements CheckDataAccess {

    @Autowired
    private ConferenceService conferenceService;

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

    @Override
    public boolean isCurrentUserPresentAtConference(Long userId, Long confId) {
        Conference conference = conferenceService.findById(confId);
        for (Participants participants : conference.getParticipants()) {
            if (participants.getParticipantsKey().getUser().getUserId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isActiveTopic(Topic topic) {
        return LocalDateTime.now().isBefore(LocalDateTime.of(topic.getDate(), topic.getFinishTime()))
                && LocalDateTime.now().isAfter(LocalDateTime.of(topic.getDate(), topic.getBeginTime()));
    }

    @Override
    public boolean isBeginTopic(Topic topic) {
        return LocalDateTime.now().isBefore(LocalDateTime.of(topic.getDate(), topic.getBeginTime()));
    }


}
