package ua.foxminded.universitycms.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import ua.foxminded.universitycms.models.Role;
import ua.foxminded.universitycms.models.Teacher;
import ua.foxminded.universitycms.models.User;
import ua.foxminded.universitycms.service.CustomUserDetailsService;
import ua.foxminded.universitycms.service.TeacherService;
import ua.foxminded.universitycms.service.UserService;
import ua.foxminded.universitycms.util.ConfigSecurity;
import ua.foxminded.universitycms.util.CustomSuccessHandler;

@Import({ConfigSecurity.class, CustomUserDetailsService.class})
@WebMvcTest(controllers = TeacherController.class)
public class TeacherControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;
	@MockBean
	private CustomUserDetailsService customUserDetailsService;	
	@MockBean
	private CustomSuccessHandler successHandler;
	@MockBean
	private TeacherService teacherService;

	@Test
	@WithMockUser(username = "user6", roles={"ADMIN", "STUFF", "STUDENT", "TEACHER"})
	void whenGetTeachersNotEmptyExpectTeachersViewWithTwoTeachers() throws Exception {
		List<Teacher> teachers = new ArrayList<>();
		Teacher teacher = new Teacher(1L, "Bob", "Mars");
		teachers.add(teacher);
		
		User user1 = new User();
		List<Role> roles1 = new ArrayList<>();
		roles1.add(new Role("ROLE_STUDENT"));
		roles1.add(new Role("ROLE_USER"));
		user1.setRoles(roles1);
		user1.setId(5L);
		user1.setUsername("user6");
		user1.setPassword("rfregr234234");
		teacher.setUser(user1);

		when(teacherService.getAllTeachers()).thenReturn(teachers);
		when(userService.findByName("user6")).thenReturn(user1);
		mockMvc.perform(get("/teachers/")).andExpect(status().isOk()).andExpect(view().name("teachers"))
			.andExpect(model().attributeExists("teachers")).andExpect(model().attribute("teachers", teachers))
			.andExpect(model().attributeExists("currentUserId")).andExpect(model().attribute("currentUserId", user1.getId()))
			.andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
			.andExpect(content().string(containsString("Bob")));
	}

	@Test
	@WithMockUser(username = "user6", roles={"ADMIN", "STUFF", "STUDENT", "TEACHER"})
	void whenGetTeachersIsEmptyExpectTeachersViewWithNoTeachers() throws Exception {
		List<Teacher> teachers = new ArrayList<>();
		
		User user1 = new User();
		List<Role> roles1 = new ArrayList<>();
		roles1.add(new Role("ROLE_STUDENT"));
		roles1.add(new Role("ROLE_USER"));
		user1.setRoles(roles1);
		user1.setId(5L);
		user1.setUsername("user6");
		user1.setPassword("rfregr234234");

		when(teacherService.getAllTeachers()).thenReturn(teachers);
		when(userService.findByName("user6")).thenReturn(user1);
		mockMvc.perform(get("/teachers/")).andExpect(status().isOk()).andExpect(view().name("teachers"))
			.andExpect(model().attributeExists("teachers")).andExpect(model().attribute("teachers", empty()))
			.andExpect(model().attributeExists("currentUserId")).andExpect(model().attribute("currentUserId", user1.getId()))
			.andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
			.andExpect(content().string(not(containsString("Bob"))));
	}
	
	@Test
	@WithMockUser(roles={"TEACHER"})
	void whenTeacherEditExpectTeacherEditView() throws Exception {
		Teacher teacher = new Teacher(1L, "Bob", "Mars");
		
		when(teacherService.getTeacherById(teacher.getId())).thenReturn(Optional.of(teacher));
		mockMvc.perform(get("/teachers/edit/{id}", teacher.getId())
				.contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(view().name("teacher-form"))
				.andExpect(model().attributeExists("teacher"))
				.andExpect(model().attribute("teacher", teacher));
	}
	
	@Test
	@WithMockUser(roles={"TEACHER"})
	void whenTeacherEditButNotFoundExpectTeachersView() throws Exception {
		Teacher teacher = new Teacher(1L, "Bob", "Mars");
		
		when(teacherService.getTeacherById(teacher.getId())).thenReturn(Optional.empty());
		mockMvc.perform(get("/teachers/edit/{id}", teacher.getId())
				.contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(view().name("redirect:/teachers/"));
	}
	
	
}
