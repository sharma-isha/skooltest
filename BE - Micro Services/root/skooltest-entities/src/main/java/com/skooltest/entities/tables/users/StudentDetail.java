package com.skooltest.entities.tables.users;

import java.util.Date;
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
@Table(name = "student_detail")
public class StudentDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	private String imageUrl;
	private Date dateOfBirth;

	@OneToMany(mappedBy = "requestedBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AssessmentRequest> assessmentRequests;

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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<AssessmentRequest> getAssessmentRequests() {
		return assessmentRequests;
	}

	public void setAssessmentRequests(List<AssessmentRequest> assessmentRequests) {
		this.assessmentRequests = assessmentRequests;
	}

}
