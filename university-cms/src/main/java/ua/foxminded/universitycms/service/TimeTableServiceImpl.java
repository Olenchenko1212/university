package ua.foxminded.universitycms.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.foxminded.universitycms.models.Group;
import ua.foxminded.universitycms.models.Student;
import ua.foxminded.universitycms.models.Teacher;
import ua.foxminded.universitycms.models.TimeTable;
import ua.foxminded.universitycms.repository.GroupRepository;
import ua.foxminded.universitycms.repository.StudentRepository;
import ua.foxminded.universitycms.repository.TeacherRepository;
import ua.foxminded.universitycms.repository.TimeTableRepository;

@Service
public class TimeTableServiceImpl implements TimeTableService {
	private static Logger logger = LoggerFactory.getLogger(TimeTableServiceImpl.class);

	private final TimeTableRepository timeTableRepository;
	private final GroupRepository groupRepository;
	private final TeacherRepository teacherRepository;
	private final StudentRepository studentRepository;

	public TimeTableServiceImpl(TimeTableRepository timeTableRepository, GroupRepository groupRepository,
			TeacherRepository teacherRepository, StudentRepository studentRepository) {
		this.timeTableRepository = timeTableRepository;
		this.groupRepository = groupRepository;
		this.teacherRepository = teacherRepository;
		this.studentRepository = studentRepository;
	}

	@Override
	public List<TimeTable> getAllTimeTable() {
		List<TimeTable> allTimeTable = timeTableRepository.findAll();
		logger.info("Getting all {} teachers from DB", allTimeTable.size());
		return allTimeTable;
	}

	@Override
	public Optional<TimeTable> getTimeTableById(Long timeTableId) throws SQLException {
		logger.info("Getting timeTable id = {} from DB", timeTableId);
		return timeTableRepository.findById(timeTableId);
	}

	@Override
	@Transactional
	public void saveEntrySchedule(TimeTable timeTable, Long groupId, Long teacherId) throws Exception {
		List<TimeTable> timeTableByPairAndDate = timeTableRepository
				.findBytimeTableDateAndPairNumber(timeTable.getTimeTableDate(), timeTable.getPairNumber());
		if (!timeTableByPairAndDate.isEmpty()) {
			if (isNotPresentScheduleByGroup(timeTableByPairAndDate, groupId)
					&& isNotPresentScheduleByTeacher(timeTableByPairAndDate, teacherId)) {
				saveTimeTable(timeTable, groupId, teacherId);
				logger.info("SAVE TimeTable Id = {} for not free time", timeTable.getTimeTableId());
			}
		} else {
			saveTimeTable(timeTable, groupId, teacherId);
			logger.info("SAVE TimeTable Id = {} for free time", timeTable.getTimeTableId());
		}
	}
	
	@Override
	public void deleteTimeTable(Long timeTableId) {
		timeTableRepository.deleteById(timeTableId);
		logger.info("TimeTable Id = {} delete from DB", timeTableId);
	}

	@Override
	public List<TimeTable> getTimeTableByDayForTeacher(LocalDate timeTableDate, Long teacherId) throws Exception {
		logger.info("Take timeTable by Day = {} for teacher id = {}", timeTableDate, teacherId);
		Optional<Teacher> teacher = teacherRepository.findById(teacherId);
		return timeTableRepository.findBytimeTableDateAndTeacher(timeTableDate, teacher.get());
	}

	@Override
	public List<TimeTable> getTimeTableByDayForStudent(LocalDate timeTableDate, Long studentId) throws Exception {
		List<TimeTable> timeTableByDayForStudent = new ArrayList<TimeTable>();
		Optional<Student> student = studentRepository.findById(studentId);
		if (student.isPresent()) {
			timeTableByDayForStudent = timeTableRepository.findBytimeTableDateAndGroupId(timeTableDate,
					student.get().getGroupId());
			logger.info("Take timeTable for student id = {}", studentId);
		} else {
			logger.error("The student id = {} is not find in DB", studentId,
					new Exception("The student is not find in DB"));
		}
		return timeTableByDayForStudent;
	}

	private boolean isNotPresentScheduleByTeacher(List<TimeTable> timeTableByPairAndDate, Long teacherId) {
		boolean isPresent = timeTableByPairAndDate.stream().anyMatch(t -> t.getTeacher().getId().equals(teacherId));
		if (isPresent) {
			logger.error("The TimeTable by teacher id = {} is already existing for this time", teacherId,
					new Exception("The TimeTable by teacher is already existing for this time"));
		}
		return !isPresent;
	}

	private boolean isNotPresentScheduleByGroup(List<TimeTable> timeTableByPairAndDate, Long groupId) {
		boolean isPresent = timeTableByPairAndDate.stream().anyMatch(t -> t.getGroupId().equals(groupId));
		if (isPresent) {
			logger.error("The TimeTable by group id = {} is already existing for this time", groupId,
					new Exception("The TimeTable by group is already existing for this time"));
		}
		return !isPresent;
	}
	
	private void saveTimeTable(TimeTable timeTable, Long groupId, Long teacherId) throws Exception {
		Optional<Group> group = groupRepository.findById(groupId);
		Optional<Teacher> teacher = teacherRepository.findById(teacherId);
		if (group.isPresent() && teacher.isPresent()) {
			timeTable.setGroup(group.get());
			timeTable.setTeacher(teacher.get());
			timeTableRepository.save(timeTable);
			logger.info("TimeTable by date = {} and pairNumber = {} save to DB", timeTable.getTimeTableDate(),
					timeTable.getPairNumber());
		} else {
			logger.error("The group id = {} or teacher id = {} is not find in DB", groupId, teacherId,
					new Exception("The group or teacher is not find in DB"));
		}
	}
}
