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

import ua.foxminded.universitycms.models.Student;
import ua.foxminded.universitycms.service.CustomUserDetailsService;
import ua.foxminded.universitycms.service.StudentService;
import ua.foxminded.universitycms.util.ConfigSecurity;
import ua.foxminded.universitycms.util.CustomSuccessHandler;

@Import({ConfigSecurity.class, CustomUserDetailsService.class})
@WebMvcTest(controllers = StudentController.class)
class StudentControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CustomUserDetailsService customUserDetailsService;	
	@MockBean
	private CustomSuccessHandler successHandler;
	@MockBean
	private StudentService studentService;

	@Test
	@WithMockUser(roles={"ADMIN", "STUFF", "STUDENT", "TEACHER"})
	void whenGetStudentsNotEmptyExpectStudentsViewWithTwoStudents() throws Exception {
		List<Student> students = new ArrayList<>();
		students.add(new Student(1L, 1L, "John", "Doe"));
		students.add(new Student(2L, 3L, "Jane", "Smith"));

		when(studentService.getAllStudents()).thenReturn(students);
		mockMvc.perform(get("/students/")).andExpect(status().isOk()).andExpect(view().name("students"))
				.andExpect(model().attributeExists("students")).andExpect(model().attribute("students", students))
				.andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(content().string(containsString("Doe")))
				.andExpect(content().string(containsString("Smith")));
	}

	@Test
	@WithMockUser(roles={"ADMIN", "STUFF", "STUDENT", "TEACHER"})
	void whenGetStudentsIsEmptyExpectStudentsViewWithNoStudents() throws Exception {
		List<Student> students = new ArrayList<>();

		when(studentService.getAllStudents()).thenReturn(students);
		mockMvc.perform(get("/students/")).andExpect(status().isOk()).andExpect(view().name("students"))
				.andExpect(model().attributeExists("students")).andExpect(model().attribute("students", empty()))
				.andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(content().string(not(containsString("Doe"))))
				.andExpect(content().string(not(containsString("Smith"))));
	}
}
