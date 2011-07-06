/*
 * @(#)UserStatDAO.java  2009-11-27
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao;

import com.painiu.core.model.User;
import com.painiu.core.model.UserStat;

/**
 * <p>
 * <a href="UserStatDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: UserStatDAO.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public interface UserStatDAO extends DAO {

	public void saveUserStat(UserStat stat);

	public UserStat getUserStat(User user);
	
	public UserStat getUserStat(String userId);
}
