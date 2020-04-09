package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.dto.ParticipantDTO;
import lms.itcluster.confassistant.dto.UserDTO;
import lms.itcluster.confassistant.entity.Conference;

import java.util.List;


public interface ParticipantService {

	 void addParticipant(UserDTO userDTO, Conference conference);
	 
	 void blockParticipant(Long participantId, Long conferenceId);
		
	 List<ParticipantDTO> findAllParticipant(Long conferenceId);

	 ParticipantDTO findParticipantById(Long userId, Long confId);

	 void updateParticipantByConfOwner(ParticipantDTO participantDTO);
	 
	 List<Long> findByUserIdAndTypeName (Long userId, String typeName);

	 List<ParticipantDTO> findAllParticipantByType(Long confId, String typeName);
}
