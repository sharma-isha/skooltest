package com.skooltest.entities.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skooltest.entities.tables.assessment.AssessmentRequest;
import com.skooltest.entities.tables.attachment.Attachment;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Integer>{

	public Optional<Attachment> findByAssessmentRequest(AssessmentRequest assessmentRequest);

}
