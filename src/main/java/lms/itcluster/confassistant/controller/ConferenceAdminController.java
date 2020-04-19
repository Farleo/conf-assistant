package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.ConferenceDTO;
import lms.itcluster.confassistant.dto.ParticipantDTO;
import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.service.ConferenceService;
import lms.itcluster.confassistant.service.ParticipantService;
import lms.itcluster.confassistant.service.ParticipantTypeService;
import lms.itcluster.confassistant.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
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
	
	@Autowired
	private SecurityService securityService;

	@RequestMapping(value = "conf/owner/{confId}")
	public String confOwnerManageUser (@AuthenticationPrincipal CurrentUser currentUser,
	                                   @PathVariable Long confId, Model model) {
		Conference conference = conferenceService.findById(confId);
		if(securityService.canManageConference(currentUser,conference.getConferenceId())) {
			model.addAttribute("confName", conference.getName());
			model.addAttribute("usersOfCurrentConf", participantService.findAllParticipant(confId));
			Set<String> availableRoles = participantTypeService.getAllParticipantType().stream().map(r -> r.getName()).collect(Collectors.toSet());
			model.addAttribute("availableRoles", availableRoles);
			return "conferenceAdmin/conf-owner-list-user";
		}
		return "redirect:/";
	}

	@RequestMapping(value="/conf/owner/{confId}/edit/{userId}")
	public String editByConf(@AuthenticationPrincipal CurrentUser currentUser,
	                         @PathVariable Long confId,
	                         @PathVariable Long userId,
	                         Model model) {
		Conference conference = conferenceService.findById(confId);
		if(securityService.canManageConference(currentUser,conference.getConferenceId())) {
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
		if(securityService.canManageConference(currentUser,conference.getConferenceId())) {
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
		if (securityService.canManageConference(currentUser, conference.getConferenceId())) {
			participantService.blockParticipant(userId, confId);
			return "redirect:/conf/owner/{confId}";
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/conf/owner/newConf")
	public String confOwnerCreateNewConf (@AuthenticationPrincipal CurrentUser currentUser,
	                                      Model model) {
			model.addAttribute(new ConferenceDTO());
			return "conferenceAdmin/add-conf";
	}

	@PostMapping(value = "/conf/owner/newConf")
	private String confOwnerCreateNewConfSave (@AuthenticationPrincipal CurrentUser currentUser,
	                                           @RequestParam("inpFile") MultipartFile photo,
	                                           @ModelAttribute @Valid ConferenceDTO conferenceDTO) throws IOException {
		conferenceDTO.setOwner(currentUser.getId());
		conferenceService.addNewConference(conferenceDTO, photo);
		return "redirect:/dashboard/conferences";
	}

	@GetMapping(value = "/conf/owner/edit/{id}")
	public String editConferenceGet (@AuthenticationPrincipal CurrentUser currentUser,
	                                 @PathVariable Long id,
	                                 Model model) {
		model.addAttribute("confDTO", conferenceService.getConferenceDTOById(id));
		return "conferenceAdmin/edit-conf";
	}

	@PostMapping(value = "/conf/owner/edit/{id}")
	public String editConferenceSave (@AuthenticationPrincipal CurrentUser currentUser,
	                                  @PathVariable Long id,
	                                  @RequestParam("inpFile") MultipartFile photo,
	                                  @ModelAttribute @Valid ConferenceDTO conferenceDTO) throws IOException  {
		conferenceService.updateConference(conferenceDTO, photo);
		return "redirect:/dashboard/conferences";
	}
	
	@RequestMapping(value = "conf/owner/delete/{id}")
	public String deleteConference (@AuthenticationPrincipal CurrentUser currentUser,
	                                @PathVariable Long id){
		conferenceService.deleteConference(id);
		return "redirect:/dashboard/conferences";
	}
}
