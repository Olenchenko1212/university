package ua.foxminded.universitycms.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.universitycms.dao.CourseDao;
import ua.foxminded.universitycms.dao.TeacherDao;
import ua.foxminded.universitycms.models.Course;
import ua.foxminded.universitycms.models.Teacher;

@Service
public class TeacherServiceImpl implements TeacherService {
	private static Logger logger = LoggerFactory.getLogger(TeacherServiceImpl.class);

	private final TeacherDao teacherDao;
	private final CourseDao courseDao;

	public TeacherServiceImpl(TeacherDao teacherDao, CourseDao courseDao) {
		this.teacherDao = teacherDao;
		this.courseDao = courseDao;
	}

	@Override
	public List<Teacher> getAllTeachers() {
		List<Teacher> allTeachers = new ArrayList<>(teacherDao.findAll());
		logger.info("Getting all {} teachers from DB", allTeachers.size());
		return allTeachers;
	}

	@Override
	public Optional<Teacher> getTeacherById(Long teacherId) throws SQLException {
		return teacherDao.findById(teacherId);
	}
	
	@Override
	public Optional<Teacher> getTeacherCourse(Long courseId) throws SQLException{
		return teacherDao.findTeacherByCourseId(courseId);
	}

	@Override
	@Transactional
	public void saveTeacherOnCourse(Teacher teacher, Course course) throws Exception, SQLException {
		if (!teacher.getTeacherName().isEmpty() && !teacher.getTeacherSurname().isEmpty()) {
			if (!course.getCourseName().isEmpty() && !course.getCourseDescription().isEmpty()) {
				
				
				
//				course.setTeacher(teacher);
//				teacher.setCourse(course);
//				courseDao.saveAndFlush(course);
				courseDao.save(course);
				teacher.setCourse(course);
				teacherDao.save(teacher);
				logger.info("Save teacher {} and course {} into DB", teacher.getTeacherSurname(), course.getCourseName());
//				 teacherDao.save(teacher);
//				course.setTeacher(teacher);
//				
//				courseDao.save(course);
//				 teacher.setCourse(course);
//				 teacherDao.save(teacher);
//				 System.out.println(teacher);
//				 
//				 System.out.println(course);
//				 courseDao.saveAndFlush(course);
//				 courseDao.saveAndFlush(course);
//				 teacherDao.save(teacher);
//				 courseDao.saveAndFlush(course);
			//	 System.out.println(teacherDao.findTeacherByCourseId(course.getCourseId()));
			} else {
				logger.error("The course's name or description is empty",
						new Exception("The course's name or description is empty"));
			}
		} else {
			logger.error("The teacher's name or surname is empty",
					new Exception("The teacher's name or surname is empty"));
		}
	

	// Optional<Course> course = courseDao.findById(courseId);
//		System.out.println(teacherDao.findTeacherByCourseId(courseId));
//		if(course.isPresent()) {
//			if(!teacherDao.findTeacherByCourseId(courseId).isPresent()) {
//				teacher.setCourse(course.get());
////				course.get().setTeacher(teacher);
//				teacherDao.save(teacher);
////				courseDao.saveAndFlush(course.get());
//				logger.info("Save teacher {} into DB", teacher.getTeacherSurname());
//			} else {
//				logger.error("The course with id = {} is taken by another teacher",
//						courseId, new Exception("The course is taken by another teacher"));
//			}
//		} else {
//			logger.error("The course with id = {} is not present from univesity",
//					courseId, new Exception("The course is not present from univesity"));
//		}
	}

	@Override
	public void deleteTeacher(Long teacherId) throws SQLException {
		teacherDao.deleteById(teacherId);
		logger.info("Teacher Id = {} delete from DB", teacherId);
	}
}
