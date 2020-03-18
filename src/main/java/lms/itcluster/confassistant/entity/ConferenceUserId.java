package lms.itcluster.confassistant.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ConferenceUserId implements Serializable {



	@JoinColumn(name = "conference_id")
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
	private Conference conference;
	
	@JoinColumn(name = "user_id")
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
	private User user;

public Conference getConference() {
	return conference;
}

public void setConference(Conference conference) {
	this.conference = conference;
}

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

public ParticipantType getParticipantType() {
	return participantType;
}

public void setParticipantType(ParticipantType participantType) {
	this.participantType = participantType;
}

@JoinColumn(name = "type_id")
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
	private ParticipantType participantType;
	
	
}
