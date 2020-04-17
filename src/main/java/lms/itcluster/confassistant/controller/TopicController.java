package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.SimpleTopicDTO;
import lms.itcluster.confassistant.exception.TopicNotFoundException;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.service.ParticipantService;
import lms.itcluster.confassistant.service.SecurityService;
import lms.itcluster.confassistant.service.StreamService;
import lms.itcluster.confassistant.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


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
	                          Model model) throws TopicNotFoundException {
		if(securityService.canManageConference(currentUser,confId)){
			SimpleTopicDTO simpleTopicDTO = topicService.getSimpleTopicDTOById(topicId);
			model.addAttribute("topic", simpleTopicDTO);
			model.addAttribute("availableSpeaker", participantService.findAllParticipantByType(confId,"speaker"));
			model.addAttribute("currentUser", currentUser.getId());
			return "topic/topic-edit";
		}
		return "redirect:/";
	}
	
	@PostMapping(value = "dashboard/conferences/{confId}/stream/{streamId}/edit/{topicId}/save")
	public String editTopicSave(@AuthenticationPrincipal CurrentUser currentUser,
	                            @PathVariable Long confId,
	                            @PathVariable Long streamId,
	                            @PathVariable Long topicId,
	                            @ModelAttribute SimpleTopicDTO simpleTopicDTO,
	                            Model model) throws TopicNotFoundException {
		topicService.updateTopic(simpleTopicDTO);
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
	public String createTopicSave (@AuthenticationPrincipal CurrentUser currentUser,
                                   @PathVariable Long confId,
                                   @PathVariable Long streamId,
                                   @ModelAttribute SimpleTopicDTO simpleTopicDTO) {
		topicService.createTopic(simpleTopicDTO);
		return "redirect:/dashboard/conferences/{confId}/stream/{streamId}/topics";
	}
}
