/*
 * @(#)SimpleEventPublisher.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.event;


/**
 * <p>
 * <a href="SimpleEventPublisher.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SimpleEventPublisher.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class SimpleEventPublisher implements EventPublisher {

	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	private EventMulticaster eventMulticaster;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	/**
	 * @param eventMulticaster The eventMulticaster to set.
	 */
	public void setEventMulticaster(EventMulticaster eventMulticaster) {
		this.eventMulticaster = eventMulticaster;
	}
	
	/*
	 * @see com.yupoo.event.EventPublisher#publishPhotoEvent(com.yupoo.event.Event)
	 */
	public void publishEvent(Event event) {
		eventMulticaster.multicastEvent(event);
	}

}
