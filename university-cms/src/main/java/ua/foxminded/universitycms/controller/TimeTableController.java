package ua.foxminded.universitycms.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.universitycms.service.TimeTableService;

@Controller
@RequestMapping("/schedule")
public class TimeTableController {

	@Autowired
	private TimeTableService timeTableService;
	
	@GetMapping("/generalSchedule")
	public String timeTables(Model model) throws SQLException {
		model.addAttribute("timeTables", timeTableService.getAllTimeTable());
		return "schedule";
	}
}
