/*
 * @(#)CachedStoreSessionManager.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.session;

import com.painiu.cache.Cache;
import com.painiu.cache.CacheManager;

/**
 * <p>
 * <a href="CachedStoreSessionManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: CachedStoreSessionManager.java 156 2010-12-24 00:56:15Z zhangsf $
 */
public class CachedStoreSessionManager implements SessionManager {

	//~ Static fields/initializers =============================================

    //	private static final Log log = LogFactory.getLog(CachedStoreSessionManager.class);
	
	public static final int DEFAULT_MAX_INACTIVE_INTERVAL = 5 * 60;
	
	private static final String CACHE_NAME = Session.class.getName();
	private static final String CACHE_KEY_PREFIX = CACHE_NAME + "#";
	
	//~ Instance fields ========================================================

	private int maxInactiveInterval = DEFAULT_MAX_INACTIVE_INTERVAL;
	
	// private Map sessions = new HashMap();
	private Cache cache = null;
	
	//~ Constructors ===========================================================

	public CachedStoreSessionManager() {
		cache = CacheManager.getSessionCache(CACHE_NAME);
	}
	
	//~ Methods ================================================================

	/*
	 * @see com.painiu.service.api.SessionManager#getMaxInactiveInterval()
	 */
	public int getMaxInactiveInterval() {
		return maxInactiveInterval;
	}

	/*
	 * @see com.painiu.service.api.SessionManager#setMaxInactiveInterval(int)
	 */
	public void setMaxInactiveInterval(int interval) {
		this.maxInactiveInterval = interval;
	}

	/*
	 * @see com.painiu.service.api.SessionManager#getSession(java.lang.String)
	 */
	public Session getSession(String id) {
		if (id == null) {
            return null;
		}
        
		Session session = (Session) cache.get(getCacheKey(id));
		if (session != null) {
			if (session.isValid()) {
				session.setNew(false);
				session.access();
			} else {
				session.expire();
				return null;
			}
		}

		return session;
	}

	/*
	 * @see com.painiu.service.api.SessionManager#createSession()
	 */
	public Session createSession(String id) {
        // Recycle or create a Session instance
        Session session = newSession(id);

        // Initialize the properties of the new session and return it
        session.setMaxInactiveInterval(this.maxInactiveInterval);
        session.access();
        
        cache.put(getCacheKey(id), session);
        
        return session;
	}
	
	/* (non-Javadoc)
	 * @see com.painiu.session.SessionManager#saveSession(com.painiu.session.Session)
	 */
	public void saveSession(Session session) {
        cache.put(getCacheKey(session.getId()), session);
	}

	protected Session newSession(String id) {
		return new CachedStoreSession(id);
	}

    /*
     * @see com.painiu.session.SessionManager#removeSession(com.painiu.session.Session)
     */
	
	public void removeSession(Session session) {
        cache.remove(getCacheKey(session.getId()));
	}
	
	private static String getCacheKey(String sessionId) {
		return CACHE_KEY_PREFIX + sessionId;
	}
}
