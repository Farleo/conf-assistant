package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.ConferenceDTO;
import lms.itcluster.confassistant.dto.ListConferenceDTO;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.service.ConferenceService;
import lms.itcluster.confassistant.service.ParticipantService;
import lms.itcluster.confassistant.service.SecurityService;
import lms.itcluster.confassistant.service.StreamService;
import lms.itcluster.confassistant.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DashboardController {
	
	@Autowired
	private ConferenceService conferenceService;
	
	@Autowired
	private ParticipantService participantService;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private StreamService streamService;

	@RequestMapping(value = "dashboard/conferences")
	public String indexConfDashboard (@AuthenticationPrincipal CurrentUser currentUser,
	                                  Model model) {
		if(SecurityUtil.userHasAdminRole(currentUser)){
			model.addAttribute("conferences", conferenceService.getListConferencesDTO());
			return "dashboard/main";
		}
		else if(SecurityUtil.userHasConfOwnerRole(currentUser)){
			model.addAttribute("conferences", conferenceService.getConferencesDTOByOwnerId(currentUser.getId()));
			return "dashboard/main";
		}
		else if (SecurityUtil.userHasConfAdminRole(currentUser)){
			List<Long> confList = participantService.findByUserIdAndTypeName(currentUser.getId(),"admin");
			List<ConferenceDTO> conferenceDTOList = conferenceService.getAllConferencesDTO().stream().filter(f->confList.contains(f.getConferenceId())).collect(Collectors.toList());
			model.addAttribute("conferences", new ListConferenceDTO(conferenceDTOList));
			return "dashboard/main";
		}
		return "redirect:/";
	}

	@RequestMapping(value = "dashboard/conferences/{confId}/stream")
	public String getConferenceStreams (@AuthenticationPrincipal CurrentUser currentUser,
	                                    @PathVariable Long confId,
	                                    Model model){
		if(securityService.canManageConference(currentUser,confId)){
			model.addAttribute("listStreams", streamService.findAllByConfId(confId));
			return "dashboard/list-stream";
		}
		return "redirect:/";
	}
	
}
