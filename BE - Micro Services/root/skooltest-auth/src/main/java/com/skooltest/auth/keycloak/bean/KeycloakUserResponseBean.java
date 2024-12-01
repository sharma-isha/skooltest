package com.skooltest.auth.keycloak.bean;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "username", "firstName", "lastName", "email", "emailVerified", "createdTimestamp", "enabled",
		"totp", "disableableCredentialTypes", "requiredActions", "notBefore", "access" })
public class KeycloakUserResponseBean implements Serializable {

	private static final long serialVersionUID = -900584276802144392L;

	@JsonProperty("id")
	private String id;

	@JsonProperty("username")
	private String username;

	@JsonProperty("firstName")
	private String firstName;

	@JsonProperty("lastName")
	private String lastName;

	@JsonProperty("email")
	private String email;

	@JsonProperty("emailVerified")
	private Boolean emailVerified;

	@JsonProperty("createdTimestamp")
	private Long createdTimestamp;

	@JsonProperty("enabled")
	private Boolean enabled;

	@JsonProperty("totp")
	private Boolean totp;

	@JsonProperty("disableableCredentialTypes")
	private List<String> disableableCredentialTypes = null;

	@JsonProperty("requiredActions")
	private List<String> requiredActions = null;

	@JsonProperty("notBefore")
	private Integer notBefore;

	@JsonProperty("access")
	private KeycloakAccessBean access;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public Long getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Long createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getTotp() {
		return totp;
	}

	public void setTotp(Boolean totp) {
		this.totp = totp;
	}

	public List<String> getDisableableCredentialTypes() {
		return disableableCredentialTypes;
	}

	public void setDisableableCredentialTypes(List<String> disableableCredentialTypes) {
		this.disableableCredentialTypes = disableableCredentialTypes;
	}

	public List<String> getRequiredActions() {
		return requiredActions;
	}

	public void setRequiredActions(List<String> requiredActions) {
		this.requiredActions = requiredActions;
	}

	public Integer getNotBefore() {
		return notBefore;
	}

	public void setNotBefore(Integer notBefore) {
		this.notBefore = notBefore;
	}

	public KeycloakAccessBean getAccess() {
		return access;
	}

	public void setAccess(KeycloakAccessBean access) {
		this.access = access;
	}

	@Override
	public String toString() {
		return "KeycloakCreateUserBean [id=" + id + ", username=" + username + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + ", emailVerified=" + emailVerified
				+ ", createdTimestamp=" + createdTimestamp + ", enabled=" + enabled + ", totp=" + totp
				+ ", disableableCredentialTypes=" + disableableCredentialTypes + ", requiredActions=" + requiredActions
				+ ", notBefore=" + notBefore + ", access=" + access + "]";
	}
}
