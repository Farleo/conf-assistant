package lms.itcluster.confassistant.entity;

import javax.persistence.*;

@Entity
@Table(name = "participants")
public class Participants {

@EmbeddedId
private ConferenceUserId id;


@ManyToOne(fetch = FetchType.LAZY)
@MapsId("conference_id")
private Conference conference;

@ManyToOne(fetch = FetchType.LAZY)
@MapsId("user_id")
private User user;

@ManyToOne(fetch = FetchType.LAZY)
@MapsId("type_id")
private ParticipantType participantType;



}
