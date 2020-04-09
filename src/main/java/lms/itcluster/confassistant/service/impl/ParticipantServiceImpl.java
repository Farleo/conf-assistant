package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.dto.ParticipantDTO;
import lms.itcluster.confassistant.dto.UserDTO;
import lms.itcluster.confassistant.entity.*;
import lms.itcluster.confassistant.mapper.Mapper;
import lms.itcluster.confassistant.repository.ConferenceRepository;
import lms.itcluster.confassistant.repository.ParticipantRepository;
import lms.itcluster.confassistant.repository.ParticipantsTypeRepository;
import lms.itcluster.confassistant.repository.UserRepository;
import lms.itcluster.confassistant.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ParticipantServiceImpl implements ParticipantService {

	@Autowired
	private ParticipantRepository participantRepository;
	
	@Autowired
	private ParticipantsTypeRepository participantsTypeRepository;
	
	@Autowired
	private ConferenceRepository conferenceRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	@Qualifier("userLoginMapper")
	private Mapper<User, UserDTO> userMapper;
	
	
	public void addParticipant(UserDTO userDTO, Conference conference) {
		ParticipantType participantType = participantsTypeRepository.findByName("visitor");
		User user = userMapper.toEntity(userDTO);
		Participants participants = new Participants(user, conference, participantType);
		participantRepository.save(participants);
	}
	
	public void blockParticipant(Long participantId, Long conferenceId) {
		participantRepository.deleteAllByUserIdAndConfId(participantId, conferenceId);
	}
	
	@Override
	public List<ParticipantDTO> findAllParticipant(Long conferenceId) {
		Optional<Conference> conferenceObject = conferenceRepository.findById(conferenceId);
		List<ParticipantDTO> participantDTOList = new ArrayList<>();
		if (conferenceObject.isPresent()) {
			Conference conference = conferenceObject.get();
			List<Participants> participantsList = conference.getParticipants();
			Set<Long> userIdSet = participantsList.stream().map(u -> u.getParticipantsKey().getUser().getUserId()).collect(Collectors.toSet());
			for (Long userId : userIdSet) {
				ParticipantDTO participantDTO = toDTO(userId, conferenceId);
				participantDTOList.add(participantDTO);
			}
		}
		return participantDTOList;
	}
	
	@Override
	public ParticipantDTO findParticipantById(Long userId, Long confId) {
		ParticipantDTO participantDTO = toDTO(userId, confId);
		return participantDTO;
	}
	
	@Override
	public void updateParticipantByConfOwner(ParticipantDTO participantDTO) {
		List<Participants> participants = this.toEntity(participantDTO);
		participantRepository.deleteAllByUserIdAndConfId(participantDTO.getParticipantId(), participantDTO.getConferenceId());
		participantRepository.saveAll(participants);
	}

	@Override
	public List<Long> findByUserIdAndTypeName (Long userId, String typeName) {
		return participantRepository.findByUserIdAndTypeName(userId,typeName);
	}

	@Override
	public List<ParticipantDTO> findAllParticipantByType(Long confId, String typeName) {
		List<Participants> participantsList = participantRepository.findAllUserByConfIdAndTypeName(confId,typeName);
		return participantsList.stream().map(p->toDTO(p)).collect(Collectors.toList());
	}

private List<Participants> toEntity(ParticipantDTO participantDTO) {
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


	private ParticipantDTO toDTO(Long userId, Long conferenceId) {
			ParticipantDTO participantDTO = new ParticipantDTO();
			Optional<User> userObject = userRepository.findById(userId);
			if (userObject.isPresent()) {
				User user = userObject.get();
				participantDTO.setParticipantId(userId);
				participantDTO.setConferenceId(conferenceId);
				participantDTO.setEmail(user.getEmail());
				participantDTO.setFirstName(user.getFirstName());
				participantDTO.setLastName(user.getLastName());
				List<Participants> participantsList = participantRepository.findByUserIdAndConfId(userId, conferenceId);
				Set<String> userConferenceRoles = participantsList.stream()
						                                  .map(t -> t.getParticipantsKey().getParticipantType().getName())
						                                  .collect(Collectors.toSet());
				participantDTO.setConferenceRoles(userConferenceRoles);
			}
			return participantDTO;
	}
	private ParticipantDTO toDTO(Participants participants) {
		ParticipantDTO participantDTO = new ParticipantDTO();
		User user = participants.getParticipantsKey().getUser();
		Conference conference = participants.getParticipantsKey().getConference();
		participantDTO.setParticipantId(user.getUserId());
		participantDTO.setConferenceId(conference.getConferenceId());
		participantDTO.setEmail(user.getEmail());
		participantDTO.setFirstName(user.getFirstName());
		participantDTO.setLastName(user.getLastName());
		List<Participants> participantsList = participantRepository.findByUserIdAndConfId(user.getUserId(), conference.getConferenceId());
		Set<String> userConferenceRoles = participantsList.stream()
				                                  .map(t -> t.getParticipantsKey().getParticipantType().getName())
				                                  .collect(Collectors.toSet());
		participantDTO.setConferenceRoles(userConferenceRoles);
	
		return participantDTO;
	}
	
}
