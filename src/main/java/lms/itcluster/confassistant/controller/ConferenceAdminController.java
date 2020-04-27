package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.component.CheckDataAccess;
import lms.itcluster.confassistant.dto.ConferenceDTO;
import lms.itcluster.confassistant.dto.ParticipantDTO;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.service.ConferenceService;
import lms.itcluster.confassistant.service.ParticipantService;
import lms.itcluster.confassistant.service.ParticipantTypeService;
import lms.itcluster.confassistant.validator.ConferenceValidator;
import lms.itcluster.confassistant.validator.UploadPhotoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
	private CheckDataAccess checkDataAccess;
	
	@Autowired
	private ConferenceValidator conferenceValidator;

	@Autowired
	private UploadPhotoValidator uploadPhotoValidator;

	@RequestMapping(value = "conf/owner/{confId}")
	public String confOwnerManageUser (@AuthenticationPrincipal CurrentUser currentUser,
	                                   @PathVariable Long confId, Model model) {
		ConferenceDTO conference = conferenceService.getConferenceDTOById(confId);
		if(checkDataAccess.canManageConference(currentUser,conference.getConferenceId())) {
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
		ConferenceDTO conference = conferenceService.getConferenceDTOById(confId);
		if(checkDataAccess.canManageConference(currentUser,conference.getConferenceId())) {
			model.addAttribute("confName", conference.getName());
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
		ConferenceDTO conference = conferenceService.getConferenceDTOById(participantDTO.getConferenceId());
		if(checkDataAccess.canManageConference(currentUser,conference.getConferenceId())) {
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
		ConferenceDTO conference = conferenceService.getConferenceDTOById(confId);
		if (checkDataAccess.canManageConference(currentUser, conference.getConferenceId())) {
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
	private String confOwnerCreateNewConfSave (@ModelAttribute @Valid ConferenceDTO conferenceDTO,
	                                           BindingResult bindingResult,
	                                           @AuthenticationPrincipal CurrentUser currentUser,
	                                           @RequestParam("inpFile") MultipartFile photo, Model model) throws IOException {
		model.addAttribute("conferenceDTO", conferenceDTO);
		conferenceDTO.setOwner(currentUser.getId());
		conferenceValidator.validate(conferenceDTO,bindingResult);
		uploadPhotoValidator.validate(photo, bindingResult);
		if(bindingResult.hasErrors()){
			return "conferenceAdmin/add-conf";
		}
		conferenceService.addNewConference(conferenceDTO, photo.getBytes(), photo.getOriginalFilename());
		return "redirect:/dashboard/conferences";
	}

	@GetMapping(value = "/conf/owner/edit/{id}")
	public String editConferenceGet (@PathVariable("id") Long id,
	                                 @AuthenticationPrincipal CurrentUser currentUser,
	                                 ConferenceDTO conferenceDTO,
	                                 Model model) {
		model.addAttribute("conferenceDTO", conferenceService.getConferenceDTOById(id));
		return "conferenceAdmin/edit-conf";
	}

	@PostMapping(value = "/conf/owner/edit/{id}")
	public String editConferenceSave (@PathVariable("id") Long id,
	                                  @ModelAttribute @Valid ConferenceDTO conferenceDTO,
	                                  BindingResult bindingResult,
	                                  @AuthenticationPrincipal CurrentUser currentUser,
	                                  @RequestParam("inpFile") MultipartFile photo, Model model) throws IOException  {
		model.addAttribute("conferenceDTO", conferenceDTO);
		conferenceValidator.validate(conferenceDTO,bindingResult);
		uploadPhotoValidator.validate(photo, bindingResult);
		if(bindingResult.hasErrors()){
			return "conferenceAdmin/edit-conf";
		}
		conferenceService.updateConference(conferenceDTO, photo.getBytes(), photo.getOriginalFilename());
		return "redirect:/dashboard/conferences";
	}
	
	@RequestMapping(value = "conf/owner/delete/{id}")
	public String deleteConference (@AuthenticationPrincipal CurrentUser currentUser,
	                                @PathVariable Long id){
		conferenceService.deleteConference(id);
		return "redirect:/dashboard/conferences";
	}
}
