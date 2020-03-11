package lms.itcluster.confassistant.entity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity
@Table(name = "topic")
public class Topic {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "topic_id", unique = true, nullable = false)
    private int topicId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "begin_time", nullable = false)
    private Time beginTime;

    @Column(name = "finish_time", nullable = false)
    private Time finishTime;

    @Column(name = "info", nullable = false)
    private String info;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stream_stream_id", nullable = false)
    private Stream stream;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "speaker_speaker_id", nullable = false)
    private Speaker speaker;

    @OneToMany(mappedBy = "topic")
    @OrderBy("rating DESC")
    private List<Question> questionList;

    public Topic() {
        super();
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
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

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }
}
