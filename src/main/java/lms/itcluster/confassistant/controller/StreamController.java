package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.component.CheckDataAccess;
import lms.itcluster.confassistant.dto.StreamDTO;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.service.ParticipantService;
import lms.itcluster.confassistant.service.StreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class StreamController {
	
	@Autowired
	private ParticipantService participantService;
	
	@Autowired
	private CheckDataAccess checkDataAccess;
	
	@Autowired
	private StreamService streamService;

	@RequestMapping(value = "dashboard/conferences/{confId}/stream")
	public String getConferenceStreams (@AuthenticationPrincipal CurrentUser currentUser,
	                                    @PathVariable Long confId,
	                                    Model model){
		if(checkDataAccess.canManageConference(currentUser,confId)){
			model.addAttribute("listStreams", streamService.findAllByConfId(confId));
			model.addAttribute("availableModer", participantService.findAllParticipantByType(confId,"moder"));
			return "stream/list-stream";
		}
		return "redirect:/";
	}
	
	@RequestMapping(value = "dashboard/conferences/{confId}/stream/delete/{streamId}")
	public String deleteStream (@AuthenticationPrincipal CurrentUser currentUser,
	                            @PathVariable Long confId,
	                            @PathVariable Long streamId){
		if(checkDataAccess.canManageConference(currentUser,confId)){
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
		if(checkDataAccess.canManageConference(currentUser,confId)){
			StreamDTO streamDTO = streamService.getStreamDTOById(streamId);
			model.addAttribute("availableModer", participantService.findAllParticipantByType(confId,"moder"));
			model.addAttribute("stream", streamDTO);
			model.addAttribute("currentUser", currentUser.getId());
			model.addAttribute("topicList", streamDTO.getTopicList());
			return "stream/stream-edit";
		}
		return "redirect:/";
	}
	
	@PostMapping(value = "dashboard/conferences/{confId}/stream/edit/{streamId}/save")
	public String saveEditStream (@AuthenticationPrincipal CurrentUser currentUser,
	                              @PathVariable Long confId,
	                              StreamDTO streamDTO,
	                              @PathVariable Long streamId){
		if(checkDataAccess.canManageConference(currentUser,confId)){
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
		if(checkDataAccess.canManageConference(currentUser,confId)){
			model.addAttribute("availableModer", participantService.findAllParticipantByType(confId,"moder"));
			model.addAttribute(new StreamDTO());
			model.addAttribute("confId", confId);
			model.addAttribute("currentUser", currentUser.getId());
			return "stream/stream-new";
		}
		return "redirect:/";
	}
	
	@PostMapping(value = "dashboard/conferences/{confId}/stream/new/save")
	public String saveNewStream (@AuthenticationPrincipal CurrentUser currentUser,
	                             @PathVariable Long confId,
	                             @ModelAttribute @Valid StreamDTO streamDTO){
		if(checkDataAccess.canManageConference(currentUser,confId)){
			streamService.addNewStream(streamDTO);
			return "redirect:/dashboard/conferences/{confId}/stream/";
		}
		return "redirect:/";
	}
}
