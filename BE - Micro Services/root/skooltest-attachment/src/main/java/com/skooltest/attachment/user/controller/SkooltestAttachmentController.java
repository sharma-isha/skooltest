package com.skooltest.attachment.user.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.DocumentException;
import com.skooltest.attachment.user.service.SkooltestAttachmentService;
import com.skooltest.common.beans.ResponseResource;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/attachment")
public class SkooltestAttachmentController {

	@Autowired
	private SkooltestAttachmentService attachmentService;

	@GetMapping(value = "/assessmentRequest/{assessmentRequestId}/generatePdf")
	public ResponseResource<String> generateQuestionPaperPdf(
			@PathVariable(value = "assessmentRequestId") Integer assessmentRequestId,
			HttpServletResponse servletResponse) throws DocumentException, IOException {
		String response = attachmentService.processQuestionPaperPdf(assessmentRequestId, servletResponse);
		return new ResponseResource<>(response);
	}

	@PostMapping(value = "/assessmentRequest/{assessmentRequestId}/uploadAnswerSheet")
	public ResponseResource<String> uploadAnswerSheet(
			@PathVariable(value = "assessmentRequestId") Integer assessmentRequestId,
			@RequestParam("file") MultipartFile file) {
		String response = attachmentService.uploadAnswerSheet(assessmentRequestId, file);
		return new ResponseResource<>(response);
	}

	@GetMapping(value = "/assessmentRequest/{assessmentRequestId}/downloadAnswerSheet")
	public ResponseResource<String> downloadAnswerSheet(
			@PathVariable(value = "assessmentRequestId") Integer assessmentRequestId,
			HttpServletResponse servletResponse) throws IOException {
		String response = attachmentService.downloadAnswerSheet(assessmentRequestId, servletResponse);
		return new ResponseResource<>(response);
	}
}
