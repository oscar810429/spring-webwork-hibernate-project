/*
 * @(#)ApplicationConfig.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.config;

/**
 * <p>
 * <a href="ApplicationConfig.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ApplicationConfig.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public class ApplicationConfig {
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	private String scheme;
	private String host;
	private int port;
	private String base;
	private String domain;
	private String mediaRoot;
	private String userDomainPattern;
	private boolean devMode;
	private String flashDomain;
	
	private String url;
	
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

	/**
	 * @return the base
	 */
	public String getBase() {
		return base;
	}
	/**
	 * @param base the base to set
	 */
	public void setBase(String base) {
		this.base = base;
	}
	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}
	/**
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}
	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}
	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}
	/**
	 * @return the mediaRoot
	 */
	public String getMediaRoot() {
		return mediaRoot;
	}
	/**
	 * @param mediaRoot the mediaRoot to set
	 */
	public void setMediaRoot(String mediaRoot) {
		this.mediaRoot = mediaRoot;
	}
	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}
	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}
	/**
	 * @return the scheme
	 */
	public String getScheme() {
		return scheme;
	}
	/**
	 * @param scheme the scheme to set
	 */
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	/**
	 * @return the userDomainPattern
	 */
	public String getUserDomainPattern() {
		return userDomainPattern;
	}
	/**
	 * @param userDomainPattern the userDomainPattern to set
	 */
	public void setUserDomainPattern(String userDomainPattern) {
		this.userDomainPattern = userDomainPattern;
	}
	/**
	 * @return the devMode
	 */
	public boolean isDevMode() {
		return devMode;
	}
	/**
	 * @param devMode the devMode to set
	 */
	public void setDevMode(boolean devMode) {
		this.devMode = devMode;
	}

	/**
	 * @return the flashDomain
	 */
	public String getFlashDomain() {
		return flashDomain;
	}

	/**
	 * @param flashDomain the flashDomain to set
	 */
	public void setFlashDomain(String flashDomain) {
		this.flashDomain = flashDomain;
	}
}
