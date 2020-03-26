package lms.itcluster.confassistant.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lms.itcluster.confassistant.entity.Question;
import lms.itcluster.confassistant.entity.Stream;
import lms.itcluster.confassistant.entity.User;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class TopicDTO {

    private long topicId;
    private String name;
    private Date date;
    private Time beginTime;
    private Time finishTime;
    private String info;
    private boolean isActive;
    private String coverPhoto;
    private String stream;
    private SpeakerDTO speakerDTO;
    private List<QuestionDTO> questionListDTO;

    public TopicDTO() {
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

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public SpeakerDTO getSpeakerDTO() {
        return speakerDTO;
    }

    public void setSpeakerDTO(SpeakerDTO speakerDTO) {
        this.speakerDTO = speakerDTO;
    }

    public List<QuestionDTO> getQuestionListDTO() {
        return questionListDTO;
    }

    public void setQuestionListDTO(List<QuestionDTO> questionListDTO) {
        this.questionListDTO = questionListDTO;
    }
}
