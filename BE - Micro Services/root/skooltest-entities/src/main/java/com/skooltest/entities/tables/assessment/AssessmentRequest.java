package com.skooltest.entities.tables.assessment;

import java.util.Date;

import com.skooltest.entities.tables.masters.MasterStatus;
import com.skooltest.entities.tables.masters.MasterSubject;
import com.skooltest.entities.tables.users.StudentDetail;
import com.skooltest.entities.tables.users.TeacherDetail;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "assessment_request")
public class AssessmentRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mst_subject_id")
	private MasterSubject masterSubject;

	private String topic;

	private String comments;

	private Date requestedDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id")
	private StudentDetail requestedBy;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "assigned_teacher_id")
	private TeacherDetail assignedTo;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "evaluation_teacher_id")
	private TeacherDetail evaluatedBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mst_status_id")
	private MasterStatus masterStatus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public MasterSubject getMasterSubject() {
		return masterSubject;
	}

	public void setMasterSubject(MasterSubject masterSubject) {
		this.masterSubject = masterSubject;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

	public StudentDetail getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(StudentDetail requestedBy) {
		this.requestedBy = requestedBy;
	}

	public TeacherDetail getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(TeacherDetail assignedTo) {
		this.assignedTo = assignedTo;
	}

	public MasterStatus getMasterStatus() {
		return masterStatus;
	}

	public void setMasterStatus(MasterStatus masterStatus) {
		this.masterStatus = masterStatus;
	}
	
	public TeacherDetail getEvaluatedBy() {
		return evaluatedBy;
	}

	public void setEvaluatedBy(TeacherDetail evaluatedBy) {
		this.evaluatedBy = evaluatedBy;
	}

	@Override
	public String toString() {
		return "AssessmentRequest [id=" + id + ", masterSubject=" + masterSubject + ", topic=" + topic + ", comments="
				+ comments + ", requestedDate=" + requestedDate + ", requestedBy=" + requestedBy + ", assignedTo="
				+ assignedTo + ", evaluatedBy=" + evaluatedBy + ", masterStatus=" + masterStatus + "]";
	}
}
