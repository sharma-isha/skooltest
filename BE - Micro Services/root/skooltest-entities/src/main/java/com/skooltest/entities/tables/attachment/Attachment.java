package com.skooltest.entities.tables.attachment;

import java.util.Date;

import com.skooltest.entities.tables.assessment.AssessmentRequest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "attachment")
public class Attachment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "assessment_request_id")
	private AssessmentRequest assessmentRequest;
	
	private String location;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="created_time")
	private Date createdTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AssessmentRequest getAssessmentRequest() {
		return assessmentRequest;
	}

	public void setAssessmentRequest(AssessmentRequest assessmentRequest) {
		this.assessmentRequest = assessmentRequest;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Override
	public String toString() {
		return "Attachment [id=" + id + ", assessmentRequest=" + assessmentRequest + ", location=" + location
				+ ", createdBy=" + createdBy + ", createdTime=" + createdTime + "]";
	}
}
