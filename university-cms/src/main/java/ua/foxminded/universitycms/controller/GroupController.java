package ua.foxminded.universitycms.controller;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.universitycms.models.Group;
import ua.foxminded.universitycms.service.GroupService;

@Controller
@RequestMapping("/groups-page")
public class GroupController {
	
	private String REDIRECT_TO_ALLGROUPS = "redirect:/groups-page/";
	private String FORM = "group-form";
	
	@Autowired
	private GroupService groupService;
	
	@GetMapping("/")
	public String groups(Model model) throws SQLException {
		model.addAttribute("groups", groupService.getAllGroups());
		return "groups-page";
	}
	
	@GetMapping("/new")
	public String addGroup(Model model) throws SQLException {
		Group group = new Group();
		group.setGroupName("");
		model.addAttribute("group", group);
		model.addAttribute("pageTitle", "Create a new group");
		return FORM;
	}
	
	@PostMapping("/save")
	public String saveGroup(Group group) throws SQLException, Exception {
		groupService.saveGroup(group);
		return REDIRECT_TO_ALLGROUPS;
	}
	
	@GetMapping("/delete/{id}")
	public String deleteGroup(@PathVariable("id") Long id) throws Exception {
		groupService.deleteGroup(id);
		return REDIRECT_TO_ALLGROUPS;
	}
	
	@GetMapping("/edit/{id}")
	public String editGroup(@PathVariable("id") Long id, Model model) throws Exception {
		Optional<Group> group = groupService.getGroupById(id);
		if(group.isPresent()) {
			model.addAttribute("group", group.get());
			model.addAttribute("pageTitle", "Edit group with id=" + id);	
			return FORM;
		} else {
			return REDIRECT_TO_ALLGROUPS;
		}	
	}
}
