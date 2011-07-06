/*
 * @(#)ApiContext.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.service.api;

import java.io.Serializable;

import com.painiu.core.model.User;
import com.painiu.core.model.Application;

/**
 * <p>
 * <a href="ApiContext.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ApiContext.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public interface ApiContext extends Serializable {

	public Application getApp();
	
	public User getUser();
	
	public ApiAuth getAuth();
}
