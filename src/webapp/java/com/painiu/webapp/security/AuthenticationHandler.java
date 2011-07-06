/*
 * @(#)AuthenticationHandler.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;

/**
 * <p>
 * <a href="AuthenticationHandler.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: AuthenticationHandler.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public interface AuthenticationHandler {
	
	public boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response);
	
	public void onPreAuthentication(HttpServletRequest request, 
			HttpServletResponse response) throws IOException;
	
	public Authentication attemptAuthentication(HttpServletRequest request, 
			HttpServletResponse response) throws AuthenticationException;
	
	public void onSuccessfulAuthentication(HttpServletRequest request, 
			HttpServletResponse response, Authentication authResult)
			throws IOException;
	
	public void onUnsuccessfulAuthentication(HttpServletRequest request, 
			HttpServletResponse response, AuthenticationException failed)
			throws IOException;
	
	public boolean supports(HttpServletRequest request, HttpServletResponse response);
	
	public boolean isCommitted(HttpServletRequest request, HttpServletResponse response);
}
