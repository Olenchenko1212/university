package ua.foxminded.universitycms.repository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.universitycms.models.TimeTable;

@Repository
public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {
	List<TimeTable> findByTeacherId(Long teacherId) throws SQLException;
	List<TimeTable> findByGroupId(Long groupId) throws SQLException;
	List<TimeTable> findByTimeTableDateAndPairNumber(LocalDate timeTableDate, int pairNumber) throws SQLException;
	List<TimeTable> findByTimeTableDate(LocalDate timeTableDate) throws SQLException;
}
