package ua.foxminded.universitycms.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.universitycms.models.Student;
import ua.foxminded.universitycms.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {
	private static Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

	private final StudentRepository studentRepository;

	public StudentServiceImpl(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@Override
	@Secured({ "ROLE_ADMIN", "ROLE_STUFF", "ROLE_STUDENT", "ROLE_TEACHER" })
	public List<Student> getAllStudents() throws SQLException {
		List<Student> allStudents = studentRepository.findAll();
		logger.info("Getting all {} students from DB", allStudents.size());
		return allStudents;
	}

	@Override
	@Secured({ "ROLE_ADMIN", "ROLE_STUFF", "ROLE_STUDENT" })
	public Optional<Student> getStudentById(Long studentId) throws SQLException {
		logger.info("Getting student id = {} from DB", studentId);
		return studentRepository.findById(studentId);
	}

	@Override
	@Transactional
	@Secured("ROLE_STUDENT")
	public void saveStudent(Student student) throws Exception, SQLException {
		if(student.getGroup().getId() == -1L) {
			student.setGroup(null);
		}
		studentRepository.save(student);
		logger.info("UPDATE student id = {} in DB", student.getId());
	}

	@Override
	@Transactional
	@Secured({ "ROLE_ADMIN", "ROLE_STUFF" })
	public void assignStudent(Student student) throws Exception, SQLException {
		studentRepository.save(student);
		logger.info("ASSIGN/REASSIGN student id = {} to group", student.getId());
	}
}
