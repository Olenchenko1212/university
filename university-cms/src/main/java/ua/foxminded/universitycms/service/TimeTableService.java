package ua.foxminded.universitycms.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import ua.foxminded.universitycms.models.TimeTable;

public interface TimeTableService {
	List<TimeTable> getAllTimeTable() throws SQLException;
	Optional<TimeTable> getTimeTableById(Long timeTableId) throws SQLException;
	boolean saveSchedule(TimeTable timeTable) throws SQLException;
	void editSchedule(TimeTable saveTimeTable) throws SQLException;
	void deleteSchedule(Long id) throws Exception;
	List<TimeTable> getTeacherSchedule(Long teacherId) throws Exception;
	List<TimeTable> getStudentSchedule(Long userId) throws Exception;
}
