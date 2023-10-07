package ua.foxminded.universitycms.service;

import java.sql.SQLException;
import java.util.List;

import ua.foxminded.universitycms.models.Course;

public interface CourseService {
	List<Course> getAllCourses() throws SQLException;
	void saveCourse(Course course) throws Exception, SQLException;
	void deleteAllCourses(List<Course> courses) throws SQLException;
}
