package ua.foxminded.universitycms.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "courses", schema = "university")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "course_name")
	private String courseName;

	@Column(name = "course_description")
	private String courseDescription;

	@OneToOne(mappedBy = "course")
	private Teacher teacher;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	@JoinTable(name = "university.course_group", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
	private List<Group> groups;

	public Course() {
		groups = new ArrayList<>();
	}

	public Course(Long id, String courseName, String courseDescription) {
		this.id = id;
		this.courseName = courseName;
		this.courseDescription = courseDescription;
		groups = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "Course [courseId=" + id + ", courseName=" + courseName.trim() + ", courseDescription="
				+ courseDescription.trim() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((courseDescription == null) ? 0 : courseDescription.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((courseName == null) ? 0 : courseName.hashCode());
		result = prime * result + ((groups == null) ? 0 : groups.hashCode());
		result = prime * result + ((teacher == null) ? 0 : teacher.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Course other = (Course) obj;
		if (courseDescription == null) {
			if (other.courseDescription != null) {
				return false;
			}
		} else if (!courseDescription.equals(other.courseDescription)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (courseName == null) {
			if (other.courseName != null) {
				return false;
			}
		} else if (!courseName.equals(other.courseName)) {
			return false;
		}
		if (groups == null) {
			if (other.groups != null) {
				return false;
			}
		} else if (!groups.equals(other.groups)) {
			return false;
		}
		if (teacher == null) {
			if (other.teacher != null) {
				return false;
			}
		} else if (!teacher.equals(other.teacher)) {
			return false;
		}
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCourseName() {
		return courseName.trim();
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName.trim();
	}

	public String getCourseDescription() {
		return courseDescription.trim();
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription.trim();
	}

	public Teacher getTeacher() {
		if (teacher == null) {
			return new Teacher();
		}
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
}
