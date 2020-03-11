package lms.itcluster.confassistant.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "conference")
public class Conference {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "conference_id", unique = true, nullable = false)
    private int conferenceId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "alias", nullable = false, unique = true)
    private String alias;

    @Column(name = "begin_date", nullable = false)
    private Date beginDate;

    @Column(name = "finish_date", nullable = false)
    private Date finishDate;

    @Column(name = "info", nullable = false)
    private String info;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "conference")
    private List<Stream> streamList;

    public Conference() {
        super();
    }

    public int getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(int conferenceId) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Stream> getStreamList() {
        return streamList;
    }

    public void setStreamList(List<Stream> streamList) {
        this.streamList = streamList;
    }
}
