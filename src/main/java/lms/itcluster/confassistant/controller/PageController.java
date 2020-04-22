package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.*;
import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.entity.Topic;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.service.*;
import lms.itcluster.confassistant.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
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
    private StaticDataService staticDataService;

    @RequestMapping(value = "/")
    public String getList(Model model) {
        model.addAttribute("conferences", conferenceService.getListConferencesDTO());
        return "welcome";
    }

    @GetMapping("/conf/{id}")
    public String getConference(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        ConferenceDTO conferenceDTO = conferenceService.getConferenceDTOById(id);
        model.addAttribute("conference", conferenceDTO);
        if (currentUser != null) {
            model.addAttribute("isRegisteredOnConf", conferenceService.isCurrentUserPresentAtConference(currentUser.getId(), id));
            model.addAttribute("canEdit", SecurityUtil.canEditConference(currentUser, conferenceDTO));
        }
        return "conference";
    }

    @GetMapping("/topic/{id}")
    public String getTopic(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        TopicDTO topicDTO = topicService.getTopicDTOById(id, currentUser);
        model.addAttribute("topic", topicDTO);
        model.addAttribute("speaker", topicDTO.getSpeakerDTO());
        model.addAttribute("confId", conferenceService.getConferenceDTOByTopicId(topicDTO.getTopicId()).getConferenceId());
        model.addAttribute("user", currentUser);
        if (currentUser == null || !currentUser.isEnabled() || !conferenceService.isCurrentUserPresentAtConference(currentUser.getId(), conferenceService.getConferenceDTOByTopicId(topicDTO.getTopicId()).getConferenceId())) {
            model.addAttribute("isRegisteredOnConf", false);
            return "topic";
        }
        model.addAttribute("isRegisteredOnConf", true);

/*            Stream stream = streamRepository.findByName(topicDTO.getStream());
            Conference currentConference = conferenceService.findById(stream.getConference().getConferenceId());
            List<Participants> confUserList = currentConference.getParticipants();
            if (confUserList.stream().anyMatch(u -> u.getParticipantsKey().getUser().getUserId() == currentUser.getId())) {

            }*/
        return "topic";
    }

    @GetMapping("/topic/join/{id}")
    public String joinConference(@PathVariable("id") long id, @AuthenticationPrincipal CurrentUser currentUser) {
        Topic topic = topicService.findById(id);
        Conference conference = topic.getStream().getConference();
        UserDTO user = userService.getUserDtoById(currentUser.getId());
        participantService.addParticipant(user, conference);
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

    @GetMapping("/schedule")
    public String getSchedule(Model model, @RequestParam("confId") Optional<Long> confId) {
        ScheduleConferenceDTO conferenceDTOS = conferenceService.getConferenceForSchedule(confId.orElse(1L));
        model.addAttribute("conference", conferenceDTOS);
        model.addAttribute("conferences", conferenceService.getAllConferencesDTO());
        return "schedule";
    }

    @GetMapping("/register/conference/{conferenceId}")
    public String getConfRegistration(Model model, @PathVariable("conferenceId") Long conferenceId) {
        ConferenceDTO conferenceDTO = conferenceService.getConferenceDTOById(conferenceId);
        model.addAttribute("conference", conferenceDTO);
        return "register-conference";
    }

    @PostMapping("/register/conference")
    public String saveConfRegistration(Model model, @RequestParam("confId") Long confId, @AuthenticationPrincipal CurrentUser currentUser) {
        boolean isRegistered = conferenceService.registerCurrentUserForConference(confId, currentUser.getId());
        if (isRegistered) {
            return "redirect:/conf/" + confId;
        }
        model.addAttribute("message", "You already registered on this conference");
        return "message";
    }

    @GetMapping("/speaker")
    public String getCabinet(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        ListConferenceDTO conferenceDTO = conferenceService.getAllConferenceDTOForCurrentSpeaker(currentUser);
        model.addAttribute("conferences", conferenceDTO);
        return "speaker/conferences";
    }

}
