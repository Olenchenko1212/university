package ua.foxminded.universitycms.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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

import ua.foxminded.universitycms.models.Group;
import ua.foxminded.universitycms.models.Student;
import ua.foxminded.universitycms.models.Teacher;
import ua.foxminded.universitycms.models.TimeTable;
import ua.foxminded.universitycms.repository.StudentRepository;
import ua.foxminded.universitycms.repository.TeacherRepository;
import ua.foxminded.universitycms.repository.TimeTableRepository;

@SpringBootTest(classes = { TimeTableServiceImpl.class })
class TimeTableServiceImplTest {
	@MockBean
	TimeTableRepository timeTableRepository;
	@MockBean
	TeacherRepository teacherRepository;
	@MockBean
	StudentRepository studentRepository;

	@Autowired
	TimeTableServiceImpl timeTableService;

	@Test
	void whenNoSchedulByDayAndPairNumberExpectSavingTimeTable() throws Exception {
		TimeTable timeTable = new TimeTable();
		timeTable.setPairNumber(3);
		timeTable.setTimeTableDate(LocalDate.of(2023, 05, 25));
		List<TimeTable> expectTimeTableForDayAndTime = new ArrayList<TimeTable>();
		Group group = new Group();
		group.setId(1L);
		group.setGroupName("ff-45");
		Teacher teacher = new Teacher();
		teacher.setId(11L);
		teacher.setTeacherName("Bob");
		teacher.setTeacherSurname("Harvy");
		timeTable.setGroup(group);
		timeTable.setTeacher(teacher);

		when(timeTableRepository.findByTimeTableDateAndPairNumber(timeTable.getTimeTableDate(),
				timeTable.getPairNumber())).thenReturn(expectTimeTableForDayAndTime);
		assertTrue(timeTableService.saveSchedule(timeTable));
		verify(timeTableRepository, times(1)).save(timeTable);
	}

	@Test
	void whenSchedulIsBusyForGroupOrTeacherExpectNoSavingTimeTable() throws Exception {
		TimeTable timeTable = new TimeTable();
		timeTable.setPairNumber(3);
		timeTable.setTimeTableDate(LocalDate.of(2023, 05, 25));
		Group group = new Group();
		group.setId(1L);
		group.setGroupName("ff-45");
		Teacher teacher = new Teacher();
		teacher.setId(11L);
		teacher.setTeacherName("Bob");
		teacher.setTeacherSurname("Harvy");
		timeTable.setGroup(group);
		timeTable.setTeacher(teacher);
		TimeTable timeTable1 = new TimeTable();
		timeTable1.setPairNumber(3);
		timeTable1.setTimeTableDate(LocalDate.of(2023, 05, 25));
		Group group1 = new Group();
		group1.setId(1L);
		group1.setGroupName("ff-45");
		Teacher teacher1 = new Teacher();
		teacher1.setId(11L);
		teacher1.setTeacherName("Bob");
		teacher1.setTeacherSurname("Harvy");
		timeTable1.setGroup(group);
		timeTable1.setTeacher(teacher);
		List<TimeTable> expectScheduleForDayAndTime = new ArrayList<TimeTable>();
		expectScheduleForDayAndTime.add(timeTable);

		when(timeTableRepository.findByTimeTableDateAndPairNumber(timeTable.getTimeTableDate(),
				timeTable.getPairNumber())).thenReturn(expectScheduleForDayAndTime);
		assertFalse(timeTableService.saveSchedule(timeTable));
		verify(timeTableRepository, never()).save(timeTable);
	}

	@Test
	void whenSchedulIsFreeForGroupOrTeacherExpectSavingTimeTable() throws Exception {
		TimeTable timeTable = new TimeTable();
		timeTable.setPairNumber(3);
		timeTable.setTimeTableDate(LocalDate.of(2023, 05, 25));
		Group group = new Group();
		group.setId(1L);
		group.setGroupName("ff-45");
		Teacher teacher = new Teacher();
		teacher.setId(11L);
		teacher.setTeacherName("Bob");
		teacher.setTeacherSurname("Harvy");
		timeTable.setGroup(group);
		timeTable.setTeacher(teacher);
		TimeTable timeTableSave = new TimeTable();
		timeTableSave.setId(1L);
		timeTableSave.setPairNumber(3);
		timeTableSave.setTimeTableDate(LocalDate.of(2023, 05, 25));
		Group groupSave = new Group();
		groupSave.setId(2L);
		groupSave.setGroupName("fa-45");
		Teacher teacherSave = new Teacher();
		teacherSave.setId(12L);
		teacherSave.setTeacherName("Rob");
		teacherSave.setTeacherSurname("Haris");
		timeTableSave.setGroup(groupSave);
		timeTableSave.setTeacher(teacherSave);
		List<TimeTable> expectScheduleForDayAndTime = new ArrayList<TimeTable>();
		expectScheduleForDayAndTime.add(timeTable);

		when(timeTableRepository.findByTimeTableDateAndPairNumber(timeTableSave.getTimeTableDate(),
				timeTableSave.getPairNumber())).thenReturn(expectScheduleForDayAndTime);
		assertTrue(timeTableService.saveSchedule(timeTableSave));
		verify(timeTableRepository, times(1)).save(timeTableSave);
	}

