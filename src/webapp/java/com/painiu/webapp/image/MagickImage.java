/*
 * @(#)MagickImage.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.image;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

import magick.ImageInfo;
import magick.MagickException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * <a href="MagickImage.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: MagickImage.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class MagickImage implements Image {
	//~ Static fields/initializers =============================================

	private static final Logger logger = LoggerFactory.getLogger(MagickImage.class);
	
	private static final Map<String, Type> TYPE_MAP = new HashMap<String, Type>();
	
	static {
		TYPE_MAP.put("JPEG", Type.JPEG);
		TYPE_MAP.put("JPG",  Type.JPEG);
		TYPE_MAP.put("GIF",  Type.GIF);
		TYPE_MAP.put("PNG",  Type.PNG);
		TYPE_MAP.put("BMP",  Type.BMP);
	}
	
	//~ Instance fields ========================================================

	private ImageInfo info;
	private magick.MagickImage image;
	
	private String filename;
	private Dimension size;
	private Type type;
	
	//~ Constructors ===========================================================
	
	public MagickImage(String filename) {
		this.filename = filename;
		try {
			info = new ImageInfo(filename);
		} catch (MagickException e) {
			logger.warn("MagickException occurred while reading image: " + filename);
			throw new ImageLoadingException(filename, e.getMessage(), e);
		}
	}
	
	private MagickImage(ImageInfo info, magick.MagickImage image) {
		this.info = info;
		this.image = image;
	}

	//~ Methods ================================================================
	
	public Image ping() {
		try {
			if (image != null) {
				image.destroyImages();
			}
			image = new magick.MagickImage(info, true);
		} catch (MagickException e) {
			logger.warn("MagickException occurred while reading image: " + filename);
			throw new ImageLoadingException(filename, e.getMessage(), e);
		}
		
		return this;
	}

	public Image load() {
		try {
			if (image != null) {
				image.readImage(info);
			} else {
				image = new magick.MagickImage(info);
			}
		} catch (MagickException e) {
			logger.warn("MagickException occurred while loading image: " + filename);
			throw new ImageLoadingException(filename, e.getMessage(), e);
		}
		
		return this;
	}
	
	public Dimension getSize() {
		if (size == null) {
			if (image == null) {
				load();
			}
			try {
				size = image.getDimension();
			} catch (MagickException e) {
				logger.error("MagickException: ", e);
				throw new ImageException(this.filename, null, e);
			}
		}
		return size;
	}
	
	public Type getType() {
		if (type == null) {
			if (image == null) {
				load();
			}
			try {
				String magick = image.getImageFormat();
				type = TYPE_MAP.get(magick != null ? magick.toUpperCase() : "");
			} catch (MagickException e) {
				logger.error("MagickException: ", e);
			}
		}
		return type;
	}
	
	/*
	 * @see com.mingda.webapp.image.Image#strip()
	 */
	public Image strip() {
		try {
			image.stripImage();
			//image.enhanceImage();
		} catch (MagickException e) {
			throw new ImageProcessingException(e);
		}
		return this;
	}

	/*
	 * @see com.mingda.webapp.image.Image#setColorspace(int)
	 */
	
	public Image setColorspace(int colorspace) {
		try {
			image.setImageColorspace(colorspace);
			//info.setColorspace(colorspace);
		} catch (MagickException e) {
			throw new ImageProcessingException(e);
		}
		return this;
	}

	/*
	 * @see com.mingda.webapp.image.Image#crop(java.awt.Rectangle)
	 */
	public Image crop(Rectangle rect) {
		try {
			magick.MagickImage croped = image.cropImage(rect);
			return new MagickImage(info, croped);
		} catch (MagickException e) {
			throw new ImageProcessingException(e);
		}
	}

	/*
	 * @see com.mingda.webapp.image.Image#resize(int, int, int, double)
	 */
	public Image resize(int cols, int rows, int filter, double blur) {
		try {
			//magick.MagickImage resized = image.resizeImage(cols, rows, filter, blur);
			magick.MagickImage resized = image.scaleImage(cols, rows);
			return new MagickImage(info, resized);
		} catch (MagickException e) {
			throw new ImageProcessingException(e);
		}
	}

	/*
	 * @see com.mingda.webapp.image.Image#setQuality(int)
	 */
	public Image setQuality(int value) {
		try {
			info.setQuality(value);
		} catch (MagickException e) {
			throw new ImageProcessingException(e);
		}
		return this;
	}

	/*
	 * @see com.mingda.webapp.image.Image#sharpen(double, double)
	 */
	public Image sharpen(double raduis, double sigma) {
		try {
			magick.MagickImage sharped = image.sharpenImage(raduis, sigma);
			return new MagickImage(info, sharped);
		} catch (MagickException e) {
			throw new ImageProcessingException(e);
		}
	}
	
	/*
	 * @see com.mingda.webapp.image.Image#write(java.lang.String)
	 */
	public Image write(String filename) {
		try {
			image.setFileName(filename);
			image.writeImage(info);
		} catch (MagickException e) {
			throw new ImageProcessingException(e);
		}
		return this;
	}

	public void destroy() {
		if (info != null && filename != null) {
			info.destroyImageInfo();
		}
		if (image != null) {
			image.destroyImages();
		}
	}
}