package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.SpeakerDTO;
import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.form.ConferenceForm;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.service.ConferenceService;
import lms.itcluster.confassistant.service.TopicService;
import lms.itcluster.confassistant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private ConferenceService conferenceService;

    @RequestMapping(value = "/")
    public String getList(Model model) {
        model.addAttribute("conferences", new ConferenceForm(conferenceService.getAllConferences()));
        return "welcome";
    }

    @GetMapping("/conf/{id}")
    public String getConference(@PathVariable("id") long id, Model model) {
        model.addAttribute("conference", conferenceService.findById(id));
        return "conference";
    }

    @GetMapping("/topic/{id}")
    public String getTopic(@PathVariable("id") long id, Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        Topic currentTopic = topicService.findById(id);
        model.addAttribute("currentTopic", currentTopic);
        model.addAttribute("speaker", new SpeakerDTO(currentTopic.getSpeaker()));
        if (currentUser == null) {
            model.addAttribute("isPresentUser", false);
        }
        else {
            model.addAttribute("isPresentUser", true);
            model.addAttribute("user", currentUser);
        }
        return "topic";
    }
}
