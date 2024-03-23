package ua.foxminded.universitycms.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import ua.foxminded.universitycms.service.CourseService;
import ua.foxminded.universitycms.service.CustomUserDetailsService;
import ua.foxminded.universitycms.service.UserService;
import ua.foxminded.universitycms.util.ConfigSecurity;
import ua.foxminded.universitycms.util.CustomSuccessHandler;

@Import({ConfigSecurity.class, CustomUserDetailsService.class})
@WebMvcTest(controllers = HomeController.class)
class HomeControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserService userService;
	@MockBean
	private CustomUserDetailsService customUserDetailsService;	
	@MockBean
	private CustomSuccessHandler successHandler;
	@MockBean
	private CourseService courseService;
	
	@WithAnonymousUser
	@Test
	void whenAnonymousUserAccessToHomePageExpectAllowAccess() throws Exception {
		mockMvc.perform(get("/home")).andExpect(status().isOk()).andExpect(view().name("home"))
			.andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
			.andExpect(content().string(containsString("Wellcome to University")));
	}
	
	@WithAnonymousUser
	@Test
	void whenAnonymousUserAccessToOurCoursesPageExpectAllowAccess() throws Exception {
		mockMvc.perform(get("/home/our-courses")).andExpect(status().isOk()).andExpect(view().name("our-courses"))
			.andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
			.andExpect(content().string(containsString("Available сourses of University")));
	}
	
	@Test
	@WithMockUser(username = "user1", roles={"ADMIN", "STUFF", "STUDENT", "TEACHER"})
	void whenUserAccessToHomePageExpectAccessIsForbidden() throws Exception {
		mockMvc.perform(get("/home")).andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(username = "user1", roles={"ADMIN", "STUFF", "STUDENT", "TEACHER"})
	void whenUserAccessToLoginPageExpectAccessIsForbidden() throws Exception {
		mockMvc.perform(get("/home/our-courses")).andExpect(status().isForbidden());
	}
}
