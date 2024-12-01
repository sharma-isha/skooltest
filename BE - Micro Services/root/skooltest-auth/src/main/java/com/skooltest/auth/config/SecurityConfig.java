package com.skooltest.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	public static final String ADMIN = "admin";
	public static final String USER = "user";
	private final JwtConverter jwtConverter;
	private final JwtAuthenticationEntryPoint authenticationEntryPoint;

	public SecurityConfig(JwtConverter jwtConverter, JwtAuthenticationEntryPoint authenticationEntryPoint) {
		this.jwtConverter = jwtConverter;
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable());
		http.authorizeHttpRequests((authz) -> authz.requestMatchers(new AntPathRequestMatcher("/signin/**")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/register/**")).permitAll()
				.requestMatchers(HttpMethod.GET, "*/hello/**").permitAll().requestMatchers("/**").permitAll()
				.anyRequest().authenticated())
				.exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)))
				.httpBasic(Customizer.withDefaults());
		return http.build();
	}
}
