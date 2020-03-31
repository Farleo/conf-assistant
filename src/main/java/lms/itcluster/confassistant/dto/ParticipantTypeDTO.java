package lms.itcluster.confassistant.dto;

public class ParticipantTypeDTO {
	private Long participantId;
	private Long conferenceId;
	private String name;

	public ParticipantTypeDTO() {
	}

	public ParticipantTypeDTO(Long participantId, Long conferenceId, String name) {
		this.participantId = participantId;
		this.conferenceId = conferenceId;
		this.name = name;
	}
	
	public Long getParticipantId() {
		return participantId;
	}
	
	public void setParticipantId(Long participantId) {
		this.participantId = participantId;
	}
	
	public Long getConferenceId() {
		return conferenceId;
	}
	
	public void setConferenceId(Long conferenceId) {
		this.conferenceId = conferenceId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}