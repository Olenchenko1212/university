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

import ua.foxminded.universitycms.models.Course;
import ua.foxminded.universitycms.models.Teacher;
import ua.foxminded.universitycms.repository.CourseRepository;
import ua.foxminded.universitycms.repository.TeacherRepository;

@SpringBootTest(classes = { CourseServiceImpl.class })
public class CourseServiceImplTest {

	@MockBean
	CourseRepository courseRepository;
	
	@MockBean
	TeacherRepository teacherRepository;

	@Autowired
	CourseServiceImpl courseService;
	
	@Test
	void whenCourseIsPresentAndLinkWithTeacherExpectCourseDeleteAndUnlinkWithTeacher() throws SQLException {
		Long courseId = 1L;
		Optional<Course> courseForDeleting = Optional.of(new Course(1L, "English", "Learn English very well"));
		Optional<Teacher> teacherLink = Optional.of(new Teacher(45L, "Bongq", "Mayc"));
		teacherLink.get().setCourse(courseForDeleting.get());
		when(courseRepository.findById(courseId)).thenReturn(courseForDeleting);
		when(teacherRepository.findByCourseId(courseId)).thenReturn(teacherLink);	
		courseService.deleteCourse(courseId);
		verify(teacherRepository, times(1)).save(teacherLink.get());
		verify(courseRepository, times(1)).deleteById(courseId);
	}
	
	@Test
	void whenCourseIsPresentAndNotLinkWithTeacherExpectCourseDelete() throws SQLException {
		Long courseId = 1L;
		Optional<Course> courseForDeleting = Optional.of(new Course(1L, "English", "Learn English very well"));
		Optional<Teacher> teacherUnlink =  Optional.of(new Teacher(45L, "Bongq", "Mayc"));	
		when(courseRepository.findById(courseId)).thenReturn(courseForDeleting);
		when(teacherRepository.findByCourseId(courseId)).thenReturn(Optional.empty());
		courseService.deleteCourse(courseId);
		verify(courseRepository, times(1)).deleteById(courseId);
		verify(teacherRepository, never()).save(teacherUnlink.get());
	}
	
	@Test
	void whenCourseForDeletingIsNotPresentInTableExpectNoDeleting() throws Exception {
		Long courseId = 4L;
		Optional<Teacher> teacherUnlink =  Optional.of(new Teacher(45L, "Bongq", "Mayc"));	
		when(courseRepository.findById(courseId)).thenReturn(Optional.empty());
		courseService.deleteCourse(courseId);
		verify(courseRepository, never()).deleteById(courseId);
		verify(teacherRepository, never()).save(teacherUnlink.get());
	}
	
	@Test
	void whenAssignCourseToTeacherExpectSaveCourseLinkWithTeacher() throws Exception {
		Course newCourse = new Course(1L, "English", "Learn English very well");

		Optional<Course> oldCourse = Optional.of(new Course(1L, "English", "Learn English very well"));
		oldCourse.get().setTeacher(null);
	
		Optional<Teacher> teacherLink =  Optional.of(new Teacher(45L, "Bongq", "Mayc"));	
		teacherLink.get().setCourse(newCourse);
		newCourse.setTeacher(teacherLink.get());
		newCourse.getTeacher().setCourse(newCourse);

		when(courseRepository.findById(newCourse.getId())).thenReturn(oldCourse);
		when(teacherRepository.save(newCourse.getTeacher())).thenReturn(newCourse.getTeacher());
		when(courseRepository.save(newCourse)).thenReturn(newCourse);

		courseService.assignCourse(newCourse);
		verify(teacherRepository).save(newCourse.getTeacher());
		verify(courseRepository).save(newCourse);
	}
	
	@Test
	void whenAssignCourseToAnotherTeacherExpectSaveCourseLinkWithAnotherTeacher() throws Exception {
		Course newCourse = new Course(1L, "English", "Learn English very well");
		Optional<Teacher> newTeacher =  Optional.of(new Teacher(30L, "Bongq", "Mayc"));	
		newTeacher.get().setCourse(newCourse);
		newCourse.setTeacher(newTeacher.get());
		newCourse.getTeacher().setCourse(newCourse);

		Optional<Course> oldCourse = Optional.of(new Course(1L, "English", "Learn English very well"));
		Optional<Teacher> oldteacher =  Optional.of(new Teacher(45L, "Bongq", "Mayc"));	
		oldCourse.get().setTeacher(oldteacher.get());
		newTeacher.get().setCourse(newCourse);
		newCourse.setTeacher(newTeacher.get());
		newCourse.getTeacher().setCourse(newCourse);

		when(teacherRepository.findByCourseId(oldCourse.get().getId())).thenReturn(oldteacher);
		when(courseRepository.findById(newCourse.getId())).thenReturn(oldCourse);
		when(teacherRepository.save(newCourse.getTeacher())).thenReturn(newCourse.getTeacher());
		when(courseRepository.save(newCourse)).thenReturn(newCourse);

		courseService.assignCourse(newCourse);
		verify(teacherRepository).save(newCourse.getTeacher());
		verify(courseRepository).save(newCourse);
	}
	@Test
	void whenReassignCourseToTeacherExpectSaveCourseUnLinkToTeacher() throws Exception {
		Course newCourse = new Course(1L, "English", "Learn English very well");
		newCourse.setTeacher(null);

		Optional<Course> oldCourse = Optional.of(new Course(1L, "English", "Learn English very well"));
		Optional<Teacher> oldteacher =  Optional.of(new Teacher(45L, "Bongq", "Mayc"));	
		oldCourse.get().setTeacher(oldteacher.get());
		oldteacher.get().setCourse(oldCourse.get());

		when(teacherRepository.findByCourseId(oldCourse.get().getId())).thenReturn(oldteacher);
		when(courseRepository.findById(newCourse.getId())).thenReturn(oldCourse);
		when(teacherRepository.save(oldCourse.get().getTeacher())).thenReturn(oldCourse.get().getTeacher());
		when(courseRepository.save(newCourse)).thenReturn(newCourse);

		courseService.assignCourse(newCourse);
		verify(teacherRepository).save(oldCourse.get().getTeacher());
		verify(courseRepository).save(newCourse);
	}
}

