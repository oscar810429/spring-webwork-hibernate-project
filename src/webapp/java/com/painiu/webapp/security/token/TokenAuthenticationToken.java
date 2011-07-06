/*
 * @(#)TokenAuthenticationToken.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.security.token;

import org.acegisecurity.providers.AbstractAuthenticationToken;

/**
 * <p>
 * <a href="TokenAuthenticationToken.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: TokenAuthenticationToken.java 38 2010-06-01 02:18:07Z zhangsf $
 */
public class TokenAuthenticationToken extends AbstractAuthenticationToken {
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	private String token;
	
	//~ Constructors ===========================================================

	public TokenAuthenticationToken(String token) {
		super(null);
        this.token = token;
        setAuthenticated(false);
	}
	
	//~ Methods ================================================================

	/*
	 * @see org.acegisecurity.Authentication#getCredentials()
	 */
	public Object getCredentials() {
		return null;
	}
	
	/*
	 * @see org.acegisecurity.Authentication#getPrincipal()
	 */
	public Object getPrincipal() {
		return null;
	}
	
	//~ Accessors ==============================================================

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
}
