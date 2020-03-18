package lms.itcluster.confassistant.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false)
    private int userId;

    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "phone_number", length = 13, unique = true)
    private String phoneNumber;

    @OneToMany(mappedBy = "conferenceUserId.user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Participants> participantsList;

@ManyToMany(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
})
@JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
)
private List<Roles> roles = new ArrayList<>();

@ManyToMany(mappedBy = "userLikes")
private List<Question> questionLikes = new ArrayList<>();

public User() {
        super();
    }


public int getUserId() {
    return userId;
}

public void setUserId(int userId) {
    this.userId = userId;
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

public List<Participants> getParticipantsList() {
    return participantsList;
}

public void setParticipantsList(List<Participants> participantsList) {
    this.participantsList = participantsList;
}

public List<Roles> getRoles() {
    return roles;
}

public void setRoles(List<Roles> roles) {
    this.roles = roles;
}

public List<Question> getQuestionLikes() {
    return questionLikes;
}

public void setQuestionLikes(List<Question> questionLikes) {
    this.questionLikes = questionLikes;
}
}
