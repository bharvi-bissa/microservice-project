package com.app.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	public static final String USER_MICROSERVICE_SIGNUP_PATH = "user.microservice.path.signup";
	public static final String USER_MICROSERVICE_LOGIN_PATH = "user.microservice.path.login";
	
	@Autowired
	Environment env;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.headers().frameOptions().disable();
		http.authorizeRequests()
				.antMatchers(HttpMethod.POST, env.getProperty(USER_MICROSERVICE_SIGNUP_PATH)).permitAll()
				.antMatchers(HttpMethod.POST, env.getProperty(USER_MICROSERVICE_LOGIN_PATH)).permitAll()
				.anyRequest().authenticated()
				.and()
				.addFilter(new AuthorizationFilter(authenticationManager(), env));

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

}
