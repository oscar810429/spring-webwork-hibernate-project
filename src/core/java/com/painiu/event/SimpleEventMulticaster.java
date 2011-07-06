/*
 * @(#)SimpleEventMulticaster.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.event;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.BeanUtils;
import org.springframework.core.CollectionFactory;

/**
 * <p>
 * <a href="SimpleEventMulticaster.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SimpleEventMulticaster.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class SimpleEventMulticaster implements EventMulticaster {

	/** Collection of PhotoEventListeners */
	private Collection listeners = CollectionFactory.createLinkedSetIfPossible(5);

	/**
	 * Specify the collection class to use. Can be populated with a fully
	 * qualified class name when defined in a Spring application context.
	 * <p>Default is a linked HashSet, keeping the registration order.
	 * If no linked Set implementation is available, a plain HashSet will
	 * be used as fallback (not keeping the registration order).
	 * <p>Note that a Set class specified will not permit multiple instances
	 * of the same listener, while a List class woill allow for registering
	 * the same listener multiple times.
	 * <p>Consider Doug Lea's <code>java.util.concurrent.CopyOnWriteArraySet</code> or its
	 * non-JDK predecessor, <code>EDU.oswego.cs.dl.util.concurrent.CopyOnWriteArraySet</code>
	 * (or the respective CopyOnWriteArrayList version). Those classes provide a thread-safe
	 * Iterator, optimized for read-mostly usage - matching this use case nicely.
	 * @see org.springframework.core.CollectionFactory#createLinkedSetIfPossible
	 * @see java.util.concurrent.CopyOnWriteArraySet
	 * @see EDU.oswego.cs.dl.util.concurrent.CopyOnWriteArraySet
	 */
	public void setCollectionClass(Class collectionClass) {
		if (collectionClass == null) {
			throw new IllegalArgumentException("collectionClass must not be null");
		}
		if (!Collection.class.isAssignableFrom(collectionClass)) {
			throw new IllegalArgumentException("collectionClass must implement [java.util.Collection]");
		}
		// Create desired collection instance.
		// Add all previously registered listeners (usually none).
		Collection newColl = (Collection) BeanUtils.instantiateClass(collectionClass);
		newColl.addAll(this.listeners);
		this.listeners = newColl;
	}


	public void addEventListener(EventListener listener) {
		this.listeners.add(listener);
	}

	public void removeEventListener(EventListener listener) {
		this.listeners.remove(listener);
	}

	public void removeAllListeners() {
		this.listeners.clear();
	}

	public void setListeners(Collection listeners) {
		this.removeAllListeners();
		for (Iterator i = listeners.iterator(); i.hasNext();) {
			EventListener listener = (EventListener) i.next();
			this.addEventListener(listener);
		}
	}
	
	/**
	 * Return the current Collection of ApplicationListeners.
	 * <p>Note that this is the raw Collection of ApplicationListeners,
	 * potentially modified when new listeners get registered or
	 * existing ones get removed. This Collection is not a snapshot copy.
	 * @return a Collection of ApplicationListeners
	 * @see org.springframework.context.ApplicationListener
	 */
	protected Collection getEventListeners() {
		return listeners;
	}

	public void multicastEvent(Event event) {
		for (Iterator it = getEventListeners().iterator(); it.hasNext();) {
			EventListener listener = (EventListener) it.next();
			listener.onEvent(event);
		}
	}

}
