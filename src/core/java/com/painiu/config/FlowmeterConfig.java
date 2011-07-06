/*
 * @(#)FlowmeterConfig.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.config;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * <a href="FlowmeterConfig.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: FlowmeterConfig.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public class FlowmeterConfig {
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	private String type;
	
	private Map maximums = new HashMap();
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	public void setMaximum(String role, Long value) {
		this.maximums.put(role, value);
	}
	
	public Long getMaximum(String role) {
		if (maximums.containsKey(role)) {
			return (Long) maximums.get(role);
		}
		return new Long(0);
	}
	
	public static long getFreeUserMaxUploadFlometer() {
		return 63963136;	//61M = 63963136b this unit is b
	}
	public static long getFreeUserMaxOutFlowmeter() {
		return 1024;	//1G = 1024M this unit is Mb
	}
	
	
	//~ Accessors ==============================================================
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
