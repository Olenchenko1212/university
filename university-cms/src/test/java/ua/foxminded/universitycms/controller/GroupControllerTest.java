package ua.foxminded.universitycms.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import ua.foxminded.universitycms.service.CustomUserDetailsService;
import ua.foxminded.universitycms.service.GroupService;
import ua.foxminded.universitycms.util.ConfigSecurity;
import ua.foxminded.universitycms.util.CustomSuccessHandler;

@Import({ConfigSecurity.class, CustomUserDetailsService.class})
@WebMvcTest(controllers = GroupController.class)
class GroupControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CustomUserDetailsService customUserDetailsService;
	@MockBean
	private CustomSuccessHandler successHandler;
	@MockBean
	private GroupService groupService;
	
	@Test
	@WithMockUser(roles={"ADMIN", "STUFF"})
	void whenGroupSaveExpectNewGroupSaveInDB() throws Exception {
		Group group = new Group();
		group.setId(1L);
		group.setGroupName("sd-34");

		doNothing().when(groupService).saveGroup(group);
		mockMvc.perform(post("/groups-page/save")
				.contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8")
				.param("id", "1")
				.param("groupName", "sd-34"))
				.andExpect(status().is3xxRedirection())
				.andExpect(header().string("Location", "/groups-page/"));
	}
	
	@Test
	@WithMockUser(roles="ADMIN")
	void whenGroupDeleteExpectGroupDeleteFromDB() throws Exception {
		Group group = new Group();
		group.setId(1L);
		group.setGroupName("sd-34");

		doNothing().when(groupService).deleteGroup(group.getId());
		mockMvc.perform(get("/groups-page/delete/{id}", group.getId())
				.contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8")
				.param("id", "1")
				.param("groupName", "sd-34"))
				.andExpect(status().is3xxRedirection())
				.andExpect(header().string("Location", "/groups-page/"));
	}
	
	@Test
	@WithMockUser(roles={"ADMIN", "STUFF"})
	void whenGroupEditExpectGroupFormView() throws Exception {
		Group group = new Group();
		group.setId(1L);
		group.setGroupName("sd-34");

		when(groupService.getGroupById(group.getId())).thenReturn(Optional.of(group));
		mockMvc.perform(get("/groups-page/edit/{id}", group.getId())
				.contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(view().name("group-form"))
				.andExpect(model().attributeExists("group"))
				.andExpect(model().attribute("group", group));
	}
	
	@Test
	@WithMockUser(roles={"ADMIN", "STUFF"})
	void whenGroupEditButNotFindInDBExpectAllGroupsView() throws Exception {
		Group group = new Group();
		group.setId(1L);
		group.setGroupName("sd-34");

		when(groupService.getGroupById(group.getId())).thenReturn(Optional.empty());
		mockMvc.perform(get("/groups-page/edit/{id}", group.getId())
				.contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(view().name("redirect:/groups-page/"));
	}
}
