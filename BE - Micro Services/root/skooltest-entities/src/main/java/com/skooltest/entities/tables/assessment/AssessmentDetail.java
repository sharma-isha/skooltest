package com.skooltest.entities.tables.assessment;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "assessment_detail")
public class AssessmentDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "assessment_request_id")
	private AssessmentRequest assessmentRequest;

	@Column(name = "assessment_code")
	private String assessmentCode;

	@Column(name = "due_date")
	private Date dueDate;

	private String title;

	@OneToMany(mappedBy = "assessmentDetail", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Question> questions;

	private String instructions;

	@Column(name = "max_marks")
	private Double maxMarks;

	@Column(name = "scored_marks")
	private Double scoredMarks;

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

	public String getAssessmentCode() {
		return assessmentCode;
	}

	public void setAssessmentCode(String assessmentCode) {
		this.assessmentCode = assessmentCode;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
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

	@Override
	public String toString() {
		return "AssessmentDetail [id=" + id + ", assessmentRequest=" + assessmentRequest + ", assessmentCode="
				+ assessmentCode + ", dueDate=" + dueDate + ", title=" + title + ", questions=" + questions
				+ ", instructions=" + instructions + ", maxMarks=" + maxMarks + ", scoredMarks=" + scoredMarks + "]";
	}
}
