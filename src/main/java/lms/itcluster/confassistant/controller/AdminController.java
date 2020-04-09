package lms.itcluster.confassistant.controller;

import com.sun.xml.bind.v2.TODO;
import lms.itcluster.confassistant.dto.UserDTO;
import lms.itcluster.confassistant.exception.UserAlreadyExistException;
import lms.itcluster.confassistant.service.RoleService;
import lms.itcluster.confassistant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

	@GetMapping(value = "/admin/users/create")
	private String addNewUserByAdmin (Model model){
		model.addAttribute("availableRoles", roleService.getAll().stream().map(r->r.getRole()).collect(toSet()));
		model.addAttribute(new UserDTO());
		return "admin/add-user";
	}

	@PostMapping(value = "/admin/users/create")
	private String addNewUserByAdminPost (@ModelAttribute @Valid UserDTO userDTO,
	                                      Errors errors, Model model) {
		if(errors.hasErrors()){
			model.addAttribute("availableRoles", roleService.getAll().stream().map(r->r.getRole()).collect(toSet()));
			return "admin/add-user";
		}
		try {
			userService.addNewUserByAdmin(userDTO);
		} catch (UserAlreadyExistException e) {
			model.addAttribute("isExistEmail", true);
			model.addAttribute("availableRoles", roleService.getAll().stream().map(r->r.getRole()).collect(toSet()));
			return "admin/add-user";
		}
		return "redirect:/admin/users";
	}

	@RequestMapping(value = "admin/users/delete/{id}")
	private String deleteUser(@PathVariable long id) {
		userService.deleteUser(id);
		return "redirect:/admin/users";
	}


	@GetMapping("admin/users/edit/{id}")
	public String adminEditUser (@PathVariable("id") long id, Model model) {
		model.addAttribute("userDTO", userService.findById(id));
		model.addAttribute("availableRoles", roleService.getAll().stream().map(r->r.getRole()).collect(toList()));
		return "admin/edit-user";
	}

	@PostMapping(value="admin/users/edit/{id}")
	public String editSave(@PathVariable("id") long id,
	                       @ModelAttribute @Valid UserDTO userDTO,
	                       Errors errors, Model model) {
		model.addAttribute("userDTO", userDTO);
		if(errors.hasErrors()){
			model.addAttribute("availableRoles", roleService.getAll().stream().map(r->r.getRole()).collect(toList()));
			return "admin/edit-user";
		}
		try {
			userService.updateUser(userDTO);
		}
		catch (UserAlreadyExistException e) {
			model.addAttribute("badEmail", true);
			model.addAttribute("availableRoles", roleService.getAll().stream().map(r->r.getRole()).collect(toList()));
			return "admin/edit-user";
		}
		return "redirect:/admin/users";
	}
	//TODO
	@GetMapping("admin/")
	public String adminMainPage (Model model) {
//		model.addAttribute("userDTO", userService.findById(id));
//		model.addAttribute("availableRoles", roleService.getAll().stream().map(r->r.getRole()).collect(toList()));
		return "admin/admin-main.html";
	}
}
