/**
 * @(#)InvalidHttpMethodException.java Oct 22, 2008
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.exception;

/**
 * <p>
 * <a href="InvalidHttpMethodException.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: InvalidHttpMethodException.java 6 2010-05-11 16:20:57Z zhangsf $
 */
public class InvalidHttpMethodException extends RuntimeException {

	private static final long serialVersionUID = -3723034820186716729L;

	/**
	 * 
	 */
	public InvalidHttpMethodException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidHttpMethodException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public InvalidHttpMethodException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public InvalidHttpMethodException(Throwable cause) {
		super(cause);
	}

}
