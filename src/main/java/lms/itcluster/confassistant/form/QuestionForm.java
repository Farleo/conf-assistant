package lms.itcluster.confassistant.form;

import lms.itcluster.confassistant.entity.Question;

import java.io.Serializable;
import java.util.List;

public class QuestionForm implements Serializable {

    private List<Question> questionList;

    public QuestionForm(List<Question> questionList) {
        this.questionList = questionList;
    }

    public QuestionForm() {
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }
}
