/*
 * @(#)ApiAuth.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.service.api;

import java.io.Serializable;

import com.painiu.core.model.Authentication.Permission;

/**
 * <p>
 * <a href="ApiAuth.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ApiAuth.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class ApiAuth implements Serializable {
	//~ Static fields/initializers =============================================
    private static final long serialVersionUID = -6111824211872512337L;
	//~ Instance fields ========================================================

	private String token;
	private String principal;
	private String apiKey;
	private String secret;
	private Permission perm;
	
	//~ Constructors ===========================================================

	public ApiAuth(String token, String principal, String apiKey, String secret, Permission perm) {
		this.token = token;
		this.principal = principal;
		this.apiKey = apiKey;
		this.secret = secret;
		this.perm = perm;
	}
	
	//~ Methods ================================================================

	//~ Accessors ==============================================================

	/**
	 * @return the principal
	 */
	public String getPrincipal() {
		return principal;
	}
	
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	
	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}
	
	/**
	 * @return the secret
	 */
	public String getSecret() {
		return secret;
	}
	
	/**
	 * @return the perm
	 */
	public Permission getPerm() {
		return perm;
	}
}
