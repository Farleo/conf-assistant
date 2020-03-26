package lms.itcluster.confassistant.controller;


import lms.itcluster.confassistant.dto.TopicDTO;
import lms.itcluster.confassistant.dto.UserDTO;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.service.*;
import lms.itcluster.confassistant.service.ParticipantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.entity.Participants;
import lms.itcluster.confassistant.entity.Topic;

import java.util.List;

import java.util.List;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private ParticipantsService participantsService;

    @RequestMapping(value = "/")
    public String getList(Model model) {
        model.addAttribute("conferences", conferenceService.getAllConferencesDTO());
        return "welcome";
    }

    @GetMapping("/conf/{id}")
    public String getConference(@PathVariable("id") Long id, Model model) {
        model.addAttribute("conference", conferenceService.getConferenceDTOById(id));
        return "conference";
    }

    @GetMapping("/topic/{id}")
    public String getTopic(@PathVariable("id") long id, Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        Topic topicDTO = topicService.findById(id);
        model.addAttribute("topic", topicDTO);
        model.addAttribute("speaker", topicDTO.getSpeaker());
        if (currentUser == null) {
            model.addAttribute("isPresentUser", false);
        }
        else {
            Conference currentConference = topicDTO.getStream().getConference();
            List<Participants> confUserList = currentConference.getParticipants();
            if(confUserList.stream().anyMatch(u-> u.getParticipantsKey().getUser().getUserId()==currentUser.getId())){
                model.addAttribute("isPresentUser", true);
            }
            model.addAttribute("user", currentUser);
        }
        return "topic";
    }

    @GetMapping("/topic/join/{id}")
    public String joinConference(@PathVariable("id") long id, Model model, @AuthenticationPrincipal CurrentUser currentUser) {
       Topic topic = topicService.findById(id);
       Conference conference = topic.getStream().getConference();
       UserDTO user = userService.findById(currentUser.getId());
       participantsService.addParticipant(user,conference);
       return "redirect:/topic/{id}";
    }
}
