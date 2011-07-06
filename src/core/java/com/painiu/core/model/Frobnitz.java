/*
 * @(#)Frobnitz.java  2006-3-27
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * <a href="Frobnitz.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zola Zhou
 * @version $Id: Frobnitz.java 52 2010-06-15 09:28:19Z zhangsf $
 */
public class Frobnitz extends BaseObject {

	//~ Static fields/initializers =============================================
	private static final long serialVersionUID = -7625060665383479001L;
    public static final long EXPIRE_TIME = 1000 * 60 * 60; // one hour.
	
	//~ Instance fields ========================================================
	
	private String id;
	private Application application;
	private Authentication authentication;
	private Date createdDate;
	private Date expireDate;

	//~ Constructors ===========================================================

	public Frobnitz() {
		
	}
	
	public Frobnitz(Application app) {
		this.application = app;
		this.createdDate = new Date();
		this.expireDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
	}
	
	//~ Methods ================================================================

	public boolean expired() {
		return System.currentTimeMillis() > this.expireDate.getTime();
	}
	
	//~ Accessors ==============================================================

	/**
	 * @return Returns the authentication.
	 */
	public Authentication getAuthentication() {
		return authentication;
	}

	/**
	 * @param authentication The authentication to set.
	 */
	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}
	
	/**
	 * @return Returns the application.
	 */
	public Application getApplication() {
		return application;
	}

	/**
	 * @param application The application to set.
	 */
	public void setApplication(Application application) {
		this.application = application;
	}

	/**
	 * @return Returns the createdDate.
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate The createdDate to set.
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return Returns the expireDate.
	 */
	public Date getExpireDate() {
		return expireDate;
	}

	/**
	 * @param expireDate The expireDate to set.
	 */
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Frobnitz)) {
			return false;
		}
		Frobnitz rhs = (Frobnitz) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(261456065, -1400696855)
				.append(this.id).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id)
				.append("createdDate", this.createdDate)
				.append("application", this.application)
				.append("expireDate", this.expireDate).toString();
	}
	
}
