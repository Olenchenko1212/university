package ua.foxminded.universitycms.service;

import java.sql.SQLException;
import java.util.List;

import ua.foxminded.universitycms.models.User;
import ua.foxminded.universitycms.models.UserDto;

public interface UserService {
	void saveUserDto(UserDto userDto) throws SQLException;
	void saveUser(User user) throws SQLException;
	void updateUserRole(User user) throws SQLException;
	void deleteUser(Long userId) throws SQLException;
	User findByName(String name) throws SQLException;
	User findById(Long userId) throws SQLException;
	List<User> getAllUsers() throws SQLException;
}