	@Test
	void whenSchedulEditForFreeTimeExpectSavingTimeTable() throws Exception {
		TimeTable timeTableSave = new TimeTable();
		timeTableSave.setId(1L);
		timeTableSave.setPairNumber(4);
		timeTableSave.setTimeTableDate(LocalDate.of(2023, 05, 25));
		Group groupSave = new Group();
		groupSave.setId(1L);
		groupSave.setGroupName("ff-45");
		Teacher teacherSave = new Teacher();
		teacherSave.setId(12L);
		teacherSave.setTeacherName("Bob");
		teacherSave.setTeacherSurname("Harvy");
		timeTableSave.setGroup(groupSave);
		timeTableSave.setTeacher(teacherSave);
		List<TimeTable> expectScheduleForDayAndTime = new ArrayList<TimeTable>();

		when(timeTableRepository.findByTimeTableDateAndPairNumber(timeTableSave.getTimeTableDate(),
				timeTableSave.getPairNumber())).thenReturn(expectScheduleForDayAndTime);
		timeTableService.editSchedule(timeTableSave);
		verify(timeTableRepository, times(1)).save(timeTableSave);
	}

	@Test
	void whenSchedulEditForBusyTimeButFreeForThisGroupOrTeacherExpectSavingTimeTable() throws Exception {
		TimeTable timeTable = new TimeTable();
		timeTable.setId(2L);
		timeTable.setPairNumber(3);
		timeTable.setTimeTableDate(LocalDate.of(2023, 05, 25));
		Group group = new Group();
		group.setId(1L);
		group.setGroupName("ff-45");
		Teacher teacher = new Teacher();
		teacher.setId(11L);
		teacher.setTeacherName("Bob");
		teacher.setTeacherSurname("Harvy");
		timeTable.setGroup(group);
		timeTable.setTeacher(teacher);

		TimeTable timeTableEdit = new TimeTable();
		timeTableEdit.setId(1L);
		timeTableEdit.setPairNumber(3);
		timeTableEdit.setTimeTableDate(LocalDate.of(2023, 05, 25));
		Group groupSave = new Group();
		groupSave.setId(2L);
		groupSave.setGroupName("fa-45");
		Teacher teacherSave = new Teacher();
		teacherSave.setId(12L);
		teacherSave.setTeacherName("Rob");
		teacherSave.setTeacherSurname("Haris");
		timeTableEdit.setGroup(groupSave);
		timeTableEdit.setTeacher(teacherSave);
		List<TimeTable> expectScheduleForDayAndTime = new ArrayList<TimeTable>();
		expectScheduleForDayAndTime.add(timeTable);

		when(timeTableRepository.findByTimeTableDateAndPairNumber(timeTableEdit.getTimeTableDate(),
				timeTableEdit.getPairNumber())).thenReturn(expectScheduleForDayAndTime);
		timeTableService.editSchedule(timeTableEdit);
		verify(timeTableRepository, times(1)).save(timeTableEdit);
	}

	@Test
	void whenSchedulEditForBusyTimeAndThisGroupOrTeacherAreBusyExpectDeleteOldScheduleAndSavingTimeTableEdit()
			throws Exception {
		TimeTable timeTable = new TimeTable();
		timeTable.setId(2L);
		timeTable.setPairNumber(3);
		timeTable.setTimeTableDate(LocalDate.of(2023, 05, 25));
		Group group = new Group();
		group.setId(1L);
		group.setGroupName("ff-45");
		Teacher teacher = new Teacher();
		teacher.setId(11L);
		teacher.setTeacherName("Bob");
		teacher.setTeacherSurname("Harvy");
		timeTable.setGroup(group);
		timeTable.setTeacher(teacher);
		TimeTable timeTableEdit = new TimeTable();
		timeTableEdit.setId(1L);
		timeTableEdit.setPairNumber(3);
		timeTableEdit.setTimeTableDate(LocalDate.of(2023, 05, 25));
		Group groupSave = new Group();
		groupSave.setId(1L);
		groupSave.setGroupName("ff-45");
		Teacher teacherSave = new Teacher();
		teacherSave.setId(12L);
		teacherSave.setTeacherName("Rob");
		teacherSave.setTeacherSurname("Haris");
		timeTableEdit.setGroup(groupSave);
		timeTableEdit.setTeacher(teacherSave);
		List<TimeTable> expectScheduleForDayAndTime = new ArrayList<TimeTable>();
		expectScheduleForDayAndTime.add(timeTable);

		when(timeTableRepository.findByTimeTableDateAndPairNumber(timeTableEdit.getTimeTableDate(),
				timeTableEdit.getPairNumber())).thenReturn(expectScheduleForDayAndTime);
		timeTableService.editSchedule(timeTableEdit);
		verify(timeTableRepository, times(1)).delete(timeTable);
		verify(timeTableRepository, times(1)).save(timeTableEdit);
	}

