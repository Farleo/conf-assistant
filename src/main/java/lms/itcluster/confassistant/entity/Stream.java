package lms.itcluster.confassistant.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "stream")
public class Stream {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "stream_id", unique = true, nullable = false)
    private int streamId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conference_conference_id", nullable = false)
    private Conference conference;

    @OneToMany(mappedBy = "stream")
    private List<Topic> topicList;

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

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public List<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }
}
