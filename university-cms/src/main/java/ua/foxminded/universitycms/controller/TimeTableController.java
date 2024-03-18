package ua.foxminded.universitycms.controller;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ua.foxminded.universitycms.models.TimeTable;
import ua.foxminded.universitycms.service.GroupService;
import ua.foxminded.universitycms.service.TeacherService;
import ua.foxminded.universitycms.service.TimeTableService;
import ua.foxminded.universitycms.service.UserService;
import ua.foxminded.universitycms.util.RoleEnum;

@Controller
@RequestMapping("/schedule")
public class TimeTableController {

	private static String REDIRECT_TO_SCHEDULE = "redirect:/schedule/";
	private static String SCHEDULE = "schedule";

	@Autowired
	private UserService userService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private TimeTableService timeTableService;

	@GetMapping
	public String timeTables(Model model, RedirectAttributes redirectAttributes) throws Exception {
		Map<String, Long> currentUser = getTeacherOrStudent();
		if(currentUser.isEmpty()) {
			model.addAttribute("timeTable", new TimeTable());
			model.addAttribute("teachers", teacherService.getAllTeachers());
			model.addAttribute("groups", groupService.getAllGroups());
			model.addAttribute("timeTables", sortSchedule(timeTableService.getAllTimeTable()));
		} else if(currentUser.containsKey(RoleEnum.ROLE_STUDENT.toString())) {
			Long currentStudentId = currentUser.entrySet().iterator().next().getValue();
			model.addAttribute("timeTables", sortSchedule(timeTableService.getStudentSchedule(currentStudentId)));
		}  else if(currentUser.containsKey(RoleEnum.ROLE_TEACHER.toString())) {
			Long currentTeacherId = currentUser.entrySet().iterator().next().getValue();
			model.addAttribute("timeTables", sortSchedule(timeTableService.getTeacherSchedule(currentTeacherId)));
		} 
		return SCHEDULE;
		
	}

	@GetMapping("/edit/{id}")
	public String editTimeTable(@PathVariable("id") Long id, Model model) throws SQLException {
		model.addAttribute("timeTable", timeTableService.getTimeTableById(id).get());
		model.addAttribute("teachers", teacherService.getAllTeachers());
		model.addAttribute("groups", groupService.getAllGroups());
		model.addAttribute("timeTables", sortSchedule(timeTableService.getAllTimeTable()));
		return SCHEDULE;
	}

	@PostMapping("/save")
	public String saveTimeTeble(TimeTable timeTable, RedirectAttributes redirectAttributes)
			throws SQLException, Exception {
		if (timeTable.getId() != null) {
			timeTableService.editSchedule(timeTable);
		} else if (!timeTableService.saveSchedule(timeTable)) {
			redirectAttributes.addFlashAttribute("message",
					"NOT SAVE schedule: group or teacher are busy for this time!");
		}
		return REDIRECT_TO_SCHEDULE;
	}

	@GetMapping("/delete/{id}")
	public String deleteSchedule(@PathVariable("id") Long id) throws Exception {
		timeTableService.deleteSchedule(id);
		return REDIRECT_TO_SCHEDULE;
	}

	private List<TimeTable> sortSchedule(List<TimeTable> schedule) throws SQLException {
		return schedule.stream()
				.sorted(Comparator
						.comparing(TimeTable::getTimeTableDate, Comparator.nullsLast(Comparator.naturalOrder()))
						.thenComparingInt(TimeTable::getPairNumber))
				.collect(Collectors.toList());
	}

	private Map<String, Long> getTeacherOrStudent() throws SQLException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Long> currentUser = new HashMap<>();
		for (GrantedAuthority e : authentication.getAuthorities()) {
			String role = e.toString();
			if (role.equals(RoleEnum.ROLE_TEACHER.toString()) || role.equals(RoleEnum.ROLE_STUDENT.toString())) {
				currentUser.put(role, userService.findByName(authentication.getName()).getId());
			}
		}
		return currentUser;
	}
}