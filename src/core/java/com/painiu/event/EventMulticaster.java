/*
 * @(#)EventMulticaster.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.event;

import java.util.Collection;


/**
 * <p>
 * <a href="EventMulticaster.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: EventMulticaster.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public interface EventMulticaster {
	/**
	 * Add a listener to be notified of all events.
	 * @param listener the listener to add
	 */
	public void addEventListener(EventListener listener);

	/**
	 * Remove a listener from the notification list.
	 * @param listener the listener to remove
	 */
	public void removeEventListener(EventListener listener);

	public void removeAllListeners();
	
	public void setListeners(Collection listeners);

	/**
	 * Multicast the given application event to appropriate listeners.
	 * @param event the event to multicast
	 */
	public void multicastEvent(Event event);
}
