package ua.foxminded.universitycms.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.universitycms.models.Course;
import ua.foxminded.universitycms.models.Group;
import ua.foxminded.universitycms.models.Teacher;
import ua.foxminded.universitycms.service.CourseService;
import ua.foxminded.universitycms.service.GroupService;
import ua.foxminded.universitycms.service.TeacherService;

@Controller
@RequestMapping("/courses-page")
public class CourseController {
	
	private String REDIRECT_TO_ALLCOURSES = "redirect:/courses-page/";
	private String FORM = "course-form";
	private String FORM_ASSIGN = "assign-course";
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private GroupService groupService;
	
	@GetMapping("/")
	public String courses(Model model) throws SQLException {
		model.addAttribute("courses", courseService.getAllCourses());
		return "courses-page";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteCourse(@PathVariable("id") Long id) throws Exception {
		courseService.deleteCourse(id);
		return REDIRECT_TO_ALLCOURSES;
	}
	
	@GetMapping("/new")
	public String addCourse(Model model) throws SQLException {
		Course course = new Course();
		course.setCourseName("");
		course.setCourseDescription("");
		course.setTeacher(makeTeacherNONE());		
		List<Group> allGroups = groupService.getAllGroups();
		model.addAttribute("course", course);
		model.addAttribute("allGroups", allGroups);
		model.addAttribute("pageTitle", "Create a new course");
		return FORM;
	}
	
	@GetMapping("/edit/{id}")
	public String editCourse(@PathVariable("id") Long id, Model model) throws Exception {
		Optional<Course> course = courseService.getCourseById(id);
		if(course.isPresent()) {
			List<Group> allGroups = groupService.getAllGroups();
			if(course.get().getTeacher().getId() == null) {
				course.get().setTeacher(makeTeacherNONE());
			}
			model.addAttribute("allGroups", allGroups);
			model.addAttribute("course", course.get());
			model.addAttribute("pageTitle", "Edit course with id=" + id);	
			return FORM;
		} else {
			return REDIRECT_TO_ALLCOURSES;
		}	
	}
	
	@GetMapping("/assign/{id}")
	public String assignCourse(@PathVariable("id") Long id, Model model) throws Exception {
		Optional<Course> course = courseService.getCourseById(id);
		if(course.isPresent()) {
			List<Group> allGroups = groupService.getAllGroups();
			model.addAttribute("allGroups", allGroups);
			model.addAttribute("course", course.get());
			model.addAttribute("freeTeachers", findFreeTeacher(course.get()));
			model.addAttribute("pageTitle", "Assign/reassign course with id=" + id);	
			return FORM_ASSIGN;
		} else {
			return REDIRECT_TO_ALLCOURSES;
		}	
	}
	
	@PostMapping("/save")
	public String saveCourse(Course course) throws SQLException, Exception {
		courseService.saveCourse(course);
		return REDIRECT_TO_ALLCOURSES;
	}
	
	@PostMapping("/saveAssign")
	public String saveAssignCourse(Course course) throws SQLException, Exception {
		courseService.assignCourse(course);
		return REDIRECT_TO_ALLCOURSES;
	}

	private Teacher makeTeacherNONE() {
		Teacher teacherNONE = new Teacher();	
		teacherNONE.setTeacherName("NONE");
		teacherNONE.setTeacherSurname("NONE");
		return teacherNONE;
	}
	
	private List<Teacher> findFreeTeacher(Course course) throws SQLException{
		List<Teacher> freeTeachers = teacherService.getAllTeachers()
				.stream()
				.filter(t -> t.getCourse() == null)
				.collect(Collectors.toList());

		if(course.getTeacher().getId() == null) {
			course.setTeacher(makeTeacherNONE());
			freeTeachers.add(0, makeTeacherNONE());
		} else {
			freeTeachers.add(0, makeTeacherNONE());
		}
		return freeTeachers;
	}
}
