package ua.foxminded.universitycms.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import ua.foxminded.universitycms.models.Group;
import ua.foxminded.universitycms.models.Student;
import ua.foxminded.universitycms.repository.GroupRepository;
import ua.foxminded.universitycms.repository.StudentRepository;

@Service
public class GroupServiceImpl implements GroupService {
	private static Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

	private final GroupRepository groupRepository;
	private final StudentRepository studentRepository;

	public GroupServiceImpl(GroupRepository groupRepository, StudentRepository studentRepository) {
		this.groupRepository = groupRepository;
		this.studentRepository = studentRepository;
	}

	@Override
	@Secured({"ROLE_ADMIN", "ROLE_STUFF", "ROLE_STUDENT", "ROLE_TEACHER"})
	public List<Group> getAllGroups() throws SQLException {
		List<Group> allGrouprs = groupRepository.findAll();
		logger.info("Getting all {} groups from DB", allGrouprs.size());
		return allGrouprs;
	}

	@Override
	@Secured({"ROLE_ADMIN", "ROLE_STUFF"})
	public Optional<Group> getGroupById(Long groupId) throws SQLException {
		logger.info("Getting group id = {} from DB", groupId);
		return groupRepository.findById(groupId);
	}

	@Override
	@Transactional
	@Secured({"ROLE_ADMIN", "ROLE_STUFF"})
	public void saveGroup(Group group) throws Exception, SQLException {
		if (!groupRepository.findByGroupName(group.getGroupName()).isPresent()) {
			groupRepository.saveAndFlush(group);
			logger.info("Save group {} into DB", group.getGroupName());
		} else {
			logger.error("Group {} is allready in DB", group.getGroupName(), new Exception("Group is allready in DB"));
		}
	}

	@Override
	@Transactional
	@Secured("ROLE_ADMIN")
	public void deleteGroup(Long groupID) throws SQLException {
		if (groupRepository.findById(groupID).isPresent()) {
			List<Student> students = studentRepository.findByGroupId(groupID);
			if(!students.isEmpty()) {
				students.stream().forEach(s -> s.setGroup(null));
				studentRepository.saveAll(students);
			}
			groupRepository.deleteById(groupID);
			logger.info("Group Id = {} delete from DB", groupID);
		} else {
			logger.error("Group Id = {} is not find in DB", groupID, new Exception("Group is not find in DB"));
		}
	}
}
