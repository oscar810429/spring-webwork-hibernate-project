/*
 * @(#)ApplicationManager.java  2009-11-28
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service;

import java.util.List;

import com.painiu.core.dao.ApplicationDAO;
import com.painiu.core.model.Application;
import com.painiu.core.model.User;

/**
 * <p>
 * <a href="ApplicationManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ApplicationManager.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public interface ApplicationManager {

	public void setApplicationDAO(ApplicationDAO applicationDAO);
	
	public Application getApplication(String apiKey);
	
	public Application getApplication(Long seq);
	
	public List getApplications(User user);
	
	public void saveApplication(Application application);
	
	public void removeApplication(Application application);
	
}
