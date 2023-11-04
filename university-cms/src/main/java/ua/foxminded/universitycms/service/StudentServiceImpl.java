package ua.foxminded.universitycms.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.universitycms.dao.GroupDao;
import ua.foxminded.universitycms.dao.StudentDao;
import ua.foxminded.universitycms.models.Group;
import ua.foxminded.universitycms.models.Student;

@Service
public class StudentServiceImpl implements StudentService {
	private static Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

	private final StudentDao studentDao;
	private final GroupDao groupDao;

	public StudentServiceImpl(StudentDao studentDao, GroupDao groupDao) {
		this.studentDao = studentDao;
		this.groupDao = groupDao;
	}

	public List<Student> getAllStudents() throws SQLException {
		List<Student> allStudents = new ArrayList<>(studentDao.findAll());
		logger.info("Getting all {} students from DB", allStudents.size());
		return allStudents;
	}

	@Override
	public Optional<Student> getStudentById(Long studentId) throws SQLException {
		return studentDao.findById(studentId);
	}

	@Override
	@Transactional
	public void saveStudent(Student student, Long groupId) throws Exception, SQLException {
		Optional<Group> group = groupDao.findById(groupId);
		if (group.isPresent()) {
			student.setGroup(group.get());
			group.get().getStudents().add(student);
			studentDao.saveAndFlush(student);
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
		studentDao.deleteById(studentId);
		logger.info("Student Id = {} delete from DB", studentId);
	}
}
