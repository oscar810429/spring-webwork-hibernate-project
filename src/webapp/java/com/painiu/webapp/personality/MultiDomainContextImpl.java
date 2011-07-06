/*
 * @(#)MultiDomainContextImpl.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.personality;

import java.io.Serializable;

/**
 * <p>
 * <a href="MultiDomainContextImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: MultiDomainContextImpl.java 150 2010-12-08 03:48:09Z zhangsf $
 */
public class MultiDomainContextImpl implements MultiDomainContext, Serializable {
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================
	private String domain;
	private String userDomainPattern = "http://${username}.jforum.js.vnet.cn";
	private String host = "www.jforum.js.vnet.cn";
	private String scheme = "http";
	private String base = "";
	private String flashDomain = "http://f.jforum.js.vnet.cn";
	private int port = 80;
	private String mediaRoot = "http://www.jforum.js.vnet.cn";
	private String url;
	private String photoURLPattern = "http://photo.painiu.com/${dir}/${filename}/${suffix}/";
	private String iconURLPattern = "http://ico.painiu.com/${user}/${filename}/";
	private String partnerName;
	//~ Constructors ===========================================================

	//~ Methods ================================================================
	public String getURL() {
		if (url == null) {
	        StringBuffer sb = new StringBuffer();
			if (port < 0) {
				port = 80; // Work around java.net.URL bug
			}
			sb.append(scheme);
			sb.append("://");
			sb.append(host);
			if ((scheme.equals("http") && (port != 80)) || (scheme.equals("https") && (port != 443))) {
				sb.append(':');
				sb.append(port);
			}
			sb.append(base);

			url = sb.toString();
		}
		
		return url;
	}
	//~ Accessors ==============================================================
	/*
	 * @see com.painiu.webapp.personality.MultiDomainContext#getDomain()
	 */
	public String getDomain() {
		return domain;
	}

	/*
	 * @see com.painiu.webapp.personality.MultiDomainContext#getHost()
	 */
	public String getHost() {
		return host;
	}

	/*
	 * @see com.painiu.webapp.personality.MultiDomainContext#getMediaRoot()
	 */
	public String getMediaRoot() {
		return mediaRoot;
	}

	/*
	 * @see com.painiu.webapp.personality.MultiDomainContext#getUserDomainPattern()
	 */
	public String getUserDomainPattern() {
		return userDomainPattern;
	}

	/*
	 * @see com.painiu.webapp.personality.MultiDomainContext#setDomain(java.lang.String)
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/*
	 * @see com.painiu.webapp.personality.MultiDomainContext#setHost(java.lang.String)
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/*
	 * @see com.painiu.webapp.personality.MultiDomainContext#setMediaRoot(java.lang.String)
	 */
	public void setMediaRoot(String mediaRoot) {
		this.mediaRoot = mediaRoot;
	}

	/*
	 * @see com.painiu.webapp.personality.MultiDomainContext#setUserDomainPattern(java.lang.String)
	 */
	public void setUserDomainPattern(String userDomainPattern) {
		this.userDomainPattern = userDomainPattern;
	}

	/*
	 * @see com.painiu.webapp.personality.MultiDomainContext#getBase()
	 */
	public String getBase() {
		return base;
	}

	/*
	 * @see com.painiu.webapp.personality.MultiDomainContext#getFlashDomain()
	 */
	public String getFlashDomain() {
		return this.flashDomain;
	}

	/*
	 * @see com.painiu.webapp.personality.MultiDomainContext#getPort()
	 */
	public int getPort() {
		return this.port;
	}

	/*
	 * @see com.painiu.webapp.personality.MultiDomainContext#getScheme()
	 */
	public String getScheme() {
		return this.scheme;
	}

	/*
	 * @see com.painiu.webapp.personality.MultiDomainContext#setBase(java.lang.String)
	 */
	public void setBase(String base) {
		this.base = base;
	}

	/*
	 * @see com.painiu.webapp.personality.MultiDomainContext#setFlashDomain(java.lang.String)
	 */
	public void setFlashDomain(String flashDomain) {
		this.flashDomain = flashDomain;
	}

	/*
	 * @see com.painiu.webapp.personality.MultiDomainContext#setPort(java.lang.String)
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/*
	 * @see com.painiu.webapp.personality.MultiDomainContext#setScheme(java.lang.String)
	 */
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	/*
	 * @see com.painiu.webapp.personality.MultiDomainContext#getPhotoURLPattern()
	 */
	public String getPhotoURLPattern() {
		return this.photoURLPattern;
	}
	/*
	 * @see com.painiu.webapp.personality.MultiDomainContext#setPhotoURLPattern(java.lang.String)
	 */
	public void setPhotoURLPattern(String photoURLPattern) {
		this.photoURLPattern = photoURLPattern;
	}
	/**
	 * @return the iconURLPattern
	 */
	public String getIconURLPattern() {
		return iconURLPattern;
	}
	/**
	 * @param iconURLPattern the iconURLPattern to set
	 */
	public void setIconURLPattern(String iconURLPattern) {
		this.iconURLPattern = iconURLPattern;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}


}
