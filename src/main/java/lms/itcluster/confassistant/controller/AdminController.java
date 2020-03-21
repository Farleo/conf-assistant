package lms.itcluster.confassistant.controller;
import lms.itcluster.confassistant.entity.User;
import lms.itcluster.confassistant.form.*;
import lms.itcluster.confassistant.service.RoleService;
import lms.itcluster.confassistant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

@Autowired
private RoleService roleService;

@Autowired
private UserService userService;


@RequestMapping(value = "admin/users")
public String adminGetAdd (Model model) {
	model.addAttribute("users", new ManageUsersForm(userService.getAllUsers()));
	return "admin/manage-users";
}

@RequestMapping(value = "admin/users/delete/{id}")
private String deleteUser(@PathVariable long id){
	userService.deleteUser(id);
	return "redirect:/admin/users";
}


@RequestMapping("admin/users/edit/{id}")
public String adminEditUser (@PathVariable("id") long id, Model model) {
	model.addAttribute("user", userService.findById(id));
	model.addAttribute("availableRoles", roleService.getAll());
	return "admin/edit-user";
}

@PostMapping(value="admin/users/editSave")
public String editSave(User user){
	userService.update(user);
	return "redirect:/admin/users";
	
}

}
