package ua.foxminded.universitycms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.universitycms.models.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	
}
