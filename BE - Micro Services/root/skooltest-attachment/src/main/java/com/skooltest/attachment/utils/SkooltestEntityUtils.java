package com.skooltest.attachment.utils;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skooltest.entities.repositories.AssessmentDetailRepository;
import com.skooltest.entities.repositories.AssessmentRequestRepository;
import com.skooltest.entities.repositories.AttachmentRepository;
import com.skooltest.entities.tables.assessment.AssessmentDetail;
import com.skooltest.entities.tables.assessment.AssessmentRequest;
import com.skooltest.entities.tables.attachment.Attachment;

@Component
public class SkooltestEntityUtils {

	@Autowired
	private AssessmentDetailRepository assessmentDetailRepository;

	@Autowired
	private AssessmentRequestRepository assessmentRequestRepository;

	@Autowired
	private AttachmentRepository attachmentRepository;

	public AssessmentDetail getAssessDetailByRequestId(Integer assessmentRequestId) {
		return assessmentDetailRepository.findByAssessmentRequest_Id(assessmentRequestId);
	}

	public AssessmentRequest getAssessmentRequestById(Integer assessmentId) {
		return assessmentRequestRepository.findById(assessmentId)
				.orElseThrow(() -> new RuntimeException("Assessment Not found"));
	}

	public Attachment createOrUpdateAttachment(AssessmentRequest assessmentRequest, String location) {
		Optional<Attachment> attachmentOp = getAttachmentByAssessmentRequest(assessmentRequest);
		Attachment attachment;
		if (attachmentOp.isEmpty()) {
			attachment = new Attachment();
			attachment.setAssessmentRequest(assessmentRequest);
		} else {
			attachment = attachmentOp.get();
		}
		attachment.setLocation(location);
		attachment.setCreatedBy(assessmentRequest.getRequestedBy().getUser().getUserName());
		attachment.setCreatedTime(new Date());
		attachmentRepository.save(attachment);
		return attachment;
	}

	public Optional<Attachment> getAttachmentByAssessmentRequest(AssessmentRequest assessmentRequest) {
		return attachmentRepository.findByAssessmentRequest(assessmentRequest);
	}
}
