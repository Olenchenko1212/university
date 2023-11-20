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

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import ua.foxminded.universitycms.models.TimeTable;
import ua.foxminded.universitycms.service.TimeTableService;

@WebMvcTest(controllers = TimeTableController.class)
public class TimeTableControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TimeTableService timeTableService;

	@Test
	void whenGetScheduleNotEmptyExpectScheduleViewWithTwoTimeTable() throws Exception {

		List<TimeTable> timeTables = new ArrayList<>();
		timeTables.add(new TimeTable(1L, 2, LocalDate.of(2023, 05, 25), 3L, 4L));
		timeTables.add(new TimeTable(2L, 3, LocalDate.of(2023, 05, 26), 5L, 6L));

		when(timeTableService.getAllTimeTable()).thenReturn(timeTables);
		mockMvc.perform(get("/schedule-page/generalSchedule")).andExpect(status().isOk()).andExpect(view().name("schedule-page"))
				.andExpect(model().attributeExists("timeTables")).andExpect(model().attribute("timeTables", timeTables))
				.andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(content().string(containsString("2023-05-25")))
				.andExpect(content().string(containsString("2023-05-26")));
	}

	@Test
	void whenGetScheduleIsEmptyExpectScheduleViewWithNoTimeTable() throws Exception {

		List<TimeTable> timeTables = new ArrayList<>();

		when(timeTableService.getAllTimeTable()).thenReturn(timeTables);
		mockMvc.perform(get("/schedule-page/generalSchedule")).andExpect(status().isOk()).andExpect(view().name("schedule-page"))
				.andExpect(model().attributeExists("timeTables")).andExpect(model().attribute("timeTables", empty()))
				.andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
				.andExpect(content().string(not(containsString("2023-05-25"))))
				.andExpect(content().string(not(containsString("2023-05-26"))));
	}
}
