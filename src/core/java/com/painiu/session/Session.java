/*
 * @(#)Session.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.session;

import java.io.Serializable;
import java.util.Enumeration;

/**
 * A <b>Session</b> is used to maintain state information
 * between requests for a particular user of a api client.
 * 
 * <p>
 * <a href="Session.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Zhang Songfu
 * @version $Id: Session.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public interface Session extends Serializable {

	/**
	 * Returns the time when this session was created, measured in milliseconds
	 * since midnight January 1, 1970 GMT.
	 * 
	 * @return a <code>long</code> specifying when this session was created,
	 *         expressed in milliseconds since 1/1/1970 GMT
	 * 
	 * @exception IllegalStateException
	 *                if this method is called on an invalidated session
	 */
	public long getCreationTime();

	/**
	 * Returns a string containing the unique identifier assigned to this
	 * session. The identifier is assigned by the servlet container and is
	 * implementation dependent.
	 * 
	 * @return a string specifying the identifier assigned to this session
	 * 
	 * @exception IllegalStateException
	 *                if this method is called on an invalidated session
	 */
	public String getId();

	/**
	 * Returns the last time the client sent a request associated with this
	 * session, as the number of milliseconds since midnight January 1, 1970
	 * GMT, and marked by the time the container received the request.
	 * 
	 * <p>
	 * Actions that your application takes, such as getting or setting a value
	 * associated with the session, do not affect the access time.
	 * 
	 * @return a <code>long</code> representing the last time the client sent
	 *         a request associated with this session, expressed in milliseconds
	 *         since 1/1/1970 GMT
	 * 
	 * @exception IllegalStateException
	 *                if this method is called on an invalidated session
	 */
	public long getLastAccessedTime();

	/**
	 * Specifies the time, in seconds, between client requests before the
	 * servlet container will invalidate this session. A negative time indicates
	 * the session should never timeout.
	 * 
	 * @param interval
	 *            An integer specifying the number of seconds
	 */
	public void setMaxInactiveInterval(int interval);

	/**
	 * Returns the maximum time interval, in seconds, that the servlet container
	 * will keep this session open between client accesses. After this interval,
	 * the servlet container will invalidate the session. The maximum time
	 * interval can be set with the <code>setMaxInactiveInterval</code>
	 * method. A negative time indicates the session should never timeout.
	 * 
	 * 
	 * @return an integer specifying the number of seconds this session remains
	 *         open between client requests
	 * 
	 * @see #setMaxInactiveInterval
	 */
	public int getMaxInactiveInterval();

	/**
	 * Returns the object bound with the specified name in this session, or
	 * <code>null</code> if no object is bound under the name.
	 * 
	 * @param name
	 *            a string specifying the name of the object
	 * 
	 * @return the object with the specified name
	 * 
	 * @exception IllegalStateException
	 *                if this method is called on an invalidated session
	 */
	public Object getAttribute(String name);

	/**
	 * Binds an object to this session, using the name specified. If an object
	 * of the same name is already bound to the session, the object is replaced.
	 * 
	 * <p>
	 * After this method executes, and if the new object implements
	 * <code>HttpSessionBindingListener</code>, the container calls
	 * <code>HttpSessionBindingListener.valueBound</code>. The container then
	 * notifies any <code>HttpSessionAttributeListener</code>s in the web
	 * application.
	 * 
	 * <p>
	 * If an object was already bound to this session of this name that
	 * implements <code>HttpSessionBindingListener</code>, its
	 * <code>HttpSessionBindingListener.valueUnbound</code> method is called.
	 * 
	 * <p>
	 * If the value passed in is null, this has the same effect as calling
	 * <code>removeAttribute()<code>.
	 *
	 *
	 * @param name			the name to which the object is bound;
	 *					cannot be null
	 *
	 * @param value			the object to be bound
	 *
	 * @exception IllegalStateException	if this method is called on an
	 *					invalidated session
	 */
	public void setAttribute(String name, Object value);

	public Enumeration getAttributeNames();
	
	/**
	 * Removes the object bound with the specified name from this session. If
	 * the session does not have an object bound with the specified name, this
	 * method does nothing.
	 * 
	 * <p>
	 * After this method executes, and if the object implements
	 * <code>HttpSessionBindingListener</code>, the container calls
	 * <code>HttpSessionBindingListener.valueUnbound</code>. The container
	 * then notifies any <code>HttpSessionAttributeListener</code>s in the
	 * web application.
	 * 
	 * @param name
	 *            the name of the object to remove from this session
	 * 
	 * @exception IllegalStateException
	 *                if this method is called on an invalidated session
	 */
	public void removeAttribute(String name);

	/**
	 * Invalidates this session then unbinds any objects bound to it.
	 * 
	 * @exception IllegalStateException
	 *                if this method is called on an already invalidated session
	 */
	public void invalidate();
	
    /**
     * Return the <code>isValid</code> flag for this session.
     */
    public boolean isValid();
    
    public void expire();
    
    public void access();
    
    public boolean isNew();
    
    public void setNew(boolean isNew);
    
    public boolean isDirty();
}