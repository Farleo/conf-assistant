package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.UserDTO;
import lms.itcluster.confassistant.exception.UserAlreadyExistException;
import lms.itcluster.confassistant.service.RoleService;
import lms.itcluster.confassistant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;


@Controller
public class AdminController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;



	@RequestMapping(value = "admin/users")
	public String adminGetAdd (Model model) {
		model.addAttribute("users", userService.getAllUsers());
		return "admin/manage-users";
	}

	@GetMapping(value = "/admin/users/new")
	private String addNewUserByAdmin (Model model){
		model.addAttribute("availableRoles", roleService.getAll().stream().map(r->r.getRole()).collect(toSet()));
		return "admin/add-user";
	}

	@PostMapping(value = "/admin/users/new")
	private String addNewUserByAdminPost (UserDTO userDTO, Model model) {
		
		try {
			userService.addNewUserByAdmin(userDTO);
		} catch (UserAlreadyExistException e) {
			return "redirect:/admin/users/new";
		}
		return "redirect:/admin/users";
	}

	@RequestMapping(value = "admin/users/delete/{id}")
	private String deleteUser(@PathVariable long id) {
		userService.deleteUser(id);
		return "redirect:/admin/users";
	}


	@RequestMapping("admin/users/edit/{id}")
	public String adminEditUser (@PathVariable("id") long id, Model model) {
		model.addAttribute("user", userService.findById(id));
		model.addAttribute("availableRoles", roleService.getAll().stream().map(r->r.getRole()).collect(toList()));
		return "admin/edit-user";
	}

	@PostMapping(value="admin/users/edit")
	public String editSave(UserDTO userDTO) {
		userService.updateUser(userDTO);
		return "redirect:/admin/users";
	
	}
}
