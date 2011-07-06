/**
 * @(#)LoginRequiredException.java Oct 22, 2008
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.exception;

/**
 * <p>
 * <a href="LoginRequiredException.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: LoginRequiredException.java 6 2010-05-11 16:20:57Z zhangsf $
 */
public class LoginRequiredException extends RuntimeException {
	
	private static final long serialVersionUID = 5813482491442699841L;
	
	String loginUri;
	
	public LoginRequiredException(String loginUri) {
		super();
		
		this.loginUri = loginUri;
	}
	
	/**
	 * @return the loginUri
	 */
	public String getLoginUri() {
		return loginUri;
	}
	
	/**
	 * @param loginUri the loginUri to set
	 */
	public void setLoginUri(String loginUri) {
		this.loginUri = loginUri;
	}
}