/*
 * @(#)PartnerDomain.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

import java.util.Date;

/**
 * <p>
 * <a href="PartnerDomain.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: PartnerDomain.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class PartnerDomain {

	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================
	
	private String domain;
	private String partnerName;
	private String userDomainPattern;
	private String flashDomain;
	private String host;
	private String mediaRoot;
	private String photoURLPattern;
	private String iconURLPattern;
	private Date createDate;
	
	//~ Constructors ===========================================================
	
	public PartnerDomain() {
		createDate = new Date();
	}
	
	//~ Methods ================================================================

	//~ Accessors ==============================================================
	
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getFlashDomain() {
		return flashDomain;
	}
	public void setFlashDomain(String flashDomain) {
		this.flashDomain = flashDomain;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getIconURLPattern() {
		return iconURLPattern;
	}
	public void setIconURLPattern(String iconURLPattern) {
		this.iconURLPattern = iconURLPattern;
	}
	public String getMediaRoot() {
		return mediaRoot;
	}
	public void setMediaRoot(String mediaRoot) {
		this.mediaRoot = mediaRoot;
	}
	public String getPhotoURLPattern() {
		return photoURLPattern;
	}
	public void setPhotoURLPattern(String photoURLPattern) {
		this.photoURLPattern = photoURLPattern;
	}
	public String getUserDomainPattern() {
		return userDomainPattern;
	}
	public void setUserDomainPattern(String userDomainPattern) {
		this.userDomainPattern = userDomainPattern;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
}
