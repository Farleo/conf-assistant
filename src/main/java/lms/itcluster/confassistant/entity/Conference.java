package lms.itcluster.confassistant.entity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "conference")
public class Conference {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "conference_id", unique = true, nullable = false)
    private long conferenceId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "alias", nullable = false, unique = true)
    private String alias;

    @Column(name = "begin_date", nullable = false)
    private Date beginDate;

    @Column(name = "finish_date", nullable = false)
    private Date finishDate;

    @Column(name = "info", nullable = false, length = 1000)
    private String info;

    @Column(name = "venue", nullable = false)
    private String venue;

    @Column(name = "cover_photo")
    private String coverPhoto;

    @OneToMany(mappedBy = "participantsKey.conference", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Participants> participants;

    @OneToMany(mappedBy = "conference")
    private List<Stream> streamList;

    public Conference() {
        super();
    }

    public long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(long conferenceId) {
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

    public List<Participants> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participants> participants) {
        this.participants = participants;
    }

    public List<Stream> getStreamList() {
        return streamList;
    }

    public void setStreamList(List<Stream> streamList) {
        this.streamList = streamList;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conference that = (Conference) o;
        return conferenceId == that.conferenceId &&
                Objects.equals(name, that.name) &&
                Objects.equals(alias, that.alias) &&
                Objects.equals(info, that.info) &&
                Objects.equals(venue, that.venue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conferenceId, name, alias, info, venue);
    }

    @Override
    public String toString() {
        return "Conference{" +
                "conferenceId=" + conferenceId +
                ", name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", venue='" + venue + '\'' +
                '}';
    }
}
