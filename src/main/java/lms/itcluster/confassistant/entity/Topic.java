package lms.itcluster.confassistant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "topic")
public class Topic {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "topic_id", unique = true, nullable = false)
    private long topicId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "begin_time", nullable = false)
    private Time beginTime;

    @Column(name = "finish_time", nullable = false)
    private Time finishTime;

    @Column(name = "info", nullable = false, length = 2000)
    private String info;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "cover_photo")
    private String coverPhoto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stream_id", nullable = false)
    private Stream stream;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "speaker_id", nullable = false)
    @JsonIgnore
    private User speaker;

    @OneToMany(mappedBy = "topic")
    private List<Question> questionList;

    public Topic() {
        super();
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public long getTopicId() {
        return topicId;
    }

    public void setTopicId(long topicId) {
        this.topicId = topicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Time beginTime) {
        this.beginTime = beginTime;
    }

    public Time getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Time finishTime) {
        this.finishTime = finishTime;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Stream getStream() {
        return stream;
    }

    public void setStream(Stream stream) {
        this.stream = stream;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public User getSpeaker() {
        return speaker;
    }

    public void setSpeaker(User speaker) {
        this.speaker = speaker;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic topic = (Topic) o;
        return topicId == topic.topicId &&
                isActive == topic.isActive &&
                Objects.equals(name, topic.name) &&
                Objects.equals(info, topic.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topicId, name, info, isActive);
    }

    @Override
    public String toString() {
        return "Topic{" +
                "topicId=" + topicId +
                ", name='" + name + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
