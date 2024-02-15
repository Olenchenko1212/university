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

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import ua.foxminded.universitycms.models.Teacher;
import ua.foxminded.universitycms.service.CustomUserDetailsService;
import ua.foxminded.universitycms.service.TeacherService;
import ua.foxminded.universitycms.util.ConfigSecurity;
import ua.foxminded.universitycms.util.CustomSuccessHandler;

@Import({ConfigSecurity.class, CustomUserDetailsService.class})
@WebMvcTest(controllers = TeacherController.class)
public class TeacherControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomUserDetailsService customUserDetailsService;	
	@MockBean
	private CustomSuccessHandler successHandler;
	@MockBean
	private TeacherService teacherService;

	@Test
	@WithMockUser(roles={"ADMIN", "STUFF", "STUDENT", "TEACHER"})
	void whenGetTeachersNotEmptyExpectTeachersViewWithTwoTeachers() throws Exception {
		List<Teacher> teachers = new ArrayList<>();
		teachers.add(new Teacher(1L, "Eva", "Brown"));
		teachers.add(new Teacher(2L, "David", "Davis"));

		when(teacherService.getAllTeachers()).thenReturn(teachers);
		mockMvc.perform(get("/teachers/")).andExpect(status().isOk()).andExpect(view().name("teachers"))
				.andExpect(model().attributeExists("teachers")).andExpect(model().attribute("teachers", teachers))
				.andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(content().string(containsString("Brown")))
				.andExpect(content().string(containsString("Davis")));
	}

	@Test
	@WithMockUser(roles={"ADMIN", "STUFF", "STUDENT", "TEACHER"})
	void whenGetGroupsIsEmptyExpectGroupsViewWithNoGroups() throws Exception {
		List<Teacher> teachers = new ArrayList<>();

		when(teacherService.getAllTeachers()).thenReturn(teachers);
		mockMvc.perform(get("/teachers/")).andExpect(status().isOk()).andExpect(view().name("teachers"))
				.andExpect(model().attributeExists("teachers")).andExpect(model().attribute("teachers", empty()))
				.andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(content().string(not(containsString("Brown"))))
				.andExpect(content().string(not(containsString("Davis"))));
	}
}
