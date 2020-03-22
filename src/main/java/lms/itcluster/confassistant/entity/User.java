package lms.itcluster.confassistant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user")
public class  User {

@Id
@GeneratedValue(strategy= GenerationType.IDENTITY)
@Column(name = "user_id", unique = true, nullable = false)
private long userId;

@Column(name = "first_name", length = 45)
private String firstName;

@Column(name = "last_name", length = 45)
private String lastName;

@Column(name = "password", nullable = false)
private String password;

@Column(name = "email", nullable = false, length = 100, unique = true)
private String email;

@Column(name = "photo")
private String photo;

@Column(name = "info", length = 1000)
private String info;

@ManyToMany(mappedBy = "likesSet")
@JsonIgnore
private Set<Question> likes;

@OneToMany(mappedBy = "user")
@JsonIgnore
private List<Question> questionList;

@OneToMany(mappedBy = "speaker")
private List<Topic> topicList;

@OneToMany(mappedBy = "participantsKey.user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
private List<Participants> participants;

@ManyToMany(fetch = FetchType.EAGER)
@JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "roles_id"))
private Set<Roles> roles;

public User() {
}

public User(String email, String password, String firstName, String lastName, Set<Roles> roles) {
    this.email=email;
    this.password=password;
    this.firstName=firstName;
    this.lastName=lastName;
    this.roles=roles;
}


public long getUserId() {
    return userId;
}

public void setUserId(long userId) {
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

public String getPhoto() {
    return photo;
}

public void setPhoto(String photo) {
    this.photo = photo;
}

public String getInfo() {
    return info;
}

public void setInfo(String info) {
    this.info = info;
}

public Set<Question> getLikes() {
    return likes;
}

public void setLikes(Set<Question> likes) {
    this.likes = likes;
}

public List<Question> getQuestionList() {
    return questionList;
}

public void setQuestionList(List<Question> questionList) {
    this.questionList = questionList;
}

public List<Topic> getTopicList() {
    return topicList;
}

public void setTopicList(List<Topic> topicList) {
    this.topicList = topicList;
}

public List<Participants> getParticipants() {
    return participants;
}

public void setParticipants(List<Participants> participants) {
    this.participants = participants;
}

public Set<Roles> getRoles() {
    return roles;
}

public void setRoles(Set<Roles> roles) {
    this.roles = roles;
}

@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return userId == user.userId &&
                   Objects.equals(firstName, user.firstName) &&
                   Objects.equals(lastName, user.lastName) &&
                   Objects.equals(password, user.password) &&
                   Objects.equals(email, user.email) &&
                   Objects.equals(photo, user.photo) &&
                   Objects.equals(info, user.info);
}

@Override
public int hashCode() {
    return Objects.hash(userId, firstName, lastName, password, email, photo, info);
}

@Override
public String toString() {
    return "User{" +
                   "userId=" + userId +
                   ", firstName='" + firstName + '\'' +
                   ", lastName='" + lastName + '\'' +
                   ", password='" + password + '\'' +
                   ", email='" + email + '\'' +
                   '}';
}
}