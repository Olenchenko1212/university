package ua.foxminded.universitycms.service;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ua.foxminded.universitycms.dao.CourseDao;
import ua.foxminded.universitycms.dao.TeacherDao;
import ua.foxminded.universitycms.models.Course;
import ua.foxminded.universitycms.models.Teacher;

@SpringBootTest(classes = { TeacherServiceImpl.class })
public class TeacherServiceImplTest {
	@MockBean
	TeacherDao teacherDao;
	@MockBean
	CourseDao courseDao;

	@Autowired
	TeacherServiceImpl teacherService;

	@Test
	void whenDateOfTeacherAndCourseArePresentExpectSavingTeacherAndCourseWithRelation() throws SQLException, Exception {
		Teacher teacher = new Teacher();
		teacher.setTeacherName("Bob");
		teacher.setTeacherSurname("Harvy");
		Course course = new Course();
		course.setCourseName("Philosophy");
		course.setCourseDescription("Learn Philosophy very well");
		
		teacherService.saveTeacherOnCourse(teacher, course);
		verify(teacherDao, times(1)).save(teacher);
		verify(courseDao, times(1)).save(course);
	}
	
	@Test
	void whenDateOfTeacherAreEmptyExpectNotSavingTeacherAndCourseWithRelation() throws SQLException, Exception {
		Teacher teacher = new Teacher();
		teacher.setTeacherName("");
		teacher.setTeacherSurname("Harvy");
		Course course = new Course();
		course.setCourseName("Philosophy");
		course.setCourseDescription("Learn Philosophy very well");

		teacherService.saveTeacherOnCourse(teacher, course);
		verify(teacherDao, never()).save(teacher);
		verify(courseDao, never()).save(course);
	}
	
	@Test
	void whenDateOfCourseAreEmptyExpectNotSavingTeacherAndCourseWithRelation() throws SQLException, Exception {
		Teacher teacher = new Teacher();
		teacher.setTeacherName("Bob");
		teacher.setTeacherSurname("Harvy");
		Course course = new Course();
		course.setCourseName("");
		course.setCourseDescription("Learn Philosophy very well");

		teacherService.saveTeacherOnCourse(teacher, course);
		verify(teacherDao, never()).save(teacher);
		verify(courseDao, never()).save(course);
	}
}
