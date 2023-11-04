package ua.foxminded.universitycms.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.foxminded.universitycms.dao.CourseDao;
import ua.foxminded.universitycms.dao.GroupDao;
import ua.foxminded.universitycms.models.Course;
import ua.foxminded.universitycms.models.Group;

@Service
public class GroupServiceImpl implements GroupService {
	private static Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

	private final GroupDao groupDao;
	private final CourseDao courseDao;

	public GroupServiceImpl(GroupDao groupDao, CourseDao courseDao) {
		this.groupDao = groupDao;
		this.courseDao = courseDao;
	}

	@Override
	public List<Group> getAllGroups() throws SQLException {
		List<Group> allGrouprs = new ArrayList<>(groupDao.findAll());
		logger.info("Getting all {} groups from DB", allGrouprs.size());
		return allGrouprs;
	}

	@Override
	public Optional<Group> getGroupById(Long groupId) throws SQLException {
		return groupDao.findById(groupId);
	}

	@Override
	public void saveGroup(Group group) throws Exception, SQLException {
		if (!groupDao.findByGroupName(group.getGroupName()).isPresent()) {
			groupDao.saveAndFlush(group);
			logger.info("Save group {} into DB", group.getGroupName());
		} else {
			logger.error("Group {} is allready in DB", group.getGroupName(), new Exception("Group is allready in DB"));
		}
	}

	@Override
	public void saveGroupWithCourse(Group group, Course course) throws Exception, SQLException {
		if (!groupDao.findByGroupName(group.getGroupName()).isPresent()
				&& !courseDao.findByCourseName(course.getCourseName()).isPresent()) {
			group.getCourses().add(course);
			course.getGroups().add(group);
			groupDao.save(group);
			courseDao.save(course);
			logger.info("Save group {} and course {} into DB", group.getGroupName(), course.getCourseName());
		} else {
			logger.error("Group {} or course {} is already in DB", group.getGroupName(), course.getCourseName(),
					new Exception("Group or course is allready in DB"));
		}
	}

	@Override
	@Transactional
	public void saveEnrollGroupToCourse(Long groupId, Long courseId) throws Exception, SQLException {
		Optional<Group> group = groupDao.findById(groupId);
		Optional<Course> course = courseDao.findById(courseId);
		if (group.isPresent() && course.isPresent()) {
			Optional<Course> courseInGroup = group.get().getCourses().stream()
					.filter(c -> c.getCourseId().equals(courseId)).findFirst();
			if (!courseInGroup.isPresent()) {
				course.get().getGroups().add(group.get());
				group.get().getCourses().add(course.get());
				courseDao.save(course.get());
				groupDao.save(group.get());
				logger.info("Save Enroll group Id = {} to course Id = {} into DB", groupId, courseId);
			} else {
				logger.error("Enroll with group Id = {} and course Id = {} is already present in DB", groupId, courseId,
						new Exception("Enroll with group or course is  already present in DB"));
			}
		} else {
			logger.error("Group Id = {} or course Id = {} is not find in DB", groupId, courseId,
					new Exception("Group or course is not find in DB"));
		}
	}

	@Override
	public void deleteGroup(Long groupID) throws SQLException {
		if (groupDao.findById(groupID).isPresent()) {
			groupDao.deleteById(groupID);
			logger.info("Group Id = {} delete from DB", groupID);
		} else {
			logger.error("Group Id = {} is not find in DB", groupID, new Exception("Group is not find in DB"));
		}
	}
}
