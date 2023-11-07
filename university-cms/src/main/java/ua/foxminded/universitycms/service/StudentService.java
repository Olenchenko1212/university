package ua.foxminded.universitycms.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ua.foxminded.universitycms.models.Student;

@Service
public interface StudentService {
	List<Student> getAllStudents() throws SQLException;
	Optional<Student> getStudentById(Long studentId) throws SQLException;
	void saveStudent(Student student, Long groupId) throws Exception, SQLException;
	void deleteStudent(Long studentId) throws SQLException;
}
