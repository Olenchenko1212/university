package ua.foxminded.universitycms.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	public void deleteAllCourses(List<Course> courses) throws SQLException {
		logger.info("Getting all {} courses from DB for the deleting", courses.size());
		if(!courses.isEmpty()) {
			courseDao.deleteAllInBatch(courses);
			logger.info("{} courses delete completely", courses.size());
		} else {
			logger.error("Not find courses from DB", new Exception("Not find courses from DB"));
		}
	}
}
