package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.EditContactsDTO;
import lms.itcluster.confassistant.dto.EditPasswordDTO;
import lms.itcluster.confassistant.dto.EditProfileDTO;
import lms.itcluster.confassistant.dto.EditTopicDTO;
import lms.itcluster.confassistant.exception.CantCompleteClientRequestException;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.service.ParticipantService;
import lms.itcluster.confassistant.service.TopicService;
import lms.itcluster.confassistant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
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
    private ParticipantService participantService;

    @GetMapping("/edit/profile/main")
    public String getSpeaker(Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        model.addAttribute("speaker", userService.getEditProfileDto(currentUser.getId()));
        return "edit/profile/main";
    }

    @PostMapping("/edit/profile/main")
    public String saveSpeaker(@Valid @ModelAttribute("speaker") EditProfileDTO speaker, BindingResult bindingResult, @RequestParam(value = "inpFile", required = false) MultipartFile photo) throws IOException {
        if (bindingResult.hasErrors()) {
            return "edit/profile/main";
        }
        try {
            userService.updateSpeaker(speaker, photo);
        } catch (CantCompleteClientRequestException e) {
            bindingResult.rejectValue("photo", "wrong.photo.format", "Use file with jpg, jpeg or png");
            return "edit/profile/main";
        }
        return "redirect:/";
    }

    @GetMapping("/edit/profile/contacts")
    public String getContacts(Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        model.addAttribute("speaker", userService.getSpeakerById(currentUser.getId()));
        return "edit/profile/contacts";
    }

    @PostMapping("/edit/profile/contacts")
    public String saveContacts(@Valid @ModelAttribute("speaker") EditContactsDTO contactsDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "edit/profile/contacts";
        }

        return "redirect:/";
    }

    @GetMapping("/edit/profile/password")
    public String getPassword(Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        model.addAttribute("speaker", new EditPasswordDTO() {{
            setUserId(currentUser.getId());
        }});
        return "edit/profile/password";
    }

    @PostMapping("/edit/profile/password")
    public String savePassword(@Valid @ModelAttribute("speaker") EditPasswordDTO passwordDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "edit/profile/password";
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
    public String getMain(@PathVariable("topicId") Long topicId, Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        EditTopicDTO topic = topicService.getEditTopicDTOById(topicId, currentUser);
        model.addAttribute("topic", topic);
        return "edit/topic/main";
    }

    @PostMapping("/edit/topic/main/{topicId}")
    public String saveMain(@PathVariable("topicId") Long topicId, @ModelAttribute("topic") @Valid EditTopicDTO topic, BindingResult bindingResult, @RequestParam("inpFile") MultipartFile photo) throws IOException {
        if (bindingResult.hasErrors()) {
            return "edit/topic/main";
        }
        try {
            topicService.updateMainTopicData(topic, photo);
        } catch (CantCompleteClientRequestException | MaxUploadSizeExceededException e) {
            bindingResult.rejectValue("coverPhoto", "wrong.photo.format", "Use file with jpg, jpeg or png");
            return "edit/topic/main";
        }
        return "redirect:/topic/" + topicId;
    }
}
