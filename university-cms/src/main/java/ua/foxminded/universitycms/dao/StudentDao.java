package ua.foxminded.universitycms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.universitycms.models.Student;

@Repository
public interface StudentDao extends JpaRepository<Student, Long> {
	
}
