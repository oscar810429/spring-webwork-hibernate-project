/*
 * @(#)PhotoStoreUtils.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.util;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;

import com.painiu.Painiu;
import com.painiu.webapp.image.Repository;
import com.painiu.core.model.PhotoAddress;

/**
 * <p>
 * <a href="PhotoStoreUtils.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: PhotoAddressUtils.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class PhotoAddressUtils {

	public static PhotoAddress newAddress() {
		Repository repository = getRepository();
		String filename = generateFilename();
		String dir = filename.substring(0, 8);
		filename = filename.substring(9);
		String secret = RandomStringUtils.randomAlphabetic(8).toLowerCase();
		
		return new PhotoAddress(repository.getHost(), dir, filename, secret);
	}

	private static Repository getRepository() {
		return Painiu.getPhotoConfig().getRepository();
	}
	
	private static SecureRandom secureRand = new SecureRandom();
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd/HHmmss_");
	
	public static String generateFilename() {
		String newName = formatter.format(new Date(System.currentTimeMillis()));
        int randInt = Math.abs(secureRand.nextInt());

        return newName + randInt;
	}
	
}
