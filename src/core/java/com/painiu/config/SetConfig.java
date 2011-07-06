/*
 * @(#)SetConfig.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.config;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * <a href="SetConfig.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SetConfig.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public class SetConfig {

	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================
	
	private Map createLimit = new HashMap();
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================
	
	public void setCreateLimit(String role, Integer value) {
		createLimit.put(role, value);
	}
	
	public Integer getCreateLimit(String role) {
		if (createLimit.containsKey(role)) {
			return (Integer) createLimit.get(role);
		}
		
		return new Integer(0);
		
	}
	
	//~ Accessors ==============================================================

	/**
	 * @return the createLimit
	 */
	public Map getCreateLimit() {
		return createLimit;
	}

	/**
	 * @param createLimit the createLimit to set
	 */
	public void setCreateLimit(Map createLimit) {
		this.createLimit = createLimit;
	}
}
