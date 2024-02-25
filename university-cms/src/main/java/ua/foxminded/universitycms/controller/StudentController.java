package ua.foxminded.universitycms.controller;

import java.sql.SQLException;
import java.util.List;
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

import ua.foxminded.universitycms.models.Group;
import ua.foxminded.universitycms.models.Student;
import ua.foxminded.universitycms.service.GroupService;
import ua.foxminded.universitycms.service.StudentService;
import ua.foxminded.universitycms.service.UserService;

@Controller
@RequestMapping("/students")
public class StudentController {

	private String REDIRECT_TO_ALLSTUDENTS = "redirect:/students/";
	private String FORM = "student-form";
	private String FORM_ASSIGN = "assign-student";

	@Autowired
	private StudentService studentService;
	@Autowired
	private UserService userService;
	@Autowired
	private GroupService groupService;

	@GetMapping("/")
	public String students(Model model) throws SQLException {
		List<Student> students = studentService.getAllStudents();
		for (Student student : students) {
			if (student.getGroup() == null) {
				student.setGroup(new Group(-1L, "NONE"));
			}
		}
		model.addAttribute("students", students);
		model.addAttribute("currentUserId", getCurrentUserId());
		return "students";
	}

	@GetMapping("/edit/{id}")
	public String editStudent(@PathVariable("id") Long id, Model model) throws Exception {
		Optional<Student> student = studentService.getStudentById(id);
		if (student.isPresent()) {
			if (student.get().getGroup() == null) {
				student.get().setGroup(new Group(-1L, "NONE"));
			}
			List<Group> groups = groupService.getAllGroups();
			groups.add(0, new Group(-1L, "NONE"));
			model.addAttribute("student", student.get());
			model.addAttribute("allGroups", groups);
			model.addAttribute("pageTitle", "Edit student with id=" + id);
			return FORM;
		} else {
			return REDIRECT_TO_ALLSTUDENTS;
		}
	}

	@GetMapping("/assign/{id}")
	public String assignStudent(@PathVariable("id") Long id, Model model) throws Exception {
		Optional<Student> student = studentService.getStudentById(id);
		if (student.isPresent()) {
			if (student.get().getGroup() == null) {
				student.get().setGroup(new Group(-1L, "NONE"));
			}
			List<Group> groups = groupService.getAllGroups();
			groups.add(0, new Group(-1L, "NONE"));
			model.addAttribute("student", student.get());
			model.addAttribute("allGroups", groups);
			model.addAttribute("pageTitle", "Assign/reassign student with id=" + id);
			return FORM_ASSIGN;
		} else {
			return REDIRECT_TO_ALLSTUDENTS;
		}
	}

	@PostMapping("/save")
	public String saveStudent(Student student) throws SQLException, Exception {
		studentService.saveStudent(student);
		return REDIRECT_TO_ALLSTUDENTS;
	}

	@PostMapping("/saveAssign")
	public String assignStudent(Student student) throws SQLException, Exception {
		studentService.assignStudent(student);
		return REDIRECT_TO_ALLSTUDENTS;
	}

	private Long getCurrentUserId() throws SQLException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return userService.findByName(authentication.getName()).getId();
	}
}
