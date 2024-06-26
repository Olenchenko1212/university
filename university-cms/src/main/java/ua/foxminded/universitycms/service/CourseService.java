package ua.foxminded.universitycms.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import ua.foxminded.universitycms.models.Course;

public interface CourseService {
	List<Course> getAllCourses() throws SQLException;
	Optional<Course> getCourseById(Long id) throws SQLException;
	void saveCourse(Course course) throws Exception, SQLException;
	void assignCourse(Course course) throws Exception, SQLException;
	void deleteCourse(Long id) throws Exception, SQLException;
}
