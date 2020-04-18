package lms.itcluster.confassistant.dto;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class TopicDTO extends AbstractDTO {

    private long topicId;
    private String name;
    private LocalDate date;
    private LocalTime beginTime;
    private LocalTime finishTime;
    private String info;
    private boolean isActive;
    private String coverPhoto;
    private String stream;
    private SpeakerDTO speakerDTO;
    private List<QuestionDTO> questionListDTO;
    private Double backdown;
    private Double bodySize;
    private Boolean finished;

    public Boolean getFinished() {
        finished = LocalTime.now().isAfter(finishTime);
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isActiveTopic() {
        return LocalTime.now().isBefore(finishTime) && LocalTime.now().isAfter(beginTime);
    }

    public TopicDTO() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public LocalTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalTime finishTime) {
        this.finishTime = finishTime;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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

    public Double getBackdown() {
        return backdown;
    }

    public void setBackdown(Double backdown) {
        this.backdown = backdown;
    }

    public Double getBodySize() {
        return bodySize;
    }

    public void setBodySize(Double bodySize) {
        this.bodySize = bodySize;
    }

    @Override
    public Long getId() {
        return topicId;
    }
}
