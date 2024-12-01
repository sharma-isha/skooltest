package com.skooltest.attachment.user.service;

import static com.skooltest.common.constants.CommonConstants.ANSWER_SHEET;
import static com.skooltest.common.constants.CommonConstants.ATTACHMENT_HEADER;
import static com.skooltest.common.constants.CommonConstants.BACK_SLASH;
import static com.skooltest.common.constants.CommonConstants.CONTENT_DISPOSITION;
import static com.skooltest.common.constants.CommonConstants.EXPIRES;
import static com.skooltest.common.constants.CommonConstants.HYPHEN;
import static com.skooltest.common.constants.CommonConstants.PDF_EXTENSION;
import static com.skooltest.common.constants.CommonConstants.REGEX_SPACE_COMMA;
import static com.skooltest.common.constants.CommonConstants.SUCCESS;
import static com.skooltest.common.constants.CommonConstants.ZERO_STRING;
import static com.skooltest.common.constants.ExceptionConstants.ASSESSMENT_DETAIL_NOT_FOUND;
import static com.skooltest.common.constants.ExceptionConstants.ATTACHMENT_NOT_FOUND;
import static com.skooltest.common.constants.ExceptionConstants.DOCUMENT_PROCESSING_ERROR;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import com.skooltest.attachment.beans.QuestionPaperPdfBean;
import com.skooltest.attachment.utils.SkooltestEntityUtils;
import com.skooltest.common.exceptions.SkooltestCommonException;
import com.skooltest.common.utils.CommonUtils;
import com.skooltest.entities.tables.assessment.AssessmentDetail;
import com.skooltest.entities.tables.assessment.AssessmentRequest;
import com.skooltest.entities.tables.assessment.Question;
import com.skooltest.entities.tables.attachment.Attachment;
import com.skooltest.entities.tables.beans.QuestionBean;

import jakarta.servlet.http.HttpServletResponse;

@Service
@Transactional
public class SkooltestAttachmentService {

	@Autowired
	private SkooltestEntityUtils skooltestEntityUtils;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private SpringTemplateEngine templateEngine;

	@Autowired
	private FileStorageService fileStorageService;

	public String processQuestionPaperPdf(Integer assessmentRequestId, HttpServletResponse servletResponse) {
		try {
			Objects.requireNonNull(assessmentRequestId);
			AssessmentDetail assessmentDetail = skooltestEntityUtils.getAssessDetailByRequestId(assessmentRequestId);
			QuestionPaperPdfBean questionPaperBean = constructQuestionPaperBean(assessmentDetail);
			String templateHtml = processTemplate(questionPaperBean);
			generateQuestionPaper(questionPaperBean, templateHtml, servletResponse);
		} catch (DocumentException | IOException e) {
			throw new SkooltestCommonException(DOCUMENT_PROCESSING_ERROR);
		}
		return SUCCESS;
	}

	private void generateQuestionPaper(QuestionPaperPdfBean questionPaperBean, String templateHtml,
			HttpServletResponse servletResponse) throws DocumentException, IOException {
		String fileName = questionPaperBean.getAssessmentCode().concat("-").concat("Question Paper.pdf");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		createPdf(templateHtml, bos);
		generatePdfResponse(fileName, bos, servletResponse);
	}

	private void generatePdfResponse(String fileName, ByteArrayOutputStream bos, HttpServletResponse response)
			throws IOException {
		byte[] outArray = bos.toByteArray();
		response.reset();
		response.setContentType(MediaType.APPLICATION_PDF_VALUE);
		response.setContentLength(outArray.length);
		response.setHeader(EXPIRES, ZERO_STRING);
		response.setHeader(CONTENT_DISPOSITION, ATTACHMENT_HEADER.concat(fileName).concat(BACK_SLASH));
		FileCopyUtils.copy(outArray, response.getOutputStream());
		bos.flush();
		bos.close();
	}

	private void createPdf(String templateHtml, ByteArrayOutputStream bos) throws DocumentException, IOException {
		final ITextRenderer iTextRenderer = new ITextRenderer();
		iTextRenderer.setDocumentFromString(templateHtml);
		iTextRenderer.layout();
		iTextRenderer.createPDF(bos);
	}

