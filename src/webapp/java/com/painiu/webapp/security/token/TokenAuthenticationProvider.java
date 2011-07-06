/*
 * @(#)TokenAuthenticationProvider.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.security.token;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.providers.AuthenticationProvider;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.util.Assert;

import com.painiu.core.service.TokenManager;
import com.painiu.core.model.Token;
import com.painiu.core.model.User;

/**
 * <p>
 * <a href="TokenAuthenticationProvider.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: TokenAuthenticationProvider.java 38 2010-06-01 02:18:07Z zhangsf $
 */
public class TokenAuthenticationProvider implements AuthenticationProvider, InitializingBean {
	
	private TokenManager tokenManager;
	private UserDetailsService userDetailsService;
	
	/**
	 * @param tokenManager the tokenManager to set
	 */
	public void setTokenManager(TokenManager tokenManager) {
		this.tokenManager = tokenManager;
	}
	
	/**
	 * @param userDetailsService the userDetailsService to set
	 */
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	/*
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(tokenManager);
		Assert.notNull(userDetailsService);
	}

	/*
	 * @see org.acegisecurity.providers.AuthenticationProvider#authenticate(org.acegisecurity.Authentication)
	 */
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		if (!supports(authentication.getClass())) {
			return null;
		}
		
		String tokenId = ((TokenAuthenticationToken) authentication).getToken();
		
		try {
			Token token = tokenManager.getToken(tokenId);
			
			if (!token.expired()) {
				User user = token.getUser();
				
				UserDetails userDetails = userDetailsService.loadUserByUsername(user.getId());
				
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
						userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
				
				auth.setDetails(authentication.getDetails());
        		
        		return auth;
			}
		} catch (ObjectRetrievalFailureException e) {
		
		}
		
		return null;
	}

	/*
	 * @see org.acegisecurity.providers.AuthenticationProvider#supports(java.lang.Class)
	 */
	public boolean supports(Class authentication) {
		return (TokenAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
