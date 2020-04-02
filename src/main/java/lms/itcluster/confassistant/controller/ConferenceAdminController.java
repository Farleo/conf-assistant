package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.ParticipantDTO;
import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.service.*;
import lms.itcluster.confassistant.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ConferenceAdminController {
	
	@Autowired
	private ConferenceService conferenceService;
	
	@Autowired
	private ParticipantService participantService;
	
	@Autowired
	private ParticipantTypeService participantTypeService;

	@RequestMapping(value = "conf/owner/{confId}")
	public String confOwnerManageUser (@AuthenticationPrincipal CurrentUser currentUser,
	                                   @PathVariable Long confId, Model model) {
		Conference conference = conferenceService.findById(confId);
		if(SecurityUtil.userHasAdminRole(currentUser)
				   || SecurityUtil.userHasAccessToConf(conference,currentUser)) {
			model.addAttribute("confName", conference.getName());
			model.addAttribute("usersOfCurrentConf", participantService.findAllParticipant(confId));
			Set<String> availableRoles = participantTypeService.getAllParticipantType().stream().map(r -> r.getName()).collect(Collectors.toSet());
			model.addAttribute("availableRoles", availableRoles);
			return "conferenceAdmin/conf-owner-list-user";
		}
		return "redirect:/";
	}

	@PostMapping(value="/conf/owner/{confId}/edit/{userId}")
	public String editByConf(@AuthenticationPrincipal CurrentUser currentUser,
	                         @PathVariable Long confId,
	                         @PathVariable Long userId,
	                         Model model) {
		Conference conference = conferenceService.findById(confId);
		if(SecurityUtil.userHasAdminRole(currentUser)
				   || SecurityUtil.userHasAccessToConf(conference,currentUser)) {
			model.addAttribute("confName", conferenceService.findById(confId).getName());
			model.addAttribute("user", participantService.findParticipantById(userId, confId));
			Set<String> availableRoles = participantTypeService.getAllParticipantType().stream().map(r -> r.getName()).collect(Collectors.toSet());
			model.addAttribute("availableRoles", availableRoles);
			return "conferenceAdmin/conf-admin-edit-user";
		}
		return "redirect:/";
	}
	

	@PostMapping(value="/conf/owner/saveChangeRole/")
	public String saveChangeRole(@AuthenticationPrincipal CurrentUser currentUser,
	                             ParticipantDTO participantDTO) {
		Conference conference = conferenceService.findById(participantDTO.getConferenceId());
		if(SecurityUtil.userHasAdminRole(currentUser)
				   || SecurityUtil.userHasAccessToConf(conference,currentUser)) {
			participantService.updateParticipantByConfOwner(participantDTO);
			Long confId = participantDTO.getConferenceId();
			return String.format("redirect:/conf/owner/%s", confId);
		}
		return "redirect:/";
		
	}
	
	
	
	@PostMapping(value = "/conf/owner/{confId}/kick/{userId}")
	public String confOwnerKickUser (@AuthenticationPrincipal CurrentUser currentUser,
	                                 @PathVariable Long confId,
	                                 @PathVariable Long userId) {
		Conference conference = conferenceService.findById(confId);
		if(SecurityUtil.userHasAdminRole(currentUser)
				   || SecurityUtil.userHasAccessToConf(conference,currentUser)) {
			participantService.blockParticipant(userId, confId);
			return "redirect:/conf/owner/{confId}";
		}
		return "redirect:/";
	}
}
