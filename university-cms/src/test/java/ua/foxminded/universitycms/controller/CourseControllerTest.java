package ua.foxminded.universitycms.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import ua.foxminded.universitycms.models.Course;
import ua.foxminded.universitycms.service.CourseService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = CourseController.class)
class CourseControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CourseService courseService;

	@Test
	void whenGetCoursesNotEmptyExpectCoursesViewWithTwoCourses() throws Exception {

		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1L, "Mathematics", "Basic mathematics course"));
		courses.add(new Course(2L, "History", "Introduction to World History"));

		when(courseService.getAllCourses()).thenReturn(courses);
		mockMvc.perform(get("/courses-page/allCourses")).andExpect(status().isOk()).andExpect(view().name("courses-page"))
				.andExpect(model().attributeExists("courses")).andExpect(model().attribute("courses", courses))
				.andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(content().string(containsString("Mathematics")))
				.andExpect(content().string(containsString("History")));
	}

	@Test
	void whenGetCoursesIsEmptyExpectCoursesViewWithNoCourses() throws Exception {

		List<Course> courses = new ArrayList<>();

		when(courseService.getAllCourses()).thenReturn(courses);
		mockMvc.perform(get("/courses-page/allCourses")).andExpect(status().isOk()).andExpect(view().name("courses-page"))
				.andExpect(model().attributeExists("courses")).andExpect(model().attribute("courses", empty()))
				.andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(content().string(not(containsString("Mathematics"))))
				.andExpect(content().string(not(containsString("History"))));
	}
}
