/*
 * @(#)UsernameExistsException.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.exception;

/**
 * <p>
 * <a href="UsernameExistsException.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: UsernameExistsException.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public class UsernameExistsException extends UserExistsException {
	private static final long serialVersionUID = 7723358055195331528L;
	
	private String username;
	
	public UsernameExistsException(String username) {
		super("Username '" + username + "' is exists");
		this.username = username;
	}
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
}
