/*
 * @(#)MemCache.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.persistence.cache;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cache.Cache;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.Timestamper;

import com.painiu.cache.CacheManager;

/**
 * <p>
 * <a href="MemCache.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SystemCache.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class SystemCache implements Cache {
	//~ Static fields/initializers =============================================

	private static final Log log = LogFactory.getLog(SystemCache.class);
	
	//~ Instance fields ========================================================

	private com.painiu.cache.Cache cache;

	//~ Constructors ===========================================================

	public SystemCache(String regionName) {
		cache = CacheManager.getCache(regionName);
	}
	
	//~ Methods ================================================================

	/* (non-Javadoc)
	 * @see org.hibernate.cache.Cache#clear()
	 */
	public void clear() throws CacheException {
		cache.clear();
	}

	/* (non-Javadoc)
	 * @see org.hibernate.cache.Cache#destroy()
	 */
	public void destroy() throws CacheException {
		if (log.isInfoEnabled()) {
			log.info("** destroy system cache");
		}
//		clear(); // NOTE: don't clear remote cache
	}

	/* (non-Javadoc)
	 * @see org.hibernate.cache.Cache#get(java.lang.Object)
	 */
	public Object get(Object key) throws CacheException {		
		return cache.get(key);
	}

	/* (non-Javadoc)
	 * @see org.hibernate.cache.Cache#getElementCountInMemory()
	 */
	public long getElementCountInMemory() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.cache.Cache#getElementCountOnDisk()
	 */
	public long getElementCountOnDisk() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.cache.Cache#getRegionName()
	 */
	public String getRegionName() {
		return cache.getRegionName();
	}

	/* (non-Javadoc)
	 * @see org.hibernate.cache.Cache#getSizeInMemory()
	 */
	public long getSizeInMemory() {
		return -1;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.cache.Cache#getTimeout()
	 */
	public int getTimeout() {
		return Timestamper.ONE_MS * 60000;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.cache.Cache#lock(java.lang.Object)
	 */
	public void lock(Object key) throws CacheException {
		throw new UnsupportedOperationException("SystemCache does not support locking (use nonstrict-read-write)");
	}

	/* (non-Javadoc)
	 * @see org.hibernate.cache.Cache#nextTimestamp()
	 */
	public long nextTimestamp() {
		return Timestamper.next();
	}

	/* (non-Javadoc)
	 * @see org.hibernate.cache.Cache#put(java.lang.Object, java.lang.Object)
	 */
	public void put(Object key, Object value) throws CacheException {
		cache.put(key, value);
	}

	/* (non-Javadoc)
	 * @see org.hibernate.cache.Cache#read(java.lang.Object)
	 */
	public Object read(Object key) throws CacheException {
		return get(key);
	}

	/* (non-Javadoc)
	 * @see org.hibernate.cache.Cache#remove(java.lang.Object)
	 */
	public void remove(Object key) throws CacheException {
		cache.remove(key);
	}

	/* (non-Javadoc)
	 * @see org.hibernate.cache.Cache#toMap()
	 */
	public Map toMap() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.cache.Cache#unlock(java.lang.Object)
	 */
	public void unlock(Object key) throws CacheException {
		throw new UnsupportedOperationException("SystemCache does not support locking (use nonstrict-read-write)");
	}

	/* (non-Javadoc)
	 * @see org.hibernate.cache.Cache#update(java.lang.Object, java.lang.Object)
	 */
	public void update(Object key, Object value) throws CacheException {
		if (log.isDebugEnabled()) {
			log.debug("** memcache.replace(" + key + ", " + value + ")");
		}
		
		cache.put(key, value);
	}

}
