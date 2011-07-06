/*
 * @(#)ImageTooLargeInDimensionException.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.image;

import java.awt.Dimension;

/**
 * <p>
 * <a href="ImageTooLargeInDimensionException.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ImageTooLargeInDimensionException.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class ImageTooLargeInDimensionException extends ImageException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8716922201674111211L;
	private Dimension size;
	
	public ImageTooLargeInDimensionException(String filename, Dimension size) {
		super(filename);
		this.size = size;
	}
	
	public ImageTooLargeInDimensionException(String filename, String message) {
		super(filename, message);
	}
	
	public Dimension getSize() {
		return size;
	}
}
