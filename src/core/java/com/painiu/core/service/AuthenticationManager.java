/*
 * @(#)AuthenticationManagerImpl.java  2009-11-28
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service;

import java.util.List;

import com.painiu.core.dao.AuthenticationDAO;
import com.painiu.core.dao.FrobnitzDAO;
import com.painiu.core.model.Application;
import com.painiu.core.model.Authentication;
import com.painiu.core.model.Frobnitz;
import com.painiu.core.model.User;

/**
 * <p>
 * <a href="AuthenticationManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: AuthenticationManager.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public interface AuthenticationManager {

	public void setFrobnitzDAO(FrobnitzDAO frobnitzDAO);
	public void setAuthenticationDAO(AuthenticationDAO authenticationDAO);
	
	
	public Authentication getAuthentication(String token);
	
	public Authentication getAuthentication(User user, Application app);
	
//	public Authentication getAuthenticationByFrob(String frob);
	
	public List getAuthentications(User user);
	
	public void saveAuthentication(Authentication auth);
	
	public void removeAuthentication(Authentication auth);
	
	// frob dao
	public Frobnitz getFrobnitz(String id);
	
	public void saveFrobnitz(Frobnitz frobnitz);
	
	public void removeFrobnitz(Frobnitz frobnitz);
}
