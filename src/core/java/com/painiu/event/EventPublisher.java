/*
 * @(#)EventPublisher.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.event;


/**
 * <p>
 * <a href="EventPublisher.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: EventPublisher.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public interface EventPublisher {
	
	public void publishEvent(Event event);
}
