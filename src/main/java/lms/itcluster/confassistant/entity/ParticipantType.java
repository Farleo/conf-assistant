package lms.itcluster.confassistant.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "participantType")
public class ParticipantType {
@Id
@GeneratedValue(strategy= GenerationType.IDENTITY)
@Column(name = "type_id", unique = true, nullable = false)
private long type_Id;

//Visitor Speaker Moderator
@Column(name = "name", nullable = false, unique = true)
private String name;

@OneToMany(mappedBy = "conferenceUserId.participantType", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
private List<Participants> participants;
}
