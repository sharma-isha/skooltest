package com.skooltest.auth.keycloak.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KeycloakTokenBean implements Serializable {

	private static final long serialVersionUID = 3098921218619336527L;

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("expires_in")
	private String expiresIn;

	@JsonProperty("refresh_expires_in")
	private String refreshExpiresIn;

	@JsonProperty("refresh_token")
	private String refreshToken;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("not-before-policy")
	private String notBeforePolicy;

	@JsonProperty("session_state")
	private String sessionState;

	@JsonProperty("scope")
	private String scope;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getRefreshExpiresIn() {
		return refreshExpiresIn;
	}

	public void setRefreshExpiresIn(String refreshExpiresIn) {
		this.refreshExpiresIn = refreshExpiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getNotBeforePolicy() {
		return notBeforePolicy;
	}

	public void setNotBeforePolicy(String notBeforePolicy) {
		this.notBeforePolicy = notBeforePolicy;
	}

	public String getSessionState() {
		return sessionState;
	}

	public void setSessionState(String sessionState) {
		this.sessionState = sessionState;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
	public String toString() {
		return "KeycloakTokenBean [accessToken=" + accessToken + ", expiresIn=" + expiresIn + ", refreshExpiresIn="
				+ refreshExpiresIn + ", refreshToken=" + refreshToken + ", tokenType=" + tokenType
				+ ", notBeforePolicy=" + notBeforePolicy + ", sessionState=" + sessionState + ", scope=" + scope + "]";
	}
}
