/*
 * @(#)DiscuzAuthenticationHandler.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.security.discuz;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.painiu.webapp.security.AbstractAuthenticationHandler;
import com.painiu.webapp.security.DefaultAuthenticationHandler;

/**
 * <p>
 * <a href="DiscuzAuthenticationHandler.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: DiscuzAuthenticationHandler.java 38 2010-06-01 02:18:07Z zhangsf $
 */
public class DiscuzAuthenticationHandler extends AbstractAuthenticationHandler implements InitializingBean {
	//~ Static fields/initializers =============================================

	private static final Log log = LogFactory.getLog(DiscuzAuthenticationHandler.class);
	
	//~ Instance fields ========================================================

	private DiscuzPassport passport;
	
	private DefaultAuthenticationHandler defaultHandler;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================
	
	/*
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(passport);
		Assert.notNull(defaultHandler);
	}

	/*
	 * @see com.yupoo.security.AbstractAuthenticationHandler#requiresAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		return false; // no, I don't do authentication work
	}
	
	/*
	 * @see com.painiu.security.AbstractAuthenticationHandler#onPreAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void onPreAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (request.getParameter(DiscuzPassport.DISCUZ_PASSPORT_FORWARD_KEY) != null) {
			request.getSession().setAttribute(SECURITY_FORWARD_KEY, 
					request.getParameter(DiscuzPassport.DISCUZ_PASSPORT_FORWARD_KEY));
		}
	}
	
	/*
	 * @see com.painiu.security.AuthenticationHandler#onSuccessfulAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.acegisecurity.Authentication)
	 */
	@Override
	public void onSuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, Authentication authResult)
			throws IOException {
		
		String discuzLoginUrl = passport.buildLoginUrl(request, response, authResult);
		
		if (log.isDebugEnabled()) {
			log.debug("DiscuzAuthentication success, redirect to: " + discuzLoginUrl);
		}
		
		sendRedirect(request, response, discuzLoginUrl);
	}

	/*
	 * @see com.painiu.security.AuthenticationHandler#supports(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public boolean supports(HttpServletRequest request, HttpServletResponse response) {
		return passport.isDiscuzRequest(request, response);
	}
	
	//~ Accessors ==============================================================

	/**
	 * @return the passport
	 */
	public DiscuzPassport getPassport() {
		return passport;
	}
	
	/**
	 * @param passport the passport to set
	 */
	public void setPassport(DiscuzPassport passport) {
		this.passport = passport;
	}
	
	/**
	 * @return the defaultHandler
	 */
	public DefaultAuthenticationHandler getDefaultHandler() {
		return defaultHandler;
	}
	
	/**
	 * @param defaultHandler the defaultHandler to set
	 */
	public void setDefaultHandler(DefaultAuthenticationHandler defaultHandler) {
		this.defaultHandler = defaultHandler;
	}
}
