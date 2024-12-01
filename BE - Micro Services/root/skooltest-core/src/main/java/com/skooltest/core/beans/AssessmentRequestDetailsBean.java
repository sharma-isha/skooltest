package com.skooltest.core.beans;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import com.skooltest.entities.tables.beans.AssessmentRequestBean;

public class AssessmentRequestDetailsBean implements Serializable {

	private static final long serialVersionUID = -8783106835276914424L;

	private String status;

	private Set<AssessmentRequestBean> assessmentRequests;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<AssessmentRequestBean> getAssessmentRequests() {
		return assessmentRequests;
	}

	public void setAssessmentRequests(Set<AssessmentRequestBean> assessmentRequests) {
		this.assessmentRequests = assessmentRequests;
	}

	@Override
	public String toString() {
		return "AssessmentRequestDetailsBean [status=" + status + ", assessmentRequests=" + assessmentRequests + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(assessmentRequests, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AssessmentRequestDetailsBean other = (AssessmentRequestDetailsBean) obj;
		return Objects.equals(assessmentRequests, other.assessmentRequests) && Objects.equals(status, other.status);
	}
}
