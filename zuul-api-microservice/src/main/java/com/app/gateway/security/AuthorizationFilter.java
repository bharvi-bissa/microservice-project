package com.app.gateway.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	public static final String AUTH_TOKEN_HEADER_NAME = "authorization.token.header.name";
	public static final String AUTH_TOKEN_HEADER_PREFIX = "authorization.token.header.prefix";
	public static final String AUTH_TOKEN_SECRET = "token.secret";
	Environment env;

	public AuthorizationFilter(AuthenticationManager authManager, Environment env) {
		super(authManager);
		this.env = env;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String authorizationHeader = req.getHeader(env.getProperty(AUTH_TOKEN_HEADER_NAME));

		if (authorizationHeader == null || !authorizationHeader.startsWith(env.getProperty(AUTH_TOKEN_HEADER_PREFIX))) {
			chain.doFilter(req, res);
			return;
		}

		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
		String authorizationHeader = req.getHeader(env.getProperty(AUTH_TOKEN_HEADER_NAME));

		if (authorizationHeader == null) {
			return null;
		}

		String token = authorizationHeader.replace(env.getProperty(AUTH_TOKEN_HEADER_PREFIX), "");

		String userId = Jwts.parser().setSigningKey(env.getProperty(AUTH_TOKEN_SECRET)).parseClaimsJws(token).getBody()
				.getSubject();

		if (userId == null) {
			return null;
		}

		return new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
	}

}
