package com.skooltest.auth.service;

import static com.skooltest.common.constants.CommonConstants.STUDENT_USER;
import static com.skooltest.common.constants.CommonConstants.SUCCESS;
import static com.skooltest.common.constants.CommonConstants.TEACHER_USER;
import static com.skooltest.common.constants.ExceptionConstants.INVALID_USER_CREDENTIALS;
import static com.skooltest.common.constants.ExceptionConstants.USERNAME_ALREADY_EXISTS;
import static com.skooltest.common.constants.ExceptionConstants.USERTYPE_NOT_FOUND;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skooltest.auth.bean.LoginBean;
import com.skooltest.auth.bean.RegistrationBean;
import com.skooltest.auth.keycloak.bean.KeycloakTokenBean;
import com.skooltest.auth.keycloak.service.KeycloakService;
import com.skooltest.auth.utils.SkooltestEntityUtils;
import com.skooltest.common.exceptions.SkooltestCommonException;
import com.skooltest.common.exceptions.SkooltestUnauthorizedException;
import com.skooltest.entities.tables.auth.User;

import jakarta.transaction.Transactional;

@Service
public class AuthService {

	@Autowired
	private KeycloakService keycloakService;

	@Autowired
	private SkooltestEntityUtils skooltestEntityUtils;

	public KeycloakTokenBean login(LoginBean loginBean) {
		Objects.requireNonNull(loginBean);
		Objects.requireNonNull(loginBean.getUsernameOrEmail());
		Objects.requireNonNull(loginBean.getPassword());

		Optional<User> userOp = skooltestEntityUtils.getUserByUserName(loginBean.getUsernameOrEmail());
		if (!userOp.isPresent()) {
			throw new SkooltestUnauthorizedException(INVALID_USER_CREDENTIALS);
		}
		KeycloakTokenBean tokenBean = keycloakService.getAuthToken(loginBean.getUsernameOrEmail(),
				loginBean.getPassword());
		return tokenBean;
	}

	@Transactional
	public String register(RegistrationBean registrationBean) {
		Objects.requireNonNull(registrationBean);
		Boolean isUserPresent = false;
		// check if user present in keycloak
		if (Objects.nonNull(registrationBean.getUsername())) {
			isUserPresent = keycloakService.checkIfUserExistsByUsername(registrationBean.getUsername());
		}
		if (Objects.nonNull(registrationBean.getEmail())) {
			isUserPresent = keycloakService.checkIfUserExistsByEmail(registrationBean.getEmail());
		}
		if (!isUserPresent) {
			// create new user in skooltest
			User user = skooltestEntityUtils.createUser(registrationBean);
			switch (registrationBean.getUserType()) {
			case STUDENT_USER:
				skooltestEntityUtils.createStudentDetail(registrationBean, user);
				break;
			case TEACHER_USER:
				skooltestEntityUtils.createTeacherDetail(registrationBean, user);
				break;
			default:
				throw new SkooltestCommonException(USERTYPE_NOT_FOUND);
			}
			// create new user in keycloak
			Boolean isUserCreated = keycloakService.createNewUser(registrationBean);
			if (isUserCreated) {
				String userId = keycloakService.getKeycloakUserIdByUsername(registrationBean.getUsername());
				keycloakService.setPasswordForUser(userId, registrationBean.getPassword());
				keycloakService.addUserRolesToUser(userId,
						keycloakService.getAndConstructRolesBean(registrationBean.getUserType()));
			}
		} else {
			throw new SkooltestCommonException(USERNAME_ALREADY_EXISTS);
		}
		return SUCCESS;
	}

	public KeycloakTokenBean refreshToken(String refreshToken) {
		Objects.requireNonNull(refreshToken);
		KeycloakTokenBean tokenBean = keycloakService.getRefreshToken(refreshToken);
		return tokenBean;
	}

	public String logout(String sessionId) {
		return keycloakService.logoutUser(sessionId);
	}
}
