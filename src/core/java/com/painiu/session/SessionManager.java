/*
 * @(#)SessionManager.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.session;

/**
 * <p>
 * <a href="SessionManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SessionManager.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public interface SessionManager {
	

    /**
     * Return the default maximum inactive interval (in seconds)
     * for Sessions created by this Manager.
     */
    public int getMaxInactiveInterval();


    /**
     * Set the default maximum inactive interval (in seconds)
     * for Sessions created by this Manager.
     *
     * @param interval The new default value
     */
    public void setMaxInactiveInterval(int interval);

    /**
     * Return Session with specfied id.
     * 
     * @param id
     * @return
     */
    public Session getSession(String id);
    
    /**
     * Construct and return a new session object, based on the default
     * settings specified by this Manager's properties.  The session
     * id will be assigned by this method, and available via the getId()
     * method of the returned session.  If a new session cannot be created
     * for any reason, return <code>null</code>.
     */
    public Session createSession(String id);
    
    public void saveSession(Session session);
    
    public void removeSession(Session session);
    
    /* ------------------------------------------------------------ */
    // public Session newHttpSession(HttpServletRequest request);

    /* ------------------------------------------------------------ */
    /** @return true if session cookies should be secure
     */
    //public boolean getSecureCookies();

    /* ------------------------------------------------------------ */
    /** @return true if session cookies should be httponly (microsoft extension)
     */
    //public boolean getHttpOnly();

    /* ------------------------------------------------------------ */
    /** Add an event listener.
     * @param listener An Event Listener. Individual SessionManagers
     * implemetations may accept arbitrary listener types, but they
     * are expected to at least handle
     *   HttpSessionActivationListener,
     *   HttpSessionAttributeListener,
     *   HttpSessionBindingListener,
     *   HttpSessionListener
     */
   // public void addEventListener(EventListener listener);
    
    /* ------------------------------------------------------------ */
  //  public void removeEventListener(EventListener listener);
    
    /* ------------------------------------------------------------ */
   // public void clearEventListeners();

    /* ------------------------------------------------------------ */
    /** Get a Cookie for a session.
     * @param session
     * @param contextPath TODO
     * @return
     */
   // public Cookie getSessionCookie(HttpSession session,String contextPath, boolean requestIsSecure);
    
}
