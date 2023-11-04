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
import ua.foxminded.universitycms.dao.GroupDao;
import ua.foxminded.universitycms.models.Course;
import ua.foxminded.universitycms.models.Group;

@SpringBootTest(classes = { GroupServiceImpl.class })
public class GroupServiceImplTest {

	@MockBean
	GroupDao groupDao;
	@MockBean
	CourseDao courseDao;

	@Autowired
	GroupServiceImpl groupService;

	@Test
	void whenGroupNameIsNotPresentInTableExpectSavingGroup() throws Exception {
		Group group = new Group();
		group.setGroupId(1L);
		group.setGroupName("df-45");

		when(groupDao.findByGroupName(group.getGroupName())).thenReturn(Optional.empty());
		groupService.saveGroup(group);
		verify(groupDao, times(1)).findByGroupName(group.getGroupName());
		verify(groupDao, times(1)).saveAndFlush(group);
	}

	@Test
	void whenGroupNameIsPresentInTableExpectNoSavingGroup() throws Exception {
		Group group = new Group();
		group.setGroupId(4L);
		group.setGroupName("ff-45");

		when(groupDao.findByGroupName(group.getGroupName())).thenReturn(Optional.of(group));
		groupService.saveGroup(group);
		verify(groupDao, times(1)).findByGroupName(group.getGroupName());
		verify(groupDao, never()).saveAndFlush(group);
	}

	@Test
	void whenGroupAndCourseAreNotPresentInTablesExpectSavingGroupCourseAndRelationsGroupAndCourse() throws Exception {
		Group group = new Group();
		group.setGroupId(4L);
		group.setGroupName("ff-45");
		Course course = new Course();
		course.setCourseName("Philosophy");
		course.setCourseDescription("Learn Philosophy very well");
		
		when(groupDao.findByGroupName(group.getGroupName())).thenReturn(Optional.empty());
		when(courseDao.findByCourseName(course.getCourseName())).thenReturn(Optional.empty());
		groupService.saveGroupWithCourse(group, course);
		verify(groupDao, times(1)).findByGroupName(group.getGroupName());
		verify(courseDao, times(1)).findByCourseName(course.getCourseName());
		verify(groupDao, times(1)).save(group);
		verify(courseDao, times(1)).save(course);
	}
	
	@Test
	void whenGroupNameAndCourseNameArePresentInTablesExpectNotSavingGroupAndCourseAndNotRelationsGroupAndCourse() throws Exception {
		Group group = new Group();
		group.setGroupId(4L);
		group.setGroupName("ff-45");
		Course course = new Course();
		course.setCourseName("Philosophy");
		course.setCourseDescription("Learn Philosophy very well");
		
		when(groupDao.findByGroupName(group.getGroupName())).thenReturn(Optional.of(group));
		when(courseDao.findByCourseName(course.getCourseName())).thenReturn(Optional.of(course));
		groupService.saveGroupWithCourse(group, course);
		verify(groupDao, times(1)).findByGroupName(group.getGroupName());
		verify(courseDao, never()).findByCourseName(course.getCourseName());
		verify(groupDao, never()).save(group);
		verify(courseDao, never()).save(course);
	}
	
	@Test
	void whenGroupAndCourseArePresentInTablesExpectSavingRelationsGroupAndCourse() throws Exception {
		Group group = new Group();
		group.setGroupId(4L);
		group.setGroupName("ff-45");
		Course course = new Course();
		course.setCourseId(5L);
		course.setCourseName("Philosophy");
		course.setCourseDescription("Learn Philosophy very well");
		
		when(groupDao.findById(group.getGroupId())).thenReturn(Optional.of(group));
		when(courseDao.findById(course.getCourseId())).thenReturn(Optional.of(course));
		groupService.saveEnrollGroupToCourse(group.getGroupId(), course.getCourseId());
		verify(groupDao, times(1)).findById(group.getGroupId());
		verify(courseDao, times(1)).findById(course.getCourseId());
		verify(courseDao, times(1)).save(course);
	}
	
	@Test
	void whenGroupOrCourseAreNotPresentInTablesExpectNotSavingRelationsGroupAndCourse() throws Exception {
		Group group = new Group();
		group.setGroupId(4L);
		group.setGroupName("ff-45");
		Course course = new Course();
		course.setCourseId(5L);
		course.setCourseName("Philosophy");
		course.setCourseDescription("Learn Philosophy very well");
		
		when(groupDao.findById(group.getGroupId())).thenReturn(Optional.empty());
		when(courseDao.findById(course.getCourseId())).thenReturn(Optional.empty());
		groupService.saveEnrollGroupToCourse(group.getGroupId(), course.getCourseId());
		verify(groupDao, times(1)).findById(group.getGroupId());
		verify(courseDao, times(1)).findById(course.getCourseId());
		verify(courseDao, never()).save(course);
	}
	
	@Test
	void whenRelationsGroupAndCourseArePresentInTablesExpectNotSavingRelationsGroupAndCourse() throws Exception {
		Group group = new Group();
		group.setGroupId(4L);
		group.setGroupName("ff-45");
		Course course = new Course();
		course.setCourseId(5L);
		course.setCourseName("Philosophy");
		course.setCourseDescription("Learn Philosophy very well");
		course.getGroups().add(group);
		group.getCourses().add(course);
		
		when(groupDao.findById(group.getGroupId())).thenReturn(Optional.of(group));
		when(courseDao.findById(course.getCourseId())).thenReturn(Optional.of(course));
		groupService.saveEnrollGroupToCourse(group.getGroupId(), course.getCourseId());
		verify(groupDao, times(1)).findById(group.getGroupId());
		verify(courseDao, times(1)).findById(course.getCourseId());
		verify(courseDao, never()).save(course);
	}
	
	@Test
	void WhenGroupIsPresentInTableExpectDeletingGroup() throws SQLException {
		Group group = new Group();
		group.setGroupId(4L);
		group.setGroupName("ff-45");
		
		when(groupDao.findById(group.getGroupId())).thenReturn(Optional.of(group));
		groupService.deleteGroup(group.getGroupId());
		verify(groupDao, times(1)).findById(group.getGroupId());
		verify(groupDao, times(1)).deleteById(group.getGroupId());
	}
	
	@Test
	void WhenGroupIsNotPresentInTableExpectNoDeletingGroup() throws SQLException {
		Group group = new Group();
		group.setGroupId(4L);
		group.setGroupName("ff-45");
		
		when(groupDao.findById(group.getGroupId())).thenReturn(Optional.empty());
		groupService.deleteGroup(group.getGroupId());
		verify(groupDao, times(1)).findById(group.getGroupId());
		verify(groupDao, never()).deleteById(group.getGroupId());
	}
}
