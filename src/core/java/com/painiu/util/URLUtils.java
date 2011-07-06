/*
 * @(#)URLUtils.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.painiu.Painiu;
import com.painiu.Context;
//import com.mingda.model.Album;
//import com.mingda.model.Group;
//import com.mingda.model.Photo;
//import com.mingda.model.Post;
//import com.mingda.model.Topic;
import com.painiu.core.model.Area;
import com.painiu.core.model.User;
import com.painiu.webapp.action.BaseAction;
import com.painiu.webapp.personality.MultiDomainContextHolder;
import com.painiu.core.security.SecurityUtils;

/**
 * <p>
 * <a href="URLUtils.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: URLUtils.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class URLUtils {
//~ Static fields/initializers =============================================
	
	private static final String USERNAME_EXP = "${username}";
	private static final String SLASH = "/";
	private static final String DEFAULT_NAME = "www";
	
	private static String userDomainPattern = null;
	private static boolean appendSlash = false;
	
	//~ Instance fields ========================================================
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================
	
	
	public static String getURL(String path) {
		return getUserURL(null, path);
	}
	
	public static String getURL(String path, boolean encode) {
		return getUserURL(null, path, encode);
	}
	
	public static String getURL() {
		return getURL(null);
	}

	public static String getURL(boolean encode) {
		return getURL(null, encode);
	}
	
	public static String getUserURL(Object user, String path) {
		return getUserURL(user, path, true);
	}
	
	public static String getRegionURL(Object region, String path, boolean encode) {
		if (userDomainPattern == null) {
			userDomainPattern = Painiu.getAppConfig().getUserDomainPattern();
			appendSlash = !userDomainPattern.endsWith(SLASH);
		}
		
		String regionname = DEFAULT_NAME;
		
		if (region != null && region instanceof Area) {
			regionname = ((Area) region).getEname();
		}
		
		String url = StringUtils.replace(userDomainPattern, USERNAME_EXP, regionname);
		
		if (path != null) {
			url = url + path;
		} else if (appendSlash) {
			url = url + SLASH;
		}
		
		if (encode && getResponse() != null) {
			return getResponse().encodeURL(url);
		}
		
		return url;
	}
	
	public static String getUserURL(Object user, String path, boolean encode) {
		if (userDomainPattern == null) {
			userDomainPattern = Painiu.getAppConfig().getUserDomainPattern();
			appendSlash = !userDomainPattern.endsWith(SLASH);
		}
		
		String username = DEFAULT_NAME;
		
		if (user != null && user instanceof User) {
			username = ((User) user).getUsername();
		}
		
		String url = StringUtils.replace(userDomainPattern, USERNAME_EXP, username);
		
		if (path != null) {
			url = url + path;
		} else if (appendSlash) {
			url = url + SLASH;
		}
		
		if (encode && getResponse() != null) {
			return getResponse().encodeURL(url);
		}
		
		return url;
	}
	
	public static String getUserURL(String path) {
		return getUserURL(getCurrentUser(), path);
	}

	public static String getUserURL(String path, boolean encode) {
		return getUserURL(getCurrentUser(), path, encode);
	}
	
	public static String getUserURL() {
		return getUserURL(null);
	}

	public static String getUserURL(boolean encode) {
		return getUserURL(null, encode);
	}
	

	public static String getRegionURL(String path) {
		return getRegionURL(getCurrentRegion(), path, appendSlash);
	}

	public static String getRegionURL(String path, boolean encode) {
		return getRegionURL(getCurrentRegion(), path, encode);
	}
	
	public static String getRegionURL() {
		return getRegionURL(null);
	}

	public static String getRegionrURL(boolean encode) {
		return getUserURL(null, encode);
	}
	
	
	public static String getUserHomeURL() {
		return getUserURL();
	}

	public static String getUserHomeURL(boolean encode) {
		return getUserURL(encode);
	}
	
	public static String getUserHomeURL(User user) {
		return getUserURL(user, null);
	}
	
	public static String getUserHomeURL(User user, boolean encode) {
		return getUserURL(user, null, encode);
	}

	
	public static String getUserProfileURL(User user) {
		return getUserProfileURL(user, true);
	}

	public static String getUserProfileURL(User user, boolean encode) {
		return getUserURL(user, "/profile/", encode);
	}
	
	public static String getRequestedURL() {
		HttpServletRequest request = getRequest();
		
		StringBuffer sb = request.getRequestURL();
		
		if (request.getQueryString() != null) {
			sb.append("?").append(request.getQueryString());
		}
		
		return sb.toString();
	}

	//~ privates =============================================================
	
	private static BaseAction getAction() {
		ActionInvocation invocation = ActionContext.getContext().getActionInvocation();
		if (invocation != null) {
			return (BaseAction) invocation.getAction();
		}
		return null;
	}
	
	private static User getCurrentUser() {
		BaseAction action = getAction();
		return action != null ? action.getCurrentUser() : SecurityUtils.getCurrentUser();
	}
	
	private static Area getCurrentRegion() {
		BaseAction action = getAction();
		return action != null ? action.getCurrentRegion() : action.getCurrentRegion();
	}
	
	private static HttpServletRequest getRequest() {
		BaseAction action = getAction();
		return action != null ? action.getRequest() : null;
	}
	
	private static HttpServletResponse getResponse() {
		BaseAction action = getAction();		
		return action != null ? action.getResponse() : null;
	}
}
