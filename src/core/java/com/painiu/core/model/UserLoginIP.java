/*
 * @(#)UserLoginIP.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * <p>
 * <a href="UserLoginIP.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: UserLoginIP.java 52 2010-06-15 09:28:19Z zhangsf $
 */
public class UserLoginIP extends BaseObject {

	//~ Static fields/initializers =============================================
    private static final long serialVersionUID = -5360245560053365447L;
	//~ Instance fields ========================================================
	private String id;
	private User user;
	private String loginIP;
	private Date loginDate;

	
	//~ Constructors ===========================================================
	
	//~ Methods ================================================================
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the loginDate
	 */
	public Date getLoginDate() {
		return loginDate;
	}

	/**
	 * @param loginDate the loginDate to set
	 */
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	/**
	 * @return the loginIP
	 */
	public String getLoginIP() {
		return loginIP;
	}

	/**
	 * @param loginIP the loginIP to set
	 */
	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}


	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof UserLoginIP)) {
			return false;
		}
		UserLoginIP rhs = (UserLoginIP) object;
		return new EqualsBuilder()
						.append(this.user, rhs.getUser())
						.append(this.loginDate, rhs.loginDate)
						.append(this.loginIP, rhs.loginIP).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-656346723, -1085805807)
					.append(this.user).append(this.loginDate)
					.append(this.loginIP).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
						.append("loginDate", this.loginDate)
						.append("loginIP", this.loginIP)
						.append("userId", this.user).toString();
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}


	//~ Accessors ==============================================================
}
