package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.*;
import lms.itcluster.confassistant.entity.*;
import lms.itcluster.confassistant.exception.TopicNotFoundException;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.repository.StreamRepository;
import lms.itcluster.confassistant.service.*;
import lms.itcluster.confassistant.service.ParticipantService;
import lms.itcluster.confassistant.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.time.temporal.ChronoUnit.MINUTES;

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
    public String getTopic(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal CurrentUser currentUser) throws TopicNotFoundException, TopicNotFoundException {
        TopicDTO topicDTO = topicService.getTopicDTOById(id);
        model.addAttribute("topic", topicDTO);
        model.addAttribute("speaker", topicDTO.getSpeakerDTO());
        model.addAttribute("confId", conferenceService.getConfIdByTopicId(topicDTO.getTopicId()));
        model.addAttribute("user", currentUser);
        if (currentUser == null || !currentUser.isEnabled() || !conferenceService.isCurrentUserPresentAtConference(currentUser.getId(), conferenceService.getConfIdByTopicId(topicDTO.getTopicId()))) {
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
    public String joinConference(@PathVariable("id") long id, @AuthenticationPrincipal CurrentUser currentUser) throws TopicNotFoundException {
        Topic topic = topicService.findById(id);
        Conference conference = topic.getStream().getConference();
        UserDTO user = userService.findById(currentUser.getId());
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
    public String getSchedule(Model model,
                              @RequestParam("page") Optional<Integer> page) {
        int currentPage = page.orElse(1);
        Page<ScheduleConferenceDTO> conferenceDTOS = conferenceService.getConferencesForSchedule(PageRequest.of(currentPage - 1, 1));
        model.addAttribute("page", conferenceDTOS);

        int totalPages = conferenceDTOS.getTotalPages();
        if (totalPages > 0) {
            List<ConferenceDTO> dtos = conferenceService.getAllConferencesDTO();
            List<String> pageNumbers = new ArrayList<>();
            for (ConferenceDTO conferenceDTO : dtos) {
                pageNumbers.add(conferenceDTO.getName());
            }
            model.addAttribute("conferences", pageNumbers);
        }
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

}
