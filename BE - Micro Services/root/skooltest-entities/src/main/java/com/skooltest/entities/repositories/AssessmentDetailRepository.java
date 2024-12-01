package com.skooltest.entities.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skooltest.entities.tables.assessment.AssessmentDetail;

@Repository
public interface AssessmentDetailRepository extends JpaRepository<AssessmentDetail, Integer> {

	AssessmentDetail findByAssessmentRequest_Id(Integer assessmentRequestId);

}
