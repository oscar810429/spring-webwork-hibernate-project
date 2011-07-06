/*
 * @(#)DiscuzPassport.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.security.discuz;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.painiu.Painiu;
import com.painiu.core.security.UserDetail;
import com.painiu.core.service.UserManager;
import com.painiu.core.model.User;
import com.painiu.webapp.security.AbstractAuthenticationHandler;
import com.painiu.webapp.util.CookieUtils;

/**
 * <p>
 * <a href="DiscuzPassport.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: DiscuzPassport.java 38 2010-06-01 02:18:07Z zhangsf $
 */
public class DiscuzPassport implements InitializingBean {
	//~ Static fields/initializers =============================================
	
	public static final String DISCUZ_PASSPORT_FORWARD_KEY = "forward";
	
	private static final String DISCUZ_PASSPORT_ENDPOINT = "api/passport.php";
	
	//~ Instance fields ========================================================

	private String discuzUrlPrefix;
	private String key;
	private String encoding = "UTF-8";
	
	private UserManager userManager;
	
	//~ Constructors ===========================================================
	
	//~ Methods ================================================================
	
	/*
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(discuzUrlPrefix, "discuzUrlPrefix must be specified");
		Assert.notNull(key, "key must be specified");
		Assert.notNull(userManager);
	}
	
	/**
	 * @param userManager the userManager to set
	 */
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	public boolean isDiscuzRequest(HttpServletRequest request, HttpServletResponse response) {
		return getForward(request) != null;
	}
	
	public String getForward(HttpServletRequest request) {
		String forward = request.getParameter(DISCUZ_PASSPORT_FORWARD_KEY);
		
		if (forward != null && forward.startsWith(discuzUrlPrefix)) {
			return forward;
		}
		
		if (request.getSession(false) != null) {
			forward = (String) request.getSession().getAttribute(AbstractAuthenticationHandler.SECURITY_FORWARD_KEY);
			if (forward != null && forward.startsWith(discuzUrlPrefix)) {
				return forward;
			}
		}
		
		return null;
	}

	public String buildLoginUrl(HttpServletRequest request, HttpServletResponse response,
			Authentication authResult) {
		String forward = getForward(request);
		
		String auth = buildParamAuth(authResult);
		
		String action = "login";
		
		String verify = DigestUtils.md5Hex((action + auth + forward + key));
		
		try {
			forward = URLEncoder.encode(forward, encoding);
		} catch (UnsupportedEncodingException e) {}
		
		try {
			auth = URLEncoder.encode(auth, encoding);
		} catch (UnsupportedEncodingException e) {}
		
		StringBuffer buf = new StringBuffer();
		buf.append(discuzUrlPrefix);
		buf.append(DISCUZ_PASSPORT_ENDPOINT);
		buf.append("?action=").append(action);
		buf.append("&auth=").append(auth);
		buf.append("&forward=").append(forward);
		buf.append("&verify=").append(verify);
		
		return buf.toString();
	}
	
	protected String buildParamAuth(Authentication auth) {
		Assert.isInstanceOf(String.class, auth.getPrincipal());
		
		//User user = ((UserDetail) auth.getPrincipal()).getUser();
		User user = userManager.getUser((String) auth.getPrincipal());
		
		
		StringBuffer buf = new StringBuffer();
		buf.append("cookietime=").append(CookieUtils.COOKIE_MAX_AGE);
		buf.append("&time=").append(System.currentTimeMillis()/1000);
		buf.append("&userid=").append(encodeURL(user.getId()));
		buf.append("&username=").append(encodeURL(user.getUsername()));
		buf.append("&password=").append(user.getPassword());
		buf.append("&email=").append(encodeURL(user.getEmail()));
		buf.append("&isadmin=").append((user.isInRole(Painiu.ADMIN_ROLE) || user.isInRole(Painiu.MANAGER_ROLE)) ? "1" : "0");
		buf.append("&nickname=").append(encodeURL(user.getNickname()));
		buf.append("&regdate=").append(user.getCreatedDate().getTime()/1000);
		
		return AzDGCrypt.encrypt(key, buf.toString(), encoding);
	}
	
	protected String encodeURL(String input) {
		try {
			return URLEncoder.encode(input, encoding);
		} catch (UnsupportedEncodingException e) {
			return input;
		}
	}
	
	public String buildLogoutUrl(HttpServletRequest request, HttpServletResponse response,
			Authentication auth) {
		String forward = getForward(request);
		
		String action = "logout";
		
		String verify = DigestUtils.md5Hex((action + forward + key));
		
		try {
			forward = URLEncoder.encode(forward, encoding);
		} catch (UnsupportedEncodingException e) {}
		
		StringBuffer buf = new StringBuffer();
		buf.append(discuzUrlPrefix);
		buf.append(DISCUZ_PASSPORT_ENDPOINT);
		buf.append("?action=").append(action);
		buf.append("&forward=").append(forward);
		buf.append("&verify=").append(verify);
		
		return buf.toString();
	}
	
	//~ Accessors ==============================================================

	/**
	 * @return the discuzUrlPrefix
	 */
	public String getDiscuzUrlPrefix() {
		return discuzUrlPrefix;
	}

	/**
	 * @param discuzUrlPrefix the discuzUrlPrefix to set
	 */
	public void setDiscuzUrlPrefix(String discuzUrlPrefix) {
		this.discuzUrlPrefix = discuzUrlPrefix;
	}

	/**
	 * @return the encoding
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * @param encoding the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

}
