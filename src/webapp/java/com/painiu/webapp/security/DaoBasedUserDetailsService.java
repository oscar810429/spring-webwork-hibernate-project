/**
 * @(#)DaoBasedUserDetailsService.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.security;

import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;

import com.painiu.core.model.User;
import com.painiu.core.security.UserDetail;
import com.painiu.core.service.UserManager;

/**
 * <p>
 * <a href="DaoBasedUserDetailsService.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: DaoBasedUserDetailsService.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class DaoBasedUserDetailsService implements UserDetailsService {
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	private UserManager userManager;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	/* (non-Javadoc)
	 * @see org.acegisecurity.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		User user = userManager.getUser(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("User[" + username + "] is not found.");
		}
		
		return new UserDetail(user);
	}

	/**
	 * @param userManager the userManager to set
	 */
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
}
