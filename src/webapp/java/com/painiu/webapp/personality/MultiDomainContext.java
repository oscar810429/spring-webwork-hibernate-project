/*
 * @(#)MultiDomainContext.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.personality;

/**
 * <p>
 * <a href="MultiDomainContext.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: MultiDomainContext.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public interface MultiDomainContext {
	public String getUserDomainPattern(); 
	public void setUserDomainPattern(String userDomainPattern);
	
	public void setMediaRoot(String mediaRoot);
	public String getMediaRoot();
	
	public void setDomain(String domain);
	public String getDomain();
	public void setHost(String host);
	public String getHost();
	
	public void setPort(int port);
	public int getPort();
	
	public void setScheme(String scheme);
	public String getScheme();
	
	public void setBase(String base);
	public String getBase();
	
	public void setFlashDomain(String flashDomain);
	public String getFlashDomain();
	public String getURL();
	
	public void setPhotoURLPattern(String photoURLPattern);
	public String getPhotoURLPattern();
	
	public void setIconURLPattern(String iconURLPattern);
	public String getIconURLPattern();
	
	public void setPartnerName(String partnerName);
	public String getPartnerName();
}
