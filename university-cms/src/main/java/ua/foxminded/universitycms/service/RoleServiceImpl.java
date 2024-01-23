package ua.foxminded.universitycms.service;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.foxminded.universitycms.models.Role;
import ua.foxminded.universitycms.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {
	private static Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	private final RoleRepository roleRepository;
	
	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	@Override
	public List<Role> getAllRoles() throws SQLException {
		List<Role> allRoles = roleRepository.findAll();
		logger.info("Getting all {} roles from DB", allRoles.size());
		return allRoles;
	}
}
