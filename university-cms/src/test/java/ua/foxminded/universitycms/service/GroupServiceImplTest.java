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

import ua.foxminded.universitycms.models.Group;
import ua.foxminded.universitycms.repository.GroupRepository;

@SpringBootTest(classes = { GroupServiceImpl.class })
public class GroupServiceImplTest {

	@MockBean
	GroupRepository groupRepository;

	@Autowired
	GroupServiceImpl groupService;

	@Test
	void whenGroupNameIsNotPresentInTableExpectSavingGroup() throws Exception {
		Group group = new Group();
		group.setId(1L);
		group.setGroupName("df-45");

		when(groupRepository.findByGroupName(group.getGroupName())).thenReturn(Optional.empty());
		groupService.saveGroup(group);
		verify(groupRepository, times(1)).findByGroupName(group.getGroupName());
		verify(groupRepository, times(1)).saveAndFlush(group);
	}

	@Test
	void whenGroupNameIsPresentInTableExpectNoSavingGroup() throws Exception {
		Group group = new Group();
		group.setId(4L);
		group.setGroupName("ff-45");

		when(groupRepository.findByGroupName(group.getGroupName())).thenReturn(Optional.of(group));
		groupService.saveGroup(group);
		verify(groupRepository, times(1)).findByGroupName(group.getGroupName());
		verify(groupRepository, never()).saveAndFlush(group);
	}
	
	@Test
	void WhenGroupIsPresentInTableExpectDeletingGroup() throws SQLException {
		Group group = new Group();
		group.setId(4L);
		group.setGroupName("ff-45");
		
		when(groupRepository.findById(group.getId())).thenReturn(Optional.of(group));
		groupService.deleteGroup(group.getId());
		verify(groupRepository, times(1)).findById(group.getId());
		verify(groupRepository, times(1)).deleteById(group.getId());
	}
	
	@Test
	void WhenGroupIsNotPresentInTableExpectNoDeletingGroup() throws SQLException {
		Group group = new Group();
		group.setId(4L);
		group.setGroupName("ff-45");
		
		when(groupRepository.findById(group.getId())).thenReturn(Optional.empty());
		groupService.deleteGroup(group.getId());
		verify(groupRepository, times(1)).findById(group.getId());
		verify(groupRepository, never()).deleteById(group.getId());
	}
}
