package com.skooltest.core.admin.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skooltest.common.beans.ResponseResource;
import com.skooltest.core.admin.service.AdminService;
import com.skooltest.core.user.service.AssessmentService;
import com.skooltest.entities.tables.beans.AssessmentRequestBean;
import com.skooltest.entities.tables.beans.UserBean;

@RestController
@RequestMapping("/admin")
public class SkooltestAdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private AssessmentService assessmentService;

	@GetMapping(value = "/students")
	public ResponseResource<Set<UserBean>> getAllStudents() {
		Set<UserBean> response = adminService.getAllStudents();
		return new ResponseResource<>(response);
	}

	@GetMapping(value = "/teachers")
	public ResponseResource<Set<UserBean>> getAllTeachers() {
		Set<UserBean> response = adminService.getAllTeachers();
		return new ResponseResource<>(response);
	}

	@GetMapping(value = "/assessmentRequests")
	public ResponseResource<List<AssessmentRequestBean>> getAllAssessmentRequests(
			@RequestParam(value = "status") String status) {
		List<AssessmentRequestBean> response = assessmentService.fetchOpenAssessmentRequestsWithStatus(status);
		return new ResponseResource<>(response);
	}

	@PostMapping(value = "/assessmentRequest/{assessmentId}/teacher/{teacherId}/assign")
	public ResponseResource<AssessmentRequestBean> assignAssessmentToTeacher(
			@PathVariable(value = "assessmentId") Integer assessmentId,
			@PathVariable(value = "teacherId") Integer teacherId) {
		AssessmentRequestBean response = adminService.assignAssessmentToTeacher(assessmentId, teacherId);
		return new ResponseResource<>(response);
	}
}
