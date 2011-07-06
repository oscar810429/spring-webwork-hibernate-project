/*
 * @(#)LogoutHandler.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;

/**
 * <p>
 * <a href="LogoutHandler.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: LogoutHandler.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public interface LogoutHandler {
	
	public boolean isCommitted(HttpServletRequest request, 
			HttpServletResponse response);
	
	public boolean supports(HttpServletRequest request, 
			HttpServletResponse response, Authentication auth);
	
	public void logout(HttpServletRequest request, 
			HttpServletResponse response, Authentication authentication) throws IOException;
}
