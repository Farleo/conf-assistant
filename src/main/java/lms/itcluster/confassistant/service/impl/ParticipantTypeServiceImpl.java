package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.dto.ParticipantTypeDTO;
import lms.itcluster.confassistant.entity.ParticipantType;
import lms.itcluster.confassistant.repository.ParticipantsTypeRepository;
import lms.itcluster.confassistant.service.ParticipantTypeService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class ParticipantTypeServiceImpl implements ParticipantTypeService {
	
	@Autowired
	ParticipantsTypeRepository participantsTypeRepository;
	
	@Override
	public List<ParticipantTypeDTO> getAllParticipantType() {
		Iterable<ParticipantType> participantTypes = participantsTypeRepository.findAll();
		Type listType = new TypeToken<List<ParticipantTypeDTO>>() {}.getType();
		ModelMapper modelMapper = new ModelMapper();
		List<ParticipantTypeDTO> participantTypeDTOS = modelMapper.map(participantTypes,listType);
		return participantTypeDTOS;
	}
}