	@SuppressWarnings("unchecked")
	private String processTemplate(QuestionPaperPdfBean questionPaperBean) {
		Map<String, Object> variable = objectMapper.convertValue(questionPaperBean, Map.class);
		Context contextVar = new Context();
		contextVar.setVariables(variable);
		return templateEngine.process("question_paper", contextVar);
	}

	private QuestionPaperPdfBean constructQuestionPaperBean(AssessmentDetail assessmentDetail) {
		QuestionPaperPdfBean pdfBean = new QuestionPaperPdfBean();
		pdfBean.setAssessmentCode(assessmentDetail.getAssessmentCode());
		pdfBean.setTitle(assessmentDetail.getTitle());
		pdfBean.setDueDate(CommonUtils.getDate(assessmentDetail.getDueDate()));
		pdfBean.setTotalMarks(assessmentDetail.getMaxMarks());
		pdfBean.setInstructions(constructInstructions(assessmentDetail));
		pdfBean.setQuestions(constructQuestions(assessmentDetail));
		return pdfBean;
	}

	private List<QuestionBean> constructQuestions(AssessmentDetail assessmentDetail) {
		if (CollectionUtils.isNotEmpty(assessmentDetail.getQuestions())) {
			return assessmentDetail.getQuestions().stream().map(question -> mapToQuestionBean(question))
					.collect(Collectors.toList());
		}
		return new ArrayList<QuestionBean>();
	}

	private List<String> constructInstructions(AssessmentDetail assessmentDetail) {
		if (StringUtils.isNotEmpty(assessmentDetail.getInstructions())) {
			return Arrays.asList(assessmentDetail.getInstructions().split(REGEX_SPACE_COMMA));
		}
		return new ArrayList<String>();
	}

	private QuestionBean mapToQuestionBean(Question question) {
		QuestionBean questionBean = new QuestionBean();
		questionBean.setContent(question.getContent());
		questionBean.setMaxMarks(question.getMaxMarks());
		return questionBean;
	}

	public String uploadAnswerSheet(Integer assessmentRequestId, MultipartFile file) {
		Objects.requireNonNull(assessmentRequestId);
		AssessmentRequest assessmentRequest = skooltestEntityUtils.getAssessmentRequestById(assessmentRequestId);
		AssessmentDetail assessmentDetail = skooltestEntityUtils.getAssessDetailByRequestId(assessmentRequestId);
		Integer userId = assessmentRequest.getRequestedBy().getUser().getId();
		if (Objects.nonNull(assessmentDetail)) {
			String fileName = getFileName(assessmentDetail);
			String location = fileStorageService.uploadFile(file, userId, fileName);
			skooltestEntityUtils.createOrUpdateAttachment(assessmentRequest, location);
			return SUCCESS;
		} else {
			throw new SkooltestCommonException(ASSESSMENT_DETAIL_NOT_FOUND);
		}
	}

	private String getFileName(AssessmentDetail assessmentDetail) {
		String fileName = new String(ANSWER_SHEET).concat(HYPHEN).concat(assessmentDetail.getAssessmentCode())
				.concat(PDF_EXTENSION);
		return fileName;
	}

	public String downloadAnswerSheet(Integer assessmentRequestId, HttpServletResponse servletResponse) {
		Objects.requireNonNull(assessmentRequestId);
		AssessmentRequest assessmentRequest = skooltestEntityUtils.getAssessmentRequestById(assessmentRequestId);
		AssessmentDetail assessmentDetail = skooltestEntityUtils.getAssessDetailByRequestId(assessmentRequestId);
		if (Objects.nonNull(assessmentDetail)) {
			try {
				Attachment attachment = skooltestEntityUtils.getAttachmentByAssessmentRequest(assessmentRequest)
						.orElseThrow(() -> new SkooltestCommonException(ATTACHMENT_NOT_FOUND));
				ByteArrayOutputStream bos = fileStorageService.downloadFile(attachment);
				String fileName = getFileName(assessmentDetail);
				generatePdfResponse(fileName, bos, servletResponse);
				return SUCCESS;
			} catch (IOException e) {
				throw new SkooltestCommonException(DOCUMENT_PROCESSING_ERROR);
			}
		} else {
			throw new SkooltestCommonException(ASSESSMENT_DETAIL_NOT_FOUND);
		}
	}
}
