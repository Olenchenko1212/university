package ua.foxminded.universitycms.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.universitycms.service.GroupService;

@Controller
@RequestMapping("/groups-page")
public class GroupController {
	
	@Autowired
	private GroupService groupService;
	
	@GetMapping("/allGroups")
	public String groups(Model model) throws SQLException {
		model.addAttribute("groups", groupService.getAllGroups());
		return "groups-page";
	}
}