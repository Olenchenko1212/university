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

@Entity
@Table(name = "timetable", schema = "university")
public class TimeTable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "timetable_id")
	private Long timetableId;

	@Column(name = "pair_number")
	private int pairNumber;

	@Column(name = "timetable_date")
	private LocalDate timeTableDate;

	@Column(name = "group_id", insertable = false, updatable = false)
	private Long groupId;

	@Column(name = "teacher_id", insertable = false, updatable = false)
	private Long teacherId;

	@ManyToOne
	@JoinColumn(name = "group_id")
	private Group group;
	
	@ManyToOne
	@JoinColumn(name = "teacher_id")
	private Teacher teacher;

	public TimeTable() {
	}

	public TimeTable(Long timetableId, int pairNumber, LocalDate timeTableDate, Long groupId, Long teacherId) {
		this.timetableId = timetableId;
		this.pairNumber = pairNumber;
		this.timeTableDate = timeTableDate;
		this.groupId = groupId;
		this.teacherId = teacherId;
	}

	@Override
	public String toString() {
		return "TimeTable [timetableId=" + timetableId + ", pairNumber=" + pairNumber + ", timeTableDate="
				+ timeTableDate + ", groupId=" + groupId + ", teacherId=" + teacherId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		result = prime * result + pairNumber;
		result = prime * result + ((teacherId == null) ? 0 : teacherId.hashCode());
		result = prime * result + ((timeTableDate == null) ? 0 : timeTableDate.hashCode());
		result = prime * result + ((timetableId == null) ? 0 : timetableId.hashCode());
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
		if (groupId == null) {
			if (other.groupId != null) {
				return false;
			}
		} else if (!groupId.equals(other.groupId)) {
			return false;
		}
		if (pairNumber != other.pairNumber) {
			return false;
		}
		if (teacherId == null) {
			if (other.teacherId != null) {
				return false;
			}
		} else if (!teacherId.equals(other.teacherId)) {
			return false;
		}
		if (timeTableDate == null) {
			if (other.timeTableDate != null) {
				return false;
			}
		} else if (!timeTableDate.equals(other.timeTableDate)) {
			return false;
		}
		if (timetableId == null) {
			if (other.timetableId != null) {
				return false;
			}
		} else if (!timetableId.equals(other.timetableId)) {
			return false;
		}
		return true;
	}

	public Long getTimetableId() {
		return timetableId;
	}

	public void setTimetableId(Long timetableId) {
		this.timetableId = timetableId;
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

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
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
