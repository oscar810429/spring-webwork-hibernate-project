/*
 * @(#)DefaultLogoutHandler.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;

/**
 * <p>
 * <a href="DefaultLogoutHandler.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: DefaultLogoutHandler.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class DefaultLogoutHandler implements LogoutHandler {
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	//~ Constructors ===========================================================

	//~ Methods ================================================================

	/*
	 * @see com.mingda.webapp.security.LogoutHandler#isCommitted(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public boolean isCommitted(HttpServletRequest request, HttpServletResponse response) {
		return false;
	}

	/*
	 * @see com.mingda.webapp.security.LogoutHandler#logout(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.acegisecurity.Authentication)
	 */
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {		
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

    	SecurityContextHolder.clearContext();
	}

	/*
	 * @see com.mingda.webapp.security.LogoutHandler#supports(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.acegisecurity.Authentication)
	 */
	public boolean supports(HttpServletRequest request, HttpServletResponse response, Authentication auth) {
		return true;
	}

	//~ Accessors ==============================================================

}
