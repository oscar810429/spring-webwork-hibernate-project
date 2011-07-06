/*
 * @(#)SessionManagerFactory.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */

package com.painiu.session;

/**
 * <p>
 * <a href="SessionManagerFactory.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SessionManagerFactory.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class SessionManagerFactory {
	
	private static SessionManager manager;
	
	private SessionManagerFactory() {
	}
	
	
	public static SessionManager getSessionManager() {
		synchronized (SessionManagerFactory.class) {
			if (manager == null) {
				manager = new CachedStoreSessionManager();
			}
		}
		
		return manager;
	}
}
