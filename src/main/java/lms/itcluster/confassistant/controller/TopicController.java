package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.SimpleTopicDTO;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.service.ParticipantService;
import lms.itcluster.confassistant.service.SecurityService;
import lms.itcluster.confassistant.service.StreamService;
import lms.itcluster.confassistant.service.TopicService;
import lms.itcluster.confassistant.validator.TopicValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;


@Controller
public class TopicController {

	@Autowired
	private TopicService topicService;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private ParticipantService participantService;
	
	@Autowired
	private StreamService streamService;
	
	@Autowired
	private TopicValidator topicValidator;

	@RequestMapping(value = "dashboard/conferences/{confId}/stream/{streamId}/topics")
	public String getTopicList (@AuthenticationPrincipal CurrentUser currentUser,
                                @PathVariable Long confId,
                                @PathVariable Long streamId,
                                Model model){
		if(securityService.canManageConference(currentUser,confId)){
			model.addAttribute("listTopics", topicService.findAllTopicByStreamId(streamId));
			model.addAttribute("availableSpeaker", participantService.findAllParticipantByType(streamId,"speaker"));
			return "topic/list-topic";
		}
		return "redirect:/";
	}
	
	@PostMapping(value = "dashboard/conferences/{confId}/stream/{streamId}/delete/{topicId}")
	public String deleteTopic (@AuthenticationPrincipal CurrentUser currentUser,
	                           @PathVariable Long confId,
	                           @PathVariable Long streamId,
	                           @PathVariable Long topicId){
		topicService.deleteTopic(topicId);
		return  "redirect:/dashboard/conferences/{confId}/stream/{streamId}/topics";
	}

	@RequestMapping(value = "dashboard/conferences/{confId}/stream/{streamId}/edit/{topicId}")
	public String editTopic (@AuthenticationPrincipal CurrentUser currentUser,
	                          @PathVariable Long confId,
	                          @PathVariable Long streamId,
	                          @PathVariable Long topicId,
	                          Model model)  {
		if(securityService.canManageConference(currentUser,confId)){
			model.addAttribute("simpleTopicDTO", topicService.getSimpleTopicDTOById(topicId));
			model.addAttribute("availableSpeaker", participantService.findAllParticipantByType(confId,"speaker"));
			model.addAttribute("currentUser", currentUser.getId());
			return "topic/topic-edit";
		}
		return "redirect:/";
	}
	
	@PostMapping(value = "dashboard/conferences/{confId}/stream/{streamId}/edit/{topicId}/save")
	public String editTopicSave(@ModelAttribute @Valid SimpleTopicDTO simpleTopicDTO,
	                            BindingResult bindingResult,
	                            @PathVariable Long confId,
	                            @PathVariable Long streamId,
	                            @PathVariable Long topicId,
	                            @AuthenticationPrincipal CurrentUser currentUser,
	                            @RequestParam("inpFile") MultipartFile photo,
	                            Model model) throws IOException {
		model.addAttribute("availableSpeaker", participantService.findAllParticipantByType(confId,"speaker"));
		model.addAttribute("currentUser", currentUser.getId());
		topicValidator.validate(simpleTopicDTO,bindingResult);
		if(bindingResult.hasErrors()){
			return "topic/topic-edit";
		}
		topicService.updateTopic(simpleTopicDTO,photo);
		return "redirect:/dashboard/conferences/{confId}/stream/{streamId}/topics";
	}
	
	@RequestMapping(value = "dashboard/conferences/{confId}/stream/{streamId}/newTopic/")
	public String createTopic (@AuthenticationPrincipal CurrentUser currentUser,
	                           @PathVariable Long confId,
	                           @PathVariable Long streamId,
	                           Model model){
		model.addAttribute("availableSpeaker", participantService.findAllParticipantByType(confId,"speaker"));
		model.addAttribute("currentUser", currentUser.getId());
		model.addAttribute("stream", streamService.getStreamDTOById(streamId).getName());
		model.addAttribute(new SimpleTopicDTO());
		return "topic/topic-add";
	}
	
	@PostMapping(value = "/dashboard/conferences/{confId}/stream/{streamId}/topics/newTopic/save")
	public String createTopicSave (@ModelAttribute SimpleTopicDTO simpleTopicDTO,
	                               BindingResult bindingResult,
	                               Model model,
                                   @PathVariable Long confId,
                                   @PathVariable Long streamId,
                                   @RequestParam("inpFile") MultipartFile photo,
                                   @AuthenticationPrincipal CurrentUser currentUser) throws IOException {
		topicValidator.validate(simpleTopicDTO,bindingResult);
		if(bindingResult.hasErrors()){
			model.addAttribute("availableSpeaker", participantService.findAllParticipantByType(confId,"speaker"));
			model.addAttribute("currentUser", currentUser.getId());
			model.addAttribute("stream", streamService.getStreamDTOById(streamId).getName());
			return "topic/topic-add";
		}
		topicService.createTopic(simpleTopicDTO,photo);
		return "redirect:/dashboard/conferences/{confId}/stream/{streamId}/topics";
	}
}
