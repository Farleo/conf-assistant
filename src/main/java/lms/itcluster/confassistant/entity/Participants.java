package lms.itcluster.confassistant.entity;

import javax.persistence.*;

@Entity
@Table(name = "participants")
public class Participants {

@EmbeddedId
private ConferenceUserId conferenceUserId = new ConferenceUserId();

@Transient
public Conference getConference() {
	return conferenceUserId.getConference();
}

@Transient
public ParticipantType getParticipantType() {
	return conferenceUserId.getParticipantType();
}

@Transient
public User getUser() {
	return conferenceUserId.getUser();
}

}
