package com.skooltest.core.utils;

import static com.skooltest.common.constants.CommonConstants.ADMIN_USER;
import static com.skooltest.common.constants.CommonConstants.STUDENT_USER;
import static com.skooltest.common.constants.CommonConstants.TEACHER_USER;

import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skooltest.common.utils.CommonUtils;
import com.skooltest.entities.tables.assessment.AssessmentDetail;
import com.skooltest.entities.tables.assessment.AssessmentRequest;
import com.skooltest.entities.tables.assessment.Question;
import com.skooltest.entities.tables.auth.User;
import com.skooltest.entities.tables.beans.AssessmentDetailBean;
import com.skooltest.entities.tables.beans.AssessmentRequestBean;
import com.skooltest.entities.tables.beans.QuestionBean;
import com.skooltest.entities.tables.beans.UserBean;
import com.skooltest.entities.tables.users.StudentDetail;
import com.skooltest.entities.tables.users.TeacherDetail;

@Component
public class SkooltestBeanOperationUtils {

	@Autowired
	private SkooltestEntityUtils skooltestEntityUtils;

	public UserBean mapToUserBean(StudentDetail studentDetail) {
		UserBean userBean = new UserBean();
		mapToUserBean(userBean, studentDetail.getUser());
		userBean.setStudentId(studentDetail.getId());
		userBean.setUserType(STUDENT_USER);
		return userBean;
	}

	public UserBean mapToUserBean(TeacherDetail teacherDetail) {
		UserBean userBean = new UserBean();
		mapToUserBean(userBean, teacherDetail.getUser());
		userBean.setTeacherId(teacherDetail.getId());
		userBean.setUserType(TEACHER_USER);
		return userBean;
	}

	public AssessmentRequestBean mapToAssessmentRequestBean(AssessmentRequest assessmentRequest) {
		AssessmentRequestBean assessmentRequestBean = new AssessmentRequestBean();
		assessmentRequestBean.setAssessmentRequestId(assessmentRequest.getId());
		assessmentRequestBean.setSubject(assessmentRequest.getMasterSubject().getName());
		assessmentRequestBean.setTopic(assessmentRequest.getTopic());
		assessmentRequestBean.setComments(assessmentRequest.getComments());
		assessmentRequestBean.setRequestedBy(mapToUserBean(assessmentRequest.getRequestedBy()));
		assessmentRequestBean.setAssignedTo(
				Objects.nonNull(assessmentRequest.getAssignedTo()) ? mapToUserBean(assessmentRequest.getAssignedTo())
						: null);
		assessmentRequestBean.setEvaluatedBy(
				Objects.nonNull(assessmentRequest.getEvaluatedBy()) ? mapToUserBean(assessmentRequest.getEvaluatedBy())
						: null);
		assessmentRequestBean.setStatus(assessmentRequest.getMasterStatus().getCode());
		assessmentRequestBean.setRequestedDate(CommonUtils.getDate(assessmentRequest.getRequestedDate()));
		AssessmentDetail assessmentDetail = skooltestEntityUtils.getAssessDetailByRequestId(assessmentRequest.getId());
		if (Objects.nonNull(assessmentDetail)) {
			assessmentRequestBean.setAssessmentDetail(mapToAssessmentDetailBean(assessmentDetail));
		}
		return assessmentRequestBean;
	}

	public AssessmentDetailBean mapToAssessmentDetailBean(AssessmentDetail assessmentDetail) {
		AssessmentDetailBean assessmentDetailBean = new AssessmentDetailBean();
		assessmentDetailBean.setAssessmentDetailId(assessmentDetail.getId());
		assessmentDetailBean.setAssessmentCode(assessmentDetail.getAssessmentCode());
		assessmentDetailBean.setAssessmentRequestId(assessmentDetail.getAssessmentRequest().getId());
		assessmentDetailBean.setDueDate(CommonUtils.getDate(assessmentDetail.getDueDate()));
		assessmentDetailBean.setInstructions(assessmentDetail.getInstructions());
		assessmentDetailBean.setMaxMarks(assessmentDetail.getMaxMarks());
		assessmentDetailBean.setScoredMarks(assessmentDetail.getScoredMarks());
		assessmentDetailBean.setTitle(assessmentDetail.getTitle());
		if (CollectionUtils.isNotEmpty(assessmentDetail.getQuestions())) {
			assessmentDetailBean.setQuestions(assessmentDetail.getQuestions().stream()
					.map(question -> mapToQuestionBean(question)).collect(Collectors.toList()));
		}
		return assessmentDetailBean;
	}

