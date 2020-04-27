package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.component.CheckDataAccess;
import lms.itcluster.confassistant.dto.*;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static lms.itcluster.confassistant.util.RequestUtil.getPreviousPageByRequest;

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
    private QuestionService questionService;

    @Autowired
    private CheckDataAccess checkDataAccess;

    @RequestMapping(value = "/")
    public String getList(Model model) {
        model.addAttribute("conferences", conferenceService.getListConferencesDTO());
        return "welcome";
    }

    @GetMapping("/schedule")
    public String getSchedule(Model model, @RequestParam(value = "confId", required = false) Long confId) {
        ScheduleConferenceDTO conferenceDTOS = conferenceService.getConferenceForSchedule(Optional.ofNullable(confId).orElse(1L));
        model.addAttribute("conference", conferenceDTOS);
        model.addAttribute("conferences", conferenceService.getAllConferencesDTO());
        return "schedule";
    }

    @GetMapping("/conf/{confId}")
    public String getConference(@PathVariable("confId") Long confId, Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        ConferenceDTO conferenceDTO = conferenceService.getConferenceDTOById(confId);
        model.addAttribute("conference", conferenceDTO);
        model.addAttribute("isRegisteredOnConf", checkDataAccess.userPresentAtConference(currentUser, confId));
        return "conference";
    }

    @GetMapping("/topic/{id}")
    public String getTopic(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        TopicDTO topicDTO = topicService.getTopicDTOById(id, currentUser);
        Long confId = conferenceService.getConferenceDTOByTopicId(topicDTO.getTopicId()).getConferenceId();
        model.addAttribute("topic", topicDTO);
        model.addAttribute("speaker", topicDTO.getSpeakerDTO());
        model.addAttribute("confId", confId);
        model.addAttribute("user", currentUser);
        model.addAttribute("isRegisteredOnConf", checkDataAccess.userPresentAtConference(currentUser, confId));
        return "topic";
    }

    @GetMapping("/change/email/{code}")
    public String changeEmail(@PathVariable("code") String code, @AuthenticationPrincipal CurrentUser currentUser, Model model) {
        try {
            UserDTO userDTO = userService.findByActivationCodeAndSaveIfValid(code, currentUser);
            model.addAttribute("message", "You email address " + userDTO.getEmail() + " was successfully updated");
            return "message";
        } catch (DataIntegrityViolationException e) {
            log.error("User with this email address is already exist", e);
            model.addAttribute("message", "User with this email address is already exist, please check your email and try again");
            return "message";
        }
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
    public String getSpeakerCabinet(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        ListConferenceDTO conferenceDTO = conferenceService.getAllConferenceDTOForCurrentSpeaker(currentUser);
        model.addAttribute("conferences", conferenceDTO);
        return "speaker/conferences";
    }

    @GetMapping("/moderator")
    public String getModeratorCabinet(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        ListConferenceDTO dtoList = conferenceService.getAllConferenceDTOForCurrentModerator(currentUser);
        model.addAttribute("conferences", dtoList);
        return "moderator/conferences";
    }

    @GetMapping("/manage/topic/{topicId}")
    public String getQuestions(@PathVariable("topicId") Long topicId, Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        TopicDTO topicDTO = topicService.getTopicDTOWithQuestionManageAccess(topicId, currentUser);
        model.addAttribute("topic", topicDTO);
        return "moderator/question";
    }

    @GetMapping("/manage/topic/{topicId}/send")
    public String sendQuestionToSpeaker(@PathVariable("topicId") Long topicId, Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        questionService.sendQuestionToSpeaker(topicId, currentUser);
        model.addAttribute("topic", topicService.getTopicDTOById(topicId, currentUser));
        return "moderator/question";
    }
    

    @RequestMapping(value = "/search")
    public String Search(@RequestParam("alias") String alias, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        ConferenceDTO conferenceDTO = conferenceService.getConferenceDTObyAlias(alias);
        if (conferenceDTO != null) {
            return "redirect:/conf/" + conferenceDTO.getId();
        }
        redirectAttributes.addFlashAttribute("confNotFound", true);
        return getPreviousPageByRequest(request).orElse("/");
    }
    
}
