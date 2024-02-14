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

import ua.foxminded.universitycms.models.Group;
import ua.foxminded.universitycms.models.Student;
import ua.foxminded.universitycms.models.Teacher;
import ua.foxminded.universitycms.models.TimeTable;
import ua.foxminded.universitycms.repository.GroupRepository;
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
	@MockBean
	GroupRepository groupRepository;

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

		when(timeTableRepository.findBytimeTableDateAndPairNumber(timeTable.getTimeTableDate(), timeTable.getPairNumber()))
				.thenReturn(expectTimeTableForDayAndTime);
		when(groupRepository.findById(group.getId())).thenReturn(Optional.of(group));
		when(teacherRepository.findById(teacher.getId())).thenReturn(Optional.of(teacher));
		timeTableService.saveEntrySchedule(timeTable, 1L, 11L);
		verify(timeTableRepository, times(1)).save(timeTable);
	}

	@Test
	void whenSchedulByDayAndPairNumberArePresentButNotForGroupAndTeacherExpectSavingTimeTable() throws Exception {
		TimeTable timeTable = new TimeTable();
		timeTable.setPairNumber(3);
		timeTable.setTimeTableDate(LocalDate.of(2023, 05, 25));
		List<TimeTable> expectTimeTableForDayAndTime = new ArrayList<TimeTable>();
		expectTimeTableForDayAndTime.add(new TimeTable(1L, 3, LocalDate.of(2023, 05, 25), 44L));
		expectTimeTableForDayAndTime.add(new TimeTable(2L, 3, LocalDate.of(2023, 05, 25), 46L));
		Group group = new Group();
		group.setId(1L);
		group.setGroupName("ff-45");
		Teacher teacher1 = new Teacher();
		teacher1.setId(11L);
		teacher1.setTeacherName("Bob");
		teacher1.setTeacherSurname("Harvy");
		Teacher teacher2 = new Teacher();
		teacher2.setId(15L);
		teacher2.setTeacherName("Bob2");
		teacher2.setTeacherSurname("Harvy2");
		Teacher teacherSave = new Teacher();
		teacherSave.setId(16L);
		teacherSave.setTeacherName("Bob3");
		teacherSave.setTeacherSurname("Harvy3");
		expectTimeTableForDayAndTime.get(0).setTeacher(teacher1);
		expectTimeTableForDayAndTime.get(1).setTeacher(teacher2);

		when(timeTableRepository.findBytimeTableDateAndPairNumber(timeTable.getTimeTableDate(), timeTable.getPairNumber()))
				.thenReturn(expectTimeTableForDayAndTime);	
		when(groupRepository.findById(group.getId())).thenReturn(Optional.of(group));
		when(teacherRepository.findById(teacherSave.getId())).thenReturn(Optional.of(teacherSave));
		when(timeTableRepository.save(timeTable)).thenReturn(timeTable);
		timeTableService.saveEntrySchedule(timeTable, 1L, 16L);
		verify(timeTableRepository, times(1)).save(timeTable);
	}

	@Test
	void whenSchedulByDayAndPairNumberArePresentButGroupAlreadyOnPairExpectNotSavingTimeTable() throws Exception {
		TimeTable timeTable = new TimeTable();
		timeTable.setPairNumber(3);
		timeTable.setTimeTableDate(LocalDate.of(2023, 05, 25));
		List<TimeTable> expectTimeTableForDayAndTime = new ArrayList<TimeTable>();
		expectTimeTableForDayAndTime.add(new TimeTable(1L, 3, LocalDate.of(2023, 05, 25), 44L));
		expectTimeTableForDayAndTime.add(new TimeTable(2L, 3, LocalDate.of(2023, 05, 25), 1L));
		Group group = new Group();
		group.setId(1L);
		group.setGroupName("ff-45");
		Teacher teacher = new Teacher();
		teacher.setId(11L);
		teacher.setTeacherName("Bob");
		teacher.setTeacherSurname("Harvy");
		Teacher teacher2 = new Teacher();
		teacher2.setId(15L);
		teacher2.setTeacherName("Bob");
		teacher2.setTeacherSurname("Harvy");	
		expectTimeTableForDayAndTime.get(0).setTeacher(teacher2);
		expectTimeTableForDayAndTime.get(1).setTeacher(teacher);

		when(timeTableRepository.findBytimeTableDateAndPairNumber(timeTable.getTimeTableDate(), timeTable.getPairNumber()))
				.thenReturn(expectTimeTableForDayAndTime);
		when(groupRepository.findById(group.getId())).thenReturn(Optional.of(group));
		when(teacherRepository.findById(teacher.getId())).thenReturn(Optional.of(teacher));
		timeTableService.saveEntrySchedule(timeTable, 1L, 11L);
		verify(timeTableRepository, never()).save(timeTable);
	}

	@Test
	void whenSchedulByDayAndPairNumberArePresentButTeacherAlreadyOnPairExpectNotSavingTimeTable() throws Exception {
		TimeTable timeTable = new TimeTable();
		timeTable.setPairNumber(3);
		timeTable.setTimeTableDate(LocalDate.of(2023, 05, 25));	
		List<TimeTable> expectTimeTableForDayAndTime = new ArrayList<TimeTable>();
		expectTimeTableForDayAndTime.add(new TimeTable(1L, 3, LocalDate.of(2023, 05, 25), 44L));
		expectTimeTableForDayAndTime.add(new TimeTable(2L, 3, LocalDate.of(2023, 05, 25), 46L));
		Group group = new Group();
		group.setId(1L);
		group.setGroupName("ff-45");
		Teacher teacher = new Teacher();
		teacher.setId(11L);
		teacher.setTeacherName("Bob");
		teacher.setTeacherSurname("Harvy");
		Teacher teacher2 = new Teacher();
		teacher2.setId(15L);
		teacher2.setTeacherName("Bob");
		teacher2.setTeacherSurname("Harvy");	
		expectTimeTableForDayAndTime.get(0).setTeacher(teacher2);
		expectTimeTableForDayAndTime.get(1).setTeacher(teacher);

		when(timeTableRepository.findBytimeTableDateAndPairNumber(timeTable.getTimeTableDate(), timeTable.getPairNumber()))
				.thenReturn(expectTimeTableForDayAndTime);
		when(groupRepository.findById(group.getId())).thenReturn(Optional.of(group));
		when(teacherRepository.findById(teacher.getId())).thenReturn(Optional.of(teacher));
		timeTableService.saveEntrySchedule(timeTable, 1L, 11L);
		verify(timeTableRepository, never()).save(timeTable);
	}

	@Test
	void whenSchedulByDayForStudentArePresentExpectListTimeTableForStudent() throws Exception {
		Student student = new Student();
		student.setStudentId(1L);
		student.setStudentName("Bob");
		student.setStudentSurname("Murray");
		student.setGroupId(2L);
		List<TimeTable> expectTimeTableForStudent = new ArrayList<TimeTable>();
		expectTimeTableForStudent.add(new TimeTable(1L, 3, LocalDate.of(2023, 05, 25), 2L));
		expectTimeTableForStudent.add(new TimeTable(2L, 4, LocalDate.of(2023, 05, 25), 2L));
		
		when(studentRepository.findById(student.getStudentId())).thenReturn(Optional.of(student));
		when(timeTableRepository.findBytimeTableDateAndGroupId(LocalDate.of(2023, 05, 25), student.getGroupId())).thenReturn(expectTimeTableForStudent);
		timeTableService.getTimeTableByDayForStudent(LocalDate.of(2023, 05, 25), student.getStudentId());
		verify(studentRepository, times(1)).findById(student.getStudentId());
		verify(timeTableRepository, times(1)).findBytimeTableDateAndGroupId(LocalDate.of(2023, 05, 25), student.getGroupId());
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

		when(studentRepository.findById(student.getStudentId())).thenReturn(Optional.of(student));
		when(timeTableRepository.findBytimeTableDateAndGroupId(LocalDate.of(2023, 05, 25), student.getGroupId())).thenReturn(expectTimeTableForStudent);
		timeTableService.getTimeTableByDayForStudent(LocalDate.of(2023, 05, 25), student.getStudentId());
		verify(studentRepository, times(1)).findById(student.getStudentId());
		verify(timeTableRepository, times(1)).findBytimeTableDateAndGroupId(LocalDate.of(2023, 05, 25), student.getGroupId());
		assertEquals(new ArrayList<TimeTable>(), timeTableService.getTimeTableByDayForStudent(LocalDate.of(2023, 05, 25), student.getStudentId()));
	}
}
