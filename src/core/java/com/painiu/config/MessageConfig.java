/*
 * @(#)MessageConfig.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.config;

/**
 * <p>
 * <a href="MessageConfig.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: MessageConfig.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public class MessageConfig {
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	private long checkInterval;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	//~ Accessors ==============================================================

	/**
	 * @return the checkInterval
	 */
	public long getCheckInterval() {
		return checkInterval;
	}
	
	/**
	 * @param checkInterval the checkInterval to set
	 */
	public void setCheckInterval(long checkInterval) {
		this.checkInterval = checkInterval;
	}
}
