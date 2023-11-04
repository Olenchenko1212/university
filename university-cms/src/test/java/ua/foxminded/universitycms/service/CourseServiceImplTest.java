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

import ua.foxminded.universitycms.dao.CourseDao;
import ua.foxminded.universitycms.models.Course;

@SpringBootTest(classes = { CourseServiceImpl.class })
public class CourseServiceImplTest {

	@MockBean
	CourseDao courseDao;

	@Autowired
	CourseServiceImpl courseService;
	
	@Test
	void whenCourseNameIsNotPresentInTableExpectCourseSave() throws Exception {
		Course course = new Course();
		course.setCourseName("QQQQ");
		course.setCourseDescription("WWWWWW");
		
		when(courseDao.findByCourseName(course.getCourseName())).thenReturn(Optional.empty());
		courseService.saveCourse(course);
		verify(courseDao, times(1)).findByCourseName(course.getCourseName());
		verify(courseDao, times(1)).saveAndFlush(course);
	}
	
	@Test
	void whenCourseNameIsPresentInTableExpectNoSaving() throws Exception {
		Course course = new Course();
		course.setCourseName("Math");
		course.setCourseDescription("Learn Mathematics very well");
		
		when(courseDao.findByCourseName(course.getCourseName())).thenReturn(Optional.of(course));
		courseService.saveCourse(course);
		verify(courseDao, times(1)).findByCourseName(course.getCourseName());
		verify(courseDao, never()).saveAndFlush(course);
	}
	
	@Test
	void whenCourseIsPresentInTableExpectCourseDelete() throws SQLException {
		Long courseId = 1L;
		Optional<Course> courseForDeleting = Optional.of((new Course(1L, "English", "Learn English very well")));
		when(courseDao.findById(courseId)).thenReturn(courseForDeleting);
		courseService.deleteCourse(courseId);
		verify(courseDao, times(1)).findById(courseId);
		verify(courseDao, times(1)).deleteById(courseId);
	}
	
	@Test
	void whenCourseForDeletingIsNotPresentInTableExpectNoDeleting() throws Exception {
		Long courseId = 4L;
		
		when(courseDao.findById(courseId)).thenReturn(Optional.empty());
		courseService.deleteCourse(courseId);
		verify(courseDao, times(1)).findById(courseId);
		verify(courseDao, never()).deleteById(courseId);
	}
}
