package ua.foxminded.universitycms.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

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
public class TeacherRepositoryTest {
	@Autowired
	private TeacherRepository daoT;
	
	@Autowired
	private CourseRepository daoC;
	
	@Test
	void whenTeachesNotRelatedWithCoursesExpactListTeachers() throws SQLException { 
		Course course1 = new Course();
		course1.setCourseName("Biology");
		course1.setCourseDescription("Descr biology");
		
		Course course2 = new Course();
		course2.setCourseName("Geometry");
		course2.setCourseDescription("Descr Geometry");
		
		List<Teacher> teachers = daoT.findAll();
		for(Teacher teacher : teachers){
			System.out.println(teacher);
		}
		
		daoT.save(new Teacher(4L, "DDDDDDD", "CCCCCC"));
		
		
		
//		course1.setTeacher(teachers.get(0));
//		teachers.get(0).setCourse(course1);
		
		
		
		List<Teacher> teachers1 = daoT.findAll();
		for(Teacher teacher : teachers1){
			System.out.println(teacher);
			System.out.println(teacher.getCourse());
		}
		
		List<Teacher> teachers2 = teachers1.stream()
			.filter(t -> t.getCourse() == null)
			.collect(Collectors.toList());
		
		System.out.println("______________________________");
		for(Teacher teacher : teachers2){
			System.out.println(teacher);
			System.out.println(teacher.getCourse());
		}
		
		
		course1.setTeacher(teachers1.get(3));
		daoC.save(course1);
		
		List<Course> courses = daoC.findAll();
		for(Course course : courses){
			System.out.println(course);
			System.out.println(course.getTeacher());
		}
		
	
		teachers1.get(3).setCourse(course1);
		daoT.save(teachers1.get(3));
		
		
		List<Teacher> teachers3 = daoT.findAll();
		for(Teacher teacher : teachers3){
			System.out.println(teacher);
			System.out.println(teacher.getCourse());
		}
	}
}
