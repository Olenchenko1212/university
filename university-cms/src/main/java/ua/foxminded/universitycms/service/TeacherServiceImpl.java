package ua.foxminded.universitycms.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.universitycms.models.Teacher;
import ua.foxminded.universitycms.repository.TeacherRepository;

@Service
public class TeacherServiceImpl implements TeacherService {
	private static Logger logger = LoggerFactory.getLogger(TeacherServiceImpl.class);

	private final TeacherRepository teacherRepository;

	public TeacherServiceImpl(TeacherRepository teacherRepository) {
		this.teacherRepository = teacherRepository;
	}

	@Override
	@Secured({"ROLE_ADMIN", "ROLE_STUFF", "ROLE_STUDENT", "ROLE_TEACHER"})
	public List<Teacher> getAllTeachers() {
		List<Teacher> allTeachers = teacherRepository.findAll();
		logger.info("Getting all {} teachers from DB", allTeachers.size());
		return allTeachers;
	}

	@Override
	@Secured({"ROLE_ADMIN", "ROLE_STUFF", "ROLE_TEACHER"})
	public Optional<Teacher> getTeacherById(Long teacherId) throws SQLException {
		logger.info("Getting teacher id = {} from DB", teacherId);
		return teacherRepository.findById(teacherId);
	}

	@Override
	@Transactional
	@Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
	public void saveTeacher(Teacher teacher) throws Exception, SQLException {
		if(teacher.getCourse().getId() == -1L) {
			teacher.setCourse(null);
		}
		teacherRepository.save(teacher);
		logger.info("SAVE/UPDATE teacher id = {} when change his name or surname", teacher.getId());
	}

	@Override
	@Transactional
	@Secured("ROLE_ADMIN")
	public void deleteTeacher(Long teacherId) throws SQLException {
		Optional<Teacher> teacher = teacherRepository.findById(teacherId);
		if (teacher.isPresent()) {
			teacherRepository.deleteById(teacherId);
			logger.info("Teacher Id = {} DELETE from DB", teacherId);
		} else {
			logger.error("Teacher Id = {} is not find in DB", teacherId, new Exception("Teacher is not find in DB"));
		}
	}
}
