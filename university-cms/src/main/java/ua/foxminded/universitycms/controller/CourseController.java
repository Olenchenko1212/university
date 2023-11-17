package ua.foxminded.universitycms.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ua.foxminded.universitycms.service.CourseService;

@Controller("/courses-page")
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	
	@GetMapping("/courses-page")
	public String courses(Model model) throws SQLException {
		model.addAttribute("courses", courseService.getAllCourses());
		return "courses-page";
	}
}
