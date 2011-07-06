/*
 * @(#)UserExistsException.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.exception;

/**
 * <p>
 * <a href="UserExistsException.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: UserExistsException.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public class UserExistsException extends RuntimeException {
	
	private static final long serialVersionUID = 4050482305178810162L;
	
	/**
     * Constructor for UserExistsException.
     *
     * @param message
     */
    public UserExistsException(String message) {
        super(message);
    }
    
    public UserExistsException() {
    	super();
    }
}