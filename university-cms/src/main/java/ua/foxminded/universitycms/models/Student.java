package ua.foxminded.universitycms.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "students", schema = "university")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "student_name")
	private String studentName;

	@Column(name = "student_surname")
	private String studentSurname;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id", referencedColumnName = "id")
	private Group group;
	
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	public Student() {
	}

	public Student(Long id, String studentName, String studentSurname) {
		this.id = id;
		this.studentName = studentName;
		this.studentSurname = studentSurname;
	}
	
	@Override
	public String toString() {
		return "Student [id=" + id + ", studentName=" + studentName + ", studentSurname=" + studentSurname +"]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((studentName == null) ? 0 : studentName.hashCode());
		result = prime * result + ((studentSurname == null) ? 0 : studentSurname.hashCode());
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
		Student other = (Student) obj;
		if (group == null) {
			if (other.group != null) {
				return false;
			}
		} else if (!group.equals(other.group)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
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

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName.trim();
	}

	public String getStudentSurname() {
		return studentSurname;
	}

	public void setStudentSurname(String studentSurname) {
		this.studentSurname = studentSurname.trim();
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
