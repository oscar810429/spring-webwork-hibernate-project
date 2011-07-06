/*
 * @(#)SignupService.java 2008-11-29
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service;

import com.painiu.core.exception.UserExistsException;
import com.painiu.core.model.User;
import com.painiu.core.model.UserProfile;

/**
 * <p>
 * <a href="SignupService.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SignupService.java 85 2010-07-01 19:37:02Z zhangsf $
 */
public interface SignupService {

	public User signup(String username, String email, String password,UserProfile userProfile) throws UserExistsException;
	
	public User signup(String username, String email, String password,UserProfile userProfile, boolean encodePassword) throws UserExistsException;
	
	//public User signup(String username, String email, String password, boolean localUser, boolean encodePassword) throws UserExistsException;

}