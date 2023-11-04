package ua.foxminded.universitycms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ua.foxminded.universitycms.dao.GroupDao;
import ua.foxminded.universitycms.dao.StudentDao;
import ua.foxminded.universitycms.dao.TeacherDao;
import ua.foxminded.universitycms.dao.TimeTableDao;
import ua.foxminded.universitycms.models.Group;
import ua.foxminded.universitycms.models.Student;
import ua.foxminded.universitycms.models.Teacher;
import ua.foxminded.universitycms.models.TimeTable;

@SpringBootTest(classes = { TimeTableServiceImpl.class })
class TimeTableServiceImplTest {
	@MockBean
	TimeTableDao timeTableDao;
	@MockBean
	TeacherDao teacherDao;
	@MockBean
	StudentDao studentDao;
	@MockBean
	GroupDao groupDao;

	@Autowired
	TimeTableServiceImpl timeTableService;

	@Test
	void whenNoSchedulByDayAndPairNumberExpectSavingTimeTable() throws Exception {
		TimeTable timeTable = new TimeTable();
		timeTable.setPairNumber(3);
		timeTable.setTimeTableDate(LocalDate.of(2023, 05, 25));
		List<TimeTable> expectTimeTableForDayAndTime = new ArrayList<TimeTable>();
		Group group = new Group();
		group.setGroupId(1L);
		group.setGroupName("ff-45");
		Teacher teacher = new Teacher();
		teacher.setTeacherId(11L);
		teacher.setTeacherName("Bob");
		teacher.setTeacherSurname("Harvy");

		when(timeTableDao.findTimeTableByPairNumberAndDate(timeTable.getPairNumber(), timeTable.getTimeTableDate()))
				.thenReturn(expectTimeTableForDayAndTime);
		when(groupDao.findById(group.getGroupId())).thenReturn(Optional.of(group));
		when(teacherDao.findById(teacher.getTeacherId())).thenReturn(Optional.of(teacher));
		timeTableService.saveEntrySchedule(timeTable, 1L, 11L);
		verify(timeTableDao, times(1)).save(timeTable);
	}

	@Test
	void whenSchedulByDayAndPairNumberArePresentButNotForGroupAndTeacherExpectSavingTimeTable() throws Exception {
		TimeTable timeTable = new TimeTable();
		timeTable.setPairNumber(3);
		timeTable.setTimeTableDate(LocalDate.of(2023, 05, 25));
		List<TimeTable> expectTimeTableForDayAndTime = new ArrayList<TimeTable>();
		expectTimeTableForDayAndTime.add(new TimeTable(1L, 3, LocalDate.of(2023, 05, 25), 44L, 55L));
		expectTimeTableForDayAndTime.add(new TimeTable(2L, 3, LocalDate.of(2023, 05, 25), 46L, 56L));
		Group group = new Group();
		group.setGroupId(1L);
		group.setGroupName("ff-45");
		Teacher teacher = new Teacher();
		teacher.setTeacherId(11L);
		teacher.setTeacherName("Bob");
		teacher.setTeacherSurname("Harvy");

		when(timeTableDao.findTimeTableByPairNumberAndDate(timeTable.getPairNumber(), timeTable.getTimeTableDate()))
				.thenReturn(expectTimeTableForDayAndTime);
		when(groupDao.findById(group.getGroupId())).thenReturn(Optional.of(group));
		when(teacherDao.findById(teacher.getTeacherId())).thenReturn(Optional.of(teacher));
		timeTableService.saveEntrySchedule(timeTable, 1L, 11L);
		verify(timeTableDao, times(1)).save(timeTable);
	}

	@Test
	void whenSchedulByDayAndPairNumberArePresentButGroupAlreadyOnPairExpectSavingTimeTable() throws Exception {
		TimeTable timeTable = new TimeTable();
		timeTable.setPairNumber(3);
		timeTable.setTimeTableDate(LocalDate.of(2023, 05, 25));
		List<TimeTable> expectTimeTableForDayAndTime = new ArrayList<TimeTable>();
		expectTimeTableForDayAndTime.add(new TimeTable(1L, 3, LocalDate.of(2023, 05, 25), 44L, 55L));
		expectTimeTableForDayAndTime.add(new TimeTable(2L, 3, LocalDate.of(2023, 05, 25), 1L, 56L));
		Group group = new Group();
		group.setGroupId(1L);
		group.setGroupName("ff-45");
		Teacher teacher = new Teacher();
		teacher.setTeacherId(11L);
		teacher.setTeacherName("Bob");
		teacher.setTeacherSurname("Harvy");

		when(timeTableDao.findTimeTableByPairNumberAndDate(timeTable.getPairNumber(), timeTable.getTimeTableDate()))
				.thenReturn(expectTimeTableForDayAndTime);
		when(groupDao.findById(group.getGroupId())).thenReturn(Optional.of(group));
		when(teacherDao.findById(teacher.getTeacherId())).thenReturn(Optional.of(teacher));
		timeTableService.saveEntrySchedule(timeTable, 1L, 11L);
		verify(timeTableDao, never()).save(timeTable);
	}

