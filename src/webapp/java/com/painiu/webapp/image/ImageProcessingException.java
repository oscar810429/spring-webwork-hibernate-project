/*
 * @(#)ImageProcessingException.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.image;

/**
 * <p>
 * <a href="ImageProcessingException.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ImageProcessingException.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class ImageProcessingException extends ImageException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2646141380170953676L;

	public ImageProcessingException(String filename) {
		super(filename);
	}
	
	public ImageProcessingException(String filename, String message) {
		super(filename, message);
	}
	
	public ImageProcessingException(String filename, String message, Throwable cause) {
		super(filename, message, cause);
	}
	
	public ImageProcessingException(Throwable cause) {
		super(null, null, cause);
	}
}
