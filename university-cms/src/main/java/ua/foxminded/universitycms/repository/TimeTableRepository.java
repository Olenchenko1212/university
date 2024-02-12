package ua.foxminded.universitycms.repository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.universitycms.models.Teacher;
import ua.foxminded.universitycms.models.TimeTable;

@Repository
public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {
	
	List<TimeTable> findBytimeTableDateAndTeacher(LocalDate timeTableDate, Teacher teacher) throws SQLException;

	List<TimeTable> findBytimeTableDateAndGroupId(LocalDate timeTableDate, Long groupId) throws SQLException;

	List<TimeTable> findBytimeTableDateAndPairNumber(LocalDate timeTableDate, int pairNumber) throws SQLException;
}
