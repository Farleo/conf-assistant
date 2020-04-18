package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.entity.Stream;
import lms.itcluster.confassistant.repository.StreamRepository;
import lms.itcluster.confassistant.service.EmailService;
import lms.itcluster.confassistant.dto.*;
import lms.itcluster.confassistant.exception.TopicNotFoundException;
import lms.itcluster.confassistant.model.Constant;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.repository.ParticipantsTypeRepository;
import lms.itcluster.confassistant.service.StaticDataService;
import lms.itcluster.confassistant.service.TopicService;
import lms.itcluster.confassistant.service.UserService;
import lms.itcluster.confassistant.util.SecurityUtil;
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

    @Autowired
    private ParticipantsTypeRepository participantsType;

    @Autowired
    private EmailService emailService;

    @Autowired
    private StreamRepository streamRepository;

    @GetMapping("/edit/profile")
    public String getCompleteSignUp (@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        if (currentUser == null) {
            return "redirect:/login";
        }
        if (SecurityUtil.userHasConfSpeakerRole(currentUser)) {
            return "redirect:/edit/speaker/main";
        }
        else {
            model.addAttribute("user", userService.getGuestProfileDTOById(currentUser.getId()));
            return "edit/profile";
        }
    }

    @PostMapping("/edit/profile")
    public String saveCompleteSignUp (@Valid @ModelAttribute("user") EditProfileDTO editProfileDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit/profile";
        }
        userService.completeGuestRegistration(editProfileDTO);
        return "redirect:/";
    }

    @GetMapping("/edit/speaker/main")
    public String getSpeaker (Model model, @AuthenticationPrincipal CurrentUser currentUser) throws IOException {
        model.addAttribute("speaker", userService.getSpeakerById(currentUser.getId()));
        return "edit/speaker/main";
    }

    @PostMapping("/edit/speaker/main")
    public String saveSpeaker (EditProfileDTO editProfileDTO, @RequestParam("inpFile") MultipartFile photo) throws IOException {
        userService.updateSpeaker(editProfileDTO, photo);
        return "redirect:/";
    }

    @GetMapping("/edit/speaker/contacts")
    public String getContacts (Model model, @AuthenticationPrincipal CurrentUser currentUser) throws IOException {
        model.addAttribute("speaker", userService.getSpeakerById(currentUser.getId()));
        return "edit/speaker/contacts";
    }

    @PostMapping("/edit/speaker/contacts")
    public String saveContacts (@Valid @ModelAttribute("speaker") EditContactsDTO contactsDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "edit/speaker/contacts";
        }

        return "redirect:/";
    }

    @GetMapping("/edit/speaker/password")
    public String getPassword (Model model, @AuthenticationPrincipal CurrentUser currentUser) throws IOException {
        model.addAttribute("speaker", new EditPasswordDTO(){{setUserId(currentUser.getId());}});
        return "edit/speaker/password";
    }

    @PostMapping("/edit/speaker/password")
    public String savePassword (@Valid @ModelAttribute("speaker") EditPasswordDTO passwordDTO, BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            return "edit/speaker/password";
        }
        boolean result = userService.updatePassword(passwordDTO);
        if (result) {
            model.addAttribute("message", "You have successfully updated password");
            return "message";
        }
        model.addAttribute("message", "Password not updated. Try again later");
        return "message";
    }

    @GetMapping("/edit/topic/main/{topicId}")
    public String getMain (@PathVariable("topicId") Long topicId, Model model, @AuthenticationPrincipal CurrentUser currentUser) throws TopicNotFoundException {
        TopicDTO topicDTO = topicService.getTopicDTOById(topicId);
        Stream stream = streamRepository.findByName(topicDTO.getStream());
        if (!SecurityUtil.canCurrentUserEditTopic(currentUser, stream, topicDTO)) {
            model.addAttribute("message", "You can't edit this topic!");
            return "message";
        }
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
    public String getSpeaker (@PathVariable("topicId") Long topicId, Model model, @AuthenticationPrincipal CurrentUser currentUser) throws TopicNotFoundException {
        if (SecurityUtil.userHasConfSpeakerRole(currentUser)) {
            return "redirect:/edit/speaker/main";
        }
        TopicDTO topicDTO = topicService.getTopicDTOById(topicId);
        SpeakerDTO speakerDTO = topicDTO.getSpeakerDTO();
        if (SecurityUtil.userHasAdminRole(currentUser)) {
            return "redirect:/admin/users/edit/" + speakerDTO.getUserId();
        }
        return "/";
    }

    @PostMapping("/edit/topic/speaker")
    public String saveSpeaker (EditProfileDTO editProfileDTO, Integer topicId, @RequestParam("inpFile") MultipartFile photo) throws IOException {
        userService.updateSpeaker(editProfileDTO, photo);
        return "redirect:/topic/" + topicId;
    }

    @GetMapping("/edit/topic/info/{topicId}")
    public String getInfo (@PathVariable("topicId") Long topicId, Model model, @AuthenticationPrincipal CurrentUser currentUser) throws TopicNotFoundException {
        TopicDTO topicDTO = topicService.getTopicDTOById(topicId);
        Stream stream = streamRepository.findByName(topicDTO.getStream());
        if (!SecurityUtil.canCurrentUserEditTopic(currentUser, stream, topicDTO)) {
            model.addAttribute("message", "You can't edit this topic!");
            return "message";
        }
        model.addAttribute("topic", topicDTO);
        return "edit/topic/info";
    }

    @PostMapping("/edit/topic/info")
    public String saveInfo (TopicDTO topicDTO) throws TopicNotFoundException {
        topicService.updateTopicInfo(topicDTO);
        return "redirect:/topic/" + topicDTO.getTopicId();
    }



}
