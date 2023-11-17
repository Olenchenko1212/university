package ua.foxminded.universitycms.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import ua.foxminded.universitycms.models.TimeTable;

public interface TimeTableService {
	List<TimeTable> getAllTimeTable() throws SQLException;
	Optional<TimeTable> getTimeTableById(Long timeTableId) throws SQLException;
	void saveEntrySchedule(TimeTable timeTable, Long groupId, Long teacherId) throws Exception;
	void deleteTimeTable(Long timeTableId) throws SQLException;
	List<TimeTable> getTimeTableByDayForTeacher(LocalDate timeTableDate, Long teacherId) throws Exception;
	List<TimeTable> getTimeTableByDayForStudent(LocalDate timeTableDate, Long studentId) throws Exception;
}
