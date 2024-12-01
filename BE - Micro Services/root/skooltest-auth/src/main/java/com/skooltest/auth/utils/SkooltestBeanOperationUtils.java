package com.skooltest.auth.utils;

import static com.skooltest.auth.constants.KeycloakConstants.PASSWORD;
import static com.skooltest.common.constants.CommonConstants.TRUE;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.skooltest.auth.bean.RegistrationBean;
import com.skooltest.auth.keycloak.bean.KeycloakCreateUserBean;
import com.skooltest.auth.keycloak.bean.KeycloakPasswordResetBean;

@Component
public class SkooltestBeanOperationUtils {

	public KeycloakCreateUserBean mapToKeycloakCreateUserBean(RegistrationBean registrationBean) {
		KeycloakCreateUserBean createUserBean = new KeycloakCreateUserBean();
		createUserBean.setFirstName(StringUtils.defaultString(registrationBean.getFirstname()));
		createUserBean.setLastName(StringUtils.defaultString(registrationBean.getLastname()));
		createUserBean.setUsername(StringUtils.defaultString(registrationBean.getUsername()));
		createUserBean.setEnabled(TRUE);
		createUserBean.setEmail(StringUtils.defaultString(registrationBean.getEmail()));
		return createUserBean;
	}

	public KeycloakPasswordResetBean mapToKeycloakPasswordResetBean(String password) {
		KeycloakPasswordResetBean passwordResetBean = new KeycloakPasswordResetBean();
		passwordResetBean.setTemporary(false);
		passwordResetBean.setValue(password);
		passwordResetBean.setType(PASSWORD);
		return passwordResetBean;
	}

}
