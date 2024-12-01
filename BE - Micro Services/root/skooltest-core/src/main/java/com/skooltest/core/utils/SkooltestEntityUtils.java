package com.skooltest.core.utils;

import static com.skooltest.common.constants.CommonConstants.ACTIVE;
import static com.skooltest.common.constants.CommonConstants.ZERO_STRING;
import static com.skooltest.common.constants.ExceptionConstants.ASSESSMENT_DETAIL_NOT_FOUND;
import static com.skooltest.common.constants.ExceptionConstants.ASSESSMENT_NOT_FOUND;
import static com.skooltest.common.constants.ExceptionConstants.QUESTION_NOT_FOUND;
import static com.skooltest.common.constants.ExceptionConstants.STATUS_NOT_FOUND;
import static com.skooltest.common.constants.ExceptionConstants.STUDENT_NOT_FOUND;
import static com.skooltest.common.constants.ExceptionConstants.SUBJECT_NOT_FOUND;
import static com.skooltest.common.constants.ExceptionConstants.TEACHER_NOT_FOUND;
import static com.skooltest.common.constants.ExceptionConstants.USER_NOT_FOUND;
import static com.skooltest.core.constants.MasterStatusConstants.ASSESSMENT_CREATED;
import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skooltest.common.exceptions.SkooltestCommonException;
import com.skooltest.common.utils.CommonUtils;
import com.skooltest.entities.repositories.AssessmentDetailRepository;
import com.skooltest.entities.repositories.AssessmentRequestRepository;
import com.skooltest.entities.repositories.MasterStatusRepository;
import com.skooltest.entities.repositories.MasterSubjectRepository;
import com.skooltest.entities.repositories.QuestionRepository;
import com.skooltest.entities.repositories.StudentDetailRepository;
import com.skooltest.entities.repositories.TeacherDetailRepository;
import com.skooltest.entities.repositories.UserRepository;
import com.skooltest.entities.tables.assessment.AssessmentDetail;
import com.skooltest.entities.tables.assessment.AssessmentRequest;
import com.skooltest.entities.tables.assessment.Question;
import com.skooltest.entities.tables.auth.User;
import com.skooltest.entities.tables.beans.AssessmentDetailBean;
import com.skooltest.entities.tables.beans.AssessmentRequestBean;
import com.skooltest.entities.tables.beans.QuestionBean;
import com.skooltest.entities.tables.masters.MasterStatus;
import com.skooltest.entities.tables.masters.MasterSubject;
import com.skooltest.entities.tables.users.StudentDetail;
import com.skooltest.entities.tables.users.TeacherDetail;

@Component
public class SkooltestEntityUtils {

	@Autowired
	private MasterSubjectRepository masterSubjectRepository;

	@Autowired
	private AssessmentRequestRepository assessmentRequestRepository;

	@Autowired
	private StudentDetailRepository studentDetailRepository;

	@Autowired
	private MasterStatusRepository masterStatusRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TeacherDetailRepository teacherDetailRepository;

	@Autowired
	private AssessmentDetailRepository assessmentDetailRepository;

	@Autowired
	private QuestionRepository questionRepository;

	public StudentDetail getStudentById(Integer studentId) {
		return studentDetailRepository.findByIdAndUser_IsActive(studentId, ACTIVE)
				.orElseThrow(() -> new SkooltestCommonException(STUDENT_NOT_FOUND));
	}

	public List<MasterSubject> getAllMasterSubjects() {
		return masterSubjectRepository.findAll();
	}

	public List<AssessmentRequest> getAssessmentRequestsOfStudent(StudentDetail student) {
		return assessmentRequestRepository.findByRequestedBy(student);
	}

	public List<AssessmentRequest> getAssessmentRequestsWithStatus(MasterStatus status) {
		return assessmentRequestRepository.findByMasterStatus(status);
	}

	public MasterStatus getMasterStatus(String stage) {
		return masterStatusRepository.findByCode(stage)
				.orElseThrow(() -> new SkooltestCommonException(STATUS_NOT_FOUND));
	}

	public User getUserByUserName(String userName) {
		return userRepository.findByUserNameAndIsActive(userName, ACTIVE)
				.orElseThrow(() -> new SkooltestCommonException(USER_NOT_FOUND));
	}

	public User getUserById(Integer userId) {
		return userRepository.findByIdAndIsActive(userId, ACTIVE)
				.orElseThrow(() -> new SkooltestCommonException(USER_NOT_FOUND));
	}

	public StudentDetail getStudentByUser(User user) {
		return studentDetailRepository.findByUser(user)
				.orElseThrow(() -> new SkooltestCommonException(STUDENT_NOT_FOUND));
	}

	public TeacherDetail getTeacherByUser(User user) {
		return teacherDetailRepository.findByUser(user)
				.orElseThrow(() -> new SkooltestCommonException(TEACHER_NOT_FOUND));
	}

	public MasterSubject getMasterSubject(String subject) {
		return masterSubjectRepository.findByName(subject)
				.orElseThrow(() -> new SkooltestCommonException(SUBJECT_NOT_FOUND));
	}

