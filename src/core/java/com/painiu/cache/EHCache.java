/*
 * @(#)EHCache.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.cache;

import java.io.Serializable;

import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cache.CacheException;

/**
 * <p>
 * <a href="EHCache.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Sognfu
 * @version $Id: EHCache.java 44 2010-06-12 18:40:46Z zhangsf $
 */
public class EHCache implements Cache {	
	//~ Static fields/initializers =============================================

	private static final Log log = LogFactory.getLog(EHCache.class);
	
	//~ Instance fields ========================================================
	
	private net.sf.ehcache.Cache cache;

	//~ Constructors ===========================================================
	
	public EHCache(net.sf.ehcache.Cache cache) {
		this.cache = cache;
	}

	//~ Methods ================================================================
	
	/*
	 * @see com.painiu.cache.Cache#clear()
	 */
	public void clear() throws CacheException {
		try {
            cache.removeAll();
        } catch (IllegalStateException e) {
            throw new CacheException(e);
        }
	}

	/*
	 * @see com.painiu.cache.Cache#get(java.lang.Object)
	 */
	public Object get(Object key) throws CacheException {
		try {
            if ( log.isDebugEnabled() ) {
                log.debug("key: " + key);
            }
            if (key == null) {
                return null;
            }
			Element element = cache.get( (Serializable) key );
			if (element == null) {
			    if ( log.isDebugEnabled() ) {
			        log.debug("Element for " + key + " is null");
			    }
			    return null;
			}
			return element.getValue();
        } 
        catch (net.sf.ehcache.CacheException e) {
            throw new CacheException(e);
        }
	}

	/*
	 * @see com.painiu.cache.Cache#put(java.lang.Object, java.lang.Object)
	 */
	public void put(Object key, Object value) throws CacheException {
		try {
            Element element = new Element( (Serializable) key, (Serializable) value );
            cache.put(element);
        } 
        catch (IllegalArgumentException e) {
            throw new CacheException(e);
        } 
        catch (IllegalStateException e) {
            throw new CacheException(e);
        }
	}

	/*
	 * @see com.painiu.cache.Cache#remove(java.lang.Object)
	 */
	public void remove(Object key) throws CacheException {
		try {
            cache.remove( (Serializable) key );
        } 
        catch (ClassCastException e) {
            throw new CacheException(e);
        } 
        catch (IllegalStateException e) {
            throw new CacheException(e);
        }
	}

	/* (non-Javadoc)
	 * @see com.painiu.cache.Cache#getRegionName()
	 */
	public String getRegionName() {
		return null;
	}
}
