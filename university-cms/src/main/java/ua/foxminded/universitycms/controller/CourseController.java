package ua.foxminded.universitycms.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.universitycms.service.CourseService;

@Controller
@RequestMapping("/courses-page")
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	
	@GetMapping("/allCourses")
	public String courses(Model model) throws SQLException {
		model.addAttribute("courses", courseService.getAllCourses());
		return "courses-page";
	}
}
