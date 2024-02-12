package ua.foxminded.universitycms.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import ua.foxminded.universitycms.models.Course;
import ua.foxminded.universitycms.models.Group;
import ua.foxminded.universitycms.models.Teacher;
import ua.foxminded.universitycms.service.CourseService;
import ua.foxminded.universitycms.service.CustomUserDetailsService;
import ua.foxminded.universitycms.service.GroupService;
import ua.foxminded.universitycms.service.TeacherService;
import ua.foxminded.universitycms.util.ConfigSecurity;
import ua.foxminded.universitycms.util.CustomSuccessHandler;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Import({ConfigSecurity.class, CustomUserDetailsService.class})
@WebMvcTest(controllers = CourseController.class)
class CourseControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CustomUserDetailsService customUserDetailsService;
	
	@MockBean
	private CustomSuccessHandler successHandler;

	@MockBean
	private CourseService courseService;
	
	@MockBean
	private TeacherService teacherService;
	
	@MockBean
	private GroupService groupService;

	@Test
	@WithMockUser(roles={"ADMIN", "STUFF", "STUDENT", "TEACHER"})
	void whenGetCoursesNotEmptyExpectCoursesViewWithTwoCourses() throws Exception {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1L, "Mathematics", "Basic mathematics course"));
		courses.add(new Course(2L, "History", "Introduction to World History"));

		when(courseService.getAllCourses()).thenReturn(courses);
		mockMvc.perform(get("/courses-page/"))
				.andExpect(status().isOk()).andExpect(view().name("courses-page"))
				.andExpect(model().attributeExists("courses")).andExpect(model().attribute("courses", courses))
				.andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(content().string(containsString("Mathematics")))
				.andExpect(content().string(containsString("History")));
	}

	@Test
	@WithMockUser(roles={"ADMIN", "STUFF", "STUDENT", "TEACHER"})
	void whenGetCoursesIsEmptyExpectCoursesViewWithNoCourses() throws Exception {
		List<Course> courses = new ArrayList<>();

		when(courseService.getAllCourses()).thenReturn(courses);
		mockMvc.perform(get("/courses-page/")).andExpect(status().isOk()).andExpect(view().name("courses-page"))
				.andExpect(model().attributeExists("courses")).andExpect(model().attribute("courses", empty()))
				.andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(content().string(not(containsString("Mathematics"))))
				.andExpect(content().string(not(containsString("History"))));
	}
	
	@Test
	@WithMockUser(roles={"ADMIN", "STUFF"})
	void whenCourseCreateExpectNewCourseView() throws Exception {
		Course course = new Course();
		course.setCourseName("");
		course.setCourseDescription("");
		List<Group> allGroups = new ArrayList<>();
		allGroups.add(new Group(1L,"er-45"));
		Teacher teacherNONE = new Teacher();	
		teacherNONE.setTeacherName("NONE");
		teacherNONE.setTeacherSurname("NONE");
		course.setTeacher(teacherNONE);

		when(groupService.getAllGroups()).thenReturn(allGroups);
		mockMvc.perform(get("/courses-page/new")).andExpect(status().isOk()).andExpect(view().name("course-form"))
				.andExpect(model().attributeExists("allGroups")).andExpect(model().attribute("allGroups", allGroups))
				.andExpect(model().attributeExists("course")).andExpect(model().attribute("course", course))
				.andExpect(model().attributeExists("pageTitle")).andExpect(model().attribute("pageTitle", "Create a new course"))
				.andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(content().string(containsString("NONE")))
				.andExpect(content().string(containsString("Create a new course")));
	}
	
	@Test
	@WithMockUser(roles={"ADMIN", "STUFF"})
	void whenCourseSaveExpectNewCourseSaveInDB() throws Exception {
		Course course = new Course();
		course.setId(1L);
		course.setCourseName("Bobby");
		course.setCourseDescription("Mars");
		Teacher teacherNONE = new Teacher();	
		teacherNONE.setTeacherName("NONE");
		teacherNONE.setTeacherSurname("NONE");
		course.setTeacher(teacherNONE);

		doNothing().when(courseService).saveCourse(course);
		mockMvc.perform(post("/courses-page/save")
				.contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8")
				.param("id", "1")
				.param("courseName", "Bobby")
				.param("courseDescription", "Mars"))
				.andExpect(status().is3xxRedirection())
				.andExpect(header().string("Location", "/courses-page/"));
	}
	
	@Test
	@WithMockUser(roles="ADMIN")
	void whenCourseDeleteExpectCourseDeleteFromDB() throws Exception {
		Course course = new Course();
		course.setId(1L);
		course.setCourseName("Bobby");
		course.setCourseDescription("Mars");
		Teacher teacherNONE = new Teacher();	
		teacherNONE.setTeacherName("NONE");
		teacherNONE.setTeacherSurname("NONE");
		course.setTeacher(teacherNONE);

		doNothing().when(courseService).deleteCourse(course.getId());
		mockMvc.perform(get("/courses-page/delete/{id}", course.getId())
				.contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8")
				.param("id", "1")
				.param("courseName", "Bobby")
				.param("courseDescription", "Mars"))
				.andExpect(status().is3xxRedirection())
				.andExpect(header().string("Location", "/courses-page/"));
	}
	
	@Test
	@WithMockUser(roles={"ADMIN", "STUFF"})
	void whenCourseEditExpectCourseFormView() throws Exception {
		Course course = new Course();
		course.setId(1L);
		course.setCourseName("Bobby");
		course.setCourseDescription("Mars");
		Teacher teacherNONE = new Teacher();	
		teacherNONE.setTeacherName("NONE");
		teacherNONE.setTeacherSurname("NONE");
		course.setTeacher(teacherNONE);

		when(courseService.getCourseById(course.getId())).thenReturn(Optional.of(course));
		mockMvc.perform(get("/courses-page/edit/{id}", course.getId())
				.contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(view().name("course-form"))
				.andExpect(model().attributeExists("course"))
				.andExpect(model().attribute("course", course));
	}
	
	@Test
	@WithMockUser(roles={"ADMIN", "STUFF"})
	void whenCourseEditButNotFindInDBExpectAllCoursesView() throws Exception {
		Course course = new Course();
		course.setId(1L);

		when(courseService.getCourseById(course.getId())).thenReturn(Optional.empty());
		mockMvc.perform(get("/courses-page/edit/{id}", course.getId())
				.contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(view().name("redirect:/courses-page/"));
	}
	
	@Test
	@WithMockUser(roles="STUFF")
	void whenCourseAssignExpectFormAssignView() throws Exception {
		Course course = new Course();
		course.setId(1L);
		course.setCourseName("Bobby");
		course.setCourseDescription("Mars");
		Teacher teacherNONE = new Teacher();	
		teacherNONE.setTeacherName("NONE");
		teacherNONE.setTeacherSurname("NONE");
		course.setTeacher(teacherNONE);

		when(courseService.getCourseById(course.getId())).thenReturn(Optional.of(course));
		mockMvc.perform(get("/courses-page/assign/{id}", course.getId())
				.contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(view().name("assign-course"))
				.andExpect(model().attributeExists("course"))
				.andExpect(model().attribute("course", course));
	}
	
	@Test
	@WithMockUser(roles="STUFF")
	void whenCourseAssignNotFindExpectAllCoursesView() throws Exception {
		Course course = new Course();
		course.setId(1L);

		when(courseService.getCourseById(course.getId())).thenReturn(Optional.empty());
		mockMvc.perform(get("/courses-page/assign/{id}", course.getId())
				.contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(view().name("redirect:/courses-page/"));
	}
}