	@Test
	void whenGetScheduleForTeacherButNotFoundTeacherByUserIdExpectEmptySchedule() throws Exception {
		Long userId = 1L;
		Teacher teacher = new Teacher();
		teacher.setId(11L);
		teacher.setTeacherName("Bob");
		teacher.setTeacherSurname("Harvy");
		
		when(teacherRepository.findByUserId(userId)).thenReturn(Optional.empty());
		timeTableService.getTeacherSchedule(userId);
		verify(timeTableRepository, never()).findByTeacherId(teacher.getId());
	}
	
	@Test
	void whenGetScheduleForTeacherByUserIdExpectListTimeTable() throws Exception {
		Long userId = 1L;
		Teacher teacher = new Teacher();
		teacher.setId(11L);
		teacher.setTeacherName("Bob");
		teacher.setTeacherSurname("Harvy");
		TimeTable timeTable = new TimeTable();
		timeTable.setId(2L);
		timeTable.setPairNumber(3);
		timeTable.setTimeTableDate(LocalDate.of(2023, 05, 25));
		timeTable.setTeacher(teacher);
		List<TimeTable> expectScheduleForDayAndTime = new ArrayList<TimeTable>();
		expectScheduleForDayAndTime.add(timeTable);
		
		when(teacherRepository.findByUserId(userId)).thenReturn(Optional.of(teacher));
		timeTableService.getTeacherSchedule(userId);
		verify(timeTableRepository, times(1)).findByTeacherId(teacher.getId());
	}
	
	@Test
	void whenGetScheduleForStudentButNotFoundStudentByUserIdExpectEmptySchedule() throws Exception {
		Long userId = 1L;
		Student student = new Student();
		student.setId(11L);
		student.setGroup(new Group(1L, "df-34"));
		student.setStudentName("Bob");
		student.setStudentSurname("Harvy");
		
		when(studentRepository.findByUserId(userId)).thenReturn(Optional.empty());
		timeTableService.getStudentSchedule(userId);
		verify(timeTableRepository, never()).findByGroupId(student.getGroup().getId());
	}
	
	@Test
	void whenGetScheduleForStudentButStudentNotEnrollToGroupExpectEmptySchedule() throws Exception {
		Long userId = 1L;
		Student student = new Student();
		student.setId(11L);
		student.setGroup(null);
		student.setStudentName("Bob");
		student.setStudentSurname("Harvy");
		
		when(studentRepository.findByUserId(userId)).thenReturn(Optional.of(student));
		timeTableService.getStudentSchedule(userId);
		verify(timeTableRepository, never()).findByGroupId(any());
	}
	
	@Test
	void whenGetScheduleForStudentExpectListTimeTable() throws Exception {
		Long userId = 1L;
		Group group = new Group();
		group.setId(1L);
		group.setGroupName("ff-45");
		Student student = new Student();
		student.setId(11L);
		student.setGroup(group);
		student.setStudentName("Bob");
		student.setStudentSurname("Harvy");
		List<TimeTable> expectScheduleForDayAndTime = new ArrayList<TimeTable>();
		expectScheduleForDayAndTime.add(new TimeTable(1L, 1, LocalDate.of(2023, 05, 25)));
		expectScheduleForDayAndTime.add(new TimeTable(2L, 2, LocalDate.of(2023, 05, 25)));
		
		when(studentRepository.findByUserId(userId)).thenReturn(Optional.of(student));
		when(timeTableRepository.findByGroupId(student.getGroup().getId())).thenReturn(expectScheduleForDayAndTime);
		timeTableService.getStudentSchedule(userId);
		verify(timeTableRepository, times(1)).findByGroupId(student.getGroup().getId());
	}
}
