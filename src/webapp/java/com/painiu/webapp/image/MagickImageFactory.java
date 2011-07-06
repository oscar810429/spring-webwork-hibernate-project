/*
 * @(#)MagickImageFactory.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.image;

/**
 * <p>
 * <a href="MagickImageFactory.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: MagickImageFactory.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class MagickImageFactory extends ImageFactory {

	/*
	 * @see com.mingda.webapp.image.ImageFactory#createImage(java.lang.String)
	 */
	public Image createImage(String filename) {
		return new MagickImage(filename);
	}

}
