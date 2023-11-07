package ua.foxminded.universitycms.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.universitycms.models.Group;
import ua.foxminded.universitycms.models.Student;
import ua.foxminded.universitycms.repository.GroupRepository;
import ua.foxminded.universitycms.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {
	private static Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

	private final StudentRepository studentRepository;
	private final GroupRepository groupRepository;

	public StudentServiceImpl(StudentRepository studentRepository, GroupRepository groupRepository) {
		this.studentRepository = studentRepository;
		this.groupRepository = groupRepository;
	}

	@Override
	public List<Student> getAllStudents() throws SQLException {
		List<Student> allStudents = studentRepository.findAll();
		logger.info("Getting all {} students from DB", allStudents.size());
		return allStudents;
	}

	@Override
	public Optional<Student> getStudentById(Long studentId) throws SQLException {
		logger.info("Getting student id = {} from DB", studentId);
		return studentRepository.findById(studentId);
	}

	@Override
	@Transactional
	public void saveStudent(Student student, Long groupId) throws Exception, SQLException {
		Optional<Group> group = groupRepository.findById(groupId);
		if (group.isPresent()) {
			student.setGroup(group.get());
			group.get().getStudents().add(student);
			studentRepository.saveAndFlush(student);
			logger.info("Save student {} into DB", student.getStudentSurname());
		} else {
			System.out.println(group);
			logger.error("The group with id = {} is not present from univesity", groupId,
					new Exception("The group is not present from univesity"));
		}
	}

	@Override
	@Transactional
	public void deleteStudent(Long studentId) throws SQLException {
		studentRepository.deleteById(studentId);
		logger.info("Student Id = {} delete from DB", studentId);
	}
}
