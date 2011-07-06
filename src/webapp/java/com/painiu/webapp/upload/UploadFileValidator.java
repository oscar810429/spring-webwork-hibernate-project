/*
 * @(#)UploadFileValidator.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.upload;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.painiu.Painiu;

/**
 * <p>
 * <a href="UploadFileValidator.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: UploadFileValidator.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class UploadFileValidator {
   
    protected static long maximumImageSize;
    protected static Set imageTypesSet = Collections.EMPTY_SET;
    protected static Set zipTypesSet = Collections.EMPTY_SET;
    
    static {
        maximumImageSize = Painiu.getPhotoConfig().getMaxLength();
        
        imageTypesSet = Painiu.getPhotoConfig().getImageTypes();
        zipTypesSet = Painiu.getPhotoConfig().getZipTypes();
    }
    
    public static boolean isTooLarge(long fileLength) {
        return fileLength > maximumImageSize;
    }
    
    public static boolean isImageFile(String contentType) {
    	contentType = stripContentType(contentType);
        return (!imageTypesSet.isEmpty()) && (containsItem(imageTypesSet, contentType));
    }
    
    public static boolean isZipFile(String contentType) {
    	contentType = stripContentType(contentType);
        return (!zipTypesSet.isEmpty()) && (containsItem(zipTypesSet, contentType));
    }
 
    private static String stripContentType(String contentType) {
    	if (contentType != null) { 
    		int index = contentType.indexOf(';');
    		if (index != -1) {
    			return contentType.substring(0, index);
    		}
    	}
    	return contentType;
    }
    
    /**
     * @param itemCollection - Collection of string items (all lowercase).
     * @param key            - Key to search for.
     * @return true if itemCollection contains the key, false otherwise.
     */
    private static boolean containsItem(Collection itemCollection, String key) {
        if (key == null) {
            return false;
        }
        return itemCollection.contains(key.toLowerCase());
    }
}
