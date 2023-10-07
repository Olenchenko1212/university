package ua.foxminded.universitycms.dao;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.universitycms.models.Course;

@Repository
public interface CourseDao extends JpaRepository<Course, Integer> {
	Optional<Course> findByCourseName(String courseName) throws SQLException;
}
