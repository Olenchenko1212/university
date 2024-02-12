package ua.foxminded.universitycms.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import ua.foxminded.universitycms.models.Course;
import ua.foxminded.universitycms.models.Teacher;

public interface TeacherService {
	List<Teacher> getAllTeachers() throws SQLException;
	List<Teacher> getFreeTeachers() throws SQLException;
	Optional<Teacher> getTeacherById(Long teacherId) throws SQLException;
	void saveTeacherOnCourse(Teacher teacher, Course course) throws Exception, SQLException;
	void deleteTeacher(Long teacherId) throws SQLException;
}