	public AssessmentRequest createAssessmentRequest(Integer studentId, AssessmentRequestBean assessmentRequestBean)
			throws ParseException {
		StudentDetail student = getStudentById(studentId);
		MasterStatus status = getMasterStatus(ASSESSMENT_CREATED);
		MasterSubject subject = getMasterSubject(assessmentRequestBean.getSubject());
		AssessmentRequest request = new AssessmentRequest();
		request.setMasterStatus(status);
		request.setMasterSubject(subject);
		request.setComments(assessmentRequestBean.getComments());
		request.setRequestedBy(student);
		request.setRequestedDate(new Date());
		request.setTopic(assessmentRequestBean.getTopic());
		return assessmentRequestRepository.save(request);
	}

	public List<StudentDetail> getAllActiveStudents() {
		return studentDetailRepository.findAllByUser_IsActive(ACTIVE);
	}

	public List<TeacherDetail> getAllActiveTeachers() {
		return teacherDetailRepository.findAllByUser_IsActive(ACTIVE);
	}

	public TeacherDetail getTeacherById(Integer teacherId) {
		return teacherDetailRepository.findById(teacherId)
				.orElseThrow(() -> new SkooltestCommonException(TEACHER_NOT_FOUND));
	}

	public AssessmentRequest getAssessmentRequestById(Integer assessmentId) {
		return assessmentRequestRepository.findById(assessmentId)
				.orElseThrow(() -> new SkooltestCommonException(ASSESSMENT_NOT_FOUND));
	}

	public AssessmentRequest saveAssessmentRequest(AssessmentRequest request) {
		return assessmentRequestRepository.save(request);
	}

	public AssessmentDetail createOrModifyAssessmentDetail(Integer assessmentRequestId,
			AssessmentDetailBean assessmentDetailBean) throws ParseException {
		AssessmentDetail assessmentDetail;
		if (Objects.nonNull(assessmentDetailBean.getAssessmentDetailId())) {
			assessmentDetail = getAssessmentDetailById(assessmentDetailBean.getAssessmentDetailId());
		} else {
			assessmentDetail = new AssessmentDetail();
			assessmentDetail.setAssessmentRequest(getAssessmentRequestById(assessmentRequestId));
			assessmentDetail.setAssessmentCode(createAssessmentCode(assessmentDetail.getAssessmentRequest()));
		}
		assessmentDetail.setDueDate(CommonUtils.getDate(assessmentDetailBean.getDueDate()));
		assessmentDetail.setInstructions(assessmentDetailBean.getInstructions());
		assessmentDetail.setMaxMarks(assessmentDetailBean.getMaxMarks());
		assessmentDetail.setScoredMarks(assessmentDetailBean.getScoredMarks());
		assessmentDetail.setTitle(assessmentDetailBean.getTitle());
		return assessmentDetailRepository.save(assessmentDetail);
	}

	private AssessmentDetail getAssessmentDetailById(Integer assessmentDetailId) {
		return assessmentDetailRepository.findById(assessmentDetailId)
				.orElseThrow(() -> new SkooltestCommonException(ASSESSMENT_DETAIL_NOT_FOUND));
	}

	private String createAssessmentCode(AssessmentRequest assessmentRequest) {
		MasterSubject subject = assessmentRequest.getMasterSubject();
		String subjectCode = subject.getCode();
		String suffix = assessmentRequest.getId().toString();
		String prefix = StringUtils.repeat(ZERO_STRING, 5 - suffix.length());
		return subjectCode.concat(prefix).concat(suffix);
	}

	public AssessmentDetail getAssessDetailByRequestId(Integer assessmentRequestId) {
		return assessmentDetailRepository.findByAssessmentRequest_Id(assessmentRequestId);
	}

	public List<Question> createOrModifyQuestions(List<QuestionBean> questionBeans, AssessmentDetail assessmentDetail) {
		List<Question> questions = new ArrayList<Question>();
		questionBeans.stream().forEach(questionBean -> {
			Question question;
			if (Objects.nonNull(questionBean.getQuestionId())) {
				question = getQuestionById(questionBean.getQuestionId());
			} else {
				question = new Question();
				question.setAssessmentDetail(assessmentDetail);
			}
			question.setComments(questionBean.getComments());
			question.setContent(questionBean.getContent());
			question.setMaxMarks(questionBean.getMaxMarks());
			question.setScoredMarks(
					Objects.isNull(question.getScoredMarks()) ? DOUBLE_ZERO : question.getScoredMarks());
			questions.add(question);
		});
		return questions;
	}

	private Question getQuestionById(Integer questionId) {
		return questionRepository.findById(questionId)
				.orElseThrow(() -> new SkooltestCommonException(QUESTION_NOT_FOUND));
	}

	public void saveQuestions(List<Question> questions) {
		questionRepository.saveAll(questions);
	}

	public List<AssessmentRequest> getAssessmentRequestsOfStudent(Integer studentId) {
		return assessmentRequestRepository.findByRequestedBy_Id(studentId);
	}

	public List<AssessmentRequest> getAssessmentRequestsOfTeacher(Integer teacherId) {
		return assessmentRequestRepository.findAssessmentRequestsOfTeacher(teacherId);
	}

	public List<AssessmentRequest> getAllAssessmentRequests() {
		return assessmentRequestRepository.findAll();
	}

	public void deleteQuestions(List<Question> toBeDeletedQuestions) {
		questionRepository.deleteAll(toBeDeletedQuestions);
	}
}
