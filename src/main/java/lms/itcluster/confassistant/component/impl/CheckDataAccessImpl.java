package lms.itcluster.confassistant.component.impl;

import lms.itcluster.confassistant.component.CheckDataAccess;
import lms.itcluster.confassistant.entity.*;
import lms.itcluster.confassistant.exception.ForbiddenAccessException;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.repository.ConferenceRepository;
import lms.itcluster.confassistant.repository.ParticipantRepository;
import lms.itcluster.confassistant.service.ConferenceService;
import lms.itcluster.confassistant.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class CheckDataAccessImpl implements CheckDataAccess {

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    ConferenceRepository conferenceRepository;

    @Autowired
    ParticipantRepository participantRepository;

    @Override
    public boolean canManageConference (CurrentUser currentUser, Long confId){
        boolean hasAccess = false;
        if(SecurityUtil.userHasAdminRole(currentUser)){
            hasAccess = true;
        }
        else if(SecurityUtil.userHasConfOwnerRole(currentUser)){
            Optional<Conference> conferenceOptional = conferenceRepository.findById(confId);
            if(conferenceOptional.isPresent()){
                Conference conference = conferenceOptional.get();
                if(conference.getOwner().getUserId()==currentUser.getId()){
                    hasAccess = true;
                }
            }
        }
        else if (SecurityUtil.userHasConfAdminRole(currentUser)){
            List<Long> list = participantRepository.findByUserIdAndTypeName(currentUser.getId(),"admin");
            if(list.contains(confId)){
                hasAccess = true;
            }
        }
        return hasAccess;
    }

    @Override
    public void canCurrentUserEditTopic(CurrentUser currentUser, Topic topic) {
        if (!userHasRightToEditTopic(currentUser, topic)) {
            throw new ForbiddenAccessException(String.format("Current user with id: %d, don't have edit access to topic with id: %d", currentUser.getId(), topic.getTopicId()));
        }
    }

    private boolean userHasRightToEditTopic(CurrentUser currentUser, Topic topic) {
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
    public void canCurrentUserManageTopic(CurrentUser currentUser, Topic topic) {
        if (!userHasRightToManageTopic(currentUser, topic)) {
            throw new ForbiddenAccessException(String.format("Current user with id: %d, can't manage the topic with id: %d", currentUser.getId(), topic.getTopicId()));
        }
    }

    @Override
    public void isUserWithActivationCodeIsCurrentUser(User user, CurrentUser currentUser) {
        if (!user.getUserId().equals(currentUser.getId())) {
            throw new ForbiddenAccessException(String.format("User with activation %s code isn't current user with id: %d", user.getActiveCode(), currentUser.getId()));
        }
    }

    private boolean userHasRightToManageTopic(CurrentUser currentUser, Topic topic) {
        if (SecurityUtil.userHasConfOwnerRole(currentUser)) {
            return topic.getStream().getConference().getOwner().getUserId().equals(currentUser.getId());
        }
        if (SecurityUtil.userHasConfModerRole(currentUser)) {
            return topic.getStream().getModerator().getUserId().equals(currentUser.getId());
        }
        return SecurityUtil.userHasAdminRole(currentUser);
    }

    @Override
    public void isCurrentUserRegisteredAtConference(CurrentUser currentUser, Long confId) {
        if (!userPresentAtConference(currentUser, confId)) {
            throw new ForbiddenAccessException(String.format("Current user with id: %d not registered for the conference with id: %d", currentUser.getId(), confId));
        }
    }

    @Override
    public void isAllowedQuestionAtTopic(Topic topic) {
        if (!topic.isAllowedQuestion()) {
            throw new ForbiddenAccessException(String.format("Questions of topic id: %d are not allowed now", topic.getTopicId()));
        }
    }

    @Override
    public boolean userPresentAtConference(CurrentUser currentUser, Long confId) {
        if (currentUser == null) {
            return false;
        }
        Conference conference = conferenceService.findById(confId);
        for (Participants participants : conference.getParticipants()) {
            if (participants.getParticipantsKey().getUser().getUserId().equals(currentUser.getId())) {
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
