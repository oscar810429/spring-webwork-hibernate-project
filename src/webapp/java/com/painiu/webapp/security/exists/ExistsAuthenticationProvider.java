/*
 * @(#)ExistsAuthenticationProvider.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.security.exists;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.providers.AuthenticationProvider;


/**
 * <p>
 * <a href="ExistsAuthenticationProvider.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ExistsAuthenticationProvider.java 38 2010-06-01 02:18:07Z zhangsf $
 */
public class ExistsAuthenticationProvider implements AuthenticationProvider {

	/*
	 * @see org.acegisecurity.providers.AuthenticationProvider#authenticate(org.acegisecurity.Authentication)
	 */
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		if (!supports(authentication.getClass())) {
			return null;
		}
		
		return ((ExistsAuthenticationToken) authentication).getAuthentication();
	}

	/*
	 * @see org.acegisecurity.providers.AuthenticationProvider#supports(java.lang.Class)
	 */
	public boolean supports(Class authentication) {
		return (ExistsAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
