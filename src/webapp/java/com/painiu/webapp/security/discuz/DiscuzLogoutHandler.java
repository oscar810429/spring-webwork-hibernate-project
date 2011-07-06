/*
 * @(#)DiscuzLogoutHandler.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.security.discuz;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.painiu.webapp.security.AbstractAuthenticationHandler;
import com.painiu.webapp.security.LogoutHandler;
import com.painiu.webapp.util.CookieUtils;

/**
 * <p>
 * <a href="DiscuzLogoutHandler.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: DiscuzLogoutHandler.java 38 2010-06-01 02:18:07Z zhangsf $
 */
public class DiscuzLogoutHandler implements LogoutHandler, InitializingBean {
	//~ Static fields/initializers =============================================

	private static final String DISCUZ_AUTH_COOKIE_NAME = "cdb_auth";
	private static final String DISCUZ_SESSION_COOKIE_NAME = "cdb_sid";
	private static final String DISCUZ_COOKIE_PATH = "/";
	
	//~ Instance fields ========================================================

	private DiscuzPassport passport;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	/*
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(passport);
	}
	
	/**
	 * @param passport the passport to set
	 */
	public void setPassport(DiscuzPassport passport) {
		this.passport = passport;
	}
	
	/**
	 * @return the passport
	 */
	public DiscuzPassport getPassport() {
		return passport;
	}
	
	/*
	 * @see com.painiu.security.LogoutHandler#isCommitted(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public boolean isCommitted(HttpServletRequest request,
			HttpServletResponse response) {
		return request.getAttribute(AbstractAuthenticationHandler.SECURITY_COMMITTED_KEY) != null;
	}

	/*
	 * @see com.painiu.security.LogoutHandler#supports(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.acegisecurity.Authentication)
	 */
	public boolean supports(HttpServletRequest request,
			HttpServletResponse response, Authentication auth) {
		if (passport.getForward(request) != null) {
			return true;
		}
		
		Cookie[] cookies = request.getCookies();
		
		if (cookies == null) {
			return false;
		}
		
		for (int i = 0; i < cookies.length; i++) {
			if (DISCUZ_AUTH_COOKIE_NAME.equals(cookies[i].getName())) {
				return true;
			}
		}
		
		return false;
	}

	/*
	 * @see org.acegisecurity.ui.logout.LogoutHandler#logout(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.acegisecurity.Authentication)
	 */
	public void logout(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) throws IOException {
		if (passport.getForward(request) != null) {
			AbstractAuthenticationHandler.sendRedirect(request, response, 
					passport.buildLogoutUrl(request, response, authentication));
		} else {
			response.addCookie(CookieUtils.makeLogoutCookie(DISCUZ_AUTH_COOKIE_NAME, DISCUZ_COOKIE_PATH));
			response.addCookie(CookieUtils.makeLogoutCookie(DISCUZ_SESSION_COOKIE_NAME, DISCUZ_COOKIE_PATH));
		}
	}

}
