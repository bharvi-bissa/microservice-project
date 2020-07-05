package com.app.users.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.web.filter.OncePerRequestFilter;

public class GatewayFilter extends OncePerRequestFilter {

	public static final String GATEWAY_IP = "gateway.ip";

	Environment env;

	public GatewayFilter(Environment env) {
		this.env = env;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String remoteAddr = "";
		if (request != null) {
			remoteAddr = request.getHeader("X-FORWARDED-FOR");
			if (remoteAddr == null || "".equals(remoteAddr)) {
				remoteAddr = request.getRemoteAddr();
			}
		}
		if (!remoteAddr.equals(env.getProperty(GATEWAY_IP))) {
			response.setStatus(401);
			return;
		}
		filterChain.doFilter(request, response);
	}

}
