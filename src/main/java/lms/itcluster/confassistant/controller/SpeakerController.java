package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.TopicDTO;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.service.ConferenceService;
import lms.itcluster.confassistant.service.StreamService;
import lms.itcluster.confassistant.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SpeakerController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private StreamService streamService;

    @Autowired
    private ConferenceService conferenceService;

    @GetMapping("/speaker")
    public String getCabinet(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        List<TopicDTO> dtoList = topicService.getAllTopicForCurrentSpeaker(currentUser.getId());
        model.addAttribute("topics", dtoList);
        return "speaker/topic";
    }
}