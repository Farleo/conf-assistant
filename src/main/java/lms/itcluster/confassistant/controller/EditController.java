package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.*;
import lms.itcluster.confassistant.exception.TopicNotFoundException;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.service.StaticDataService;
import lms.itcluster.confassistant.service.TopicService;
import lms.itcluster.confassistant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Controller
public class EditController {

    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private StaticDataService staticDataService;

    @GetMapping("/edit/profile")
    public String getCompleteSignUp (@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        if (currentUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", userService.getGuestProfileDTOById(currentUser.getId()));
        return "edit/profile";
    }

    @PostMapping("/edit/profile")
    public String saveCompleteSignUp (@Valid @ModelAttribute("user") EditProfileDTO editProfileDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit/profile";
        }
        userService.completeGuestRegistration(editProfileDTO);
        return "redirect:/";
    }

    @GetMapping("/edit/topic/main/{topicId}")
    public String getMain (@PathVariable("topicId") Long topicId, Model model) throws TopicNotFoundException {
        TopicDTO topicDTO = topicService.getTopicDTOById(topicId);
        model.addAttribute("topic", topicDTO);
        model.addAttribute("month", staticDataService.getMonthMap());
        model.addAttribute("years", staticDataService.getYears());
        model.addAttribute("days", staticDataService.getDays(topicDTO.getDate().getYear(), topicDTO.getDate().getMonthValue()));
        return "edit/topic/main";
    }

    @PostMapping("/edit/topic/main")
    public String saveMain (EditTopicDTO editTopicDTO, @RequestParam("inpFile") MultipartFile photo) throws IOException, TopicNotFoundException {
        topicService.updateMainTopicData(editTopicDTO, photo);
        return "redirect:/topic/" + editTopicDTO.getTopicId();
    }

    @GetMapping("/edit/topic/speaker/{topicId}")
    public String getSpeaker (@PathVariable("topicId") Long topicId, Model model) throws TopicNotFoundException {
        TopicDTO topicDTO = topicService.getTopicDTOById(topicId);
        SpeakerDTO speakerDTO = topicDTO.getSpeakerDTO();
        model.addAttribute("speaker", speakerDTO);
        model.addAttribute("topicId", topicDTO.getTopicId());
        return "edit/topic/speaker";
    }

    @PostMapping("/edit/topic/speaker")
    public String saveSpeaker (EditProfileDTO editProfileDTO, Integer topicId, @RequestParam("inpFile") MultipartFile photo) throws IOException {
        userService.updateSpeaker(editProfileDTO, photo);
        return "redirect:/topic/" + topicId;
    }

    @GetMapping("/edit/topic/info/{topicId}")
    public String getInfo (@PathVariable("topicId") Long topicId, Model model) throws TopicNotFoundException {
        TopicDTO topicDTO = topicService.getTopicDTOById(topicId);
        model.addAttribute("topic", topicDTO);
        return "edit/topic/info";
    }

    @PostMapping("/edit/topic/info")
    public String saveInfo (TopicDTO topicDTO) throws TopicNotFoundException {
        topicService.updateTopicInfo(topicDTO);
        return "redirect:/topic/" + topicDTO.getTopicId();
    }



}