	public QuestionBean mapToQuestionBean(Question question) {
		QuestionBean questionBean = new QuestionBean();
		questionBean.setComments(question.getComments());
		questionBean.setContent(question.getContent());
		questionBean.setMaxMarks(question.getMaxMarks());
		questionBean.setScoredMarks(question.getScoredMarks());
		questionBean.setQuestionId(question.getId());
		return questionBean;
	}

	public void mapToUserBean(UserBean userBean, User user) {
		userBean.setFirstName(user.getFirstName());
		userBean.setLastName(user.getLastName());
		userBean.setUserName(user.getUserName());
	}

	public UserBean mapToAdminUserBean(User user) {
		UserBean userBean = new UserBean();
		mapToUserBean(userBean, user);
		userBean.setUserType(ADMIN_USER);
		return userBean;
	}

	public AssessmentRequestBean mapToAssessmentRequestBeanForStudent(AssessmentRequest assessmentRequest) {
		AssessmentRequestBean assessmentRequestBean = new AssessmentRequestBean();
		assessmentRequestBean.setAssessmentRequestId(assessmentRequest.getId());
		assessmentRequestBean.setSubject(assessmentRequest.getMasterSubject().getName());
		assessmentRequestBean.setTopic(assessmentRequest.getTopic());
		assessmentRequestBean.setComments(assessmentRequest.getComments());
		assessmentRequestBean.setRequestedBy(mapToUserBean(assessmentRequest.getRequestedBy()));
		assessmentRequestBean.setAssignedTo(
				Objects.nonNull(assessmentRequest.getAssignedTo()) ? mapToUserBean(assessmentRequest.getAssignedTo())
						: null);
		assessmentRequestBean.setEvaluatedBy(
				Objects.nonNull(assessmentRequest.getEvaluatedBy()) ? mapToUserBean(assessmentRequest.getEvaluatedBy())
						: null);
		assessmentRequestBean.setStatus(assessmentRequest.getMasterStatus().getCode());
		assessmentRequestBean.setRequestedDate(CommonUtils.getDate(assessmentRequest.getRequestedDate()));
		AssessmentDetail assessmentDetail = skooltestEntityUtils.getAssessDetailByRequestId(assessmentRequest.getId());
		if (Objects.nonNull(assessmentDetail)) {
			assessmentRequestBean.setAssessmentDetail(mapToAssessmentDetailBeanForStudent(assessmentDetail));
		}
		return assessmentRequestBean;
	}

	private AssessmentDetailBean mapToAssessmentDetailBeanForStudent(AssessmentDetail assessmentDetail) {
		AssessmentDetailBean assessmentDetailBean = new AssessmentDetailBean();
		assessmentDetailBean.setAssessmentDetailId(assessmentDetail.getId());
		assessmentDetailBean.setAssessmentCode(assessmentDetail.getAssessmentCode());
		assessmentDetailBean.setAssessmentRequestId(assessmentDetail.getAssessmentRequest().getId());
		assessmentDetailBean.setDueDate(CommonUtils.getDate(assessmentDetail.getDueDate()));
		assessmentDetailBean.setInstructions(assessmentDetail.getInstructions());
		assessmentDetailBean.setMaxMarks(assessmentDetail.getMaxMarks());
		assessmentDetailBean.setScoredMarks(assessmentDetail.getScoredMarks());
		assessmentDetailBean.setTitle(assessmentDetail.getTitle());
		return assessmentDetailBean;
	}
}
