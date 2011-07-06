/**
 * @(#)NeedRedirectionException.java Oct 22, 2008
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.exception;

/**
 * <p>
 * <a href="NeedRedirectionException.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: NeedRedirectionException.java 6 2010-05-11 16:20:57Z zhangsf $
 */
public class NeedRedirectionException extends RuntimeException {
	
	private static final long serialVersionUID = 639758144320526726L;
	
	String redirectTo;
	
	public NeedRedirectionException(String redirectTo) {
		super();
		
		this.redirectTo = redirectTo;
	}
	
	/**
	 * @param redirectTo the redirectTo to set
	 */
	public void setRedirectTo(String redirectTo) {
		this.redirectTo = redirectTo;
	}
	
	/**
	 * @return the redirectTo
	 */
	public String getRedirectTo() {
		return redirectTo;
	}
}
