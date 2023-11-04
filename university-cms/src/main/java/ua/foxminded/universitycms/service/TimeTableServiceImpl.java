package ua.foxminded.universitycms.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.foxminded.universitycms.dao.GroupDao;
import ua.foxminded.universitycms.dao.StudentDao;
import ua.foxminded.universitycms.dao.TeacherDao;
import ua.foxminded.universitycms.dao.TimeTableDao;
import ua.foxminded.universitycms.models.Group;
import ua.foxminded.universitycms.models.Student;
import ua.foxminded.universitycms.models.Teacher;
import ua.foxminded.universitycms.models.TimeTable;

@Service
public class TimeTableServiceImpl implements TimeTableService {
	private static Logger logger = LoggerFactory.getLogger(TimeTableServiceImpl.class);

	private final TimeTableDao timeTableDao;
	private final GroupDao groupDao;
	private final TeacherDao teacherDao;
	private final StudentDao studentDao;

	public TimeTableServiceImpl(TimeTableDao timeTableDao, GroupDao groupDao, TeacherDao teacherDao,
			StudentDao studentDao) {
		this.timeTableDao = timeTableDao;
		this.groupDao = groupDao;
		this.teacherDao = teacherDao;
		this.studentDao = studentDao;
	}

	@Override
	public List<TimeTable> getAllTimeTable() {
		List<TimeTable> allTimeTable = new ArrayList<>(timeTableDao.findAll());
		logger.info("Getting all {} teachers from DB", allTimeTable.size());
		return allTimeTable;
	}

	@Override
	public Optional<TimeTable> getTimeTableById(Long timeTableId) throws SQLException {
		return timeTableDao.findById(timeTableId);
	}

	@Override
	public void saveEntrySchedule(TimeTable timeTable, Long groupId, Long teacherId) throws Exception {
		List<TimeTable> timeTableByPairAndDate = timeTableDao
				.findTimeTableByPairNumberAndDate(timeTable.getPairNumber(), timeTable.getTimeTableDate());
		if (!timeTableByPairAndDate.isEmpty()) {
			Optional<TimeTable> timeTableByGroup = timeTableByPairAndDate.stream()
					.filter(t -> t.getGroupId().equals(groupId)).findFirst();
			if (!timeTableByGroup.isPresent()) {
				Optional<TimeTable> timeTableByTeacher = timeTableByPairAndDate.stream()
						.filter(t -> t.getTeacherId().equals(teacherId)).findFirst();
				if (!timeTableByTeacher.isPresent()) {
					saveTimeTable(timeTable, groupId, teacherId);
				} else {
					logger.error("The TimeTable by teacher id = {} is already existing for this time", teacherId,
							new Exception("The TimeTable by teacher is already existing for this time"));
				}
			} else {
				logger.error("The TimeTable by group id = {} is already existing for this time", groupId,
						new Exception("The TimeTable by group is already existing for this time"));
			}
		} else {
			saveTimeTable(timeTable, groupId, teacherId);
		}
	}

	@Override
	public List<TimeTable> getTimeTableByDayForTeacher(LocalDate timeTableDate, Long teacherId)
			throws Exception {
		return timeTableDao.findTimeTableByTeacherForDay(timeTableDate, teacherId);
	}

	@Override
	public List<TimeTable> getTimeTableByDayForStudent(LocalDate timeTableDate, Long studentId)
			throws Exception {
		List<TimeTable> timeTableByDayForStudent = new ArrayList<TimeTable>();
		Optional<Student> student = studentDao.findById(studentId);
		if (student.isPresent()) {
			timeTableByDayForStudent = timeTableDao.findTimeTableByGroupForDay(timeTableDate,
					student.get().getGroupId());
			logger.info("Take timeTable for student id {}", studentId);
		} else {
			logger.error("The student id = {} is not find in DB", studentId,
					new Exception("The student is not find in DB"));
		}
		return timeTableByDayForStudent;
	}

	private void saveTimeTable(TimeTable timeTable, Long groupId, Long teacherId) throws Exception {
		Optional<Group> group = groupDao.findById(groupId);
		Optional<Teacher> teacher = teacherDao.findById(teacherId);
		if (group.isPresent() && teacher.isPresent()) {
			timeTable.setGroup(group.get());
			timeTable.setTeacher(teacher.get());
			timeTableDao.save(timeTable);
			logger.info("TimeTable save to DB");
		} else {
			logger.error("The group id = {} or teacher id = {} is not find in DB", groupId, teacherId,
					new Exception("The student is not find in DB"));
		}
	}
}
