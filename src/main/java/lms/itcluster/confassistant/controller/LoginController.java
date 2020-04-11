package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.EditProfileDTO;
import lms.itcluster.confassistant.dto.SignUpDTO;
import lms.itcluster.confassistant.dto.UserDTO;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String getLoginHands (@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null) {
            return "redirect:/";
        }
        return "login/login";
    }

    @GetMapping("/sign-up-guest")
    public String getLoginGuest (@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        if (currentUser != null) {
            return "redirect:/";
        }
        model.addAttribute("signForm", new SignUpDTO());
        return "login/sign-up-guest";
    }

    @PostMapping("/sign-up-guest")
    public String saveGuest (@Valid @ModelAttribute("signForm") SignUpDTO signForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login/sign-up-guest";
        }
        userService.createNewUserAsGuest(signForm);
        return "confirm-email";
    }

    @GetMapping("/active/{code}")
    public String activeProfile(@PathVariable("code") String code, @AuthenticationPrincipal CurrentUser currentUser) {
        UserDTO userDTO = userService.findByActivationCode(code);
        if (userDTO != null) {
            return "redirect:/edit/profile";
        } else {
            return "not-found";
        }
    }

}
