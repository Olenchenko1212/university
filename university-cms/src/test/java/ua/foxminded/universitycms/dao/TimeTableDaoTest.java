package ua.foxminded.universitycms.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.universitycms.util.Config;

@DataJpaTest
@ContextConfiguration(classes = Config.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/sql/clean_tables.sql", "/sql/samply_data.sql" },
				executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class TimeTableDaoTest {
	
	@Autowired
	private TimeTableDao dao;
	
	@Test
	void whenTwoPairsByTeacherForDayIsPresentExpectListTimeTableWithSizeEqualsTwo() throws SQLException {
		assertEquals(3, dao.findTimeTableByTeacherForDay(LocalDate.of(2023, 05, 18), 3L).size());
	}
	
	@Test
	void whenPairByTeacherForDayIsNotPresentExpectEmptyList() throws SQLException {
		assertEquals(0, dao.findTimeTableByTeacherForDay(LocalDate.of(2023, 05, 01), 3L).size());
	}
	
	@Test
	void whenTwoPairsByGroupForDayIsPresentExpectListTimeTableWithSizeEqualsTwo() throws SQLException {
		assertEquals(2, dao.findTimeTableByGroupForDay(LocalDate.of(2023, 05, 18), 1L).size());
	}
	
	@Test
	void whenPairByGroupForDayIsNotPresentExpectEmptyList() throws SQLException {
		assertEquals(0, dao.findTimeTableByGroupForDay(LocalDate.of(2023, 05, 01), 3L).size());
	}
	
	@Test
	void whenThreeLessonsInSecondPairForDayIsPresentExpectListTimeTableWithSizeEqualsThree() throws SQLException {
		assertEquals(3, dao.findTimeTableByPairNumberAndDate(2, LocalDate.of(2023, 05, 18)).size());
	}
	
	@Test
	void whenLessonInFirstPairForDayIsNotPresentExpectEmptyList() throws SQLException {
		assertEquals(0, dao.findTimeTableByPairNumberAndDate(1, LocalDate.of(2023, 05, 15)).size());
	}
}
