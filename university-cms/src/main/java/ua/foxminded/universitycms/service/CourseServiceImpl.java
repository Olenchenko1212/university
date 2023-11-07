package ua.foxminded.universitycms.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.universitycms.models.Course;
import ua.foxminded.universitycms.repository.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService {
	private static Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

	private final CourseRepository courseRepository;

	public CourseServiceImpl(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	@Override
	public List<Course> getAllCourses() {
		List<Course> allCourses = courseRepository.findAll();
		logger.info("Getting all {} courses from DB", allCourses.size());
		return allCourses;
	}
	
	@Override
	public Optional<Course> getCourseById(Long courseId) throws SQLException {
		logger.info("Getting course id = {} from DB", courseId);
		return courseRepository.findById(courseId);
	}
	
	@Override
	@Transactional
	public void saveCourse(Course course) throws Exception, SQLException {
		if(!courseRepository.findByCourseName(course.getCourseName()).isPresent()) {
			courseRepository.saveAndFlush(course);
			logger.info("Save course {} into DB", course.getCourseName());
		} else {
			logger.error("Course {} is allready in DB",
				course.getCourseName(), new Exception("Course is allready in DB"));
		}
	}
	
	@Override
	@Transactional
	public void deleteCourse(Long courseId) throws SQLException {
		logger.info("Getting course Id = {} for the deleting", courseId);
		if(courseRepository.findById(courseId).isPresent()) {
			courseRepository.deleteById(courseId);
			logger.info("Course Id = {} delete completely", courseId);
		} else {
			logger.error("Not find course from DB", new Exception("Not find course from DB"));
		}
	}
}
