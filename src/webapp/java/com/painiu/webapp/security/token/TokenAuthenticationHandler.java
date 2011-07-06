/*
 * @(#)TokenAuthenticationHandler.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.security.token;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.anonymous.AnonymousAuthenticationToken;
import org.acegisecurity.ui.AuthenticationDetailsSource;
import org.acegisecurity.ui.AuthenticationDetailsSourceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.painiu.webapp.security.AbstractAuthenticationHandler;

/**
 * <p>
 * <a href="TokenAuthenticationHandler.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: TokenAuthenticationHandler.java 38 2010-06-01 02:18:07Z zhangsf $
 */
public class TokenAuthenticationHandler extends AbstractAuthenticationHandler implements InitializingBean {
	//~ Static fields/initializers =============================================

	private static final String TOKEN_PARAMETER_NAME = "auth_token";
	private static final Set<String> EXCLUDE_URIS = new HashSet<String>(2);
	
	static {
		EXCLUDE_URIS.add("/api/");
		//EXCLUDE_URIS.add("/big/");
	}
	
	//~ Instance fields ========================================================

	protected AuthenticationDetailsSource authenticationDetailsSource = new AuthenticationDetailsSourceImpl();
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	/*
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(authenticationManager);
	}
	
	protected String getTokenParameterName() {
		return TOKEN_PARAMETER_NAME;
	}
	
	/*
	 * @see com.painiu.security.AuthenticationHandler#attemptAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		TokenAuthenticationToken auth = new TokenAuthenticationToken(request.getParameter(getTokenParameterName()));
		auth.setDetails(authenticationDetailsSource.buildDetails(request));
		return getAuthenticationManager().authenticate(auth);
	}

	/*
	 * @see com.painiu.security.AbstractAuthenticationHandler#requiresAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		for (String uri : EXCLUDE_URIS) {
			if (request.getRequestURI().startsWith(uri)) {
				return false;
			}
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		return auth == null || AnonymousAuthenticationToken.class.isAssignableFrom(auth.getClass());
	}
	
	/*
	 * @see com.painiu.security.AbstractAuthenticationHandler#onSuccessfulAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.acegisecurity.Authentication)
	 */
	@Override
	public void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
		redirect(request, response);
	}
	
	/*
	 * @see com.painiu.security.AbstractAuthenticationHandler#onUnsuccessfulAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.acegisecurity.AuthenticationException)
	 */
	@Override
	public void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
		redirect(request, response);
	}
	
	protected String parepareQueryString(HttpServletRequest request) {
		String queryString = request.getQueryString();
		
		if (queryString != null) {
			int idx = queryString.indexOf(getTokenParameterName());
			while (idx != -1) {
				int sepIdx = queryString.indexOf("&", idx);
				if (sepIdx != -1) {
					queryString = queryString.substring(0, idx) + queryString.substring(sepIdx + 1);
				} else {
					queryString = queryString.substring(0, idx);
				}
				idx = queryString.indexOf(getTokenParameterName());
			}
		}
		
		return queryString;
	}
	
	protected void redirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
		StringBuffer sb = request.getRequestURL();
		
		sb.append(";jsessionid=").append(request.getSession().getId());
		
		String queryString = parepareQueryString(request);
		if (queryString != null & queryString.length() > 0) {
			sb.append("?").append(queryString);
		}
		
		sendRedirect(request, response, sb.toString());
	}
	
	/*
	 * @see com.painiu.security.AbstractAuthenticationHandler#supports(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public boolean supports(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getParameter(getTokenParameterName());
		return token != null && token.length() >= 32;
	}
	
	//~ Accessors ==============================================================
}
