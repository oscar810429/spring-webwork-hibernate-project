/*
 * @(#)ExistsAuthenticationHandler.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.security.exists;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.anonymous.AnonymousAuthenticationToken;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.painiu.webapp.security.AbstractAuthenticationHandler;

/**
 * <p>
 * When the user made an authentication request from discuz, and he/she was already
 * authenticated at yupoo.com, we should not show the singin form but automatically 
 * sign him/she in.
 * <p>
 * This handler will make this work.
 * 
 * <p>
 * <a href="ExistsAuthenticationHandler.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ExistsAuthenticationHandler.java 38 2010-06-01 02:18:07Z zhangsf $
 */
public class ExistsAuthenticationHandler extends AbstractAuthenticationHandler implements InitializingBean {
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	//~ Constructors ===========================================================

	//~ Methods ================================================================
	
	/*
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.hasLength(this.filterProcessesUrl);
		Assert.notNull(this.getAuthenticationManager());
	}
	
	/*
	 * @see com.yupoo.security.AbstractAuthenticationHandler#attemptAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		ExistsAuthenticationToken authRequest = new ExistsAuthenticationToken(
				SecurityContextHolder.getContext().getAuthentication(), false);
		
		return this.getAuthenticationManager().authenticate(authRequest);
	}
	
	/*
	 * @see com.painiu.security.AbstractAuthenticationHandler#supports(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public boolean supports(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth == null) {
			return false;
		}
		if (!auth.isAuthenticated()) {
			return false;
		}
		if (AnonymousAuthenticationToken.class.isAssignableFrom(auth.getClass())) {
			return false;
		}
		return true;
	}
	
	//~ Accessors ==============================================================

}
