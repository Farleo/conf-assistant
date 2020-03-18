package lms.itcluster.confassistant.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
//+
@Entity
@Table(name = "conference")
public class Conference {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "conference_id", unique = true, nullable = false)
    private long conferenceId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "alias", nullable = false, unique = true)
    private String alias;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "conferenceUserId.conference", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Participants> participants;

    @OneToMany(mappedBy = "conference")
    private List<Stream> streamList;

    public Conference() {
        super();
    }

public long getConferenceId() {
    return conferenceId;
}

public void setConferenceId(long conferenceId) {
    this.conferenceId = conferenceId;
}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public String getAlias() {
    return alias;
}

public void setAlias(String alias) {
    this.alias = alias;
}

public String getDescription() {
    return description;
}

public void setDescription(String description) {
    this.description = description;
}

public List<Participants> getParticipants() {
    return participants;
}

public void setParticipants(List<Participants> participants) {
    this.participants = participants;
}

public List<Stream> getStreamList() {
    return streamList;
}

public void setStreamList(List<Stream> streamList) {
    this.streamList = streamList;
}
}
