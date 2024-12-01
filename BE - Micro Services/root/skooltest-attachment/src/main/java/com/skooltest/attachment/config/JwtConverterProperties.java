package com.skooltest.attachment.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
@ConfigurationProperties(prefix = "jwt.auth.converter")
public class JwtConverterProperties {

	private String resourceId;
	private String principalAttribute;

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getPrincipalAttribute() {
		return principalAttribute;
	}

	public void setPrincipalAttribute(String principalAttribute) {
		this.principalAttribute = principalAttribute;
	}

	@Override
	public String toString() {
		return "JwtConverterProperties [resourceId=" + resourceId + ", principalAttribute=" + principalAttribute + "]";
	}
}