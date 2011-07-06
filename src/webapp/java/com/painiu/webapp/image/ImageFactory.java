/*
 * @(#)ImageFactory.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.image;

import java.io.File;

/**
 * <p>
 * <a href="ImageFactory.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ImageFactory.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public abstract class ImageFactory {

	private static final ImageFactory DEFAULT_FACTORY = new MagickImageFactory();
	
	public static ImageFactory getFactory() {
		return DEFAULT_FACTORY;
	}
	
	public Image createImage(File file) {
		return createImage(file.getAbsolutePath());
	}
	
	public abstract Image createImage(String filename);
	
}
