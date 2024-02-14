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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "groups", schema = "university")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "group_name")
	private String groupName;

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Student> students = new ArrayList<>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	@JoinTable(name = "university.course_group", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
	private List<Course> courses;

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TimeTable> timeTable = new ArrayList<>();

	public Group() {
		courses = new ArrayList<>();
	}

	public Group(Long id, String groupName) {
		this.id = id;
		this.groupName = groupName;
		courses = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "Group [groupId=" + id + ", groupName=" + groupName.trim() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((courses == null) ? 0 : courses.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
		result = prime * result + ((students == null) ? 0 : students.hashCode());
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
		Group other = (Group) obj;
		if (courses == null) {
			if (other.courses != null) {
				return false;
			}
		} else if (!courses.equals(other.courses)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (groupName == null) {
			if (other.groupName != null) {
				return false;
			}
		} else if (!groupName.equals(other.groupName)) {
			return false;
		}
		if (students == null) {
			if (other.students != null) {
				return false;
			}
		} else if (!students.equals(other.students)) {
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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
}
