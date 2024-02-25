package ua.foxminded.universitycms.repository;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.universitycms.models.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	Optional<Student> findByUserId(Long userId) throws SQLException;
}
