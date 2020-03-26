package lms.itcluster.confassistant.service;

import lms.itcluster.confassistant.dto.UserDTO;
import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.entity.User;

public interface ParticipantsService {

	 void addParticipant(UserDTO userDTO, Conference conference);
}
