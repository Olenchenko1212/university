package ua.foxminded.universitycms.controller;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.universitycms.models.Teacher;
import ua.foxminded.universitycms.service.TeacherService;
import ua.foxminded.universitycms.service.UserService;

@Controller
@RequestMapping("/teachers")
public class TeacherController {
	
	private String REDIRECT_TO_ALLTEACHERS = "redirect:/teachers/";
	private String FORM = "teacher-form";
	
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String teachers(Model model) throws SQLException {
		model.addAttribute("currentUserId", getCurrentUserId());
		model.addAttribute("teachers", teacherService.getAllTeachers());
		return "teachers";
	}
	
	@GetMapping("/edit/{id}")
	public String editTeacher(@PathVariable("id") Long id, Model model) throws Exception {
		Optional<Teacher> teacher = teacherService.getTeacherById(id);
		if (teacher.isPresent()) {			
			model.addAttribute("teacher", teacher.get());
			model.addAttribute("pageTitle", "Edit teacher with id=" + id);
			return FORM;
		} else {
			return REDIRECT_TO_ALLTEACHERS;
		}
	}
	
	@PostMapping("/save")
	public String saveTeacher(Teacher teacher) throws SQLException, Exception {
		teacherService.saveTeacher(teacher);
		return REDIRECT_TO_ALLTEACHERS;
	}
	
	private Long getCurrentUserId() throws SQLException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return userService.findByName(authentication.getName()).getId();
	}
}
