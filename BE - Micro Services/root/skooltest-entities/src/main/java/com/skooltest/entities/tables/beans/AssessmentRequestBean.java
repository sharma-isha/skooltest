package com.skooltest.entities.tables.beans;

import java.io.Serializable;

public class AssessmentRequestBean implements Serializable{
	
	private static final long serialVersionUID = 139269564849531522L;

	private Integer assessmentRequestId;
	
	private String subject;
	
	private String topic;
	
	private String comments;
	
	private UserBean requestedBy;
	
	private UserBean assignedTo;
	
	private String status;
	
	private String requestedDate;
	
	private UserBean evaluatedBy;
	
	private AssessmentDetailBean assessmentDetail;
	
	public Integer getAssessmentRequestId() {
		return assessmentRequestId;
	}

	public void setAssessmentRequestId(Integer assessmentRequestId) {
		this.assessmentRequestId = assessmentRequestId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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

	public UserBean getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(UserBean requestedBy) {
		this.requestedBy = requestedBy;
	}

	public UserBean getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(UserBean assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(String requestedDate) {
		this.requestedDate = requestedDate;
	}

	public UserBean getEvaluatedBy() {
		return evaluatedBy;
	}

	public void setEvaluatedBy(UserBean evaluatedBy) {
		this.evaluatedBy = evaluatedBy;
	}

	public AssessmentDetailBean getAssessmentDetail() {
		return assessmentDetail;
	}

	public void setAssessmentDetail(AssessmentDetailBean assessmentDetail) {
		this.assessmentDetail = assessmentDetail;
	}

	@Override
	public String toString() {
		return "AssessmentRequestBean [assessmentRequestId=" + assessmentRequestId + ", subject=" + subject + ", topic="
				+ topic + ", comments=" + comments + ", requestedBy=" + requestedBy + ", assignedTo=" + assignedTo
				+ ", status=" + status + ", requestedDate=" + requestedDate + ", evaluatedBy=" + evaluatedBy
				+ ", assessmentDetail=" + assessmentDetail + "]";
	}
}
