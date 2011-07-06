/*
 * @(#)Cache.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.cache;

import org.hibernate.cache.CacheException;

/**
 * <p>
 * <a href="Cache.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Cache.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public interface Cache {
	
	/**
	 * Get the name of the cache region
	 */
	public String getRegionName();
	
	/**
	 * Get an item from the cache, nontransactionally
	 * @param key
	 * @return the cached object or <tt>null</tt>
	 * @throws CacheException
	 */
	public Object get(Object key) throws CacheException;
	
	/**
	 * Add an item to the cache, nontransactionally, with
	 * failfast semantics
	 * @param key
	 * @param value
	 * @throws CacheException
	 */
	public void put(Object key, Object value) throws CacheException;

	/**
	 * Remove an item from the cache
	 */
	public void remove(Object key) throws CacheException;
	
	/**
	 * Clear the cache
	 */
	public void clear() throws CacheException;
	
	
}
