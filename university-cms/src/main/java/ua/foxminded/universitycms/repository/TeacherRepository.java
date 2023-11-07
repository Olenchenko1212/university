package ua.foxminded.universitycms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.universitycms.models.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
	
}
