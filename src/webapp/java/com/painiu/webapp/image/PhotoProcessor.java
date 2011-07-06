/*
 * @(#)UploadProcessor.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.image;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;

import magick.FilterType;

import com.painiu.Painiu;
import com.painiu.webapp.image.Image;
import com.painiu.webapp.image.ImageProcessingException;

/**
 * <p>
 * <a href="UploadProcessor.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: PhotoProcessor.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class PhotoProcessor {
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	//~ Constructors ===========================================================

	private PhotoProcessor() {
	}
	
	//~ Methods ================================================================
	
//	public static Dimension process(File file, File distFile) throws IOException {
//		return process(file.getAbsolutePath(), distFile.getAbsolutePath());
//	}
	
	public static void makeIcon(Image image, String distFilename, int size) throws ImageProcessingException {
		createDirectoryIfNotExists(distFilename);
		
		Image resized = null;
		Image cropped = null;
		
		try {
			Rectangle rect = parseGeometry(image.getSize(), size, true);
            resized = image.resize(rect.width, rect.height, FilterType.LanczosFilter, 1.0);
            
            rect.x = rect.y = 0;
            if (rect.width > size) {
                rect.x = (rect.width - size)/2;
            } else if (rect.height > size) {
                rect.y = (rect.height - size)/2;
            }
            rect.width = rect.height = size;
            
            cropped = resized.crop(rect);
            cropped.setQuality(96);
            cropped.write(distFilename);
            
		} finally {
			if (resized != null) {
				resized.destroy();
			}
			if (cropped != null) {
				cropped.destroy();
			}
		}
	}
	
    /**
     * 
     * @param image
     * @param disFilename
     * @throws ImageProcessingException
     */
    public static void process(Image image, String distFilename) throws ImageProcessingException {
    	createDirectoryIfNotExists(distFilename);
    	
		Image sharped = null;
		Image resized = null;
		Image cropped = null;
		
		try {
			// StripImage
			//image.strip();
			// TODO do not use jmagick class ColorspaceType here...
			//image.setColorspace(ColorspaceType.RGBColorspace);
			
			Dimension dimension = image.getSize();
			Rectangle rect = null;
			
			// version 1024x1024 
			rect = parseGeometry(dimension, 1024, false);
			resized = image.resize(rect.width, rect.height, FilterType.LanczosFilter, 1.0);
			sharped = resized.sharpen(1.0, 5.0);
			sharped.setQuality(98);
			sharped.write(photoFilename(distFilename, Painiu.SUFFIX_LARGE));
			resized.destroy();
			sharped.destroy();
			
			// version 500x500 
			rect = parseGeometry(dimension, 500, false);
			resized = image.resize(rect.width, rect.height, FilterType.LanczosFilter, 1.0);
			sharped = resized.sharpen(1.0, 5.0);
			sharped.setQuality(98);
			sharped.write(photoFilename(distFilename, Painiu.SUFFIX_MEDUIM));
			resized.destroy();
			sharped.destroy();
			
			// version 240x240
			rect = parseGeometry(dimension, 240, false);
			resized = image.resize(rect.width, rect.height, FilterType.LanczosFilter, 1.0);
			sharped = resized.sharpen(1.0, 5.0);
			sharped.setQuality(90);
			sharped.write(photoFilename(distFilename, Painiu.SUFFIX_SMALL));
			resized.destroy();
			sharped.destroy();
			
			// version 100x100
			rect = parseGeometry(dimension, 100, false);
			resized = image.resize(rect.width, rect.height, FilterType.LanczosFilter, 1.0);
			sharped = resized.sharpen(1.0, 5.0);
			sharped.setQuality(96);
			sharped.write(photoFilename(distFilename, Painiu.SUFFIX_THUMB));
			resized.destroy();
			sharped.destroy();
			
			// version 75x75
			rect = parseGeometry(dimension, 75, true);
			resized = image.resize(rect.width, rect.height, FilterType.LanczosFilter, 1.0);
			
			rect.x = rect.y = 0;
			if (rect.width > 75) {
				rect.x = (rect.width - 75)/2;
			} else if (rect.height > 75) {
				rect.y = (rect.height - 75)/2;
			}
			rect.width = rect.height = 75;
			
			cropped = resized.crop(rect);
			sharped = cropped.sharpen(1.0, 5.0);
			sharped.setQuality(96);
			sharped.write(photoFilename(distFilename, Painiu.SUFFIX_SQUARE));
			
		} finally {
			if (sharped != null) {
				sharped.destroy();
			}
			if (resized != null) {
				resized.destroy();
			}
			if (cropped != null) {
				cropped.destroy();
			}
		}
    }

    private static void createDirectoryIfNotExists(String filename) {
    	File distFile = new File(filename);
		File parentFile = distFile.getParentFile();
		
		if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
    }
    
	private static Rectangle parseGeometry(Dimension dimension, int max, boolean slice) {
		int imageWidth = dimension.width;
		int imageHeight = dimension.height;
		
		int thumbWidth = max;
		int thumbHeight = max;
		
		if (max < 0 || (imageWidth < max && imageHeight < max && !slice)) {
			thumbWidth = imageWidth;
			thumbHeight = imageHeight;
		} else {
			double thumbRatio = (double) thumbWidth / (double) thumbHeight;
			double photoRatio = (double) imageWidth / (double) imageHeight;
			
			if (slice) {
				if (thumbRatio < photoRatio)
					thumbWidth = (int) Math.round(thumbHeight * photoRatio);
				else
					thumbHeight = (int) Math.round(thumbWidth / photoRatio);
			} else {
				if (thumbRatio < photoRatio)
					thumbHeight = (int) Math.round(thumbWidth / photoRatio);
				else
					thumbWidth = (int) Math.round(thumbHeight * photoRatio);
			}
		}
		return new Rectangle(0, 0, thumbWidth, thumbHeight);
	}
	
	private static String photoFilename(String original, String suffix) {
		int lastUnderline = original.lastIndexOf('_');
		if (lastUnderline != -1) {
			return original.substring(0, lastUnderline) + suffix;	
		}
		int lastDot = original.lastIndexOf('.');
		if (lastDot != -1) {
			return original.substring(0, lastDot) + suffix;
		}
		return original + suffix;
	}
	
//	public static void main(String[] args) throws IOException {
//		UploadProcessor.generateImages("/home/zola/tmp/test_o.jpg");
//	}
}
