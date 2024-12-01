package com.skooltest.entities.tables.assessment;

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
@Table(name = "question")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(columnDefinition = "TEXT")
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assessment_detail_id")
	private AssessmentDetail assessmentDetail;
	
	@Column(name="max_marks")
	private Double maxMarks;
	
	@Column(name="scored_marks")
	private Double scoredMarks;
	
	private String comments;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public AssessmentDetail getAssessmentDetail() {
		return assessmentDetail;
	}

	public void setAssessmentDetail(AssessmentDetail assessmentDetail) {
		this.assessmentDetail = assessmentDetail;
	}

	public Double getMaxMarks() {
		return maxMarks;
	}

	public void setMaxMarks(Double maxMarks) {
		this.maxMarks = maxMarks;
	}

	public Double getScoredMarks() {
		return scoredMarks;
	}

	public void setScoredMarks(Double scoredMarks) {
		this.scoredMarks = scoredMarks;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", content=" + content + ", assessmentDetail=" + assessmentDetail + ", maxMarks="
				+ maxMarks + ", scoredMarks=" + scoredMarks + ", comments=" + comments + "]";
	}
}
