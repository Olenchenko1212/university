package ua.foxminded.universitycms.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "timetable", schema = "university")
public class TimeTable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "pair_number")
	private int pairNumber;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "timetable_date")
	private LocalDate timeTableDate;

	@ManyToOne
	@JoinColumn(name = "group_id")
	private Group group;
	
	@ManyToOne
	@JoinColumn(name = "teacher_id")
	private Teacher teacher;

	public TimeTable() {
	}

	public TimeTable(Long id, int pairNumber, LocalDate timeTableDate) {
		this.id = id;
		this.pairNumber = pairNumber;
		this.timeTableDate = timeTableDate;
	}

	@Override
	public String toString() {
		return "TimeTable [timetableId=" + id + ", pairNumber=" + pairNumber + ", timeTableDate="
				+ timeTableDate +"]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + pairNumber;
		result = prime * result + ((teacher == null) ? 0 : teacher.hashCode());
		result = prime * result + ((timeTableDate == null) ? 0 : timeTableDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		TimeTable other = (TimeTable) obj;
		if (group == null) {
			if (other.group != null) {
				return false;
			}
		} else if (!group.equals(other.group)) {
			return false;
		}

		if (pairNumber != other.pairNumber) {
			return false;
		}
		if (teacher == null) {
			if (other.teacher != null) {
				return false;
			}
		} else if (!teacher.equals(other.teacher)) {
			return false;
		}
		if (timeTableDate == null) {
			if (other.timeTableDate != null) {
				return false;
			}
		} else if (!timeTableDate.equals(other.timeTableDate)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
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

	public int getPairNumber() {
		return pairNumber;
	}

	public void setPairNumber(int pairNumber) {
		this.pairNumber = pairNumber;
	}

	public LocalDate getTimeTableDate() {
		return timeTableDate;
	}

	public void setTimeTableDate(LocalDate timeTableDate) {
		this.timeTableDate = timeTableDate;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
}
