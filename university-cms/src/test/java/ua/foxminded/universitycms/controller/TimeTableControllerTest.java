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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import ua.foxminded.universitycms.models.Group;
import ua.foxminded.universitycms.models.Student;
import ua.foxminded.universitycms.models.Teacher;
import ua.foxminded.universitycms.models.TimeTable;
import ua.foxminded.universitycms.models.User;
import ua.foxminded.universitycms.service.CustomUserDetailsService;
import ua.foxminded.universitycms.service.GroupService;
import ua.foxminded.universitycms.service.TeacherService;
import ua.foxminded.universitycms.service.TimeTableService;
import ua.foxminded.universitycms.service.UserService;
import ua.foxminded.universitycms.util.ConfigSecurity;
import ua.foxminded.universitycms.util.CustomSuccessHandler;

@Import({ConfigSecurity.class, CustomUserDetailsService.class})
@WebMvcTest(controllers = TimeTableController.class)
public class TimeTableControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomUserDetailsService customUserDetailsService;	
	@MockBean
	private CustomSuccessHandler successHandler;
	@MockBean
	private UserService userService;
	@MockBean
	private GroupService groupService;
	@MockBean
	private TeacherService teacherService;
	@MockBean
	private TimeTableService timeTableService;

	@Test
	@WithMockUser(username = "user4", roles={"ADMIN", "STUFF"})
	void whenUserIsNotTeacherOrStudentAndGetScheduleExpectScheduleViewWithTwoTimeTable() throws Exception {
		List<TimeTable> timeTables = new ArrayList<>();
		Teacher teacher1 = new Teacher(1L, "Bob", "Mars");
		Teacher teacher2 = new Teacher(2L, "Bob2", "Mars2");
		Group group1 = new Group(1L, "df-45");
		Group group2 = new Group(2L, "df-45");
		timeTables.add(new TimeTable(1L, 2, LocalDate.of(2023, 05, 25)));
		timeTables.add(new TimeTable(2L, 3, LocalDate.of(2023, 05, 26)));
		timeTables.get(0).setTeacher(teacher1);
		timeTables.get(0).setGroup(group1);
		timeTables.get(1).setTeacher(teacher2);
		timeTables.get(1).setGroup(group2);

		when(timeTableService.getAllTimeTable()).thenReturn(timeTables);
		mockMvc.perform(get("/schedule/")).andExpect(status().isOk()).andExpect(view().name("schedule"))
				.andExpect(model().attributeExists("timeTables")).andExpect(model().attribute("timeTables", timeTables))
				.andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(content().string(containsString("Select group:")))
				.andExpect(content().string(containsString("2023-05-25")))
				.andExpect(content().string(containsString("2023-05-26")));
	}

	@Test
	@WithMockUser(username = "user4", roles={"ADMIN", "STUFF"})
	void whenUserIsNotTeacherOrStudentAndNoScheduleInDBExpectScheduleViewWithNoTimeTable() throws Exception {
		List<TimeTable> timeTables = new ArrayList<>();

		when(timeTableService.getAllTimeTable()).thenReturn(timeTables);
		mockMvc.perform(get("/schedule/")).andExpect(status().isOk()).andExpect(view().name("schedule"))
				.andExpect(model().attributeExists("timeTables")).andExpect(model().attribute("timeTables", empty()))
				.andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(content().string(containsString("Select group:")))
				.andExpect(content().string(not(containsString("2023-05-25"))))
				.andExpect(content().string(not(containsString("2023-05-26"))));
	}
	
	@Test
	@WithMockUser(username = "user6", roles={"TEACHER"})
	void whenUserIsTeacherAndGetScheduleExpectScheduleViewWithOneTimeTable() throws Exception {
		List<TimeTable> expectTimeTables = new ArrayList<>();
		Teacher teacher1 = new Teacher(1L, "Bob", "Mars");
		Group group1 = new Group(1L, "df-45");
		expectTimeTables.add(new TimeTable(1L, 2, LocalDate.of(2023, 05, 25)));
		expectTimeTables.get(0).setTeacher(teacher1);
		expectTimeTables.get(0).setGroup(group1);
		User user = new User();
		user.setId(5L);
		user.setUsername("user6");
		user.setPassword("rfregr234234");

		when(userService.findByName("user6")).thenReturn(user);
		when(timeTableService.getTeacherSchedule(5L)).thenReturn(expectTimeTables);
		mockMvc.perform(get("/schedule/")).andExpect(status().isOk()).andExpect(view().name("schedule"))
				.andExpect(model().attributeExists("timeTables")).andExpect(model().attribute("timeTables", expectTimeTables))
				.andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(content().string(not(containsString("Select group:"))))
				.andExpect(content().string(containsString("2023-05-25")));
	}
	
	@Test
	@WithMockUser(username = "user6", roles={"STUDENT"})
	void whenUserIsStudentAndGetScheduleExpectScheduleViewWithOneTimeTable() throws Exception {
		List<TimeTable> expectTimeTables = new ArrayList<>();
		Teacher teacher1 = new Teacher(1L, "Bob", "Mars");
		Group group1 = new Group(3L, "df-45");
		Student student = new Student(1L, "John", "Doe");
		student.setGroup(new Group(3L, "df-45"));
		expectTimeTables.add(new TimeTable(1L, 2, LocalDate.of(2023, 05, 25)));
		expectTimeTables.get(0).setTeacher(teacher1);
		expectTimeTables.get(0).setGroup(group1);
		User user = new User();
		user.setId(5L);
		user.setUsername("user6");
		user.setPassword("rfregr234234");

		when(userService.findByName("user6")).thenReturn(user);
		when(timeTableService.getStudentSchedule(5L)).thenReturn(expectTimeTables);
		mockMvc.perform(get("/schedule/")).andExpect(status().isOk()).andExpect(view().name("schedule"))
				.andExpect(model().attributeExists("timeTables")).andExpect(model().attribute("timeTables", expectTimeTables))
				.andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(content().string(not(containsString("Select group:"))))
				.andExpect(content().string(containsString("2023-05-25")));
	}
	
	@Test
	@WithMockUser(username = "user4", roles={"ADMIN", "STUFF"})
	void whenEditScheduleExpectScheduleViewWithEditTimeTable() throws Exception {
		List<TimeTable> timeTables = new ArrayList<>();
		Teacher teacher1 = new Teacher(1L, "Bob", "Mars");
		Teacher teacher2 = new Teacher(2L, "Bob2", "Mars2");
		Group group1 = new Group(1L, "df-45");
		Group group2 = new Group(2L, "df-45");
		timeTables.add(new TimeTable(1L, 2, LocalDate.of(2023, 05, 25)));
		timeTables.add(new TimeTable(2L, 3, LocalDate.of(2023, 05, 26)));
		timeTables.get(0).setTeacher(teacher1);
		timeTables.get(0).setGroup(group1);
		timeTables.get(1).setTeacher(teacher2);
		timeTables.get(1).setGroup(group2);

		when(timeTableService.getTimeTableById(timeTables.get(0).getId())).thenReturn(Optional.of(timeTables.get(0)));
		when(timeTableService.getAllTimeTable()).thenReturn(timeTables);
		mockMvc.perform(get("/schedule/edit/{id}", timeTables.get(0).getId()))
				.andExpect(model().attributeExists("timeTables")).andExpect(model().attribute("timeTables", timeTables))
				.andExpect(model().attributeExists("timeTable")).andExpect(model().attribute("timeTable", timeTables.get(0)))
				.andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(content().string(containsString("Select group:")))
				.andExpect(content().string(containsString("2023-05-25")))
				.andExpect(content().string(Matchers.allOf(
				        containsString("2023-05-25"),
				        Matchers.not(containsString("2023-05-25" + "2023-05-25")))))
				.andExpect(content().string(containsString("2023-05-26")));
	}
}
