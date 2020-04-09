package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.EditProfileDTO;
import lms.itcluster.confassistant.dto.SignUpDTO;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
        return "redirect:/complete-sign-up-guest";
    }

    @GetMapping("/complete-sign-up-guest")
    public String getCompleteSignUp (@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        if (currentUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", userService.getGuestProfileDTOById(currentUser.getId()));
        return "login/complete-sign-up-guest";
    }

    @PostMapping("/complete-sign-up-guest")
    public String saveCompleteSignUp (@Valid @ModelAttribute("user") EditProfileDTO editProfileDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login/complete-sign-up-guest";
        }
        userService.completeGuestRegistration(editProfileDTO);
        return "redirect:/";
    }

}
