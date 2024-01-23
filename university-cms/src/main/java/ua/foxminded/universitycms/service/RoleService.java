package ua.foxminded.universitycms.service;

import java.sql.SQLException;
import java.util.List;

import ua.foxminded.universitycms.models.Role;

public interface RoleService {
	List<Role> getAllRoles() throws SQLException;
}
