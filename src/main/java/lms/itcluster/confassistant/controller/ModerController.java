package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.ListConferenceDTO;
import lms.itcluster.confassistant.dto.SpeakerDTO;
import lms.itcluster.confassistant.dto.StreamDTO;
import lms.itcluster.confassistant.dto.TopicDTO;
import lms.itcluster.confassistant.exception.TopicNotFoundException;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.service.ConferenceService;
import lms.itcluster.confassistant.service.QuestionService;
import lms.itcluster.confassistant.service.StreamService;
import lms.itcluster.confassistant.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ModerController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private StreamService streamService;

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/moderator")
    public String getCabinet(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        ListConferenceDTO dtoList = conferenceService.getAllConferenceDTOForCurrentModerator(currentUser);
        model.addAttribute("conferences", dtoList);
        return "moderator/conferences";
    }

    @GetMapping("/moderator/questions/{topicId}")
    public String getQuestions(@PathVariable("topicId") Long topicId, Model model, @AuthenticationPrincipal CurrentUser currentUser) throws TopicNotFoundException {
        TopicDTO topicDTO = topicService.getTopicDTOById(topicId);
        StreamDTO streamDTO = streamService.getStreamDTOByName(topicDTO.getStream());
        model.addAttribute("isEnable", topicDTO.isActive());
        model.addAttribute("topic", topicDTO);
        return "moderator/question";
    }

    @GetMapping("/moderator/questions/send/{topicId}")
    public String sendQuestionToSpeaker(@PathVariable("topicId") Long topicId, Model model, @AuthenticationPrincipal CurrentUser currentUser) throws TopicNotFoundException {
        questionService.sendQuestionToSpeaker(topicId);
        model.addAttribute("topic", topicService.getTopicDTOById(topicId));
        model.addAttribute("isEnable", topicService.getTopicDTOById(topicId).isActive());
        return "moderator/question";
    }
}