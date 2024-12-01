package com.skooltest.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skooltest.auth.bean.LoginBean;
import com.skooltest.auth.bean.RegistrationBean;
import com.skooltest.auth.keycloak.bean.KeycloakTokenBean;
import com.skooltest.auth.service.AuthService;
import com.skooltest.common.beans.ResponseResource;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping(value = "/signin")
	public ResponseResource<KeycloakTokenBean> login(@RequestBody LoginBean loginBean) {
		KeycloakTokenBean response = authService.login(loginBean);
		return new ResponseResource<>(response);
	}

	@PostMapping(value = "/register")
	public ResponseResource<String> register(@RequestBody RegistrationBean registrationBean) {
		String response = authService.register(registrationBean);
		return new ResponseResource<>(response);
	}

	@PostMapping(value = "/refresh")
	public ResponseResource<KeycloakTokenBean> refreshToken(@RequestBody KeycloakTokenBean refreshTokenBean) {
		KeycloakTokenBean response = authService.refreshToken(refreshTokenBean.getRefreshToken());
		return new ResponseResource<>(response);
	}

	@PostMapping(value = "/logout")
	public ResponseResource<String> logout(@RequestBody KeycloakTokenBean sessionIdBean) {
		String response = authService.logout(sessionIdBean.getSessionState());
		return new ResponseResource<>(response);
	}
}
