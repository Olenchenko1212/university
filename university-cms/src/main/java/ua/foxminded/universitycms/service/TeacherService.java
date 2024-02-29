package ua.foxminded.universitycms.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import ua.foxminded.universitycms.models.Teacher;

public interface TeacherService {
	List<Teacher> getAllTeachers() throws SQLException;
	Optional<Teacher> getTeacherById(Long teacherId) throws SQLException;
	void saveTeacher(Teacher teacher) throws Exception, SQLException;
	void deleteTeacher(Long teacherId) throws SQLException;
}
