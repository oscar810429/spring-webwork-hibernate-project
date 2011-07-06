/*
 * @(#)ImageLoadingException.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.image;

/**
 * <p>
 * <a href="ImageLoadingException.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ImageLoadingException.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class ImageLoadingException extends ImageException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5515418807117698171L;

	public ImageLoadingException(String filename) {
		super(filename);
	}
	
	public ImageLoadingException(String filename, String message) {
		super(filename, message);
	}
	
	public ImageLoadingException(String filename, String message, Throwable cause) {
		super(filename, message, cause);
	}
}
