package com.skooltest.core.admin.service;

import static com.skooltest.core.constants.MasterStatusConstants.ASSESSMENT_ASSIGNED;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skooltest.core.utils.SkooltestBeanOperationUtils;
import com.skooltest.core.utils.SkooltestEntityUtils;
import com.skooltest.entities.tables.assessment.AssessmentRequest;
import com.skooltest.entities.tables.beans.AssessmentRequestBean;
import com.skooltest.entities.tables.beans.UserBean;
import com.skooltest.entities.tables.masters.MasterStatus;
import com.skooltest.entities.tables.users.StudentDetail;
import com.skooltest.entities.tables.users.TeacherDetail;

@Service
public class AdminService {

	@Autowired
	private SkooltestBeanOperationUtils beanOperationUtils;

	@Autowired
	private SkooltestEntityUtils skooltestEntityUtils;

	public Set<UserBean> getAllStudents() {
		List<StudentDetail> students = skooltestEntityUtils.getAllActiveStudents();
		return students.stream().map(student -> beanOperationUtils.mapToUserBean(student)).collect(Collectors.toSet());
	}

	public Set<UserBean> getAllTeachers() {
		List<TeacherDetail> teachers = skooltestEntityUtils.getAllActiveTeachers();
		return teachers.stream().map(teacher -> beanOperationUtils.mapToUserBean(teacher)).collect(Collectors.toSet());
	}

	public AssessmentRequestBean assignAssessmentToTeacher(Integer assessmentId, Integer teacherId) {
		Objects.requireNonNull(assessmentId);
		Objects.requireNonNull(teacherId);
		TeacherDetail teacher = skooltestEntityUtils.getTeacherById(teacherId);
		AssessmentRequest request = skooltestEntityUtils.getAssessmentRequestById(assessmentId);
		MasterStatus status = skooltestEntityUtils.getMasterStatus(ASSESSMENT_ASSIGNED);
		request.setAssignedTo(teacher);
		request.setMasterStatus(status);
		return beanOperationUtils.mapToAssessmentRequestBean(skooltestEntityUtils.saveAssessmentRequest(request));
	}
}
