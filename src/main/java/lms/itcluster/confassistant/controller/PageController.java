package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.SpeakerDTO;
import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.entity.Participants;
import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.form.ConferenceForm;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.service.ConferenceService;
import lms.itcluster.confassistant.service.ParticipantsService;
import lms.itcluster.confassistant.service.TopicService;
import lms.itcluster.confassistant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
            Conference currentConference = currentTopic.getStream().getConference();
            List<Participants> confUserList = currentConference.getParticipants();
            if(confUserList.stream().anyMatch(u-> u.getParticipantsKey()
                                                          .getUser().getUserId()==currentUser.getId())){
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
       User user = userService.findById(currentUser.getId());
       participantsService.addParticipant(user,conference);
       return "redirect:/topic/{id}";
    }
}
