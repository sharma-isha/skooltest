package com.skooltest.auth.keycloak.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KeycloakCreateUserBean implements Serializable {

	private static final long serialVersionUID = -900584276802144392L;

	private String username;

	private String firstName;

	private String lastName;

	private String email;

	private String enabled;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "KeycloakCreateUserBean [username=" + username + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", enabled=" + enabled + "]";
	}
}
