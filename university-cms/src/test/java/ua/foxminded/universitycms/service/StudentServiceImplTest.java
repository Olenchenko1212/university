package ua.foxminded.universitycms.service;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ua.foxminded.universitycms.dao.GroupDao;
import ua.foxminded.universitycms.dao.StudentDao;
import ua.foxminded.universitycms.models.Group;
import ua.foxminded.universitycms.models.Student;

@SpringBootTest(classes = { StudentServiceImpl.class })
public class StudentServiceImplTest {
	@MockBean
	StudentDao studentDao;
	
	@MockBean
	GroupDao groupDao;
	
	@Autowired
	StudentServiceImpl studentService;
	
	@Test
	void whenGroupIsPresentInTableExpectSavingStudentAndRelatingWithGroup() throws SQLException, Exception {
		Student student = new Student();
		student.setStudentId(1L);
		student.setStudentName("Marck");
		student.setStudentSurname("Bry");
		Group group = new Group();
		group.setGroupId(1L);
		group.setGroupName("hh-23");
		
		when(groupDao.findById(group.getGroupId())).thenReturn(Optional.of(group));
		studentService.saveStudent(student, 1L);
		verify(groupDao, times(1)).findById(group.getGroupId());
		verify(studentDao, times(1)).saveAndFlush(student);
	}
	
	@Test
	void whenGroupIsNotPresentInTableExpectNotSavingStudentAndRelatingWithGroup() throws SQLException, Exception {
		Student student = new Student();
		student.setStudentId(1L);
		student.setGroupId(-1L);
		student.setStudentName("Marck");
		student.setStudentSurname("Golem");
		
		when(groupDao.findById(2L)).thenReturn(Optional.empty());
		studentService.saveStudent(student, 2L);
		verify(groupDao, times(1)).findById(2L);
		verify(studentDao, never()).saveAndFlush(student);
	}
	
	
}