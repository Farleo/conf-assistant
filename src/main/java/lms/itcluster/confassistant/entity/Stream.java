package lms.itcluster.confassistant.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
//+
@Entity
@Table(name = "stream")
public class Stream {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "stream_id", unique = true, nullable = false)
    private int streamId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "location")
    private String location;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conference_id", nullable = false)
    private Conference conference;

    @OneToMany(mappedBy = "stream")
    private List<Presentations> presentationsList;

    public Stream() {
        super();
    }

public int getStreamId() {
    return streamId;
}

public void setStreamId(int streamId) {
    this.streamId = streamId;
}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public String getLocation() {
    return location;
}

public void setLocation(String location) {
    this.location = location;
}

public Conference getConference() {
    return conference;
}

public void setConference(Conference conference) {
    this.conference = conference;
}

public List<Presentations> getPresentationsList() {
    return presentationsList;
}

public void setPresentationsList(List<Presentations> presentationsList) {
    this.presentationsList = presentationsList;
}
}
