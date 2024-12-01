package com.skooltest.core.user.service;

import static com.skooltest.core.constants.MasterStatusConstants.ANSWER_SHEET_EVALUATION;
import static com.skooltest.core.constants.MasterStatusConstants.ASSESSMENT_ASSIGNED;
import static com.skooltest.core.constants.MasterStatusConstants.ASSESSMENT_COMPLETED;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;
import com.skooltest.core.utils.SkooltestBeanOperationUtils;
import com.skooltest.core.utils.SkooltestEntityUtils;
import com.skooltest.entities.tables.assessment.AssessmentDetail;
import com.skooltest.entities.tables.assessment.AssessmentRequest;
import com.skooltest.entities.tables.assessment.Question;
import com.skooltest.entities.tables.beans.AssessmentDetailBean;
import com.skooltest.entities.tables.beans.AssessmentRequestBean;
import com.skooltest.entities.tables.beans.QuestionBean;
import com.skooltest.entities.tables.masters.MasterStatus;
import com.skooltest.entities.tables.users.StudentDetail;

@Service
public class AssessmentService {

	@Autowired
	SkooltestEntityUtils skooltestEntityUtils;

	@Autowired
	SkooltestBeanOperationUtils skooltestBeanOperationUtils;

	public List<AssessmentRequestBean> fetchOpenAssessmentRequests(StudentDetail student) {
		List<AssessmentRequest> allStudentAssessmentRequests = skooltestEntityUtils
				.getAssessmentRequestsOfStudent(student);
		List<AssessmentRequestBean> openAssessmentRequests = allStudentAssessmentRequests.stream()
				.filter(assessment -> !ASSESSMENT_COMPLETED.equalsIgnoreCase(assessment.getMasterSubject().getName()))
				.map(assessment -> {
					return skooltestBeanOperationUtils.mapToAssessmentRequestBean(assessment);
				}).collect(Collectors.toList());
		return openAssessmentRequests;
	}

	public List<AssessmentRequestBean> fetchOpenAssessmentRequestsWithStatus(String status) {
		Objects.requireNonNull(status);
		MasterStatus masterStatus = skooltestEntityUtils.getMasterStatus(status);
		List<AssessmentRequestBean> assessmentRequests = skooltestEntityUtils
				.getAssessmentRequestsWithStatus(masterStatus).stream().map(assessment -> {
					return skooltestBeanOperationUtils.mapToAssessmentRequestBean(assessment);
				}).collect(Collectors.toList());
		return assessmentRequests;
	}

	public AssessmentRequestBean createAssessmentRequest(Integer studentId, AssessmentRequestBean assessmentRequestBean)
			throws ParseException {
		Objects.requireNonNull(studentId);
		Objects.requireNonNull(assessmentRequestBean);
		AssessmentRequest request = skooltestEntityUtils.createAssessmentRequest(studentId, assessmentRequestBean);
		return skooltestBeanOperationUtils.mapToAssessmentRequestBean(request);
	}

	public List<AssessmentRequestBean> fetchOpenAssessmentRequestsWithTeacherAndStatus(Integer teacherId,
			String status) {
		Objects.requireNonNull(teacherId);
		Objects.requireNonNull(status);
		skooltestEntityUtils.getTeacherById(teacherId);
		List<AssessmentRequestBean> assessmentRequests = fetchOpenAssessmentRequestsWithStatus(status);
		if (ASSESSMENT_ASSIGNED.equalsIgnoreCase(status)) {
			return assessmentRequests.stream().filter(
					req -> (Objects.nonNull(req.getAssignedTo()) && (teacherId == req.getAssignedTo().getTeacherId())))
					.collect(Collectors.toList());
		} else if (ANSWER_SHEET_EVALUATION.equalsIgnoreCase(status)) {
			return assessmentRequests.stream().filter(req -> (Objects.nonNull(req.getEvaluatedBy())
					&& (teacherId == req.getEvaluatedBy().getTeacherId()))).collect(Collectors.toList());
		} else {
			return new ArrayList<AssessmentRequestBean>();
		}
	}

	public AssessmentDetailBean createOrModifyAssessmentDetail(Integer assessmentRequestId,
			AssessmentDetailBean assessmentDetailBean) throws ParseException {
		Objects.requireNonNull(assessmentRequestId);
		Objects.requireNonNull(assessmentDetailBean);
		calculateTotalMarks(assessmentDetailBean);
		AssessmentDetail assessmentDetail = skooltestEntityUtils.createOrModifyAssessmentDetail(assessmentRequestId,
				assessmentDetailBean);
		if (CollectionUtils.isNotEmpty(assessmentDetailBean.getQuestions())) {
			checkAndDeleteQuestions(assessmentDetail.getQuestions(), assessmentDetailBean.getQuestions());
			List<Question> questions = skooltestEntityUtils.createOrModifyQuestions(assessmentDetailBean.getQuestions(),
					assessmentDetail);
			skooltestEntityUtils.saveQuestions(questions);
		}
		return skooltestBeanOperationUtils.mapToAssessmentDetailBean(assessmentDetail);
	}

	private void checkAndDeleteQuestions(List<Question> questions, List<QuestionBean> questionBeans) {
		Set<Integer> existingQuestionIds = questions.stream().map(question -> question.getId())
				.collect(Collectors.toSet());
		Set<Integer> toBeUpdatedQuestionIds = questionBeans.stream()
				.filter(questionBean -> Objects.nonNull(questionBean.getQuestionId()))
				.map(questionBean -> questionBean.getQuestionId()).collect(Collectors.toSet());
		Set<Integer> toBeDeletedIds = Sets.difference(existingQuestionIds, toBeUpdatedQuestionIds);
		List<Question> toBeDeletedQuestions = questions.stream()
				.filter(question -> toBeDeletedIds.contains(question.getId())).collect(Collectors.toList());
		skooltestEntityUtils.deleteQuestions(toBeDeletedQuestions);
	}

	private void calculateTotalMarks(AssessmentDetailBean assessmentDetailBean) {
		double totalMarks = ListUtils.emptyIfNull(assessmentDetailBean.getQuestions()).stream()
				.filter(question -> Objects.nonNull(question.getMaxMarks()))
				.mapToDouble(question -> question.getMaxMarks()).sum();
		assessmentDetailBean.setMaxMarks(totalMarks);
	}

	public AssessmentDetailBean getAssessmentDetail(Integer assessmentRequestId) {
		Objects.requireNonNull(assessmentRequestId);
		AssessmentDetail assessmentDetail = skooltestEntityUtils.getAssessDetailByRequestId(assessmentRequestId);
		return Objects.nonNull(assessmentDetail)
				? skooltestBeanOperationUtils.mapToAssessmentDetailBean(assessmentDetail)
				: null;
	}

	public AssessmentRequestBean updateStageOfAssessmentRequest(Integer assessmentRequestId, String status) {
		Objects.requireNonNull(assessmentRequestId);
		Objects.requireNonNull(status);
		AssessmentRequest assessmentRequest = skooltestEntityUtils.getAssessmentRequestById(assessmentRequestId);
		MasterStatus masterStatus = skooltestEntityUtils.getMasterStatus(status);
		assessmentRequest.setMasterStatus(masterStatus);
		assessmentRequest = skooltestEntityUtils.saveAssessmentRequest(assessmentRequest);
		return skooltestBeanOperationUtils.mapToAssessmentRequestBean(assessmentRequest);
	}
}
