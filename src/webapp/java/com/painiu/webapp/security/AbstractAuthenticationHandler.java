/*
 * @(#)AbstractAuthenticationHandler.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.AuthenticationManager;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.ui.AuthenticationDetailsSource;
import org.acegisecurity.ui.AuthenticationDetailsSourceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * <a href="AbstractAuthenticationHandler.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: AbstractAuthenticationHandler.java 24 2010-05-12 19:09:19Z zhangsf $
 */
public abstract class AbstractAuthenticationHandler implements AuthenticationHandler {
	//~ Static fields/initializers =============================================
	
	private static final Logger log = LoggerFactory.getLogger(AbstractAuthenticationHandler.class);

	public static final String SECURITY_COMMITTED_KEY = "SECURITY_COMMITTED";
	
	public static final String SECURITY_FORWARD_KEY = "SECURITY_FORWARD";
	
	//~ Instance fields ========================================================

	protected AuthenticationDetailsSource authenticationDetailsSource = new AuthenticationDetailsSourceImpl();
	
	protected AuthenticationManager authenticationManager;
	
	protected String filterProcessesUrl = getDefaultFilterProcessesUrl();

	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	public String getDefaultFilterProcessesUrl() {
		return null;
	}
	
	/*
	 * @see com.painiu.security.AuthenticationHandler#requiresAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		return requestedURLMatch(request, this.filterProcessesUrl);
	}
	
	public static boolean requestedURLMatch(HttpServletRequest request, String url) {
		if (url == null) {
			return false;
		}
		String uri = request.getRequestURI();
        int pathParamIndex = uri.indexOf(';');

        if (pathParamIndex > 0) {
            // strip everything after the first semi-colon
            uri = uri.substring(0, pathParamIndex);
        }
        
        if (uri.endsWith("/")) {
        	uri = uri.substring(0, uri.length() - 1);
        }
        
        if (url.endsWith("/")) {
        	url = url.substring(0, url.length() - 1);
        }

        return uri.endsWith(request.getContextPath() + url);
	}
	
	/*
	 * @see com.painiu.security.AuthenticationHandler#isCommitted(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public boolean isCommitted(HttpServletRequest request, HttpServletResponse response) {
		return request.getAttribute(SECURITY_COMMITTED_KEY) != null;
	}

	/*
	 * @see com.painiu.security.AuthenticationHandler#onPreAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void onPreAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
	}

	/*
	 * @see com.painiu.security.AuthenticationHandler#onSuccessfulAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.acegisecurity.Authentication)
	 */
	public void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
		
	}

	/*
	 * @see com.painiu.security.AuthenticationHandler#onUnsuccessfulAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.acegisecurity.AuthenticationException)
	 */
	public void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
		
	}

	/*
	 * @see com.painiu.security.AuthenticationHandler#attemptAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		return null;
	}

	/*
	 * @see com.painiu.security.AuthenticationHandler#supports(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public boolean supports(HttpServletRequest request, HttpServletResponse response) {
		return requiresAuthentication(request, response);
	}

	public static void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) 
																					throws IOException {
		if (!url.startsWith("http://") && !url.startsWith("https://")) {
			url = request.getContextPath() + url;
		}

		response.sendRedirect(response.encodeRedirectURL(url));
		
		request.setAttribute(SECURITY_COMMITTED_KEY, Boolean.TRUE);
	}
	
	public static void forward(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
		try {
			request.getRequestDispatcher(url).forward(request, response);
			
			request.setAttribute(SECURITY_COMMITTED_KEY, Boolean.TRUE);
		} catch (ServletException se) {
			log.error("ServletException occurred while forwarding to: " + url, se);
		}
	}
	
    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

	//~ Accessors ==============================================================

	/**
	 * @return the authenticationDetailsSource
	 */
	public AuthenticationDetailsSource getAuthenticationDetailsSource() {
		return authenticationDetailsSource;
	}
	
	/**
	 * @param authenticationDetailsSource the authenticationDetailsSource to set
	 */
	public void setAuthenticationDetailsSource(
			AuthenticationDetailsSource authenticationDetailsSource) {
		this.authenticationDetailsSource = authenticationDetailsSource;
	}
	
	/**
	 * @return the authenticationManager
	 */
	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}
	
	/**
	 * @param authenticationManager the authenticationManager to set
	 */
	public void setAuthenticationManager(
			AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	/**
	 * @return the filterProcessesUrl
	 */
	public String getFilterProcessesUrl() {
		return filterProcessesUrl;
	}
	
	/**
	 * @param filterProcessesUrl the filterProcessesUrl to set
	 */
	public void setFilterProcessesUrl(String filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
	}
}
