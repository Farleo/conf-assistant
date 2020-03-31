package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.dto.ParticipantTypeDTO;
import lms.itcluster.confassistant.entity.ParticipantType;

import java.util.List;

public interface ParticipantTypeService {
	
	List<ParticipantTypeDTO> getAllParticipantType();
	
}
