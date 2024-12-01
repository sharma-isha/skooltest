package com.skooltest.auth.keycloak.service;

import static com.skooltest.auth.constants.KeycloakConstants.CLIENT_ID;
import static com.skooltest.auth.constants.KeycloakConstants.EMAIL;
import static com.skooltest.auth.constants.KeycloakConstants.GRANT_TYPE;
import static com.skooltest.auth.constants.KeycloakConstants.PASSWORD;
import static com.skooltest.auth.constants.KeycloakConstants.REFRESH_TOKEN;
import static com.skooltest.auth.constants.KeycloakConstants.USERNAME;
import static com.skooltest.common.constants.CommonConstants.EMPTY_ARRAY;
import static com.skooltest.common.constants.CommonConstants.SLASH;
import static com.skooltest.common.constants.CommonConstants.SUCCESS;
import static com.skooltest.common.constants.CommonConstants.USER;
import static com.skooltest.common.constants.ExceptionConstants.INVALID_PASSWORD;
import static com.skooltest.common.constants.ExceptionConstants.INVALID_SESSION;
import static com.skooltest.common.constants.ExceptionConstants.INVALID_TOKEN;
import static com.skooltest.common.constants.ExceptionConstants.INVALID_USER_CREDENTIALS;
import static com.skooltest.common.utils.CommonUtils.convertJsonToObject;
import static com.skooltest.common.utils.CommonUtils.convertObjectToJson;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.skooltest.auth.bean.RegistrationBean;
import com.skooltest.auth.keycloak.bean.KeycloakCreateUserBean;
import com.skooltest.auth.keycloak.bean.KeycloakPasswordResetBean;
import com.skooltest.auth.keycloak.bean.KeycloakRoleResponseBean;
import com.skooltest.auth.keycloak.bean.KeycloakTokenBean;
import com.skooltest.auth.keycloak.bean.KeycloakUserResponseBean;
import com.skooltest.auth.utils.SkooltestBeanOperationUtils;
import com.skooltest.common.exceptions.SkooltestServiceUnavailableException;
import com.skooltest.common.exceptions.SkooltestUnauthorizedException;

@Service
public class KeycloakService {

	@Autowired
	private SkooltestBeanOperationUtils beanOperationUtils;

	@Value("${keycloak-app.realm.client_id}")
	private String realmClientid;

	@Value("${keycloak-app.grant_type}")
	private String grant_type;

	@Value("${keycloak-app.token_url}")
	private String token_url;

	@Value("${keycloak-app.master.client_id}")
	private String masterClientId;

	@Value("${keycloak-app.master.username}")
	private String masterUser;

	@Value("${keycloak-app.master.password}")
	private String masterPassword;

	@Value("${keycloak-app.user_url}")
	private String user_url;

	@Value("${keycloak-app.master.token_url}")
	private String master_token_url;

	@Value("${keycloak-app.master.reset_password}")
	private String reset_password;

	@Value("${keycloak-app.master.get_role}")
	private String get_all_roles;

	@Value("${keycloak-app.master.role_mapping}")
	private String role_mapping;

	@Value("${keycloak-app.grant_type_refresh}")
	private String grant_type_refresh;

	@Value("${keycloak-app.delete_session}")
	private String delete_session_url;

