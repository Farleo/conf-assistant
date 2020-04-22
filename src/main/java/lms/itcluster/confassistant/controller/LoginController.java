package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.SignUpDTO;
import lms.itcluster.confassistant.dto.UserDTO;
import lms.itcluster.confassistant.exception.UserAlreadyExistException;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;

@Slf4j
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
    public String saveGuest (@Valid @ModelAttribute("signForm") SignUpDTO signForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "login/sign-up-guest";
        }
        try {
            userService.createNewUserAsGuest(signForm);
        } catch (DataIntegrityViolationException e) {
            bindingResult.rejectValue("email", "user.already.exist", "User with this email address already exist");
            return "login/sign-up-guest";
        }
        return "confirm-email";
    }

    @GetMapping("/active/{code}")
    public String activeProfile(@PathVariable("code") String code, @AuthenticationPrincipal CurrentUser currentUser) {
        UserDTO userDTO = userService.findByActivationCode(code, currentUser.getId());
        if (userDTO != null) {
            return "redirect:/edit/profile/main";
        } else {
            return "not-found";
        }
    }

}
