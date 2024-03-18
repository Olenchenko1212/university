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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "teachers", schema = "university")
public class Teacher {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "teacher_name")
	private String teacherName;

	@Column(name = "teacher_surname")
	private String teacherSurname;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id", referencedColumnName = "id")
	private Course course;
	
	@OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TimeTable> timeTable = new ArrayList<>();
	
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	public Teacher() {
	}

	public Teacher(Long id, String teacherName, String teacherSurname) {
		this.id = id;
		this.teacherName = teacherName;
		this.teacherSurname = teacherSurname;
	}

	@Override
	public String toString() {
		return "Teacher [teacherId=" + id + ", teacherName=" + teacherName.trim() + ", teacherSurname="
				+ teacherSurname.trim() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((course == null) ? 0 : course.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((teacherName == null) ? 0 : teacherName.hashCode());
		result = prime * result + ((teacherSurname == null) ? 0 : teacherSurname.hashCode());
		result = prime * result + ((timeTable == null) ? 0 : timeTable.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
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
		if (timeTable == null) {
			if (other.timeTable != null) {
				return false;
			}
		} else if (!timeTable.equals(other.timeTable)) {
			return false;
		}
		if (user == null) {
			if (other.user != null) {
				return false;
			}
		} else if (!user.equals(other.user)) {
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

	public List<TimeTable> getTimeTable() {
		return timeTable;
	}

	public void setTimeTable(List<TimeTable> timeTable) {
		this.timeTable = timeTable;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
