package lms.itcluster.confassistant.controller;

import lms.itcluster.confassistant.dto.UserDTO;
import lms.itcluster.confassistant.exception.UserAlreadyExistException;
import lms.itcluster.confassistant.service.RoleService;
import lms.itcluster.confassistant.service.UserService;
import lms.itcluster.confassistant.validator.UploadPhotoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;


@Controller
public class AdminController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	@Autowired
	private UploadPhotoValidator uploadPhotoValidator;



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
	                                      BindingResult bindingResult,
	                                      @RequestParam("inpFile") MultipartFile photo,
	                                      Model model) {
		uploadPhotoValidator.validate(photo, bindingResult);
		if(bindingResult.hasErrors()){
			model.addAttribute("availableRoles", roleService.getAll().stream().map(r->r.getRole()).collect(toSet()));
			return "admin/add-user";
		}
		try {
			userService.addNewUserByAdmin(userDTO, photo.getBytes(), photo.getOriginalFilename());
		} catch (UserAlreadyExistException | IOException e) {
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
		model.addAttribute("userDTO", userService.getUserDtoById(id));
		model.addAttribute("availableRoles", roleService.getAll().stream().map(r->r.getRole()).collect(toList()));
		return "admin/edit-user";
	}

	@PostMapping(value="admin/users/edit/{id}")
	public String editSave(@PathVariable("id") long id,
	                       @ModelAttribute @Valid UserDTO userDTO,
	                       BindingResult bindingResult,
	                       @RequestParam("inpFile") MultipartFile photo,
	                       Model model) {
		model.addAttribute("userDTO", userDTO);
		uploadPhotoValidator.validate(photo, bindingResult);
		if(bindingResult.hasErrors()){
			model.addAttribute("availableRoles", roleService.getAll().stream().map(r->r.getRole()).collect(toList()));
			return "admin/edit-user";
		}
		try {
			userService.updateUser(userDTO, photo.getBytes(), photo.getOriginalFilename());
		}
		catch (UserAlreadyExistException | IOException e) {
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
