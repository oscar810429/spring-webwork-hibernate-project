/*
 * @(#)BlacklistConfig.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.config;

/**
 * <p>
 * <a href="BlacklistConfig.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: BlacklistConfig.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public class BlacklistConfig {
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	private String dir;
	private String url;
	private long updateInterval;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	//~ Accessors ==============================================================

	/**
	 * @return the dir
	 */
	public String getDir() {
		return dir;
	}
	/**
	 * @param dir the dir to set
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
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
