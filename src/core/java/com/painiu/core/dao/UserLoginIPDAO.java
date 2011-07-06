/*
 * @(#)UserStatDAO.java  2009-12-03
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao;

import java.util.Date;

import com.painiu.core.model.User;
import com.painiu.core.model.UserLoginIP;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="UserLoginIPDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: UserLoginIPDAO.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public interface UserLoginIPDAO extends DAO {

	public void saveUserLoginIP(UserLoginIP userLoginIP);

	public Result getUserLoginIPs(User user, Date fromDate, Date toDate, int start, int limit);
	
	public UserLoginIP getUserLoginIP(String id);
}
