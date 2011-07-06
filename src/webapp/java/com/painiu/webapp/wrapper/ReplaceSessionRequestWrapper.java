/*
 * @(#)ReplaceSessionRequestWrapper.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.wrapper;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.painiu.session.Session;
import com.painiu.session.SessionManager;
import com.painiu.session.SessionManagerFactory;
import com.painiu.webapp.util.CookieUtils;
import com.painiu.util.RandomGUID;

/**
 * <p>
 * <a href="ReplaceSessionRequestWrapper.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Zhang Songfu
 * @version $Id: ReplaceSessionRequestWrapper.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class ReplaceSessionRequestWrapper extends HttpServletRequestWrapper {
	//~ Static fields/initializers =============================================
	
	private static final Log log = LogFactory.getLog(ReplaceSessionRequestWrapper.class);
	
	private static final String SESSION_COOKIE_NAME = "JSESSIONID";
	private static final String SESSION_URL_PARAM   = "jsessionid";
	
	//~ Instance fields ========================================================

	private CachedStoreHttpSession session = null;

	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private ServletContext context = null;
	
	private String requestedSessionId = null;
	private boolean sessionIdFromCookie = false;
	private boolean sessionIdFromUri = false;

	// ~ Constructors ===========================================================

	public ReplaceSessionRequestWrapper(HttpServletRequest request, HttpServletResponse response, ServletContext context) {
		super(request);

		this.request = request;
		this.response = response;
		this.context = context;
	}

	// ~ Methods ================================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServletRequestWrapper#getSession()
	 */
	public HttpSession getSession() {
		return getSession(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServletRequestWrapper#getSession(boolean)
	 */
	public HttpSession getSession(boolean create) {
		if (session != null && session.getId() != null && session.isValid()) {
			return session;
		}

		session = null;

		SessionManager manager = SessionManagerFactory.getSessionManager();

		String id = getRequestedSessionId();

		if (id == null) { // SESSION ID IS NOT SPECIFIED
			if (!create) {
				return null;
			}
			
			return createNewSession(manager);
		}
		
		Session cachedSession = manager.getSession(id);
		
		if (cachedSession == null || !(cachedSession instanceof CachedStoreHttpSession)) {
			if (!create) {
				return null;
			}
			
			return createNewSession(manager);
		}
		
		CachedStoreHttpSession cachedStoreHttpSession = (CachedStoreHttpSession) cachedSession;
		cachedStoreHttpSession.setServletContext(context); // SERVLET CONTEXT is not stored
		
		session = cachedStoreHttpSession;
		
		return session;
	}
	
	private HttpSession createNewSession(SessionManager manager) {
		String id = new RandomGUID().toString();
		
		while (manager.getSession(id) != null) {
			id = new RandomGUID().toString();
		}
		
		CachedStoreHttpSession cachedSession = new CachedStoreHttpSession(id, context);
		
		cachedSession.setNew(true);
		cachedSession.setMaxInactiveInterval(manager.getMaxInactiveInterval());
		
		manager.saveSession(cachedSession);
		session = cachedSession;
		
		// Creating a new session cookie based on that session
		Cookie cookie = new Cookie(SESSION_COOKIE_NAME, session.getId());
		cookie.setPath(CookieUtils.getCookiePath(request));
		cookie.setMaxAge(-1);
		if (isSecure()) {
            cookie.setSecure(true);
        }
		response.addCookie(cookie);
		
		if (log.isDebugEnabled()) {
			log.debug("New Session[" + cachedSession.getId() + "] created");
		}
		
		return session;
	}
	
	/*
	 * @see javax.servlet.http.HttpServletRequestWrapper#getRequestedSessionId()
	 */
	@Override
	public String getRequestedSessionId() {
		if (requestedSessionId == null) {
			requestedSessionId = super.getRequestedSessionId();
			
			if (requestedSessionId != null) {
				SessionManager manager = SessionManagerFactory.getSessionManager();
				if (manager.getSession(requestedSessionId) == null) {
					requestedSessionId = null;
				} else {
					sessionIdFromCookie = super.isRequestedSessionIdFromCookie();
					sessionIdFromUri = super.isRequestedSessionIdFromURL();
				}
			}
			if (requestedSessionId == null) {
				requestedSessionId = parseSessionIdFromCookie();
			}
			if (requestedSessionId == null) {
				requestedSessionId = parseSessionIdFromUri();
			}
			if (requestedSessionId == null) {
				requestedSessionId = getParameter(SESSION_URL_PARAM);
			}
		}
		return requestedSessionId;
	}
	
	/*
	 * @see javax.servlet.http.HttpServletRequestWrapper#isRequestedSessionIdFromCookie()
	 */
	@Override
	public boolean isRequestedSessionIdFromCookie() {
		return sessionIdFromCookie;
	}
	
	/*
	 * @see javax.servlet.http.HttpServletRequestWrapper#isRequestedSessionIdFromURL()
	 */
	@Override
	public boolean isRequestedSessionIdFromURL() {
		return sessionIdFromUri;
	}
	
	/*
	 * @see javax.servlet.http.HttpServletRequestWrapper#isRequestedSessionIdFromUrl()
	 */
	@Override
	public boolean isRequestedSessionIdFromUrl() {
		return sessionIdFromUri;
	}
	
	private String parseSessionIdFromCookie() {
		String id = null;
		SessionManager manager = SessionManagerFactory.getSessionManager();
		
		Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {
                if (SESSION_COOKIE_NAME.equalsIgnoreCase(cookies[i].getName())) {
                    if (id != null) {
                        // Multiple jsessionid cookies. Probably due to
                        // multiple paths and/or domains. Pick the first
                        // known session or the last defined cookie.
                        if (manager.getSession(id) != null)
                        	break;
                    }

                    id = cookies[i].getValue();
                }
            }
        }
        
        if (id != null) {
        	sessionIdFromCookie = true;
        }
        
        return id;
	}
	
	private String parseSessionIdFromUri() {
		String id = null;
		String uri = request.getRequestURI();
        
        int semi = uri.lastIndexOf(';');
        if (semi >= 0) {
            String pathParams = uri.substring(semi + 1);
            
            // check if there is a url encoded session param.
            String param = SESSION_URL_PARAM;
            if (param != null && pathParams != null && pathParams.startsWith(param) && pathParams.length() > param.length() + 1) {
                id = pathParams.substring(param.length() + 1);
            }
        }
        
        if (id != null) {
        	sessionIdFromUri = true;
        }
        
        return id;
	}
}
