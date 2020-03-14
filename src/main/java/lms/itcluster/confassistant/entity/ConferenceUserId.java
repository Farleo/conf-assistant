package lms.itcluster.confassistant.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ConferenceUserId implements Serializable {



	@Column(name = "conference_id")
	private Long conferenceId;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "type_id")
	private Long typeId;
	
	private ConferenceUserId() {}
	
	public ConferenceUserId(
			Long conferenceId,
			Long userId,
			Long typeId) {
		this.conferenceId = conferenceId;
		this.userId = userId;
		this.typeId = typeId;
	}
	
	//Getters omitted for brevity
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		
		if (o == null || getClass() != o.getClass())
			return false;
		
		ConferenceUserId that = (ConferenceUserId) o;
		return Objects.equals(conferenceId, that.conferenceId) &&
				       Objects.equals(userId, that.userId) && Objects.equals(typeId, that.typeId);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(conferenceId, userId, typeId);
	}
}
