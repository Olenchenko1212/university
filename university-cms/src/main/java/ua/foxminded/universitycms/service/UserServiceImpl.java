package ua.foxminded.universitycms.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.universitycms.models.Role;
import ua.foxminded.universitycms.models.User;
import ua.foxminded.universitycms.models.UserDto;
import ua.foxminded.universitycms.repository.RoleRepository;
import ua.foxminded.universitycms.repository.UserRepository;
import ua.foxminded.universitycms.util.RoleEnum;

@Service
public class UserServiceImpl implements UserService {
	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;

	@Override
	@Transactional
	public void saveUserDto(UserDto userDto) throws SQLException {	
		User user = new User(userDto.getName(), passwordEncoder.encode(userDto.getPassword()), new ArrayList<>());
		Optional<Role> userRole = roleRepository.findByName(RoleEnum.ROLE_USER.toString());
		if(userRole.isPresent()) {
			user.getRoles().add(userRole.get());
			userRepository.save(user);
			logger.info("Save User With login = {} to DB", userDto.getName());
		} else {
			logger.info("Role 'USER' not found in DB");
		}
	}
	
	@Override
	@Transactional
	public void updateUserRole(User user) throws SQLException {
		Optional<User> userSave = userRepository.findById(user.getId());
		if(userSave.isPresent() && !userSave.get().getRoles().equals(user.getRoles())) {
			userSave.get().getRoles().clear();
			userSave.get().getRoles().addAll(user.getRoles());
			logger.info("User With ID = {} UPDATE Roles", user.getId());
		} else {
			logger.info("User With ID = {} isnt found or with same roles", user.getId());	
		}	
	}
	
	@Override
	@Transactional
	public void saveUser(User user) throws SQLException {
		userRepository.save(user);
		logger.info("Save User With ID = {} into DB", user.getId());	
	}
	
	@Override
	@Transactional
	public void deleteUser(Long userId) throws SQLException {
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent()) {
			userRepository.deleteById(userId);
			logger.info("User With ID = {} is remove", userId);
		} else {
			logger.info("User With ID = {} not found to remove", userId);
		}
	}

	@Override
	public User findByName(String name) throws SQLException {
		logger.info("Get User With login = {} from DB", name);
		Optional<User> user = userRepository.findByUsername(name);
		if (user.isPresent()) {
			return user.get();
		} else {
			return null;
		}
	}
	
	@Override
	public User findById(Long userId) throws SQLException {
		logger.info("Get User With ID = {} from DB", userId);
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			return user.get();
		} else {
			return null;
		}
	}

	@Override
	public List<User> getAllUsers() throws SQLException {
		List<User> users = userRepository.findAll();
		logger.info("Getting all users ({}) from DB", users.size());
		return users;
	}
}
