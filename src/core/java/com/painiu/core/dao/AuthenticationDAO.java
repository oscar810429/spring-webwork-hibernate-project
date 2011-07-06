/*
 * @(#)AuthenticationDAO.java  2009-11-28
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao;

import java.util.List;

import com.painiu.core.model.Application;
import com.painiu.core.model.Authentication;
import com.painiu.core.model.User;

/**
 * <p>
 * <a href="AuthenticationDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: AuthenticationDAO.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public interface AuthenticationDAO extends DAO {

	public Authentication getAuthentication(String token);
	
	public Authentication getAuthentication(User user, Application application);
	
	// public Authentication getAuthenticationByFrob(String frob);
	
	public List getAuthentications(User user);

	public void saveAuthentication(Authentication authentication);
	
	public void removeAuthentication(Authentication authentication);

	
}
