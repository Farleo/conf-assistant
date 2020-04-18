package lms.itcluster.confassistant.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ScheduleConferenceDTO extends AbstractDTO {
    private Long conferenceId;
    private String name;
    private String alias;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date finishDate;
    private String venue;
    private Map<LocalDate, List<StreamDTO>> schedule;

    public ScheduleConferenceDTO() {
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

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Map<LocalDate, List<StreamDTO>> getSchedule() {
        return schedule;
    }

    public void setSchedule(Map<LocalDate, List<StreamDTO>> schedule) {
        this.schedule = schedule;
    }

    @Override
    public Long getId() {
        return conferenceId;
    }
}
