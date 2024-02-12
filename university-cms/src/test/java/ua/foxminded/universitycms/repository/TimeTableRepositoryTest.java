package ua.foxminded.universitycms.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.universitycms.models.Teacher;
import ua.foxminded.universitycms.util.Config;

@DataJpaTest
@ContextConfiguration(classes = Config.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/sql/clean_tables.sql", "/sql/samply_data.sql" },
				executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class TimeTableRepositoryTest {
	
	@Autowired
	private TimeTableRepository dao;
	
	@Autowired
	private TeacherRepository daoT;
	
	@Test
	void whenTwoPairsByTeacherForDayIsPresentExpectListTimeTableWithSizeEqualsTwo() throws SQLException {	
		Optional<Teacher> teacher = daoT.findById(2L); 
		assertEquals(2, dao.findBytimeTableDateAndTeacher(LocalDate.of(2023, 05, 18), teacher.get()).size());
	}
	
	@Test
	void whenPairByTeacherForDayIsNotPresentExpectEmptyList() throws SQLException {
		Optional<Teacher> teacher = daoT.findById(2L);
		assertEquals(0, dao.findBytimeTableDateAndTeacher(LocalDate.of(2023, 05, 01), teacher.get()).size());
	}
	
	@Test
	void whenTwoPairsByGroupForDayIsPresentExpectListTimeTableWithSizeEqualsTwo() throws SQLException {
		assertEquals(2, dao.findBytimeTableDateAndGroupId(LocalDate.of(2023, 05, 18), 1L).size());
	}
	
	@Test
	void whenPairByGroupForDayIsNotPresentExpectEmptyList() throws SQLException {
		assertEquals(0, dao.findBytimeTableDateAndGroupId(LocalDate.of(2023, 05, 01), 3L).size());
	}
	
	@Test
	void whenThreeLessonsInSecondPairForDayIsPresentExpectListTimeTableWithSizeEqualsThree() throws SQLException {
		assertEquals(2, dao.findBytimeTableDateAndPairNumber(LocalDate.of(2023, 05, 18), 2).size());
	}
	
	@Test
	void whenLessonInFirstPairForDayIsNotPresentExpectEmptyList() throws SQLException {
		assertEquals(0, dao.findBytimeTableDateAndPairNumber(LocalDate.of(2023, 05, 15), 1).size());
	}
}
