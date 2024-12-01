package com.skooltest.entities.tables.users;

import java.util.List;

import com.skooltest.entities.tables.assessment.AssessmentRequest;
import com.skooltest.entities.tables.auth.User;

import jakarta.persistence.CascadeType;
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
@Table(name = "teacher_detail")
public class TeacherDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "assignedTo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AssessmentRequest> assignedAssessments;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<AssessmentRequest> getAssignedAssessments() {
		return assignedAssessments;
	}

	public void setAssignedAssessments(List<AssessmentRequest> assignedAssessments) {
		this.assignedAssessments = assignedAssessments;
	}
	
	
}
