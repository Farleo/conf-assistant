package lms.itcluster.confassistant.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "stream")
public class Stream {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "stream_id", unique = true, nullable = false)
    private long streamId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conference_id", nullable = false)
    private Conference conference;

    @OneToMany(mappedBy = "stream")
    private List<Topic> topicList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_id", nullable = false)
    private User moderator;

    public Stream() {
        super();
    }

    public long getStreamId() {
        return streamId;
    }

    public void setStreamId(long streamId) {
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

    public User getModerator() {
        return moderator;
    }

    public void setModerator(User moderator) {
        this.moderator = moderator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stream stream = (Stream) o;
        return streamId == stream.streamId &&
                Objects.equals(name, stream.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streamId, name);
    }

    @Override
    public String toString() {
        return "Stream{" +
                "name='" + name + '\'' +
                '}';
    }
}
