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

import ua.foxminded.universitycms.models.Group;
import ua.foxminded.universitycms.models.Role;
import ua.foxminded.universitycms.models.Student;
import ua.foxminded.universitycms.models.User;
import ua.foxminded.universitycms.service.CustomUserDetailsService;
import ua.foxminded.universitycms.service.GroupService;
import ua.foxminded.universitycms.service.StudentService;
import ua.foxminded.universitycms.service.UserService;
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
	@MockBean
	private UserService userService;
	@MockBean
	private GroupService groupService;

	@Test
	@WithMockUser(username = "user6", roles={"ADMIN", "STUFF", "STUDENT", "TEACHER"})
	void whenGetStudentsNotEmptyExpectStudentsViewWithTwoStudents() throws Exception {
		Student student1 = new Student(1L, "John", "Doe");
		student1.setGroup(new Group(45L, "fg-56"));
		User user1 = new User();
		user1.setId(5L);
		user1.setUsername("user5");
		user1.setPassword("rfregr234234");
		user1.setStudent(student1);
		List<Role> roles1 = new ArrayList<>();
		roles1.add(new Role("ROLE_STUDENT"));
		roles1.add(new Role("ROLE_USER"));
		user1.setRoles(roles1);
		student1.setUser(user1);
		Student student2 = new Student(2L, "Johna", "Doena");
		student2.setGroup(new Group(45L, "fg-56"));
		User user2 = new User();
		user2.setId(6L);
		user2.setUsername("user6");
		user2.setPassword("rfreg35434r234234");
		user2.setStudent(student2);
		List<Role> roles2 = new ArrayList<>();
		roles2.add(new Role("ROLE_STUDENT"));
		roles2.add(new Role("ROLE_USER"));
		user2.setRoles(roles2);
		student2.setUser(user2);		
		List<Student> students = new ArrayList<>();
		students.add(student1);
		students.add(student2);
		
		when(studentService.getAllStudents()).thenReturn(students);
		when(userService.findByName("user6")).thenReturn(user2);
		mockMvc.perform(get("/students/")).andExpect(status().isOk()).andExpect(view().name("students"))
				.andExpect(model().attributeExists("students")).andExpect(model().attribute("students", students))
				.andExpect(model().attributeExists("currentUserId")).andExpect(model().attribute("currentUserId", user2.getId()))
				.andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(content().string(containsString("Doe")))
				.andExpect(content().string(containsString("Doena")));
	}

	@Test
	@WithMockUser(username = "user6", roles={"ADMIN", "STUFF", "TEACHER"})
	void whenGetStudentsIsEmptyExpectStudentsViewWithNoStudents() throws Exception {
		List<Student> students = new ArrayList<>();
		User user2 = new User();
		user2.setId(6L);
		user2.setUsername("user6");
		user2.setPassword("rfreg35434r234234");
		List<Role> roles2 = new ArrayList<>();
		roles2.add(new Role("ROLE_ADMIN"));
		roles2.add(new Role("ROLE_USER"));
		user2.setRoles(roles2);
		
		when(studentService.getAllStudents()).thenReturn(students);
		when(userService.findByName("user6")).thenReturn(user2);
		mockMvc.perform(get("/students/")).andExpect(status().isOk()).andExpect(view().name("students"))
				.andExpect(model().attributeExists("students")).andExpect(model().attribute("students", empty()))
				.andExpect(model().attributeExists("currentUserId")).andExpect(model().attribute("currentUserId", user2.getId()))
				.andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(content().string(not(containsString("Doe"))))
				.andExpect(content().string(not(containsString("Smith"))));
	}
	
	@Test
	@WithMockUser(roles={"ADMIN", "STUFF", "STUDENT"})
	void whenStudentEditExpectStudentFormView() throws Exception {
		Student student = new Student(1L, "John", "Doe");
		student.setGroup(new Group(45L, "fg-56"));
		User user1 = new User();
		List<Role> roles1 = new ArrayList<>();
		roles1.add(new Role("ROLE_STUDENT"));
		roles1.add(new Role("ROLE_USER"));
		user1.setRoles(roles1);
		user1.setId(5L);
		user1.setUsername("user5");
		user1.setPassword("rfregr234234");
		user1.setStudent(student);
		student.setUser(user1);
		
		when(studentService.getStudentById(student.getId())).thenReturn(Optional.of(student));
		mockMvc.perform(get("/students/edit/{id}", student.getId())
				.contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(view().name("student-form"))
				.andExpect(model().attributeExists("student"))
				.andExpect(model().attribute("student", student));
	}
	
	@Test
	@WithMockUser(roles={"ADMIN", "STUFF", "STUDENT"})
	void whenStudentEditButNotFindInDBExpectAllStudentsView() throws Exception {
		Student student = new Student(1L, "John", "Doe");

		when(studentService.getStudentById(student.getId())).thenReturn(Optional.empty());
		mockMvc.perform(get("/students/edit/{id}", student.getId())
				.contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(view().name("redirect:/students/"));
	}
	
	@Test
	@WithMockUser(roles={"STUFF", "ADMIN"})
	void whenStudentAssignToGroupExpectFormAssignView() throws Exception {
		Student student = new Student(1L, "John", "Doe");
		student.setGroup(new Group(45L, "fg-56"));
		User user1 = new User();
		List<Role> roles1 = new ArrayList<>();
		roles1.add(new Role("ROLE_STUDENT"));
		roles1.add(new Role("ROLE_USER"));
		user1.setRoles(roles1);
		user1.setId(5L);
		user1.setUsername("user5");
		user1.setPassword("rfregr234234");
		user1.setStudent(student);
		student.setUser(user1);
		
		when(studentService.getStudentById(student.getId())).thenReturn(Optional.of(student));
		mockMvc.perform(get("/students/assign/{id}", student.getId())
				.contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(view().name("assign-student"))
				.andExpect(model().attributeExists("student"))
				.andExpect(model().attribute("student", student));
	}
	
	@Test
	@WithMockUser(roles={"STUFF", "ADMIN"})
	void whenAssignStudentNotFindExpectAllStudentView() throws Exception {
		Student student = new Student(1L, "John", "Doe");
		
		when(studentService.getStudentById(student.getId())).thenReturn(Optional.empty());
		mockMvc.perform(get("/students/assign/{id}", student.getId())
				.contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(view().name("redirect:/students/"));
	}
}
