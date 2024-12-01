package com.skooltest.entities.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.skooltest.entities.tables.assessment.AssessmentRequest;
import com.skooltest.entities.tables.masters.MasterStatus;
import com.skooltest.entities.tables.users.StudentDetail;

@Repository
public interface AssessmentRequestRepository extends JpaRepository<AssessmentRequest, Integer> {

	public List<AssessmentRequest> findByRequestedBy(StudentDetail student);

	public List<AssessmentRequest> findByMasterStatus(MasterStatus status);

	public List<AssessmentRequest> findByRequestedBy_Id(Integer studentId);

	@Query(value = "select * from assessment_request where (assigned_teacher_id = :teacherId) or (evaluation_teacher_id = :teacherId)", nativeQuery = true)
	public List<AssessmentRequest> findAssessmentRequestsOfTeacher(Integer teacherId);
}
