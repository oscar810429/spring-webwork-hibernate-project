/*
 * @(#)SystemCacheProvider.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.persistence.cache;

import java.util.Properties;

import org.hibernate.cache.Cache;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.CacheProvider;

/**
 * <p>
 * <a href="SystemCacheProvider.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SystemCacheProvider.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class SystemCacheProvider implements CacheProvider {

	/* (non-Javadoc)
	 * @see org.hibernate.cache.CacheProvider#buildCache(java.lang.String, java.util.Properties)
	 */
	public Cache buildCache(String regionName, Properties properties)
			throws CacheException {
		return new SystemCache(regionName);
	}

	/* (non-Javadoc)
	 * @see org.hibernate.cache.CacheProvider#isMinimalPutsEnabledByDefault()
	 */
	public boolean isMinimalPutsEnabledByDefault() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.cache.CacheProvider#nextTimestamp()
	 */
	public long nextTimestamp() {
		return System.currentTimeMillis() / 100;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.cache.CacheProvider#start(java.util.Properties)
	 */
	public void start(Properties properties) throws CacheException {
		
	}

	/* (non-Javadoc)
	 * @see org.hibernate.cache.CacheProvider#stop()
	 */
	public void stop() {
		
	}

}
