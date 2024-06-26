package ua.foxminded.universitycms.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import ua.foxminded.universitycms.models.Group;

public interface GroupService {
	List<Group> getAllGroups() throws SQLException;
	Optional<Group> getGroupById(Long groupId) throws SQLException;
	void saveGroup(Group group) throws Exception, SQLException;
	void deleteGroup(Long groupId) throws SQLException;
}
