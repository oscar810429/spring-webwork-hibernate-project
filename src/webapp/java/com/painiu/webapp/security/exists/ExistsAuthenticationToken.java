/*
 * @(#)ExistsAuthenticationToken.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.security.exists;

import org.acegisecurity.Authentication;
import org.acegisecurity.providers.AbstractAuthenticationToken;

/**
 * <p>
 * <a href="ExistsAuthenticationToken.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ExistsAuthenticationToken.java 38 2010-06-01 02:18:07Z zhangsf $
 */
public class ExistsAuthenticationToken extends AbstractAuthenticationToken {
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	private Authentication authentication;
	
	//~ Constructors ===========================================================

	public ExistsAuthenticationToken(Authentication auth, boolean authenticated) {
		super(auth.getAuthorities());
		this.authentication = auth;
		this.setAuthenticated(authenticated);
	}
	
	//~ Methods ================================================================

	//~ Accessors ==============================================================

	/*
	 * @see org.acegisecurity.Authentication#getCredentials()
	 */
	public Object getCredentials() {
		return authentication.getCredentials();
	}

	/*
	 * @see org.acegisecurity.Authentication#getPrincipal()
	 */
	public Object getPrincipal() {
		return authentication.getPrincipal();
	}

	/**
	 * @return the authentication
	 */
	public Authentication getAuthentication() {
		return authentication;
	}
	
	/**
	 * @param authentication the authentication to set
	 */
	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}
}
