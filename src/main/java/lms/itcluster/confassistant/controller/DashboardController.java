package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.*;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.service.*;
import lms.itcluster.confassistant.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
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
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "dashboard/conferences")
	public String indexConfDashboard (@AuthenticationPrincipal CurrentUser currentUser,
	                                  Model model) {
		model.addAttribute("allUser", userService.getAllUsers());
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
			model.addAttribute("availableModer", participantService.findAllParticipantByType(confId,"moder"));
			return "dashboard/list-stream";
		}
		return "redirect:/";
	}

	@RequestMapping(value = "dashboard/conferences/{confId}/stream/delete/{streamId}")
	public String deleteStream (@AuthenticationPrincipal CurrentUser currentUser,
	                            @PathVariable Long confId,
	                            @PathVariable Long streamId){
		if(securityService.canManageConference(currentUser,confId)){
			streamService.deleteStream(streamId);
			return "redirect:/dashboard/conferences/{confId}/stream/";
		}
		return "redirect:/";
	}

	@RequestMapping(value = "dashboard/conferences/{confId}/stream/edit/{streamId}")
	public String editStream (@AuthenticationPrincipal CurrentUser currentUser,
	                          @PathVariable Long confId,
	                          @PathVariable Long streamId,
	                          Model model){
		if(securityService.canManageConference(currentUser,confId)){
			StreamDTO streamDTO = streamService.getStreamDTOById(streamId);
			model.addAttribute("availableModer", participantService.findAllParticipantByType(confId,"moder"));
			model.addAttribute("stream", streamDTO);
			model.addAttribute("topicList", streamDTO.getTopicList());
			return "dashboard/stream-edit";
		}
		return "redirect:/";
	}

	@PostMapping(value = "dashboard/conferences/{confId}/stream/edit/{streamId}/save")
	public String saveEditStream (@AuthenticationPrincipal CurrentUser currentUser,
	                          @PathVariable Long confId,
	                          StreamDTO streamDTO,
	                          @PathVariable Long streamId){
		if(securityService.canManageConference(currentUser,confId)){
			StreamDTO originStreamDTO = streamService.getStreamDTOById(streamId);
			streamDTO.setTopicList(originStreamDTO.getTopicList());
			streamService.updateStream(streamDTO);
			return "redirect:/dashboard/conferences/{confId}/stream/";
		}
		return "redirect:/";
	}
	
	@RequestMapping(value = "dashboard/conferences/{confId}/stream/new")
	public String newStream (@AuthenticationPrincipal CurrentUser currentUser,
	                         @PathVariable Long confId,
	                         Model model){
		if(securityService.canManageConference(currentUser,confId)){
			model.addAttribute("availableModer", participantService.findAllParticipantByType(confId,"moder"));
			model.addAttribute(new StreamDTO());
			model.addAttribute("confId", confId);
			return "dashboard/stream-new";
		}
		return "redirect:/";
	}

	@PostMapping(value = "dashboard/conferences/{confId}/stream/new/save")
	public String saveNewStream (@AuthenticationPrincipal CurrentUser currentUser,
	                             @PathVariable Long confId,
	                             @ModelAttribute @Valid StreamDTO streamDTO){
		if(securityService.canManageConference(currentUser,confId)){
			streamService.addNewStream(streamDTO);
			return "redirect:/dashboard/conferences/{confId}/stream/";
		}
		return "redirect:/";
	}
}
