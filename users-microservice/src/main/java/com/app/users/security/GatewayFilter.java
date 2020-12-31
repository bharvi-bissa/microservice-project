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
	public static final String IS_GATEWAY_FILTER_ENABLED = "gateway.filter.enabled";

	Environment env;

	public GatewayFilter(Environment env) {
		this.env = env;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Boolean isGatewayFilterEnabled = Boolean.parseBoolean(env.getProperty(IS_GATEWAY_FILTER_ENABLED));
		if(!isGatewayFilterEnabled) {
			filterChain.doFilter(request, response);
			return;
		}
		String remoteAddr = "";
		if (request != null) {
			remoteAddr = request.getHeader("X-FORWARDED-FOR");
			if (remoteAddr == null || "".equals(remoteAddr)) {
				remoteAddr = request.getRemoteAddr();
			}
		}
		if (remoteAddr.equals(env.getProperty(GATEWAY_IP))) {
			response.setStatus(401);
			return;
		}
		filterChain.doFilter(request, response);
	}

}
