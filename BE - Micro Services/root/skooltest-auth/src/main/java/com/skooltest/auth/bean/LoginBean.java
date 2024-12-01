package com.skooltest.auth.bean;

import java.io.Serializable;

public class LoginBean implements Serializable {

	private static final long serialVersionUID = -4850232268380928727L;

	private String usernameOrEmail;

	private String password;

	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}

	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginBean [usernameOrEmail=" + usernameOrEmail + ", password=" + password + "]";
	}
}
