package lms.itcluster.confassistant.entity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "question_id", unique = true, nullable = false)
    private int questionId;

    @Column(name = "question", nullable = false, unique = true)
    private String question;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "presentation_id", nullable = false)
    private Presentations presentations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;


@ManyToMany(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
})
@JoinTable(name = "likes",
        joinColumns = @JoinColumn(name = "question_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
)
private List<User> userLikes = new ArrayList<>();
    

    public Question() {
        super();
    }

    
    
}
