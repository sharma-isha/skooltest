package com.skooltest.auth.utils;

import static com.skooltest.common.constants.CommonConstants.ACTIVE;
import static com.skooltest.common.constants.ExceptionConstants.PARSING_ERROR;
import static com.skooltest.common.constants.ExceptionConstants.ROLE_NOT_FOUND;

import java.text.ParseException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;
import com.skooltest.auth.bean.RegistrationBean;
import com.skooltest.common.exceptions.SkooltestCommonException;
import com.skooltest.common.utils.CommonUtils;
import com.skooltest.entities.repositories.RoleRepository;
import com.skooltest.entities.repositories.StudentDetailRepository;
import com.skooltest.entities.repositories.TeacherDetailRepository;
import com.skooltest.entities.repositories.UserRepository;
import com.skooltest.entities.tables.auth.Role;
import com.skooltest.entities.tables.auth.User;
import com.skooltest.entities.tables.users.StudentDetail;
import com.skooltest.entities.tables.users.TeacherDetail;

@Component
public class SkooltestEntityUtils {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private StudentDetailRepository studentDetailRepository;

	@Autowired
	private TeacherDetailRepository teacherDetailRepository;

	public User createUser(RegistrationBean registrationBean) {
		User user = new User();
		user.setUserName(registrationBean.getUsername());
		user.setFirstName(registrationBean.getFirstname());
		user.setLastName(registrationBean.getLastname());
		user.setIsActive(ACTIVE);
		user.setRole(Sets.newHashSet(getRoles(registrationBean.getUserType())));
		return userRepository.save(user);
	}

	private Role getRoles(String roleName) {
		return roleRepository.findByRoleName(roleName).orElseThrow(() -> new SkooltestCommonException(ROLE_NOT_FOUND));
	}

	public void createStudentDetail(RegistrationBean registrationBean, User user) {
		StudentDetail studentDetail = new StudentDetail();
		try {
			studentDetail.setUser(user);
			studentDetail.setDateOfBirth(CommonUtils.getDate(registrationBean.getDateOfBirth()));
		} catch (ParseException e) {
			throw new SkooltestCommonException(PARSING_ERROR);
		}
		studentDetailRepository.save(studentDetail);
	}

	public void createTeacherDetail(RegistrationBean registrationBean, User user) {
		TeacherDetail teacherDetail = new TeacherDetail();
		teacherDetail.setUser(user);
		teacherDetailRepository.save(teacherDetail);
	}

	public Optional<User> getUserByUserName(String userName) {
		return userRepository.findByUserNameAndIsActive(userName, ACTIVE);
	}
}
