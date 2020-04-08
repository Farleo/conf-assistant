package lms.itcluster.confassistant.dto;

import javax.persistence.Column;
import java.sql.Date;
import java.sql.Time;
import java.util.Set;

public class QuestionDTO {

    private Long questionId;
    private String question;
    private Time created;
    private Long topic;
    private Long user;
    private Set<Long> likesSet;
    private int rating;
    private boolean selected;
    private boolean deleted;

    public QuestionDTO() {

    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Long getTopic() {
        return topic;
    }

    public void setTopic(Long topic) {
        this.topic = topic;
    }

    public Time getCreated() {
        return created;
    }

    public void setCreated(Time created) {
        this.created = created;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Set<Long> getLikesSet() {
        return likesSet;
    }

    public void setLikesSet(Set<Long> likesSet) {
        this.likesSet = likesSet;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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
