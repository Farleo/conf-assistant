package lms.itcluster.confassistant.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ParticipantDTO implements Serializable {

	private Long participantId;
	private Long conferenceId;
	private String email;
	private String firstName;
	private String lastName;
	private Set<String> conferenceRoles = new HashSet<>();
	
	public ParticipantDTO() {
	}
	
	public ParticipantDTO(Long participantId, Long conferenceId, String email, String firstName, String lastName, Set<String> conferenceRoles) {
		this.participantId = participantId;
		this.conferenceId = conferenceId;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.conferenceRoles = conferenceRoles;
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
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Set<String> getConferenceRoles() {
		return conferenceRoles;
	}
	
	public void setConferenceRoles(Set<String> conferenceRoles) {
		this.conferenceRoles = conferenceRoles;
	}
	}
