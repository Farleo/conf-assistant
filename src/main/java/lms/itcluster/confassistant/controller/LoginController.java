package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.form.UserForm;
import lms.itcluster.confassistant.model.Constant;
import lms.itcluster.confassistant.repository.RolesRepository;
import lms.itcluster.confassistant.service.UserService;
import lms.itcluster.confassistant.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private RolesRepository rolesRepository;

    @GetMapping("/login")
    public String getLoginHands (HttpServletRequest request, Model model) {
        model.addAttribute("loginForm", new UserForm(){{setReferer(request.getHeader("referer"));}});
        return "login/login";
    }

    @PostMapping("/login")
    public String saveLoginHands (@ModelAttribute UserForm userForm, Model model) {
        User user = userService.findByEmail(userForm.getEmail());
                if (user!=null && user.getRoles().contains(rolesRepository.findByRole(Constant.GUEST))) {
            SecurityUtil.authenticate(user);
            return "redirect:/" + userForm.getReferer().substring(22);
        }
        model.addAttribute("loginForm", userForm);
        return "login/password";
    }

    @GetMapping("/sign-up-guest")
    public String getLoginGuest (Model model) {
        model.addAttribute("userForm", new UserForm());
        return "login/sign-up-guest";
    }

    @PostMapping("/sign-up-guest")
    public String saveGuest (@ModelAttribute UserForm userForm) {
        User user = userService.createNewUser(userForm);
        SecurityUtil.authenticate(user);
        return "redirect:/";
    }

    @GetMapping("/login-failed")
    public String getLoginFailed () {
        return "failed/failed";
    }
}
