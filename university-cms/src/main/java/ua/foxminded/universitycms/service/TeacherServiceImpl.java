package ua.foxminded.universitycms.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.universitycms.models.Course;
import ua.foxminded.universitycms.models.Teacher;
import ua.foxminded.universitycms.repository.CourseRepository;
import ua.foxminded.universitycms.repository.TeacherRepository;

@Service
public class TeacherServiceImpl implements TeacherService {
	private static Logger logger = LoggerFactory.getLogger(TeacherServiceImpl.class);

	private final TeacherRepository teacherRepository;
	private final CourseRepository courseRepository;

	public TeacherServiceImpl(TeacherRepository teacherRepository, CourseRepository courseRepository) {
		this.teacherRepository = teacherRepository;
		this.courseRepository = courseRepository;
	}

	@Override
	public List<Teacher> getAllTeachers() {
		List<Teacher> allTeachers = teacherRepository.findAll();
		logger.info("Getting all {} teachers from DB", allTeachers.size());
		return allTeachers;
	}

	@Override
	public Optional<Teacher> getTeacherById(Long teacherId) throws SQLException {
		logger.info("Getting teacher id = {} from DB", teacherId);
		return teacherRepository.findById(teacherId);
	}

	@Override
	@Transactional
	public void saveTeacherOnCourse(Teacher teacher, Course course) throws Exception, SQLException {
		if (!teacher.getTeacherName().isEmpty() && !teacher.getTeacherSurname().isEmpty()) {
			if (!course.getCourseName().isEmpty() && !course.getCourseDescription().isEmpty()) {
				courseRepository.save(course);
				teacher.setCourse(course);
				teacherRepository.save(teacher);
				logger.info("Save teacher {} and course {} into DB", teacher.getTeacherSurname(),
						course.getCourseName());
			} else {
				logger.error("The course's name or description is empty",
						new Exception("The course's name or description is empty"));
			}
		} else {
			logger.error("The teacher's name or surname is empty",
					new Exception("The teacher's name or surname is empty"));
		}
	}

	@Override
	@Transactional
	public void deleteTeacher(Long teacherId) throws SQLException {
		teacherRepository.deleteById(teacherId);
		logger.info("Teacher Id = {} delete from DB", teacherId);
	}
}
