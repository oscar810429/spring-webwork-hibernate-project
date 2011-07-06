/*
 * @(#)Token.java Dec 13, 2009
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
 * <a href="Token.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Token.java 88 2010-07-03 21:18:44Z zhangsf $
 */
public class Token extends BaseObject {

	//~ Static fields/initializers =============================================
	private static final long serialVersionUID = -9214546413204012090L;
	//~ Instance fields ========================================================

	private String id;
	private User user;
	private Date createdDate;
	private Date expireDate;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	//~ Accessors ==============================================================
	
	public boolean expired() {
		return new Date().after(getExpireDate());
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
	 * @return Returns the user.
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user The user to set.
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1655983159, -1750125525)
				.append(this.createdDate).append(this.user)
				.append(this.expireDate)
				.append(this.id).toHashCode();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Token)) {
			return false;
		}
		Token rhs = (Token) object;
		return new EqualsBuilder()
				.append(this.createdDate, rhs.createdDate)
				.append(this.user, rhs.user)
				.append(this.id, rhs.id)
				.append(this.expireDate, rhs.expireDate).isEquals();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id).toString();
	}
}
