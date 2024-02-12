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
import ua.foxminded.universitycms.models.Group;
import ua.foxminded.universitycms.repository.CourseRepository;
import ua.foxminded.universitycms.repository.GroupRepository;

@SpringBootTest(classes = { GroupServiceImpl.class })
public class GroupServiceImplTest {

	@MockBean
	GroupRepository groupRepository;
	@MockBean
	CourseRepository courseRepository;

	@Autowired
	GroupServiceImpl groupService;

	@Test
	void whenGroupNameIsNotPresentInTableExpectSavingGroup() throws Exception {
		Group group = new Group();
		group.setGroupId(1L);
		group.setGroupName("df-45");

		when(groupRepository.findByGroupName(group.getGroupName())).thenReturn(Optional.empty());
		groupService.saveGroup(group);
		verify(groupRepository, times(1)).findByGroupName(group.getGroupName());
		verify(groupRepository, times(1)).saveAndFlush(group);
	}

	@Test
	void whenGroupNameIsPresentInTableExpectNoSavingGroup() throws Exception {
		Group group = new Group();
		group.setGroupId(4L);
		group.setGroupName("ff-45");

		when(groupRepository.findByGroupName(group.getGroupName())).thenReturn(Optional.of(group));
		groupService.saveGroup(group);
		verify(groupRepository, times(1)).findByGroupName(group.getGroupName());
		verify(groupRepository, never()).saveAndFlush(group);
	}

	@Test
	void whenGroupAndCourseAreNotPresentInTablesExpectSavingGroupCourseAndRelationsGroupAndCourse() throws Exception {
		Group group = new Group();
		group.setGroupId(4L);
		group.setGroupName("ff-45");
		Course course = new Course();
		course.setCourseName("Philosophy");
		course.setCourseDescription("Learn Philosophy very well");
		
		when(groupRepository.findByGroupName(group.getGroupName())).thenReturn(Optional.empty());
		when(courseRepository.findByCourseName(course.getCourseName())).thenReturn(Optional.empty());
		groupService.saveGroupWithCourse(group, course);
		verify(groupRepository, times(1)).findByGroupName(group.getGroupName());
		verify(courseRepository, times(1)).findByCourseName(course.getCourseName());
		verify(groupRepository, times(1)).save(group);
		verify(courseRepository, times(1)).save(course);
	}
	
	@Test
	void whenGroupNameAndCourseNameArePresentInTablesExpectNotSavingGroupAndCourseAndNotRelationsGroupAndCourse() throws Exception {
		Group group = new Group();
		group.setGroupId(4L);
		group.setGroupName("ff-45");
		Course course = new Course();
		course.setCourseName("Philosophy");
		course.setCourseDescription("Learn Philosophy very well");
		
		when(groupRepository.findByGroupName(group.getGroupName())).thenReturn(Optional.of(group));
		when(courseRepository.findByCourseName(course.getCourseName())).thenReturn(Optional.of(course));
		groupService.saveGroupWithCourse(group, course);
		verify(groupRepository, times(1)).findByGroupName(group.getGroupName());
		verify(courseRepository, never()).findByCourseName(course.getCourseName());
		verify(groupRepository, never()).save(group);
		verify(courseRepository, never()).save(course);
	}
	
	@Test
	void whenGroupAndCourseArePresentInTablesExpectSavingRelationsGroupAndCourse() throws Exception {
		Group group = new Group();
		group.setGroupId(4L);
		group.setGroupName("ff-45");
		Course course = new Course();
		course.setId(5L);
		course.setCourseName("Philosophy");
		course.setCourseDescription("Learn Philosophy very well");
		
		when(groupRepository.findById(group.getGroupId())).thenReturn(Optional.of(group));
		when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
		groupService.saveEnrollGroupToCourse(group.getGroupId(), course.getId());
		verify(groupRepository, times(1)).findById(group.getGroupId());
		verify(courseRepository, times(1)).findById(course.getId());
		verify(courseRepository, times(1)).save(course);
	}
	
	@Test
	void whenGroupOrCourseAreNotPresentInTablesExpectNotSavingRelationsGroupAndCourse() throws Exception {
		Group group = new Group();
		group.setGroupId(4L);
		group.setGroupName("ff-45");
		Course course = new Course();
		course.setId(5L);
		course.setCourseName("Philosophy");
		course.setCourseDescription("Learn Philosophy very well");
		
		when(groupRepository.findById(group.getGroupId())).thenReturn(Optional.empty());
		when(courseRepository.findById(course.getId())).thenReturn(Optional.empty());
		groupService.saveEnrollGroupToCourse(group.getGroupId(), course.getId());
		verify(groupRepository, times(1)).findById(group.getGroupId());
		verify(courseRepository, times(1)).findById(course.getId());
		verify(courseRepository, never()).save(course);
	}
	
	@Test
	void whenRelationsGroupAndCourseArePresentInTablesExpectNotSavingRelationsGroupAndCourse() throws Exception {
		Group group = new Group();
		group.setGroupId(4L);
		group.setGroupName("ff-45");
		Course course = new Course();
		course.setId(5L);
		course.setCourseName("Philosophy");
		course.setCourseDescription("Learn Philosophy very well");
		course.getGroups().add(group);
		group.getCourses().add(course);
		
		when(groupRepository.findById(group.getGroupId())).thenReturn(Optional.of(group));
		when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
		groupService.saveEnrollGroupToCourse(group.getGroupId(), course.getId());
		verify(groupRepository, times(1)).findById(group.getGroupId());
		verify(courseRepository, times(1)).findById(course.getId());
		verify(courseRepository, never()).save(course);
	}
	
	@Test
	void WhenGroupIsPresentInTableExpectDeletingGroup() throws SQLException {
		Group group = new Group();
		group.setGroupId(4L);
		group.setGroupName("ff-45");
		
		when(groupRepository.findById(group.getGroupId())).thenReturn(Optional.of(group));
		groupService.deleteGroup(group.getGroupId());
		verify(groupRepository, times(1)).findById(group.getGroupId());
		verify(groupRepository, times(1)).deleteById(group.getGroupId());
	}
	
	@Test
	void WhenGroupIsNotPresentInTableExpectNoDeletingGroup() throws SQLException {
		Group group = new Group();
		group.setGroupId(4L);
		group.setGroupName("ff-45");
		
		when(groupRepository.findById(group.getGroupId())).thenReturn(Optional.empty());
		groupService.deleteGroup(group.getGroupId());
		verify(groupRepository, times(1)).findById(group.getGroupId());
		verify(groupRepository, never()).deleteById(group.getGroupId());
	}
}
