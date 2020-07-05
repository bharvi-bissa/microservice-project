package com.app.users.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.users.service.UsersService;
import com.app.users.shared.UserDto;
import com.app.users.ui.model.UserSignInRequestModel;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private UsersService usersService;
	private Environment env;
	
	public AuthenticationFilter(UsersService usersService, 
			Environment env, 
			AuthenticationManager authenticationManager) {
		this.usersService = usersService;
		this.env = env;
		super.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			UserSignInRequestModel userSignInRequestModel = new ObjectMapper().readValue(request.getInputStream(),
					UserSignInRequestModel.class);

			return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
					userSignInRequestModel.getEmail(), userSignInRequestModel.getPassword(), new ArrayList<>()));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		String userName = ((User) auth.getPrincipal()).getUsername();
		UserDto userDetails = usersService.getUserDetailsByEmail(userName);

		String token = Jwts.builder().setSubject(userDetails.getUserId())
				.setExpiration(new Date(
						System.currentTimeMillis() + Long.parseLong(env.getProperty("token.exp_time"))))
				.signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret")).compact();

		res.addHeader("token", token);
		res.addHeader("userId", userDetails.getUserId());
	}
}
