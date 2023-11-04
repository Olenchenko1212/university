package ua.foxminded.universitycms.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.universitycms.models.Course;
import ua.foxminded.universitycms.models.Teacher;
import ua.foxminded.universitycms.util.Config;

@DataJpaTest
@ContextConfiguration(classes = Config.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/sql/clean_tables.sql", "/sql/samply_data.sql" },
				executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class TeacherDaoTest {
	
	@Autowired
	private TeacherDao teacherDao;
	@Autowired
	private CourseDao courseDao;
	
	@Test
	void whenReadAllTeachersExpectCalcOfAllCourses() {
		assertEquals(3, teacherDao.findAll().size());
	}
	
	@Test
	void whenTeacherRelatedWithCourseExpectThisTeacher () throws SQLException{
		Teacher teacher = new Teacher();
		teacher.setTeacherName("Mary");
		teacher.setTeacherSurname("Bordo");
		
		Course course = new Course();
		course.setCourseName("Philosophy");
		course.setCourseDescription("Description");
		
		courseDao.save(course);
		teacher.setCourse(course);
		teacherDao.save(teacher);
		assertEquals(teacher, teacherDao.findTeacherByCourseId(course.getCourseId()).get());
	}
}
