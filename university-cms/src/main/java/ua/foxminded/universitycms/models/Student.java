package ua.foxminded.universitycms.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "students", schema = "university")
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_id")
	private int studentId;
	
	@Column(name = "group_id")
	private int groupId;
	
	@Column(name = "student_name")
	private String studentName;
	
	@Column(name = "student_surname")
	private String studentSurname;
	
	protected Student() {}

	public Student(int studentId, int groupId, String studentName, String studentSurname) {
		this.studentId = studentId;
		this.groupId = groupId;
		this.studentName = studentName;
		this.studentSurname = studentSurname;
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", groupId=" + groupId + ", studentName=" + studentName
				+ ", studentSurname=" + studentSurname + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + groupId;
		result = prime * result + studentId;
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
		if (groupId != other.groupId) {
			return false;
		}
		if (studentId != other.studentId) {
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

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
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
}
	
