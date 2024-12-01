package com.skooltest.core.user.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skooltest.core.utils.SkooltestEntityUtils;
import com.skooltest.entities.tables.masters.MasterSubject;
import com.skooltest.entities.tables.users.StudentDetail;

@Service
public class StudentService {

	@Autowired
	SkooltestEntityUtils skooltestEntityUtils;

	@Autowired
	private AssessmentService assessmentService;

	public Set<String> getApplicableSubjectsForAssessmentRequest(Integer studentId) {
		StudentDetail student = skooltestEntityUtils.getStudentById(studentId);
		List<MasterSubject> allSubjects = skooltestEntityUtils.getAllMasterSubjects();
		List<String> allOpenAssessmentSubjects = assessmentService.fetchOpenAssessmentRequests(student).stream()
				.map(assessment -> assessment.getSubject()).collect(Collectors.toList());
		return allSubjects.stream().map(subject -> subject.getName())
				.filter(subject -> !allOpenAssessmentSubjects.contains(subject)).collect(Collectors.toSet());
	}
	
	public Set<String> getApplicableSubjectsForAssessmentRequest(StudentDetail studentDetail) {
		List<MasterSubject> allSubjects = skooltestEntityUtils.getAllMasterSubjects();
		Set<String> allOpenAssessmentSubjects = assessmentService.fetchOpenAssessmentRequests(studentDetail).stream()
				.map(assessment -> assessment.getSubject()).collect(Collectors.toSet());
		return allSubjects.stream().map(subject -> subject.getName())
				.filter(subject -> !allOpenAssessmentSubjects.contains(subject)).collect(Collectors.toSet());
	}

}
