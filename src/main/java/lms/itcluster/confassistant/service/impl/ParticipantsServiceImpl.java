package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.entity.ParticipantType;
import lms.itcluster.confassistant.entity.Participants;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.repository.ConferenceRepository;
import lms.itcluster.confassistant.repository.ParticipantsRepository;
import lms.itcluster.confassistant.repository.ParticipantsTypeRepository;
import lms.itcluster.confassistant.repository.UserRepository;
import lms.itcluster.confassistant.service.ParticipantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipantsServiceImpl implements ParticipantsService {
	
	@Autowired
	private ParticipantsRepository participantsRepository;
	
	@Autowired
	private ParticipantsTypeRepository participantsTypeRepository;
	
	
	public void addParticipant(User user, Conference conference){
		ParticipantType participantType = participantsTypeRepository.findByName("visitor");
		Participants participants = new Participants(user, conference, participantType);
		participantsRepository.save(participants);
	}
}
