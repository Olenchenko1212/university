package ua.foxminded.universitycms.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.universitycms.dao.CourseDao;
import ua.foxminded.universitycms.models.Course;

@Service
public class CourseServiceImpl implements CourseService {
	private static Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

	private final CourseDao courseDao;

	public CourseServiceImpl(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	@Override
	public List<Course> getAllCourses() {
		List<Course> allCourses = new ArrayList<>(courseDao.findAll());
		logger.info("Getting all {} courses from DB", allCourses.size());
		return allCourses;
	}
	
	@Override
	public Optional<Course> getCourseById(Long courseId) throws SQLException {
		return courseDao.findById(courseId);
	}
	
	@Override
	@Transactional
	public void saveCourse(Course course) throws Exception, SQLException {
		if(!courseDao.findByCourseName(course.getCourseName()).isPresent()) {
			courseDao.saveAndFlush(course);
			logger.info("Save course {} into DB", course.getCourseName());
		} else {
			logger.error("Course {} is allready in DB",
				course.getCourseName(), new Exception("Course is allready in DB"));
		}
	}
	
	@Override
	public void deleteCourse(Long courseId) throws SQLException {
		logger.info("Getting course Id = {} for the deleting", courseId);
		if(courseDao.findById(courseId).isPresent()) {
			courseDao.deleteById(courseId);
			logger.info("Course Id = {} delete completely", courseId);
		} else {
			logger.error("Not find course from DB", new Exception("Not find course from DB"));
		}
	}
}
