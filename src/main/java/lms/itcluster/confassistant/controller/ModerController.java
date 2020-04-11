package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.ListConferenceDTO;
import lms.itcluster.confassistant.dto.StreamDTO;
import lms.itcluster.confassistant.dto.TopicDTO;
import lms.itcluster.confassistant.exception.TopicNotFoundException;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.service.ConferenceService;
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
        if (streamDTO.getModerator() != currentUser.getId()) {
            return "forbidden";
        }
        model.addAttribute("topicId", topicId);
        return "moderator/question";
    }
}