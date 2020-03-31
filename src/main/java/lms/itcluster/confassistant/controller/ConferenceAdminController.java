package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.ParticipantDTO;
import lms.itcluster.confassistant.service.*;
import org.springframework.beans.factory.annotation.Autowired;
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
	public String confOwnerManageUser (@PathVariable Long confId,Model model) {
		model.addAttribute("confName", conferenceService.findById(confId).getName());
		model.addAttribute("usersOfCurrentConf", participantService.findAllParticipant(confId));
		Set<String> availableRoles = participantTypeService.getAllParticipantType().stream().map(r->r.getName()).collect(Collectors.toSet());
		model.addAttribute("availableRoles", availableRoles);
		return "conferenceAdmin/conf-owner-list-user";
	}

	@PostMapping(value="/conf/owner/{confId}/edit/{userId}")
	public String editByConf(@PathVariable Long confId,
	                       @PathVariable Long userId,
	                       Model model) {
		model.addAttribute("confName", conferenceService.findById(confId).getName());
		model.addAttribute("user", participantService.findParticipantById(userId, confId));
		Set<String> availableRoles = participantTypeService.getAllParticipantType().stream().map(r->r.getName()).collect(Collectors.toSet());
		model.addAttribute("availableRoles", availableRoles);
		return "conferenceAdmin/conf-admin-edit-user";
	}
	

	@PostMapping(value="/conf/owner/saveChangeRole/")
	public String saveChangeRole(ParticipantDTO participantDTO) {
		participantService.updateParticipantByConfOwner(participantDTO);
		Long confId = participantDTO.getConferenceId();
		return String.format("redirect:/conf/owner/%s", confId);
		
	}
	
	
	
	@PostMapping(value = "/conf/owner/{confId}/kick/{userId}")
	public String confOwnerKickUser (@PathVariable Long confId,
	                                 @PathVariable Long userId) {
		participantService.blockParticipant(userId,confId);
		return "redirect:/conf/owner/{confId}";
	}
}
