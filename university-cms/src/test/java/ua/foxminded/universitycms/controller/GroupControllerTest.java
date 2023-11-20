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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import ua.foxminded.universitycms.models.Group;
import ua.foxminded.universitycms.service.GroupService;

@WebMvcTest(controllers = GroupController.class)
public class GroupControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GroupService groupService;

	@Test
	void whenGetGroupsNotEmptyExpectGroupsViewWithTwoGroups() throws Exception {

		List<Group> groups = new ArrayList<>();
		groups.add(new Group(1L, "df-23"));
		groups.add(new Group(2L, "gh-47"));

		when(groupService.getAllGroups()).thenReturn(groups);
		mockMvc.perform(get("/groups-page/allGroups")).andExpect(status().isOk()).andExpect(view().name("groups-page"))
				.andExpect(model().attributeExists("groups")).andExpect(model().attribute("groups", groups))
				.andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(content().string(containsString("df-23")))
				.andExpect(content().string(containsString("gh-47")));
	}

	@Test
	void whenGetGroupsIsEmptyExpectGroupsViewWithNoGroups() throws Exception {

		List<Group> groups = new ArrayList<>();

		when(groupService.getAllGroups()).thenReturn(groups);
		mockMvc.perform(get("/groups-page/allGroups")).andExpect(status().isOk()).andExpect(view().name("groups-page"))
				.andExpect(model().attributeExists("groups")).andExpect(model().attribute("groups", empty()))
				.andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(content().string(not(containsString("df-23"))))
				.andExpect(content().string(not(containsString("gh-47"))));
	}
}
