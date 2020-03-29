package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.ConferenceDTO;
import lms.itcluster.confassistant.dto.TopicDTO;
import lms.itcluster.confassistant.dto.UserDTO;
import lms.itcluster.confassistant.entity.*;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.repository.StreamRepository;
import lms.itcluster.confassistant.service.*;
import lms.itcluster.confassistant.service.ParticipantsService;
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

    @Autowired
    private StreamRepository streamRepository;

    @Autowired
    private RoleService roleService;

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
    public String getTopic(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        TopicDTO topicDTO = topicService.getTopicDTOBuId(id);
        model.addAttribute("topic", topicDTO);
        model.addAttribute("speaker", topicDTO.getSpeakerDTO());
        if (currentUser == null) {
            model.addAttribute("isPresentUser", false);
        }
        else {
            Stream stream = streamRepository.findByName(topicDTO.getStream());
            Conference currentConference = conferenceService.findById(stream.getConference().getConferenceId());
            List<Participants> confUserList = currentConference.getParticipants();
            if(confUserList.stream().anyMatch(u-> u.getParticipantsKey().getUser().getUserId()==currentUser.getId())){
                model.addAttribute("isPresentUser", true);
            }
            model.addAttribute("user", currentUser);
        }
        return "topic";
    }

    @GetMapping("/topic/join/{id}")
    public String joinConference(@PathVariable("id") long id, @AuthenticationPrincipal CurrentUser currentUser) {
       Topic topic = topicService.findById(id);
       Conference conference = topic.getStream().getConference();
       UserDTO user = userService.findById(currentUser.getId());
       participantsService.addParticipant(user,conference);
       return "redirect:/topic/{id}";
    }
}
