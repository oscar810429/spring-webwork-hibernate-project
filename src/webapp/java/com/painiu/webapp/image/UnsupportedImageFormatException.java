/*
 * @(#)UnsupportedImageFormatException.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.image;

/**
 * <p>
 * <a href="UnsupportedImageFormatException.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: UnsupportedImageFormatException.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class UnsupportedImageFormatException extends ImageException {
	
	private static final long serialVersionUID = -3236896224129970510L;

	public UnsupportedImageFormatException(String filename) {
		super(filename);
	}
	
	public UnsupportedImageFormatException(String filename, String message) {
		super(filename, message);
	}
	
	public UnsupportedImageFormatException(String filename, String message, Throwable cause) {
		super(filename, message, cause);
	}
}