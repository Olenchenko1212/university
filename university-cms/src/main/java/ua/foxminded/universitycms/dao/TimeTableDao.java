package ua.foxminded.universitycms.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ua.foxminded.universitycms.models.TimeTable;

@Repository
public interface TimeTableDao extends JpaRepository<TimeTable, Long> {
	@Query("SELECT t FROM TimeTable t WHERE t.timeTableDate = :timeTableDate AND t.teacherId = :teacherId")
	List<TimeTable> findTimeTableByTeacherForDay(
			@Param("timeTableDate") LocalDate timeTableDate,
			@Param("teacherId") Long teacherId) throws SQLException;
	
	@Query("SELECT t FROM TimeTable t WHERE t.timeTableDate = :timeTableDate AND t.groupId = :groupId")
	List<TimeTable> findTimeTableByGroupForDay(
			@Param("timeTableDate") LocalDate timeTableDate,
			@Param("groupId") Long groupId) throws SQLException;
	
	@Query("SELECT t FROM TimeTable t WHERE t.pairNumber = :pairNumber AND t.timeTableDate = :timeTableDate")
	List<TimeTable> findTimeTableByPairNumberAndDate(
			@Param("pairNumber") int pairNumber,
			@Param("timeTableDate") LocalDate timeTableDate) throws SQLException;
}
