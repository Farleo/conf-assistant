package lms.itcluster.confassistant.dto;

import lms.itcluster.confassistant.annotation.CorrectDateAndTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@CorrectDateAndTime(topicId = "topicId", beginDateTime = "beginDateTime", finishTime = "finishTime")
public class EditTopicDTO extends AbstractDTO {
    private Long topicId;
    private String coverPhoto;
    @NotNull
    @NotBlank
    @Size(min = 1, max = 255)
    private String name;
    private String beginDateTime;
    private String finishTime;
    private String info;

    public EditTopicDTO() {
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
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

    public String getBeginDateTime() {
        return beginDateTime;
    }

    public void setBeginDateTime(String beginDateTime) {
        this.beginDateTime = beginDateTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    public Long getId() {
        return topicId;
    }
}
