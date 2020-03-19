package lms.itcluster.confassistant.dto;

public class QuestionDTO {
    private String question;

    public QuestionDTO(String ask) {
        this.question = ask;
    }

    public QuestionDTO() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
