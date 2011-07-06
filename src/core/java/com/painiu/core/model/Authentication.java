/*
 * @(#)Authentication.java  2006-3-1
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * <p>
 * <a href="Authentication.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zola Zhou
 * @version $Id: Authentication.java 52 2010-06-15 09:28:19Z zhangsf $
 */
public class Authentication extends BaseObject {

	private static final long serialVersionUID = 6908837653386977029L;

	//~ Static fields/initializers =============================================
	
	public static class Permission extends IntegerEnum implements Comparable, Serializable {

		public static final Permission NONE      = new Permission(0);
		public static final Permission READ      = new Permission(1);
	    public static final Permission WRITE     = new Permission(2);
	    public static final Permission DELETE    = new Permission(3);
	    public static final Permission ALL       = new Permission(4);
	    
	    private Permission(int value) {
	    	super(value);
	    }
	    
	    public static Permission valueOf(int value) {
	    	return (Permission) IntegerEnum.valueOf(Permission.class, value);
	    }

		public int compareTo(Object o) {
			int thisVal = this.value();
			int anotherVal = ((Permission) o).value();
			return (thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1));
		}
		
		public String toString() {
			if (value() == 0) {
				return "none";
			} else if (value() == 1) {
				return "read";
			} else if (value() == 2) {
				return "write";
			} else if (value() == 3) {
				return "delete";
			} else {
				return "all";
			}
		}
	}
	
	//~ Instance fields ========================================================
	
	private String token;
	private User user;
	private Application application;
	private Permission permission = Permission.READ;
	private Date authDate;
	private Date expireDate;
//	private String frob;
	
	//~ Constructors ===========================================================

	public Authentication() {}
	
	public Authentication(User user, Application application) {
		this.user = user;
		this.application = application;
	}
	
	//~ Methods ================================================================

	public boolean isExpired() {
		if (expireDate == null) {
			return false;
		}
		return new Date().after(expireDate);
	}
	
	//~ Accessors ==============================================================
	
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
	 * @return Returns the authDate.
	 */
	public Date getAuthDate() {
		return authDate;
	}

	/**
	 * @param authDate The authDate to set.
	 */
	public void setAuthDate(Date authDate) {
		this.authDate = authDate;
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
	 * @return Returns the permission.
	 */
	public Permission getPermission() {
		return permission;
	}

	/**
	 * @param permission The permission to set.
	 */
	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	/**
	 * @return Returns the token.
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token The token to set.
	 */
	public void setToken(String token) {
		this.token = token;
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Authentication)) {
			return false;
		}
		Authentication rhs = (Authentication) object;
		return new EqualsBuilder()
				.append(this.user, rhs.user)
				.append(this.application, rhs.application).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(1303085927, 1104102907)
				.append(this.user)
				.append(this.application).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("user", this.user)
				.append("application", this.application).toString();
	}
}
