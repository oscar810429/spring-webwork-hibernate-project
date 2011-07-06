/*
 * @(#)CookieFilterResponseWrapper.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.wrapper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.painiu.webapp.util.CookieUtils;

/**
 * <p>
 * <a href="CookieFilterResponseWrapper.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: CookieFilterResponseWrapper.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class CookieFilterResponseWrapper extends HttpServletResponseWrapper {	
	//~ Static fields/initializers =============================================

	private static final Log log = LogFactory.getLog(CookieFilterResponseWrapper.class);
	
	//~ Instance fields ========================================================
	
	//~ Constructors ===========================================================
	
	public CookieFilterResponseWrapper(HttpServletResponse response) {
		super(response);
	}

	//~ Methods ================================================================
	
	/*
	 * @see javax.servlet.http.HttpServletResponseWrapper#addCookie(javax.servlet.http.Cookie)
	 */
	public void addCookie(Cookie cookie) {
		if (log.isDebugEnabled()) {
			log.debug("Add cookie: " + cookie.toString());
		}
		cookie.setDomain(CookieUtils.getCookieDomain());
		
		super.addCookie(cookie);
	}

}
