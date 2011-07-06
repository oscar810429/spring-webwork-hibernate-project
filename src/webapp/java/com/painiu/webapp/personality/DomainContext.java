/*
 * @(#)DomainContext.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.personality;

import com.painiu.core.model.Area;
import com.painiu.core.model.User;

/**
 * <p>
 * <a href="DomainContext.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: DomainContext.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public interface DomainContext {
	
	public User getUser();
	
	public void setUser(User user);
	
	public Area getArea();
	
	public void setArea(Area area);
	
}
