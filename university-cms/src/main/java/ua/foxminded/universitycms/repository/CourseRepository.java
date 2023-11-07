package ua.foxminded.universitycms.repository;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.universitycms.models.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
	Optional<Course> findByCourseName(String courseName) throws SQLException;
}
