package lms.itcluster.confassistant.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "guest")
public class Guest {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "guest_id", unique = true, nullable = false)
    private int guestId;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @OneToMany(mappedBy = "guest")
    private List<Question> questionList;

    public Guest() {
        super();
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }
}
