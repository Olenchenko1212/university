package ua.foxminded.universitycms.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.universitycms.service.CourseService;

@Controller
@RequestMapping("/home")
public class HomeController {
	private static String HOME = "home";
	private static String OUR_COURSES = "our-courses";
	
	@Autowired
	private CourseService courseService;
	
	@GetMapping
	public String home() {
		return HOME;
	}
	
	@GetMapping("/our-courses")
	public String ourCourses(Model model) throws SQLException {
		model.addAttribute("courses", courseService.getAllCourses());
		return OUR_COURSES;
	}
}
