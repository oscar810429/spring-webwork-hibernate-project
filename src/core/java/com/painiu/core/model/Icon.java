/*
 * @(#)Icon.java  2009-11-13
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

//import org.apache.commons.lang.StringUtils;

import com.painiu.Painiu;

/**
 * <p>
 * <a href="Icon.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Icon.java 52 2010-06-15 09:28:19Z zhangsf $
 */
public class Icon extends PhotoAddress implements Serializable {

	//~ Static fields/initializers =============================================

	private static final long serialVersionUID = -5818851903509652966L;

	//~ Instance fields ========================================================
	
	//~ Constructors ===========================================================

	public Icon() {
	}
	
	public Icon(Integer host, String dir, String filename) {
		super(host, dir, filename, null);
	}
	
	public Icon(PhotoAddress address) {
		this(address.getHost(), address.getDir(), address.getFilename());
	}
	
	//~ Methods ================================================================
	
    public String getURL() {
    	if (Photo.USE_PNFS) {
    		/*if (fileKey == null) {
    			return getDefaultURL();
    		}
    		String pattern = MultiDomainContextHolder.getContext().getIconURLPattern();
        	pattern = StringUtils.replace(pattern, "${user}", username);
        	return StringUtils.replace(pattern, "${filename}", fileKey);*/
    	}else{
    		String pattern = Painiu.getIconConfig().getUrlPattern();
        	pattern = StringUtils.replace(pattern, "${host}", host.toString());
        	pattern = StringUtils.replace(pattern, "${dir}", dir);
        	return StringUtils.replace(pattern, "${filename}", filename+Painiu.SUFFIX_MEDUIM);
    	}
    	if (host == null || dir == null || filename == null) {
    		return getDefaultURL();
    	}
    	return super.getURL("icon");
    }
    
    public static String getDefaultURL() {
    	return Painiu.getIconConfig().getBuddy();
    }
	
    public static String getStaffIconURL() {
    	return Painiu.getIconConfig().getStaff();
    }
    
    public static String getProIconURL() {
    	return Painiu.getIconConfig().getPro();
    }
    
    public static String getHotIconURL() {
    	return Painiu.getIconConfig().getHot();
    }
    
    public static String getVolunteerIconURL() {
    	return Painiu.getIconConfig().getVolunteer();
    }
    
    public static String getVIPNormalIconURL() {
    	return Painiu.getIconConfig().getVipNormal();
    }
    
    public static String getVIPBusinessIconURL() {
    	return Painiu.getIconConfig().getVipBusiness();
    }
}
