/*
 * @(#)ApplicationDAO.java  2009-11-28
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao;

import java.util.List;

import com.painiu.core.model.Application;
import com.painiu.core.model.User;

/**
 * <p>
 * <a href="ApplicationDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ApplicationDAO.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public interface ApplicationDAO extends DAO {

	public Application getApplication(String apiKey);
	
	
	public Application getApplication(Long sequence);
	
	public List getApplications(User user);
	
	public void saveApplication(Application application);
	
	public void removeApplication(Application application);
}
