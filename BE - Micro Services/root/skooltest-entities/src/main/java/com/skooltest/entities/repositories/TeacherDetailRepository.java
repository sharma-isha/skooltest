package com.skooltest.entities.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skooltest.entities.tables.auth.User;
import com.skooltest.entities.tables.users.TeacherDetail;

@Repository
public interface TeacherDetailRepository extends JpaRepository<TeacherDetail, Integer> {

	public List<TeacherDetail> findAllByUser_IsActive(byte isActive);

	public List<TeacherDetail> findByIdIn(List<Integer> teacherIds);

	public Optional<TeacherDetail> findByUser(User user);

}
