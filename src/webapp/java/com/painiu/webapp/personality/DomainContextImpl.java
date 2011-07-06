/*
 * @(#)DomainContextImpl.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.personality;

import java.io.Serializable;

import com.painiu.core.model.Area;
import com.painiu.core.model.User;

/**
 * <p>
 * <a href="DomainContextImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: DomainContextImpl.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class DomainContextImpl implements DomainContext, Serializable {
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	private User user;
	private Area area;
	
	//~ Constructors ===========================================================

	public DomainContextImpl() {
		
	}
	
	public DomainContextImpl(User user) {
		setUser(user);
	}
	
	public DomainContextImpl(Area area) {
		setArea(area);
	}
	
	//~ Methods ================================================================
	

	public User getUser() {
		return this.user;
	}


	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the area
	 */
	public Area getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(Area area) {
		this.area = area;
	}
	
	
	

}
