/*
 * @(#)CacheProvider.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.cache;

import java.util.Properties;

/**
 * <p>
 * <a href="CacheProvider.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: CacheProvider.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public interface CacheProvider {

	/**
	 * Configure the cache
	 *
	 * @param regionName the name of the cache region
	 * @param properties configuration settings
	 * @throws CacheException
	 */
	public Cache buildCache(String regionName, Properties properties) throws CacheException;

	/**
	 * Callback to perform any necessary initialization of the underlying cache implementation
	 * during SessionFactory construction.
	 *
	 * @param properties current configuration settings.
	 */
	public void start(Properties properties) throws CacheException;

	/**
	 * Callback to perform any necessary cleanup of the underlying cache implementation
	 * during SessionFactory.close().
	 */
	public void stop();
}
