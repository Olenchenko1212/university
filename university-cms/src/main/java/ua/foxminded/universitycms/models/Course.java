package ua.foxminded.universitycms.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "courses", schema = "university")
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_id")
	private int courseId;
	
	@Column(name = "course_name")
	private String courseName;
	
	@Column(name = "course_description")
	private String courseDescription;

	public Course() {}

	public Course(int courseId, String courseName, String courseDescription) {
		this.courseId = courseId;
		this.courseName = courseName;
		this.courseDescription = courseDescription;
	}

	@Override
	public String toString() {
		return "Course [courseId=" + courseId + ", courseName=" + courseName.trim() + ", courseDescription="
				+ courseDescription.trim() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((courseDescription == null) ? 0 : courseDescription.hashCode());
		result = prime * result + courseId;
		result = prime * result + ((courseName == null) ? 0 : courseName.hashCode());
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
		if (courseId != other.courseId) {
			return false;
		}
		if (courseName == null) {
			if (other.courseName != null) {
				return false;
			}
		} else if (!courseName.equals(other.courseName)) {
			return false;
		}
		return true;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}
}
