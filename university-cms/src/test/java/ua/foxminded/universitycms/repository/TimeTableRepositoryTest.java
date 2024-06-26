package ua.foxminded.universitycms.repository;

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
public class TimeTableRepositoryTest {
	
	@Autowired
	private TimeTableRepository dao;
	
	@Test
	void whenTwoPairsByDayAndPairNumberIsPresentExpectListTimeTableWithSizeEqualsTwo() throws SQLException {	
		assertEquals(2, dao.findByTimeTableDateAndPairNumber(LocalDate.of(2023, 05, 18), 3).size());
	}
	
	@Test
	void whenPairByDayIsNotPresentExpectEmptyList() throws SQLException {
		assertEquals(0, dao.findByTimeTableDateAndPairNumber(LocalDate.of(2023, 05, 25), 1).size());
	}
}
