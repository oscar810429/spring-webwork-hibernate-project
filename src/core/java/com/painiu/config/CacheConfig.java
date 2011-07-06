/*
 * @(#)CacheConfig.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.config;

/**
 * <p>
 * <a href="CacheConfig.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: CacheConfig.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public class CacheConfig {
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	private String provider;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	//~ Accessors ==============================================================

	/**
	 * @return the provider
	 */
	public String getProvider() {
		return provider;
	}
	
	/**
	 * @param provider the provider to set
	 */
	public void setProvider(String provider) {
		this.provider = provider;
	}
}