	@Test
	void whenSchedulByDayAndPairNumberArePresentButTeacherAlreadyOnPairExpectSavingTimeTable() throws Exception {
		TimeTable timeTable = new TimeTable();
		timeTable.setPairNumber(3);
		timeTable.setTimeTableDate(LocalDate.of(2023, 05, 25));
		List<TimeTable> expectTimeTableForDayAndTime = new ArrayList<TimeTable>();
		expectTimeTableForDayAndTime.add(new TimeTable(1L, 3, LocalDate.of(2023, 05, 25), 44L, 55L));
		expectTimeTableForDayAndTime.add(new TimeTable(2L, 3, LocalDate.of(2023, 05, 25), 46L, 11L));
		Group group = new Group();
		group.setGroupId(1L);
		group.setGroupName("ff-45");
		Teacher teacher = new Teacher();
		teacher.setTeacherId(11L);
		teacher.setTeacherName("Bob");
		teacher.setTeacherSurname("Harvy");

		when(timeTableDao.findTimeTableByPairNumberAndDate(timeTable.getPairNumber(), timeTable.getTimeTableDate()))
				.thenReturn(expectTimeTableForDayAndTime);
		when(groupDao.findById(group.getGroupId())).thenReturn(Optional.of(group));
		when(teacherDao.findById(teacher.getTeacherId())).thenReturn(Optional.of(teacher));
		timeTableService.saveEntrySchedule(timeTable, 1L, 11L);
		verify(timeTableDao, never()).save(timeTable);
	}

	@Test
	void whenSchedulByDayForStudentArePresentExpectListTimeTableForStudent() throws Exception {
		Student student = new Student();
		student.setStudentId(1L);
		student.setStudentName("Bob");
		student.setStudentSurname("Murray");
		student.setGroupId(2L);
		List<TimeTable> expectTimeTableForStudent = new ArrayList<TimeTable>();
		expectTimeTableForStudent.add(new TimeTable(1L, 3, LocalDate.of(2023, 05, 25), 2L, 55L));
		expectTimeTableForStudent.add(new TimeTable(2L, 4, LocalDate.of(2023, 05, 25), 2L, 11L));
		
		when(studentDao.findById(student.getStudentId())).thenReturn(Optional.of(student));
		when(timeTableDao.findTimeTableByGroupForDay(LocalDate.of(2023, 05, 25), student.getGroupId())).thenReturn(expectTimeTableForStudent);
		timeTableService.getTimeTableByDayForStudent(LocalDate.of(2023, 05, 25), student.getStudentId());
		verify(studentDao, times(1)).findById(student.getStudentId());
		verify(timeTableDao, times(1)).findTimeTableByGroupForDay(LocalDate.of(2023, 05, 25), student.getGroupId());
		assertEquals(expectTimeTableForStudent, timeTableService.getTimeTableByDayForStudent(LocalDate.of(2023, 05, 25), student.getStudentId()));
	}
	
	@Test
	void whenSchedulByDayForStudentAreNotPresentExpectEmptyListTimeTable() throws Exception {
		Student student = new Student();
		student.setStudentId(1L);
		student.setStudentName("Bob");
		student.setStudentSurname("Murray");
		student.setGroupId(2L);
		List<TimeTable> expectTimeTableForStudent = new ArrayList<TimeTable>();

		when(studentDao.findById(student.getStudentId())).thenReturn(Optional.of(student));
		when(timeTableDao.findTimeTableByGroupForDay(LocalDate.of(2023, 05, 25), student.getGroupId())).thenReturn(expectTimeTableForStudent);
		timeTableService.getTimeTableByDayForStudent(LocalDate.of(2023, 05, 25), student.getStudentId());
		verify(studentDao, times(1)).findById(student.getStudentId());
		verify(timeTableDao, times(1)).findTimeTableByGroupForDay(LocalDate.of(2023, 05, 25), student.getGroupId());
		assertEquals(new ArrayList<TimeTable>(), timeTableService.getTimeTableByDayForStudent(LocalDate.of(2023, 05, 25), student.getStudentId()));
	}
}
