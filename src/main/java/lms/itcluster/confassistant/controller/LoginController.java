package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.UserDTO;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.exception.UserAlreadyExistException;
import lms.itcluster.confassistant.model.CurrentUser;
import lms.itcluster.confassistant.repository.RolesRepository;
import lms.itcluster.confassistant.service.UserService;
import lms.itcluster.confassistant.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;

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
    public String getLoginGuest (@AuthenticationPrincipal CurrentUser currentUser, HttpSession session) {
        if (currentUser != null) {
            return "redirect:/";
        }
        return "login/sign-up-guest";
    }

    @PostMapping("/sign-up-guest")
    public String saveGuest (UserDTO userDto, HttpSession session) {
        try {
            User user = userService.createNewUserAsGuest(userDto);
            SecurityUtil.authenticate(user);
            return "redirect:/complete-sign-up-guest";
        } catch (UserAlreadyExistException e) {
            session.setAttribute("isUserPresent", true);
            return "redirect:/sign-up-failed";
        }
    }

    @GetMapping("/complete-sign-up-guest")
    public String getCompleteSignUp (@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        UserDTO userDto = userService.getUserDTOById(currentUser.getId());
        if (userDto != null) {
            return "redirect:/login";
        }
        model.addAttribute("user", userDto);
        return "login/complete-sign-up-guest";
    }

    @PostMapping("/complete-sign-up-guest")
    public String saveCompleteSignUp (UserDTO userDto) {
        userService.completeGuestRegistration(userDto);
        return "redirect:/";
    }

    @GetMapping("/sign-up-failed")
    public String getLoginFailed (HttpSession session) {
        if (session.getAttribute("userAlreadyExist") == null) {
            return "redirect:/sign-up-guest";
        }
        return "login/sign-up-guest";
    }
}
