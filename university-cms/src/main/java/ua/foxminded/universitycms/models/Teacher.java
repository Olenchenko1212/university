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
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "teachers", schema = "university")
public class Teacher {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "teacher_id")
	private Long teacherId;

	@Column(name = "teacher_name")
	private String teacherName;

	@Column(name = "teacher_surname")
	private String teacherSurname;

	@OneToOne(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "teacher_id")
	private Course course;
	
	@OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TimeTable> timeTable = new ArrayList<>();

	public Teacher() {
	}

	public Teacher(Long teacherId, String teacherName, String teacherSurname) {
		this.teacherId = teacherId;
		this.teacherName = teacherName;
		this.teacherSurname = teacherSurname;
	}

	@Override
	public String toString() {
		return "Teacher [teacherId=" + teacherId + ", teacherName=" + teacherName.trim() + ", teacherSurname="
				+ teacherSurname.trim() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((course == null) ? 0 : course.hashCode());
		result = prime * result + ((teacherId == null) ? 0 : teacherId.hashCode());
		result = prime * result + ((teacherName == null) ? 0 : teacherName.hashCode());
		result = prime * result + ((teacherSurname == null) ? 0 : teacherSurname.hashCode());
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
		Teacher other = (Teacher) obj;
		if (course == null) {
			if (other.course != null) {
				return false;
			}
		} else if (!course.equals(other.course)) {
			return false;
		}
		if (teacherId == null) {
			if (other.teacherId != null) {
				return false;
			}
		} else if (!teacherId.equals(other.teacherId)) {
			return false;
		}
		if (teacherName == null) {
			if (other.teacherName != null) {
				return false;
			}
		} else if (!teacherName.equals(other.teacherName)) {
			return false;
		}
		if (teacherSurname == null) {
			if (other.teacherSurname != null) {
				return false;
			}
		} else if (!teacherSurname.equals(other.teacherSurname)) {
			return false;
		}
		return true;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}


	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherSurname() {
		return teacherSurname;
	}

	public void setTeacherSurname(String teacherSurname) {
		this.teacherSurname = teacherSurname;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
}
