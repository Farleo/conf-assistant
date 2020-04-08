package lms.itcluster.confassistant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.*;

@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "question_id", unique = true, nullable = false)
    private long questionId;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "created", nullable = false)
    private Time created;

    @Column(name = "selected", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean selected;

    @Column(name = "deleted", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    @JsonIgnore
    private Topic topic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToMany
    @JoinTable(
            name = "likes",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> likesSet = new HashSet<>();

    @Transient
    private int rating;

    public int getRating() {
        rating = likesSet.size();
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Time getCreated() {
        return created;
    }

    public void setCreated(Time created) {
        this.created = created;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<User> getLikesSet() {
        return likesSet;
    }

    public void setLikesSet(Set<User> likesSet) {
        this.likesSet = likesSet;
    }

    public Question() {
        super();
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
