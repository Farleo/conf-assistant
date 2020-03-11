package lms.itcluster.confassistant.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "speaker")
public class Speaker {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "speaker_id", unique = true, nullable = false)
    private int speakerId;

    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "info", nullable = false)
    private String info;

    @Column(name = "photo")
    private String photo;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "phone_number", length = 13, unique = true)
    private String phoneNumber;

    @OneToMany(mappedBy = "speaker")
    private List<Topic> topicList;

    public Speaker() {
        super();
    }

    public int getSpeakerId() {
        return speakerId;
    }

    public void setSpeakerId(int speakerId) {
        this.speakerId = speakerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }
}