	public KeycloakTokenBean getAuthToken(String usernameOrEmail, String password) {
		KeycloakTokenBean tokenBean = null;
		try {
			LinkedMultiValueMap<String, Object> formBody = new LinkedMultiValueMap<String, Object>();
			formBody.add(CLIENT_ID, realmClientid);
			formBody.add(USERNAME, usernameOrEmail);
			formBody.add(PASSWORD, password);
			formBody.add(GRANT_TYPE, grant_type);
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(formBody);
			ResponseEntity<String> response = restTemplate.postForEntity(token_url, entity, String.class);
			tokenBean = convertJsonToObject(response.getBody(), KeycloakTokenBean.class);
			return tokenBean;
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().equals(UNAUTHORIZED)) {
				throw new SkooltestUnauthorizedException(INVALID_PASSWORD);
			} else {
				throw new SkooltestUnauthorizedException(e.getStatusText());
			}
		} catch (HttpServerErrorException e) {
			throw new SkooltestServiceUnavailableException(e.getStatusText());
		}
	}

	public Boolean checkIfUserExistsByUsername(String username) {
		ResponseEntity<String> response = getKeycloakUserByUsername(username);
		return !StringUtils.equals(EMPTY_ARRAY, (response.getBody()));
	}

	public ResponseEntity<String> getKeycloakUserByUsername(String username) {
		try {
			KeycloakTokenBean masterTokenBean = getMasterAuthToken();
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setBearerAuth(masterTokenBean.getAccessToken());
			HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(headers);
			String urlTemplate = UriComponentsBuilder.fromHttpUrl(user_url).queryParam(USERNAME, username)
					.toUriString();
			ResponseEntity<String> response = restTemplate.exchange(urlTemplate, GET, entity, String.class);
			return response;
		} catch (HttpClientErrorException e) {
			throw new SkooltestUnauthorizedException(e.getStatusText());
		} catch (HttpServerErrorException e) {
			throw new SkooltestServiceUnavailableException(e.getStatusText());
		}
	}

	public String getKeycloakUserIdByUsername(String username) {
		KeycloakUserResponseBean[] userResponse = null;
		ResponseEntity<String> response = getKeycloakUserByUsername(username);
		userResponse = convertJsonToObject(response.getBody(), KeycloakUserResponseBean[].class);
		List<KeycloakUserResponseBean> responseBeans = Arrays.asList(userResponse);
		return responseBeans.stream().findFirst().orElse(new KeycloakUserResponseBean()).getId();
	}

	public Boolean checkIfUserExistsByEmail(String email) {
		try {
			KeycloakTokenBean masterTokenBean = getMasterAuthToken();
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setBearerAuth(masterTokenBean.getAccessToken());
			HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(headers);
			String urlTemplate = UriComponentsBuilder.fromHttpUrl(user_url).queryParam(EMAIL, email).toUriString();
			ResponseEntity<String> response = restTemplate.exchange(urlTemplate, GET, entity, String.class);
			return !StringUtils.equals(EMPTY_ARRAY, (response.getBody()));
		} catch (HttpClientErrorException e) {
			throw new SkooltestUnauthorizedException(e.getStatusText());
		} catch (HttpServerErrorException e) {
			throw new SkooltestServiceUnavailableException(e.getStatusText());
		}
	}

	public KeycloakTokenBean getMasterAuthToken() {
		KeycloakTokenBean tokenBean = null;
		try {
			LinkedMultiValueMap<String, Object> formBody = new LinkedMultiValueMap<String, Object>();
			formBody.add(CLIENT_ID, masterClientId);
			formBody.add(USERNAME, masterUser);
			formBody.add(PASSWORD, masterPassword);
			formBody.add(GRANT_TYPE, grant_type);
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(formBody);
			ResponseEntity<String> response = restTemplate.postForEntity(master_token_url, entity, String.class);
			tokenBean = convertJsonToObject(response.getBody(), KeycloakTokenBean.class);
			return tokenBean;
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().equals(UNAUTHORIZED)) {
				throw new SkooltestUnauthorizedException(INVALID_USER_CREDENTIALS);
			} else {
				throw new SkooltestUnauthorizedException(e.getStatusText());
			}
		} catch (HttpServerErrorException e) {
			throw new SkooltestServiceUnavailableException(e.getStatusText());
		}
	}

	public Boolean createNewUser(RegistrationBean registrationBean) {
		try {
			KeycloakCreateUserBean userBean = beanOperationUtils.mapToKeycloakCreateUserBean(registrationBean);
			KeycloakTokenBean masterTokenBean = getMasterAuthToken();
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setBearerAuth(masterTokenBean.getAccessToken());
			headers.setContentType(APPLICATION_JSON);
			String request = convertObjectToJson(userBean);
			HttpEntity<String> entity = new HttpEntity<>(request, headers);
			ResponseEntity<String> response = restTemplate.exchange(user_url, POST, entity, String.class);
			if (CREATED == response.getStatusCode()) {
				return true;
			}
		} catch (HttpClientErrorException e) {
			throw new SkooltestUnauthorizedException(e.getStatusText());
		} catch (HttpServerErrorException e) {
			throw new SkooltestServiceUnavailableException(e.getStatusText());
		}
		return false;
	}

	public HttpStatusCode setPasswordForUser(String userId, String password) {
		try {
			KeycloakPasswordResetBean passwordResetBean = beanOperationUtils.mapToKeycloakPasswordResetBean(password);
			KeycloakTokenBean masterTokenBean = getMasterAuthToken();
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setBearerAuth(masterTokenBean.getAccessToken());
			headers.setContentType(APPLICATION_JSON);
			String request = convertObjectToJson(passwordResetBean);
			HttpEntity<String> entity = new HttpEntity<>(request, headers);
			ResponseEntity<String> response = restTemplate
					.exchange(user_url.concat(SLASH).concat(userId).concat(reset_password), PUT, entity, String.class);
			return response.getStatusCode();
		} catch (HttpClientErrorException e) {
			throw new SkooltestUnauthorizedException(e.getStatusText());
		} catch (HttpServerErrorException e) {
			throw new SkooltestServiceUnavailableException(e.getStatusText());
		}
	}

	public List<KeycloakRoleResponseBean> getAndConstructRolesBean(String userType) {
		List<KeycloakRoleResponseBean> getAllRoles = getAllRoles();
		List<KeycloakRoleResponseBean> rolesToBeMapped = new ArrayList<KeycloakRoleResponseBean>();
		Map<String, List<KeycloakRoleResponseBean>> roleMap = getAllRoles.stream()
				.collect(Collectors.groupingBy(role -> role.getName()));
		rolesToBeMapped.addAll(ListUtils.emptyIfNull(roleMap.get(userType)));
		rolesToBeMapped.addAll(ListUtils.emptyIfNull(roleMap.get(USER)));
		return rolesToBeMapped;
	}

	public List<KeycloakRoleResponseBean> getAllRoles() {
		KeycloakRoleResponseBean[] keycloakRoles = null;
		try {
			KeycloakTokenBean masterTokenBean = getMasterAuthToken();
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setBearerAuth(masterTokenBean.getAccessToken());
			HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(headers);
			ResponseEntity<String> response = restTemplate.exchange(get_all_roles, GET, entity, String.class);
			keycloakRoles = convertJsonToObject(response.getBody(), KeycloakRoleResponseBean[].class);
			return Arrays.asList(keycloakRoles);
		} catch (HttpClientErrorException e) {
			throw new SkooltestUnauthorizedException(e.getStatusText());
		} catch (HttpServerErrorException e) {
			throw new SkooltestServiceUnavailableException(e.getStatusText());
		}
	}

	public HttpStatusCode addUserRolesToUser(String userId, List<KeycloakRoleResponseBean> rolesBean) {
		try {
			KeycloakTokenBean masterTokenBean = getMasterAuthToken();
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setBearerAuth(masterTokenBean.getAccessToken());
			headers.setContentType(APPLICATION_JSON);
			String request = convertObjectToJson(rolesBean);
			HttpEntity<String> entity = new HttpEntity<>(request, headers);
			ResponseEntity<String> response = restTemplate
					.exchange(user_url.concat(SLASH).concat(userId).concat(role_mapping), POST, entity, String.class);
			return response.getStatusCode();
		} catch (HttpClientErrorException e) {
			throw new SkooltestUnauthorizedException(e.getStatusText());
		} catch (HttpServerErrorException e) {
			throw new SkooltestServiceUnavailableException(e.getStatusText());
		}
	}

	public KeycloakTokenBean getRefreshToken(String refreshToken) {
		KeycloakTokenBean tokenBean = null;
		try {
			LinkedMultiValueMap<String, Object> formBody = new LinkedMultiValueMap<String, Object>();
			formBody.add(CLIENT_ID, realmClientid);
			formBody.add(GRANT_TYPE, grant_type_refresh);
			formBody.add(REFRESH_TOKEN, refreshToken);
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(formBody);
			ResponseEntity<String> response = restTemplate.postForEntity(token_url, entity, String.class);
			tokenBean = convertJsonToObject(response.getBody(), KeycloakTokenBean.class);
			return tokenBean;
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().equals(UNAUTHORIZED) || e.getStatusCode().equals(BAD_REQUEST)) {
				throw new SkooltestUnauthorizedException(INVALID_TOKEN);
			} else {
				throw new SkooltestUnauthorizedException(e.getStatusText());
			}
		} catch (HttpServerErrorException e) {
			throw new SkooltestServiceUnavailableException(e.getStatusText());
		}
	}

	public String logoutUser(String sessionId) {
		try {
			KeycloakTokenBean masterTokenBean = getMasterAuthToken();
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setBearerAuth(masterTokenBean.getAccessToken());
			HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(headers);
			ResponseEntity<String> response = restTemplate.exchange(delete_session_url.concat(SLASH).concat(sessionId),
					DELETE, entity, String.class);
			if (NO_CONTENT == response.getStatusCode()) {
				return SUCCESS;
			}
		} catch (HttpClientErrorException e) {
			throw new SkooltestUnauthorizedException(INVALID_SESSION);
		} catch (HttpServerErrorException e) {
			throw new SkooltestServiceUnavailableException(e.getStatusText());
		}
		return null;
	}
}
