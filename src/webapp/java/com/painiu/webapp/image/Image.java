/*
 * @(#)Image.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.image;

import java.awt.Dimension;
import java.awt.Rectangle;

/**
 * <p>
 * <a href="Image.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Image.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public interface Image {
	
	public enum Type {
		JPEG("image/jpeg"),
		GIF ("image/gif"),
		PNG ("image/png"),
		BMP ("image/bmp");
		
		private final String contentType;
		
		Type(String contentType) {
			this.contentType = contentType;
		}
		
		public String getContentType() {
			return contentType;
		}
	}
	
	public Image ping();
	
	public Image load();
	
	public Dimension getSize();
	
	public Type getType();
	
	/**
	 * Remove attributes of this image.
	 * @return
	 */
	public Image strip();
	
	public Image setColorspace(int colorspace);
	
	public Image resize(int cols, int rows, int filter, double blur);
	
	public Image crop(Rectangle rect);
	
	public Image sharpen(double raduis, double sigma);
	
	public Image setQuality(int value);
	
	public Image write(String filename);
	
	public void destroy();
}
