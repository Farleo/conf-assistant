package lms.itcluster.confassistant.mapper.impl;

import lms.itcluster.confassistant.dto.ParticipantDTO;
import lms.itcluster.confassistant.dto.QuestionDTO;
import lms.itcluster.confassistant.entity.*;
import lms.itcluster.confassistant.mapper.AbstractMapper;
import lms.itcluster.confassistant.mapper.Mapper;
import lms.itcluster.confassistant.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ParticipantMapper {

private final UserRepository userRepository;
private final ConferenceRepository conferenceRepository;
private final ParticipantsTypeRepository participantsTypeRepository;


@Autowired
public ParticipantMapper(ConferenceRepository conferenceRepository, UserRepository userRepository, ParticipantsTypeRepository participantsTypeRepository) {
	this.userRepository = userRepository;
	this.conferenceRepository = conferenceRepository;
	this.participantsTypeRepository = participantsTypeRepository;
}

public List<Participants> toEntity(ParticipantDTO participantDTO) {
	
	List<Participants> participantsList = new ArrayList<>();
	User user = userRepository.findById(participantDTO.getParticipantId()).get();
	Conference conference = conferenceRepository.findById(participantDTO.getConferenceId()).get();
	for (String role: participantDTO.getConferenceRoles()) {
		Participants participants = new Participants();
		ParticipantsKey participantsKey = new ParticipantsKey();
		participantsKey.setUser(user);
		participantsKey.setConference(conference);
		participantsKey.setParticipantType(participantsTypeRepository.findByName(role));
		participants.setParticipantsKey(participantsKey);
		participantsList.add(participants);
	}
	return participantsList;
	
	
}

public ParticipantDTO toDto(List<Participants> participants) {
	ParticipantsKey participantsKey = participants.stream().findFirst().get().getParticipantsKey();
	Long userId = participantsKey.getUser().getUserId();
	Long conferenceId = participantsKey.getConference().getConferenceId();
	ParticipantDTO participantDTO = new ParticipantDTO();
	Optional<User> userObject = userRepository.findById(userId);
	if (userObject.isPresent()) {
		User user = userObject.get();
		participantDTO.setParticipantId(userId);
		participantDTO.setConferenceId(conferenceId);
		participantDTO.setEmail(user.getEmail());
		participantDTO.setFirstName(user.getFirstName());
		participantDTO.setLastName(user.getLastName());
		Set<String> userConferenceRoles = participants.stream()
				                                  .map(t -> t.getParticipantsKey().getParticipantType().getName())
				                                  .collect(Collectors.toSet());
		participantDTO.setConferenceRoles(userConferenceRoles);
	}
	return participantDTO;
}

}
