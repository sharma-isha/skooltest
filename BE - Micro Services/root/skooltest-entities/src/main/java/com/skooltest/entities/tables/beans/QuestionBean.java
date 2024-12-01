package com.skooltest.entities.tables.beans;

import java.io.Serializable;

public class QuestionBean implements Serializable{

	private static final long serialVersionUID = -7102283611260976751L;
	
	private Integer questionId;
	
	private String content;
	
	private Double maxMarks;
	
	private Double scoredMarks;
	
	private String comments;

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
		return "QuestionBean [questionId=" + questionId + ", content=" + content + ", maxMarks=" + maxMarks
				+ ", scoredMarks=" + scoredMarks + ", comments=" + comments + "]";
	}
}
