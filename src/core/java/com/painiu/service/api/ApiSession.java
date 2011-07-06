/*
 * @(#)ApiSession.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.service.api;

import com.painiu.session.Session;
import com.painiu.session.SessionManager;
import com.painiu.session.SessionManagerFactory;

/**
 * <p>
 * <a href="ApiSession.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ApiSession.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class ApiSession {
	
	private ApiSession() {}
	
	private static String getSessionId(String token) {
		return "token_" + token;
	}
	
	public static Session getSession(String token) {
		return getSession(token, true);
	}
	
	public static Session getSession(String token, boolean create) {
		String sessionId = getSessionId(token);
		
		SessionManager sessionManager = SessionManagerFactory.getSessionManager();
		
		Session session = sessionManager.getSession(sessionId);
		
		if (session == null && create) {
			session = sessionManager.createSession(sessionId);
		}
		
		return session;
	}
	
	public static void saveSession(Session session) {
		SessionManagerFactory.getSessionManager().saveSession(session);
	}
}
