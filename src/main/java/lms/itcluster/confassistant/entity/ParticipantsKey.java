package lms.itcluster.confassistant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ParticipantsKey implements Serializable {

    @JoinColumn(name = "conference_id")
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonIgnore
    private Conference conference;

    @JoinColumn(name = "user_id")
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonIgnore
    private User user;

    @JoinColumn(name = "type_id")
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonIgnore
    private ParticipantType participantType;

    public ParticipantsKey() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantsKey that = (ParticipantsKey) o;
        return Objects.equals(conference, that.conference) &&
                Objects.equals(user, that.user) &&
                Objects.equals(participantType, that.participantType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conference, user, participantType);
    }

    @Override
    public String toString() {
        return "ParticipantsKey{" +
                "conference=" + conference.getConferenceId() +
                ", user=" + user.getUserId() +
                ", participantType=" + participantType.getTypeId() +
                '}';
    }
}
