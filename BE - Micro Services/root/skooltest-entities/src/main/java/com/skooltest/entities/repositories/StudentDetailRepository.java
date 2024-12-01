package com.skooltest.entities.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skooltest.entities.tables.auth.User;
import com.skooltest.entities.tables.users.StudentDetail;

@Repository
public interface StudentDetailRepository extends JpaRepository<StudentDetail, Integer> {

	public List<StudentDetail> findAllByUser_IsActive(byte isActive);

	public Optional<StudentDetail> findByIdAndUser_IsActive(Integer studentId, byte isActive);

	public Optional<StudentDetail> findByUser(User user);
}
