package com.skooltest.attachment.beans;

import java.io.Serializable;
import java.util.List;

import com.skooltest.entities.tables.beans.QuestionBean;

public class QuestionPaperPdfBean implements Serializable{

	private static final long serialVersionUID = 6506012563807226443L;
	
	private String title;
	
	private String assessmentCode;
	
	private String dueDate;
	
	private Double totalMarks;
	
	private List<String> instructions;
	
	private List<QuestionBean> questions;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public Double getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(Double totalMarks) {
		this.totalMarks = totalMarks;
	}

	public List<String> getInstructions() {
		return instructions;
	}

	public void setInstructions(List<String> instructions) {
		this.instructions = instructions;
	}

	public List<QuestionBean> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionBean> questions) {
		this.questions = questions;
	}

	@Override
	public String toString() {
		return "QuestionPaperPdfBean [title=" + title + ", assessmentCode=" + assessmentCode + ", dueDate=" + dueDate
				+ ", totalMarks=" + totalMarks + ", instructions=" + instructions + ", questions=" + questions + "]";
	}
}
