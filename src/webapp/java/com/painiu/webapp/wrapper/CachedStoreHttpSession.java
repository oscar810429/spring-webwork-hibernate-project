/*
 * @(#)CachedStoreHttpSession.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.wrapper;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.painiu.session.CachedStoreSession;

/**
 * <p>
 * <a href="CachedStoreHttpSession.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id $
 */
public class CachedStoreHttpSession extends CachedStoreSession implements HttpSession {
	
	//~ Static fields/initializers =============================================
    
	private static final long serialVersionUID = 5400909765496257075L;
	
	//~ Instance fields ========================================================

	private transient ServletContext context;

	//~ Constructors ===========================================================
	
	/**
	 * @param id
	 */
	public CachedStoreHttpSession(String id, ServletContext context) {
		super(id);
		
		this.context = context;
	}
	
	//~ Methods ================================================================

	public void setServletContext(ServletContext context) {
		this.context = context;
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSession#getServletContext()
	 */
	public ServletContext getServletContext() {
		return context;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSession#getSessionContext()
	 */
	@SuppressWarnings("deprecation")
	public javax.servlet.http.HttpSessionContext getSessionContext() {
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSession#getValue(java.lang.String)
	 */
	public Object getValue(String name) {
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSession#getValueNames()
	 */
	public String[] getValueNames() {
		return new String[0];
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSession#putValue(java.lang.String, java.lang.Object)
	 */
	public void putValue(String name, Object value) {
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSession#removeValue(java.lang.String)
	 */
	public void removeValue(String name) {
		
	}
}
