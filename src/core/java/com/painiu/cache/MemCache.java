/*
 * @(#)MemCache.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.cache;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cache.CacheException;

import com.danga.MemCached.MemCachedClient;

/**
 * <p>
 * <a href="MemCache.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: MemCache.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public class MemCache implements Cache {
	//~ Static fields/initializers =============================================

	private static final Log log = LogFactory.getLog(MemCache.class);
		
	//~ Instance fields ========================================================

	private final String regionName;
	private long timeToLiveMillis;
	
	private MemCachedClient client;
	
	private String prefix;
	private boolean addPrefix = false;
	
	//~ Constructors ===========================================================
	
	public MemCache(String regionName, long timeToLiveSeconds) {
		this(regionName, null, timeToLiveSeconds, false);
	}
	
	public MemCache(String regionName, long timeToLiveSeconds, boolean addPrefix) {
		this(regionName, null, timeToLiveSeconds, addPrefix);
	}
	
	public MemCache(String regionName, String poolName, long timeToLiveSeconds, boolean addPrefix) {
		this.regionName = regionName;
		this.timeToLiveMillis = timeToLiveSeconds * 1000;
		this.addPrefix = addPrefix;
		
		this.prefix = regionName + "#";
		
	    //client = new MemCachedClient(poolName);
		client = new MemCachedClient();
		
		if (poolName != null) {
			client.setPoolName(poolName);
		}
	}
	
	//~ Methods ================================================================

	/* (non-Javadoc)
	 * @see com.mingda.cache.Cache#clear()
	 */
	public void clear() throws CacheException {
		if (log.isInfoEnabled()) {
			log.info("** clear memcache");
		}
		if (!client.flushAll()) {
			throw new CacheException("Memcached flushing failed");
		}
	}

	/* (non-Javadoc)
	 * @see com.mingda.cache.Cache#get(java.lang.Object)
	 */
	public Object get(Object key) throws CacheException {
		if (log.isDebugEnabled()) {
			log.debug("** memcached[" + regionName + "].get(" + key + ")");
		}
		
		return client.get(addPrefix ? (prefix + key.toString()) : key.toString());
	}

	/* (non-Javadoc)
	 * @see com.mingda.cache.Cache#put(java.lang.Object, java.lang.Object)
	 */
	public void put(Object key, Object value) throws CacheException {
		if (log.isDebugEnabled()) {
			log.debug("** memcached[" + regionName + "].set(" + key + ", " + value + ")");
		}
		
		Date expiry = timeToLiveMillis > 0 ? new Date(System.currentTimeMillis() + timeToLiveMillis) : null;
		
		client.set((addPrefix ? (prefix + key.toString()) : key.toString()), value, expiry);
	}

	/* (non-Javadoc)
	 * @see com.mingda.cache.Cache#remove(java.lang.Object)
	 */
	public void remove(Object key) throws CacheException {
		if (log.isDebugEnabled()) {
			log.debug("** memcached[" + regionName + "].delete(" + key + ")");
		}
		
		client.delete(addPrefix ? (prefix + key.toString()) : key.toString());
	}
	
	/**
	 * @return the regionName
	 */
	public String getRegionName() {
		return regionName;
	}

	//~ Accessors ==============================================================

	/**
	 * @return the timeToLiveSeconds
	 */
	public long getTimeToLiveSeconds() {
		return timeToLiveMillis / 1000;
	}

	/**
	 * @param timeToLiveSeconds the timeToLiveSeconds to set
	 */
	public void setTimeToLiveSeconds(long timeToLiveSeconds) {
		this.timeToLiveMillis = timeToLiveSeconds * 1000;
	}

	/**
	 * @return the addPrefix
	 */
	public boolean isAddPrefix() {
		return addPrefix;
	}

	/**
	 * @param addPrefix the addPrefix to set
	 */
	public void setAddPrefix(boolean addPrefix) {
		this.addPrefix = addPrefix;
	}
}
