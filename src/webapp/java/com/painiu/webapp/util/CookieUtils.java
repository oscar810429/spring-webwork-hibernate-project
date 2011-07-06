/*
 * @(#)CookieUtils.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.painiu.Painiu;
import com.painiu.webapp.personality.MultiDomainContextHolder;

/**
 * <p>
 * <a href="CookieUtils.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: CookieUtils.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class CookieUtils {
	
	public static final int COOKIE_MAX_AGE = 60 * 60 * 24 * 365; // 1 years

	public static Cookie makeLogoutCookie(String key, String path) {
        Cookie cookie = new Cookie(key, null);
        cookie.setPath(path);
        cookie.setDomain(getCookieDomain());
        cookie.setMaxAge(0);

        return cookie;
    }
    
    public static Cookie makeLoginCookie(String key, String tokenValueBase64, String path) {
        Cookie cookie = new Cookie(key, tokenValueBase64);
        cookie.setPath(path);
        cookie.setDomain(getCookieDomain());
        cookie.setMaxAge(COOKIE_MAX_AGE);

        return cookie;
    }
	
	private static String cookieDomain = null;
	
	public static String getCookieDomain() {
//		if (cookieDomain == null) { 
			// see RFC 2109
//			cookieDomain = "." + "yupoo.js.vnet.cn";
		if (MultiDomainContextHolder.getContext() != null) {
			cookieDomain = "." + MultiDomainContextHolder.getContext().getDomain();
		} else {
			cookieDomain = "." + Painiu.getAppConfig().getDomain();
		}
//		}
		return cookieDomain;
	}
	
	public static String getCookiePath(HttpServletRequest request) {
		String path = request.getContextPath();
		if ("".equals(path)) {
			path = "/";
		}
		return path;
	}
	
	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		
		if ((cookies == null) || (cookies.length == 0)) {
            return null;
        }
		
		for (int i = 0; i < cookies.length; i++) {
            if (name.equals(cookies[i].getName())) {
            	return cookies[i].getValue();
            }
		}
		
		return null;
	}
}
