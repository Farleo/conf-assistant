package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.repository.ConferenceRepository;
import lms.itcluster.confassistant.repository.ParticipantRepository;
import lms.itcluster.confassistant.service.SecurityService;
import lms.itcluster.confassistant.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SecurityServiceImpl implements SecurityService {
	
	@Autowired
	ConferenceRepository conferenceRepository;
	
	@Autowired
	ParticipantRepository participantRepository;
	
	public boolean canManageConference (CurrentUser currentUser, Long confId){
		boolean hasAccess = false;
		if(SecurityUtil.userHasAdminRole(currentUser)){
			hasAccess = true;
		}
		else if(SecurityUtil.userHasConfOwnerRole(currentUser)){
			Optional<Conference> conferenceOptional = conferenceRepository.findById(confId);
			if(conferenceOptional.isPresent()){
				Conference conference = conferenceOptional.get();
				if(conference.getOwner().getUserId()==currentUser.getId()){
					hasAccess = true;
				}
			}
		}
		else if (SecurityUtil.userHasConfAdminRole(currentUser)){
			List<Long> list = participantRepository.findByUserIdAndTypeName(currentUser.getId(),"admin");
			if(list.contains(confId)){
				hasAccess = true;
			}
		}
		return hasAccess;
	}
}
