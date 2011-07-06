/*
 * @(#)ImageException.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.image;

/**
 * <p>
 * <a href="ImageException.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ImageException.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class ImageException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4444013238262667545L;
	private String filename;
	
	public ImageException(String filename) {
		this.filename = filename;
	}
	
	public ImageException(String filename, String message) {
		super(message);
		this.filename = filename;
	}
	
	public ImageException(String filename, String message, Throwable cause) {
		super(message, cause);
		this.filename = filename;
	}
	
	
	public String getFilename() {
		return filename;
	}
}
