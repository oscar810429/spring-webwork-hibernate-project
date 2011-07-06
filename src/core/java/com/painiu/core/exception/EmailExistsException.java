/**
 * @(#)EmailExistsException.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.exception;

/**
 * <p>
 * <a href="EmailExistsException.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: EmailExistsException.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public class EmailExistsException extends UserExistsException {

	private static final long serialVersionUID = -8676777143209612079L;

	private String email;
	
	public EmailExistsException(String email) {
		super("Email '" + email + "' exists");
		this.email = email;
	}
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
