/*
 * @(#)ProgressConfig.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.config;

/**
 * <p>
 * <a href="ProgressConfig.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ProgressConfig.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public class ProgressConfig {
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	private long delay = 0;
	private long updateInterval = 1000;

	//~ Constructors ===========================================================

	//~ Methods ================================================================

	//~ Accessors ==============================================================

	/**
	 * @return the delay
	 */
	public long getDelay() {
		return delay;
	}
	/**
	 * @param delay the delay to set
	 */
	public void setDelay(long delay) {
		this.delay = delay;
	}
	/**
	 * @return the updateInterval
	 */
	public long getUpdateInterval() {
		return updateInterval;
	}
	/**
	 * @param updateInterval the updateInterval to set
	 */
	public void setUpdateInterval(long updateInterval) {
		this.updateInterval = updateInterval;
	}
	
}
