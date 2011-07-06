/*
 * @(#)CacheManager.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.cache;

import java.util.Hashtable;
import java.util.Properties;

import net.sf.ehcache.CacheManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Cache Provider plugin
 * 
 * Taken from EhCache 1.3 distribution
 * @author zhangsf
 */
public class EHCacheProvider implements CacheProvider {

    private static final Log log = LogFactory.getLog(EHCacheProvider.class);

	private CacheManager manager;
	private Hashtable<String, EHCache> _CacheManager ;

    /**
     * Builds a Cache.
     * <p>
     * Even though this method provides properties, they are not used.
     * Properties for EHCache are specified in the EHCache.xml file.
     * Configuration will be read from EHCache.xml for a cache declaration
     * where the name attribute matches the name parameter in this builder.
     *
     * @param name the name of the cache. Must match a cache configured in EHCache.xml
     * @param properties not used
     * @return a newly built cache will be built and initialised
     * @throws CacheException inter alia, if a cache of the same name already exists
     */
    public EHCache buildCache(String name, Properties properties) throws CacheException {
    	EHCache EHCache = _CacheManager.get(name);
    	if(EHCache != null)
    		return EHCache ;
	    try {
            net.sf.ehcache.Cache cache = manager.getCache(name);
            if (cache == null) {
                log.warn("Could not find configuration [" + name + "]; using defaults.");
                manager.addCache(name);
                cache = manager.getCache(name);
                log.debug("started EHCache region: " + name);                
            }
            synchronized(_CacheManager){
	            EHCache = new EHCache(cache);
	            _CacheManager.put(name, EHCache);
	            return EHCache ;
            }
	    }
        catch (net.sf.ehcache.CacheException e) {
            throw new CacheException(e);
        }
    }

	/**
	 * Callback to perform any necessary initialization of the underlying cache implementation
	 * during SessionFactory construction.
	 *
	 * @param properties current configuration settings.
	 */
	public void start(Properties properties) throws CacheException {
		if (manager != null) {
            log.warn("Attempt to restart an already started EHCacheProvider. Use sessionFactory.close() " +
                    " between repeated calls to buildSessionFactory. Using previously created EHCacheProvider." +
                    " If this behaviour is required, consider using net.sf.EHCache.hibernate.SingletonEHCacheProvider.");
            return;
        }
        manager = new CacheManager();
        _CacheManager = new Hashtable<String, EHCache>();
	}

	/**
	 * Callback to perform any necessary cleanup of the underlying cache implementation
	 * during SessionFactory.close().
	 */
	public void stop() {
		if (manager != null) {
            manager.shutdown();
            manager = null;
        }
	}

}
