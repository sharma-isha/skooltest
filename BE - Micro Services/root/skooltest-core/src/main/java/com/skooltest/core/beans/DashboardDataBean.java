package com.skooltest.core.beans;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import com.skooltest.entities.tables.beans.UserBean;

public class DashboardDataBean implements Serializable {

	private static final long serialVersionUID = -2944161484446709886L;

	private UserBean user;
	private Set<String> masterSubjects;
	private Set<UserBean> students;
	private Set<UserBean> teachers;
	private Set<AssessmentRequestDetailsBean> assessmentRequestDetails;

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public Set<String> getMasterSubjects() {
		return masterSubjects;
	}

	public void setMasterSubjects(Set<String> masterSubjects) {
		this.masterSubjects = masterSubjects;
	}

	public Set<AssessmentRequestDetailsBean> getAssessmentRequestDetails() {
		return assessmentRequestDetails;
	}

	public void setAssessmentRequestDetails(Set<AssessmentRequestDetailsBean> assessmentRequestDetails) {
		this.assessmentRequestDetails = assessmentRequestDetails;
	}

	public Set<UserBean> getStudents() {
		return students;
	}

	public void setStudents(Set<UserBean> students) {
		this.students = students;
	}

	public Set<UserBean> getTeachers() {
		return teachers;
	}

	public void setTeachers(Set<UserBean> teachers) {
		this.teachers = teachers;
	}

	@Override
	public String toString() {
		return "DashboardDataBean [user=" + user + ", masterSubjects=" + masterSubjects + ", students=" + students
				+ ", teachers=" + teachers + ", assessmentRequestDetails=" + assessmentRequestDetails + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(assessmentRequestDetails, masterSubjects, students, teachers, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DashboardDataBean other = (DashboardDataBean) obj;
		return Objects.equals(assessmentRequestDetails, other.assessmentRequestDetails)
				&& Objects.equals(masterSubjects, other.masterSubjects) && Objects.equals(students, other.students)
				&& Objects.equals(teachers, other.teachers) && Objects.equals(user, other.user);
	}
}
