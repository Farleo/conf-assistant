package lms.itcluster.confassistant.dto;

import java.util.Set;

public class QuestionDTO {

    private Long questionId;
    private String question;
    private Long topic;
    private Long user;
    private Set<Long> likesSet;
    private int rating;

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
}
