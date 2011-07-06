/*
 * @(#)CacheManager.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.painiu.util.ClassLoaderUtils;
import com.painiu.Painiu;

/**
 * <p>
 * <a href="CacheManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: CacheManager.java 101 2010-07-15 02:07:16Z zhangsf $
 */
public class CacheManager {
	private static final Log log = LogFactory.getLog(CacheManager.class);
	
	//private static final String DEFAULT_PROVIDER = Painiu.getCacheConfig().getProvider();
	private static final String DEFAULT_PROVIDER = "com.painiu.cache.MemCacheProvider";
	
	
	private static CacheProvider cacheProvider;
	private static Map caches = new HashMap();
	
	static {

		String className = DEFAULT_PROVIDER;
		
		Class baseClazz = CacheProvider.class;

		Class clazz = null;

		try {
			clazz = ClassLoaderUtils.loadClass(className, CacheManager.class);
		} catch (ClassNotFoundException e) {
			log.warn("specified CacheProvider[" + className + "] is not found.");
		}

		// make sure it extends CacheProvider
		if (clazz == null || !baseClazz.isAssignableFrom(clazz)) {
			if (clazz != null) {
				log.warn("specified CacheProvider[" + className + "] is not assignable from com.painiu.cache.CacheProvider.");				
			}
			try {
				clazz = ClassLoaderUtils.loadClass(DEFAULT_PROVIDER, CacheManager.class);
			} catch (ClassNotFoundException ex) {
				// should not happen.
			}
		}

		try {
			cacheProvider = (CacheProvider) clazz.newInstance();
			cacheProvider.start(null);
		} catch (InstantiationException e) {
			log.error("InstantiationException occurred while initialze CacheProvider");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			log.error("IllegalAccessException occurred while initialze CacheProvider");			
			e.printStackTrace();
		}

	}
	
	
	private CacheManager() {}
	
	public static Cache getSessionCache(String regionName) {
		Cache cache = (Cache) caches.get(regionName);
		if (cache == null) {
			Properties props = new Properties();
			props.put("poolName", "session");
			cache = buildCache(regionName, props);
			caches.put(regionName, cache);
		}
		
		return cache;
	}
	
	public static Cache getCache(String regionName) {
		Cache cache = (Cache) caches.get(regionName);
		
		if (cache == null) {
			cache = buildCache(regionName, null);
			caches.put(regionName, cache);
		}
		
		return cache;
	}
	
	private static Cache buildCache(String regionName, Properties properties) {
		return cacheProvider.buildCache(regionName, properties);
	}
	
	public static void shutdown() {
		cacheProvider.stop();
	}
	
	//~ Accessors ==============================================================
}
