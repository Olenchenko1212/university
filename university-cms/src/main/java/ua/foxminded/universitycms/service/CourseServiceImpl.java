package ua.foxminded.universitycms.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.universitycms.models.Course;
import ua.foxminded.universitycms.models.Teacher;
import ua.foxminded.universitycms.repository.CourseRepository;
import ua.foxminded.universitycms.repository.TeacherRepository;

@Service
public class CourseServiceImpl implements CourseService {
	private static Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

	private final CourseRepository courseRepository;
	private final TeacherRepository teacherRepository;

	public CourseServiceImpl(CourseRepository courseRepository, TeacherRepository teacherRepository) {
		this.courseRepository = courseRepository;
		this.teacherRepository = teacherRepository;
	}

	@Override
	@Secured({ "ROLE_ADMIN", "ROLE_STUFF", "ROLE_STUDENT", "ROLE_TEACHER" })
	public List<Course> getAllCourses() {
		List<Course> allCourses = courseRepository.findAll();
		logger.info("Getting all {} courses from DB", allCourses.size());
		return allCourses;
	}

	@Override
	@Secured({ "ROLE_ADMIN", "ROLE_STUFF" })
	public Optional<Course> getCourseById(Long id) throws SQLException {
		logger.info("Getting course id = {} from DB", id);
		return courseRepository.findById(id);
	}

	@Override
	@Transactional
	@Secured({ "ROLE_ADMIN", "ROLE_STUFF" })
	public void saveCourse(Course course) throws Exception, SQLException {
		courseRepository.save(course);
		logger.info("SAVE/UPDATE course id = {} to DB", course.getId());
	}

	@Override
	@Transactional
	@Secured("ROLE_ADMIN")
	public void deleteCourse(Long id) throws SQLException {
		Optional<Course> course = courseRepository.findById(id);
		Optional<Teacher> teacher = teacherRepository.findByCourseId(id);
		if (course.isPresent()) {
			if (teacher.isPresent()) {
				teacher.get().setCourse(null);
				teacherRepository.save(teacher.get());
				courseRepository.deleteById(id);
				logger.info("Course Id = {} delete completely", id);
			} else {
				courseRepository.deleteById(id);
				logger.info("Course Id = {} delete completely (course not link to teacher)", id);
			}
		} else {
			logger.error("Not find course from DB for deleting", new Exception("Not find course from DB"));
		}
	}

	@Override
	@Transactional
	@Secured("ROLE_STUFF")
	public void assignCourse(Course newCourse) throws Exception, SQLException {
		Optional<Teacher> teacher = teacherRepository.findByCourseId(newCourse.getId());
		if (newCourse.getTeacher() != null && teacher.isPresent()) {
			teacher.get().setCourse(null);
			newCourse.getTeacher().setCourse(newCourse);
		} else if (newCourse.getTeacher() != null && !teacher.isPresent()) {
			newCourse.getTeacher().setCourse(newCourse);
		} else if (newCourse.getTeacher() == null && teacher.isPresent()) {
			teacher.get().setCourse(null);
		}
		courseRepository.save(newCourse);
		logger.info("ASSIGN Course id = {} successful", newCourse.getId());
	}
}
