package lms.itcluster.confassistant.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "participant_type")
public class ParticipantType {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "type_id", unique = true, nullable = false)
    private long typeId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "participantsKey.participantType", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Participants> participants;

    public ParticipantType() {
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Participants> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participants> participants) {
        this.participants = participants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantType that = (ParticipantType) o;
        return typeId == that.typeId &&
                Objects.equals(name, that.name) &&
                Objects.equals(participants, that.participants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeId, name, participants);
    }

    @Override
    public String toString() {
        return "ParticipantType{" +
                "typeId=" + typeId +
                ", name='" + name +
                '}';
    }
}
