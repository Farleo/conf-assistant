package lms.itcluster.confassistant.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TopicDTO extends AbstractDTO {

    private Long topicId;
    private String name;
    private LocalDate date;
    private LocalTime beginTime;
    private LocalTime finishTime;
    private String info;
    private Boolean isAllowedQuestion;
    private String coverPhoto;
    private String stream;
    private SpeakerDTO speakerDTO;
    private List<QuestionDTO> questionListDTO;
    private Double backDown;
    private Double bodySize;
    private Boolean isBegin;
    private Boolean isActive;



    public Boolean isAllowedQuestion() {
        return isAllowedQuestion;
    }

    public void setAllowedQuestion(Boolean allowedQuestion) {
        isAllowedQuestion = allowedQuestion;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
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

    public Double getBackDown() {
        return backDown;
    }

    public void setBackDown(Double backDown) {
        this.backDown = backDown;
    }

    public Double getBodySize() {
        return bodySize;
    }

    public void setBodySize(Double bodySize) {
        this.bodySize = bodySize;
    }

    public Boolean getBegin() {
        return isBegin;
    }

    public void setBegin(Boolean begin) {
        isBegin = begin;
    }

    @Override
    public Long getId() {
        return topicId;
    }
}
