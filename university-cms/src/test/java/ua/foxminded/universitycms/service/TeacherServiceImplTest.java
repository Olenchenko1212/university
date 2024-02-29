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

import ua.foxminded.universitycms.models.Teacher;
import ua.foxminded.universitycms.repository.CourseRepository;
import ua.foxminded.universitycms.repository.TeacherRepository;

@SpringBootTest(classes = { TeacherServiceImpl.class })
public class TeacherServiceImplTest {
	@MockBean
	TeacherRepository teacherRepository;
	@MockBean
	CourseRepository courseRepository;

	@Autowired
	TeacherServiceImpl teacherService;

	@Test
	void whenTeacherNotFoundExpectTeacherNotDelete() throws SQLException, Exception {
		Teacher teacher = new Teacher();
		teacher.setId(1L);
		teacher.setTeacherName("Bob");
		teacher.setTeacherSurname("Harvy");
		
		when(teacherRepository.findById(teacher.getId())).thenReturn(Optional.empty());
		teacherService.deleteTeacher(teacher.getId());
		verify(teacherRepository, times(1)).findById(teacher.getId());
		verify(teacherRepository, never()).deleteById(teacher.getId());
	}
	
	@Test
	void whenTeacherArePresentExpectTeacherDelete() throws SQLException, Exception {
		Teacher teacher = new Teacher();
		teacher.setId(1L);
		teacher.setTeacherName("");
		teacher.setTeacherSurname("Harvy");

		when(teacherRepository.findById(teacher.getId())).thenReturn(Optional.ofNullable(teacher));
		teacherService.deleteTeacher(teacher.getId());
		verify(teacherRepository, times(1)).findById(teacher.getId());
		verify(teacherRepository, times(1)).deleteById(teacher.getId());
	}
}
