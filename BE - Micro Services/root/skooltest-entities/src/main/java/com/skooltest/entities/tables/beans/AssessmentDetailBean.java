package com.skooltest.entities.tables.beans;

import java.io.Serializable;
import java.util.List;

public class AssessmentDetailBean implements Serializable{

	private static final long serialVersionUID = -8501285636620437716L;
	
	private Integer assessmentDetailId;
	
	private Integer assessmentRequestId;
	
	private String assessmentCode;
	
	private String dueDate;
	
	private String title;
	
	private String instructions;
	
	private Double scoredMarks;

	private Double maxMarks;
	
	private List<QuestionBean> questions;

	public Integer getAssessmentDetailId() {
		return assessmentDetailId;
	}

	public void setAssessmentDetailId(Integer assessmentDetailId) {
		this.assessmentDetailId = assessmentDetailId;
	}

	public Integer getAssessmentRequestId() {
		return assessmentRequestId;
	}

	public void setAssessmentRequestId(Integer assessmentRequestId) {
		this.assessmentRequestId = assessmentRequestId;
	}

	public String getAssessmentCode() {
		return assessmentCode;
	}

	public void setAssessmentCode(String assessmentCode) {
		this.assessmentCode = assessmentCode;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public Double getScoredMarks() {
		return scoredMarks;
	}

	public void setScoredMarks(Double scoredMarks) {
		this.scoredMarks = scoredMarks;
	}

	public Double getMaxMarks() {
		return maxMarks;
	}

	public void setMaxMarks(Double maxMarks) {
		this.maxMarks = maxMarks;
	}

	public List<QuestionBean> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionBean> questions) {
		this.questions = questions;
	}

	@Override
	public String toString() {
		return "AssessmentDetailBean [assessmentDetailId=" + assessmentDetailId + ", assessmentRequestId="
				+ assessmentRequestId + ", assessmentCode=" + assessmentCode + ", dueDate=" + dueDate + ", title="
				+ title + ", instructions=" + instructions + ", scoredMarks=" + scoredMarks + ", maxMarks=" + maxMarks
				+ ", questions=" + questions + "]";
	}
}
