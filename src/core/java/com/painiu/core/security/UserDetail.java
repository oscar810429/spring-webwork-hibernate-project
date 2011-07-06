/*
 * @(#)UserDetail.java  2009-12-11
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */

package com.painiu.core.security;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;

import com.painiu.core.model.User;

/**
 * <p>
 * <a href="UserDetail.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: UserDetail.java 35 2010-06-01 01:53:10Z zhangsf $
 */

public class UserDetail implements UserDetails {
	//~ Static fields/initializers =============================================

	private static final long serialVersionUID = -3110622094917462576L;


	//~ Instance fields ========================================================
	
	private User user;

	//~ Constructors ===========================================================

	//~ Methods ================================================================

	//~ Accessors ==============================================================

	
	public UserDetail(User user) {
		this.user = user;
	}
	
	/**
	 * @return Returns the user.
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
	/*
	 * @see org.acegisecurity.userdetails.UserDetails#isAccountNonExpired()
	 */
	public boolean isAccountNonExpired() {
		return true;
	}

	/*
	 * @see org.acegisecurity.userdetails.UserDetails#isAccountNonLocked()
	 */
	public boolean isAccountNonLocked() {
		return true;
	}

	/*
	 * @see org.acegisecurity.userdetails.UserDetails#getAuthorities()
	 */
	public GrantedAuthority[] getAuthorities() {
		return (GrantedAuthority[]) user.getRoles().toArray(new GrantedAuthority[0]);
	}

	/*
	 * @see org.acegisecurity.userdetails.UserDetails#isCredentialsNonExpired()
	 */
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/*
	 * @see org.acegisecurity.userdetails.UserDetails#isEnabled()
	 */
	public boolean isEnabled() {
		return User.State.ENABLE.equals(user.getState());
	}

	/*
	 * @see org.acegisecurity.userdetails.UserDetails#getPassword()
	 */
	public String getPassword() {
		return user.getPassword();
	}

	/*
	 * @see org.acegisecurity.userdetails.UserDetails#getUsername()
	 */
	public String getUsername() {
		return user.getId();
	}

	/*
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.user.hashCode();
	}
}
