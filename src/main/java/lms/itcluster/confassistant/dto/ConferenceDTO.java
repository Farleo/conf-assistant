package lms.itcluster.confassistant.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ConferenceDTO extends AbstractDTO {
    private Long conferenceId;
    private String name;
    private String alias;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date finishDate;
    private String info;
    private String venue;
    private String coverPhoto;
    private List<Long> participants = new ArrayList<>();
    private List<StreamDTO> streamList = new ArrayList<>();
    private Long owner;


    public ConferenceDTO() {
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Long conferenceId) {
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

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public List<Long> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Long> participants) {
        this.participants = participants;
    }

    public List<StreamDTO> getStreamList() {
        return streamList;
    }

    public void setStreamList(List<StreamDTO> streamList) {
        this.streamList = streamList;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    @Override
    public Long getId() {
        return conferenceId;
    }
}
