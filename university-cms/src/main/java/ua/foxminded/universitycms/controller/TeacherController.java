package ua.foxminded.universitycms.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ua.foxminded.universitycms.service.TeacherService;

@Controller
public class TeacherController {
	
	@Autowired
	private TeacherService teacherService;
	
	@GetMapping("/teachers-page")
	public String teachers(Model model) throws SQLException {
		model.addAttribute("teachers", teacherService.getAllTeachers());
		return "teachers-page";
	}
}
