package ua.foxminded.universitycms.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import ua.foxminded.universitycms.models.Student;
import ua.foxminded.universitycms.models.Teacher;
import ua.foxminded.universitycms.models.TimeTable;
import ua.foxminded.universitycms.repository.StudentRepository;
import ua.foxminded.universitycms.repository.TeacherRepository;
import ua.foxminded.universitycms.repository.TimeTableRepository;

@Service
public class TimeTableServiceImpl implements TimeTableService {
	private static Logger logger = LoggerFactory.getLogger(TimeTableServiceImpl.class);

	private final TimeTableRepository timeTableRepository;
	private final TeacherRepository teacherRepository;
	private final StudentRepository studentRepository;

	public TimeTableServiceImpl(TimeTableRepository timeTableRepository, TeacherRepository teacherRepository,
			StudentRepository studentRepository) {
		this.timeTableRepository = timeTableRepository;
		this.teacherRepository = teacherRepository;
		this.studentRepository = studentRepository;
	}

	@Override
	@Secured({ "ROLE_ADMIN", "ROLE_STUFF", "ROLE_STUDENT", "ROLE_TEACHER" })
	public List<TimeTable> getAllTimeTable() {
		List<TimeTable> allTimeTable = timeTableRepository.findAll();
		logger.info("Getting all {} schedule from DB", allTimeTable.size());
		return allTimeTable;
	}

	@Override
	@Secured({ "ROLE_ADMIN", "ROLE_STUFF", "ROLE_STUDENT", "ROLE_TEACHER" })
	public Optional<TimeTable> getTimeTableById(Long timeTableId) throws SQLException {
		logger.info("Getting timeTable id = {} from DB", timeTableId);
		return timeTableRepository.findById(timeTableId);
	}

	@Override
	@Transactional
	@Secured({ "ROLE_ADMIN", "ROLE_STUFF" })
	public void deleteSchedule(Long id) throws Exception {
		timeTableRepository.deleteById(id);
		logger.info("DELETE TimeTable Id = {} for not free time", id);
	}

	@Override
	@Transactional
	@Secured({ "ROLE_ADMIN", "ROLE_STUFF" })
	public boolean saveSchedule(TimeTable timeTable) throws SQLException {
		List<TimeTable> scheduleInTime = timeTableRepository
				.findByTimeTableDateAndPairNumber(timeTable.getTimeTableDate(), timeTable.getPairNumber());
		if (scheduleInTime.isEmpty()) {
			timeTableRepository.save(timeTable);
			logger.info("SAVE free time TimeTable Id = {}", timeTable.getId());
		} else {
			if (isScheduleBusy(scheduleInTime, timeTable)) {
				logger.info("NOT SAVE schedule (group or teacher are busy for this time)");
				return false;
			} else {
				timeTableRepository.save(timeTable);
				logger.info("SAVE another lesson TimeTable Id = {} for one time", timeTable.getId());
			}
		}
		return true;
	}

	@Override
	@Transactional
	@Secured({ "ROLE_ADMIN", "ROLE_STUFF" })
	public void editSchedule(TimeTable scheduleEdit) throws SQLException {
		List<TimeTable> scheduleForTime = timeTableRepository
				.findByTimeTableDateAndPairNumber(scheduleEdit.getTimeTableDate(), scheduleEdit.getPairNumber());
		if (!scheduleForTime.isEmpty()) {
			for (TimeTable timeTable : scheduleForTime) {
				if (scheduleEdit.getGroup().equals(timeTable.getGroup())
						|| scheduleEdit.getTeacher().equals(timeTable.getTeacher())) {
					timeTableRepository.delete(timeTable);
					timeTableRepository.save(scheduleEdit);
					logger.info("REMOVE old timeTable and EDIT new timeTable id = {}", scheduleEdit.getId());
				} else {
					timeTableRepository.save(scheduleEdit);
					logger.info("EDIT TimeTable Id = {}", scheduleEdit.getId());
				}
			}
		} else {
			timeTableRepository.save(scheduleEdit);
			logger.info("EDIT TimeTable Id = {} for free time", scheduleEdit.getId());
		}
	}

	@Override
	@Secured("ROLE_TEACHER")
	public List<TimeTable> getTeacherSchedule(Long userId) throws Exception {
		List<TimeTable> teacherSchedule = new ArrayList<>();
		Optional<Teacher> teacher = teacherRepository.findByUserId(userId);
		if (teacher.isPresent()) {
			teacherSchedule = timeTableRepository.findByTeacherId(teacher.get().getId());
			logger.info("Take schedule for teacher id = {} by userId = {}", teacher.get().getId(), userId);
		} else {
			logger.info("Not found teacher by userId = {}", userId);
		}
		return teacherSchedule;
	}

	@Override
	@Secured("ROLE_STUDENT")
	public List<TimeTable> getStudentSchedule(Long userId) throws Exception {
		List<TimeTable> studentSchedule = new ArrayList<>();
		Optional<Student> student = studentRepository.findByUserId(userId);
		if (student.isPresent()) {
			if (student.get().getGroup() != null) {
				studentSchedule = timeTableRepository.findByGroupId(student.get().getGroup().getId());
				logger.info("Take schedule for student id = {} by userId = {}", student.get().getId(), userId);
			} else {
				logger.info("Student Id = {} not enroll to group", student.get().getId());
			}
		} else {
			logger.info("Not found student by userId = {}", userId);
		}
		return studentSchedule;
	}

	private boolean isScheduleBusy(List<TimeTable> scheduleInTime, TimeTable timeTable) {
		return scheduleInTime.stream().anyMatch(t -> t.getGroup().equals(timeTable.getGroup()))
				|| scheduleInTime.stream().anyMatch(t -> t.getTeacher().equals(timeTable.getTeacher()));
	}
}
