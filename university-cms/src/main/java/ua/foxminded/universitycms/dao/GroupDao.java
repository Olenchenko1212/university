package ua.foxminded.universitycms.dao;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.universitycms.models.Group;

@Repository
public interface GroupDao extends JpaRepository<Group, Long> {
	Optional<Group> findByGroupName(String groupName) throws SQLException;
}
