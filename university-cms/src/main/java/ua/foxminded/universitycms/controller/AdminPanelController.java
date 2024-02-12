package ua.foxminded.universitycms.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.universitycms.models.Role;
import ua.foxminded.universitycms.models.User;
import ua.foxminded.universitycms.service.RoleService;
import ua.foxminded.universitycms.service.UserService;

@Controller
@RequestMapping("/admin-panel")
public class AdminPanelController {
	
	private String REDIRECT_TO_ADMIN_PANEL = "redirect:/admin-panel";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping
	public String adminPage(Model model) throws SQLException {
		List<User> users = userService.getAllUsers();
		model.addAttribute("users", users);
		return "admin-panel";
	}
	
	@GetMapping("/delete/{userId}")
	public String deleteUser(@PathVariable("userId") Long userId) throws SQLException {
		userService.deleteUser(userId);
		return REDIRECT_TO_ADMIN_PANEL;
	}
	
	@GetMapping("/edit-roles/{userId}")
	public String updateRoles(@PathVariable("userId") Long userId, Model model) throws SQLException {
		User user = userService.findById(userId);
		List<Role> allRoles = roleService.getAllRoles();
		model.addAttribute("user", user);
		model.addAttribute("allRoles", allRoles);
		return "edit-roles";
	}
	
	@PostMapping("/save")
	public String saveRoles(User user) throws SQLException {
		userService.updateUserRole(user);
		return REDIRECT_TO_ADMIN_PANEL;
	}
}
