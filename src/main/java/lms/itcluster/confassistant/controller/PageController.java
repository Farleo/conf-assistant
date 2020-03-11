package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.entity.Conference;
import lms.itcluster.confassistant.entity.Guest;
import lms.itcluster.confassistant.entity.Question;
import lms.itcluster.confassistant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/")
    public String getList(Model model) {
        List<Conference> conferenceList = userService.getAllConferences();
        model.addAttribute("conferences", conferenceList);
        return "welcome";
    }

    @GetMapping("/home")
    public String getHome (Model model) {
        return "home";
    }

    @GetMapping("/hello")
    public String getHello (Model model) {
        return "hello";
    }

    @GetMapping("/login")
    public String getLogin (Model model) {
        return "login";
    }

    @GetMapping("/login-guest")
    public String getLoginGuest (Model model) {
        return "login-guest";
    }

    @GetMapping("/registration-guest")
    public String getRegistrationGuest (Model model) {
        model.addAttribute("guest", new Guest());
        return "registration-guest";
    }


    @GetMapping("/login-admin")
    public String getLoginAdmin (Model model) {
        return "login-admin";
    }

    @GetMapping("/login-failed")
    public String getLoginFailed (Model model) {
        return "login-guest";
    }

    @GetMapping("/ask-question")
    public String getQuestion (Model model) {
        return "ask-question";
    }

    @GetMapping("/create-moderator")
    public String getModerator (Model model) {
        return "create-moderator";
    }

}
