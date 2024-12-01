package com.skooltest.core.user.service;

import static com.skooltest.common.constants.CommonConstants.ADMIN_USER;
import static com.skooltest.common.constants.CommonConstants.STUDENT_USER;
import static com.skooltest.common.constants.CommonConstants.TEACHER_USER;
import static com.skooltest.core.constants.MasterStatusConstants.TEACHER_STATUS_CONSTANTS;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skooltest.core.admin.service.AdminService;
import com.skooltest.core.beans.AssessmentRequestDetailsBean;
import com.skooltest.core.beans.DashboardDataBean;
import com.skooltest.core.utils.SkooltestBeanOperationUtils;
import com.skooltest.core.utils.SkooltestEntityUtils;
import com.skooltest.entities.tables.assessment.AssessmentRequest;
import com.skooltest.entities.tables.auth.User;
import com.skooltest.entities.tables.beans.UserBean;

@Service
public class UserService {

	@Autowired
	private SkooltestEntityUtils skooltestEntityUtils;

	@Autowired
	private SkooltestBeanOperationUtils skooltestBeanOperationUtils;

	@Autowired
	private StudentService studentService;

	@Autowired
	private AdminService adminService;

	public UserBean getUserDetails(String userName) {
		Objects.requireNonNull(userName);
		User user = skooltestEntityUtils.getUserByUserName(userName);
		Set<String> roles = user.getRole().stream().map(role -> role.getRoleName()).collect(Collectors.toSet());
		if (roles.contains(ADMIN_USER)) {
			return skooltestBeanOperationUtils.mapToAdminUserBean(user);
		} else if (roles.contains(STUDENT_USER)) {
			return skooltestBeanOperationUtils.mapToUserBean(skooltestEntityUtils.getStudentByUser(user));
		} else if (roles.contains(TEACHER_USER)) {
			return skooltestBeanOperationUtils.mapToUserBean(skooltestEntityUtils.getTeacherByUser(user));
		}
		return null;
	}

	public DashboardDataBean getDashboardData(String userName) {
		Objects.requireNonNull(userName);
		DashboardDataBean dashboardDataBean = new DashboardDataBean();
		UserBean user = getUserDetails(userName);
		dashboardDataBean.setUser(user);
		if (ADMIN_USER.equalsIgnoreCase(user.getUserType())) {
			dashboardDataBean.setStudents(adminService.getAllStudents());
			dashboardDataBean.setTeachers(adminService.getAllTeachers());
			Map<String, List<AssessmentRequest>> statusRequestMap = skooltestEntityUtils.getAllAssessmentRequests()
					.stream()
					.collect(Collectors.groupingBy(assessmentRequest -> assessmentRequest.getMasterStatus().getCode()));
			dashboardDataBean.setAssessmentRequestDetails(setAssessmentRequestDetailsBean(statusRequestMap));
		} else if (STUDENT_USER.equalsIgnoreCase(user.getUserType())) {
			dashboardDataBean
					.setMasterSubjects(studentService.getApplicableSubjectsForAssessmentRequest(user.getStudentId()));
			Map<String, List<AssessmentRequest>> statusRequestMap = skooltestEntityUtils
					.getAssessmentRequestsOfStudent(user.getStudentId()).stream()
					.collect(Collectors.groupingBy(assessmentRequest -> assessmentRequest.getMasterStatus().getCode()));
			dashboardDataBean.setAssessmentRequestDetails(setAssessmentRequestDetailsBeanForStudent(statusRequestMap));
		} else if (TEACHER_USER.equalsIgnoreCase(user.getUserType())) {
			Map<String, List<AssessmentRequest>> statusRequestMap = skooltestEntityUtils
					.getAssessmentRequestsOfTeacher(user.getTeacherId()).stream()
					.filter(assessmentRequest -> TEACHER_STATUS_CONSTANTS
							.contains(assessmentRequest.getMasterStatus().getCode()))
					.collect(Collectors.groupingBy(assessmentRequest -> assessmentRequest.getMasterStatus().getCode()));
			dashboardDataBean.setAssessmentRequestDetails(setAssessmentRequestDetailsBean(statusRequestMap));
		}
		return dashboardDataBean;
	}

	private Set<AssessmentRequestDetailsBean> setAssessmentRequestDetailsBeanForStudent(
			Map<String, List<AssessmentRequest>> statusRequestMap) {
		Set<AssessmentRequestDetailsBean> details = new HashSet<>();
		statusRequestMap.entrySet().stream().forEach(status -> {
			AssessmentRequestDetailsBean detailsBean = new AssessmentRequestDetailsBean();
			detailsBean.setStatus(status.getKey());
			detailsBean
					.setAssessmentRequests(status.getValue().stream()
							.map(assessmentRequest -> skooltestBeanOperationUtils
									.mapToAssessmentRequestBeanForStudent(assessmentRequest))
							.collect(Collectors.toSet()));
			details.add(detailsBean);
		});
		return details;
	}

	private Set<AssessmentRequestDetailsBean> setAssessmentRequestDetailsBean(
			Map<String, List<AssessmentRequest>> statusRequestMap) {
		Set<AssessmentRequestDetailsBean> details = new HashSet<>();
		statusRequestMap.entrySet().stream().forEach(status -> {
			AssessmentRequestDetailsBean detailsBean = new AssessmentRequestDetailsBean();
			detailsBean.setStatus(status.getKey());
			detailsBean.setAssessmentRequests(status.getValue().stream()
					.map(assessmentRequest -> skooltestBeanOperationUtils.mapToAssessmentRequestBean(assessmentRequest))
					.collect(Collectors.toSet()));
			details.add(detailsBean);
		});
		return details;
	}
}
