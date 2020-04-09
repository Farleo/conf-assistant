package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.*;
import lms.itcluster.confassistant.exception.TopicNotFoundException;
import lms.itcluster.confassistant.service.StaticDataService;
import lms.itcluster.confassistant.service.TopicService;
import lms.itcluster.confassistant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class EditController {

    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private StaticDataService staticDataService;

    @GetMapping("/edit/topic/main/{topicId}")
    public String getMain (@PathVariable("topicId") Long topicId, Model model) throws TopicNotFoundException {
        TopicDTO topicDTO = topicService.getTopicDTOById(topicId);
        model.addAttribute("topic", topicDTO);
        model.addAttribute("month", staticDataService.getMonthMap());
        model.addAttribute("years", staticDataService.getYears());
        model.addAttribute("days", staticDataService.getDays(topicDTO.getDate().getYear(), topicDTO.getDate().getMonthValue()));
        return "main";
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
        return "speaker";
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
        return "info";
    }

    @PostMapping("/edit/topic/info")
    public String saveInfo (TopicDTO topicDTO) throws TopicNotFoundException {
        topicService.updateTopicInfo(topicDTO);
        return "redirect:/topic/" + topicDTO.getTopicId();
    }



}
