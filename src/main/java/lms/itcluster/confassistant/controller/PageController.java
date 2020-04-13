package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.TopicDTO;
import lms.itcluster.confassistant.dto.UserDTO;
import lms.itcluster.confassistant.entity.*;
import lms.itcluster.confassistant.exception.TopicNotFoundException;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.repository.StreamRepository;
import lms.itcluster.confassistant.service.*;
import lms.itcluster.confassistant.service.ParticipantService;
import lms.itcluster.confassistant.util.SecurityUtil;
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
    private ParticipantService participantService;

    @Autowired
    private StreamRepository streamRepository;

    @Autowired
    private StaticDataService staticDataService;

    @RequestMapping(value = "/")
    public String getList(Model model) {
        model.addAttribute("conferences", conferenceService.getListConferencesDTO());
        return "welcome";
    }

    @GetMapping("/conf/{id}")
    public String getConference(@PathVariable("id") Long id, Model model) {
        model.addAttribute("conference", conferenceService.getConferenceDTOById(id));
        return "conference";
    }

    @GetMapping("/topic/{id}")
    public String getTopic(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal CurrentUser currentUser) throws TopicNotFoundException, TopicNotFoundException {
        TopicDTO topicDTO = topicService.getTopicDTOById(id);
        model.addAttribute("topic", topicDTO);
        model.addAttribute("speaker", topicDTO.getSpeakerDTO());
        if (currentUser == null || !currentUser.isEnabled()) {
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
            model.addAttribute("canEdit", SecurityUtil.canCurrentUserEditTopic(currentUser, stream));
        }
        return "topic";
    }

    @GetMapping("/topic/join/{id}")
    public String joinConference(@PathVariable("id") long id, @AuthenticationPrincipal CurrentUser currentUser) throws TopicNotFoundException {
        Topic topic = topicService.findById(id);
        Conference conference = topic.getStream().getConference();
        UserDTO user = userService.findById(currentUser.getId());
        participantService.addParticipant(user,conference);
        return "redirect:/topic/{id}";
    }

    @GetMapping("/change/{code}")
    public String changeEmail(@PathVariable("code") String code, @AuthenticationPrincipal CurrentUser currentUser, Model model) {
        UserDTO userDTO = userService.findByCode(code, currentUser.getId());
        if (userDTO != null) {
            String newEmail = staticDataService.getUpdatedEmail(userDTO.getUserId());
            if (userService.findByEmail(newEmail) != null) {
                staticDataService.removeUpdatedEmail(userDTO.getUserId());
                model.addAttribute("message", "User with email: " + userDTO.getEmail() + " already exist");
                return "message";
            }
            userDTO.setEmail(newEmail);
            userService.updateEmail(userDTO);
            staticDataService.removeUpdatedEmail(userDTO.getUserId());
            model.addAttribute("message", "You email address " + userDTO.getEmail() + " was successfully updated");
            return "message";
        } else {
            return "not-found";
        }
    }
}
