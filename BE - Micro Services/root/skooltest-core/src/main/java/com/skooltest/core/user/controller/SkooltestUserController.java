package com.skooltest.core.user.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skooltest.common.beans.ResponseResource;
import com.skooltest.core.beans.DashboardDataBean;
import com.skooltest.core.user.service.AssessmentService;
import com.skooltest.core.user.service.StudentService;
import com.skooltest.core.user.service.UserService;
import com.skooltest.entities.tables.beans.AssessmentDetailBean;
import com.skooltest.entities.tables.beans.AssessmentRequestBean;
import com.skooltest.entities.tables.beans.UserBean;

@RestController
@RequestMapping("/user")
public class SkooltestUserController {

	@Autowired
	private StudentService studentService;

	@Autowired
	private UserService userService;

	@Autowired
	private AssessmentService assessmentService;

	@GetMapping(value = "/student/{studentId}/subjects")
	public ResponseResource<Set<String>> getApplicableSubjectsForAssessmentRequest(
			@PathVariable(value = "studentId") Integer studentId) {
		Set<String> response = studentService.getApplicableSubjectsForAssessmentRequest(studentId);
		return new ResponseResource<>(response);
	}

	@GetMapping(value = "/getDashboardData")
	public ResponseResource<DashboardDataBean> getDashboardData(@RequestParam("userName") String userName) {
		DashboardDataBean response = userService.getDashboardData(userName);
		return new ResponseResource<>(response);
	}

	@GetMapping(value = "/getUser")
	public ResponseResource<UserBean> getUserDetailsByUserName(@RequestParam(value = "userName") String userName) {
		UserBean response = userService.getUserDetails(userName);
		return new ResponseResource<>(response);
	}

	@PostMapping(value = "/student/{studentId}/assessmentRequest/create")
	public ResponseResource<AssessmentRequestBean> createAssessmentRequest(
			@PathVariable(value = "studentId") Integer studentId,
			@RequestBody AssessmentRequestBean assessmentRequestBean) throws ParseException {
		AssessmentRequestBean response = assessmentService.createAssessmentRequest(studentId, assessmentRequestBean);
		return new ResponseResource<>(response);
	}

	@GetMapping(value = "/teacher/{teacherId}/assessmentRequests")
	public ResponseResource<List<AssessmentRequestBean>> fetchOpenAssessmentRequestsForATeacher(
			@PathVariable(value = "teacherId") Integer teacherId, @RequestParam(value = "status") String status) {
		List<AssessmentRequestBean> response = assessmentService
				.fetchOpenAssessmentRequestsWithTeacherAndStatus(teacherId, status);
		return new ResponseResource<>(response);
	}

	@PostMapping(value = "/assessmentRequest/{assessmentRequestId}/assessmentDetail/createOrModify")
	public ResponseResource<AssessmentDetailBean> createOrModifyAssessmentDetail(
			@PathVariable(value = "assessmentRequestId") Integer assessmentRequestId,
			@RequestBody AssessmentDetailBean assessmentDetailBean) throws ParseException {
		AssessmentDetailBean response = assessmentService.createOrModifyAssessmentDetail(assessmentRequestId,
				assessmentDetailBean);
		return new ResponseResource<>(response);
	}

	@GetMapping(value = "/assessmentRequest/{assessmentRequestId}/assessmentDetail")
	public ResponseResource<AssessmentDetailBean> getAssessmentDetail(
			@PathVariable(value = "assessmentRequestId") Integer assessmentRequestId) {
		AssessmentDetailBean response = assessmentService.getAssessmentDetail(assessmentRequestId);
		return new ResponseResource<>(response);
	}

	@PostMapping(value = "/assessmentRequest/{assessmentRequestId}/updateStatus")
	public ResponseResource<AssessmentRequestBean> updateStageOfAssessmentRequest(
			@PathVariable(value = "assessmentRequestId") Integer assessmentRequestId,
			@RequestParam(value = "status") String status) {
		AssessmentRequestBean response = assessmentService.updateStageOfAssessmentRequest(assessmentRequestId, status);
		return new ResponseResource<>(response);
	}
}
