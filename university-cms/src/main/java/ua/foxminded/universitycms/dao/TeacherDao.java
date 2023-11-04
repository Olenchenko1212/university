package ua.foxminded.universitycms.dao;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ua.foxminded.universitycms.models.Teacher;

@Repository
public interface TeacherDao extends JpaRepository<Teacher, Long> {
	@Query("SELECT t FROM Teacher t WHERE t.course.courseId = :courseId")
	Optional<Teacher> findTeacherByCourseId(@Param("courseId") Long courseId) throws SQLException;
}
