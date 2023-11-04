package ua.foxminded.universitycms.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "students", schema = "university")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_id")
	private Long studentId;

	@Column(name = "group_id", insertable = false, updatable = false)
	private Long groupId;

	@Column(name = "student_name")
	private String studentName;

	@Column(name = "student_surname")
	private String studentSurname;

	@ManyToOne
	@JoinColumn(name = "group_id")
	private Group group;

	public Student() {
	}

	public Student(Long studentId, Long groupId, String studentName, String studentSurname) {
		this.studentId = studentId;
		this.groupId = groupId;
		this.studentName = studentName;
		this.studentSurname = studentSurname;
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", groupId=" + groupId + ", studentName=" + studentName.trim()
				+ ", studentSurname=" + studentSurname.trim() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		result = prime * result + ((studentId == null) ? 0 : studentId.hashCode());
		result = prime * result + ((studentName == null) ? 0 : studentName.hashCode());
		result = prime * result + ((studentSurname == null) ? 0 : studentSurname.hashCode());
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
		Student other = (Student) obj;
		if (group == null) {
			if (other.group != null) {
				return false;
			}
		} else if (!group.equals(other.group)) {
			return false;
		}
		if (groupId == null) {
			if (other.groupId != null) {
				return false;
			}
		} else if (!groupId.equals(other.groupId)) {
			return false;
		}
		if (studentId == null) {
			if (other.studentId != null) {
				return false;
			}
		} else if (!studentId.equals(other.studentId)) {
			return false;
		}
		if (studentName == null) {
			if (other.studentName != null) {
				return false;
			}
		} else if (!studentName.equals(other.studentName)) {
			return false;
		}
		if (studentSurname == null) {
			if (other.studentSurname != null) {
				return false;
			}
		} else if (!studentSurname.equals(other.studentSurname)) {
			return false;
		}
		return true;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentSurname() {
		return studentSurname;
	}

	public void setStudentSurname(String studentSurname) {
		this.studentSurname = studentSurname;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
}
