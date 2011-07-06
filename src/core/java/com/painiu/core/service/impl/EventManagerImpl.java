/*
 * @(#)MovementManagerImpl.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.painiu.core.aop.transaction.NonTransactional;
import com.painiu.core.dao.EventDAO;
import com.painiu.event.EventPublisher;
import com.painiu.event.EventPublisherAware;
import com.painiu.core.service.EventManager;
import com.painiu.core.model.Event;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="EventManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Zhang Songfu
 * @version $Id: EventManagerImpl.java 8 2010-05-11 16:48:01Z zhangsf $
 */
@Transactional
public class EventManagerImpl extends BaseManager implements EventManager,
		EventPublisherAware {
	// ~ Static fields/initializers
	// =============================================

	// ~ Instance fields
	// ========================================================
	private EventDAO eventDAO;

	// ~ Constructors
	// ===========================================================

	// ~ Methods
	// ================================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.core.logic.EventManager#setEventDAO(com.painiu.core.model.Event)
	 */
	@NonTransactional
	public void setEventDAO(EventDAO dao) {
		eventDAO = dao;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.core.logic.EventManager#getEvent(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public Event getEvent(String id) {
		return eventDAO.getEvent(id);
	}
	
	/*
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@NonTransactional
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(eventDAO);

	} 

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.core.logic.EventManager#getEventByAlias(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public Event getEventByAlias(int kind,String alias) {
		return eventDAO.getEventByAlias(kind,alias);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.core.logic.EventManager#getEventsByName(java.lang.String, int,
	 *      int)
	 */
	@Transactional(readOnly=true)
	public Result getEventsByName(int kind,String name, int start, int limit) {
		return eventDAO.findEventsByName(kind,name, start, limit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.core.logic.EventManager#getEventsByStartDate(java.util.Date)
	 */
	@Transactional(readOnly=true)
	public Result getEventsStartAfterDate(int kind,Date startDate, int start, int limit) {
		return eventDAO.getEventsByStartDate(kind,startDate, start, limit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.core.logic.EventManager#removeEvent(com.painiu.core.model.Event)
	 */
	public void removeEvent(Event event) {
		eventDAO.removeEvent(event);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.core.logic.EventManager#removeEvent(java.lang.String)
	 */
	public void removeEvent(String id) {
		eventDAO.removeEvent(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.core.logic.EventManager#removeEventByAlias(java.lang.String)
	 */
	public void removeEventByAlias(int kind,String alias) {
		eventDAO.removeEventByAlias(kind,alias);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.core.logic.EventManager#saveEvent(com.painiu.core.model.Event)
	 */
	public void saveEvent(Event event) {
		eventDAO.saveEvent(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.core.logic.EventManager#getEventsEndBeforeDate(java.util.Date,
	 *      int, int)
	 */
	@Transactional(readOnly = true)
	public Result getEventsEndBeforeDate(int kind,Date endDate, int start, int limit) {
		return eventDAO.getEventsEndBeforeDate(kind,endDate, start, limit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.core.event.EventPublisherAware#setEventPublisher(com.painiu.core.event.EventPublisher)
	 */
	@Transactional(readOnly = true)
	public boolean aliasIsUnique(int kind,String alias, String id) {
		return eventDAO.aliasIsUnique(kind,alias, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.core.logic.EventManager#getLastEvents(int)
	 */
	@Transactional(readOnly = true)
	public List getLastEvents(int kind,int amount) {
		return eventDAO.getLastEvents(kind,amount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.core.logic.EventManager#getEvents(int, int)
	 */
	@Transactional(readOnly = true)
	public Result getEvents(int kind,int start, int limit) {
		return eventDAO.getEvents(kind,start, limit);
	}
	
	/*
	 * @see com.painiu.core.logic.EventManager#getEvents(int, int)
	 */
	@Transactional(readOnly = true)
	public Result getEvents(int start, int limit) {
		return eventDAO.getEvents(start, limit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.core.logic.EventManager#getEventsBetweenPeriod(java.util.Date,
	 *      java.util.Date, int, int)
	 */
	@Transactional(readOnly = true)
	public Result getEventsBetweenPeriod(int kind,Date startDate, Date endDate,int start, int limit) {
		return eventDAO.getEventsBetweenPeriod(kind,startDate, endDate, start, limit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.core.logic.EventManager#getUnfinishedEvents(int, int)
	 */
	@Transactional(readOnly = true)
	public Result getUnfinishedEvents(int kind,int start, int limit) {
		return eventDAO.getUnfinishedEvents(kind,start, limit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.core.event.EventPublisherAware#setEventPublisher(com.painiu.core.event.EventPublisher)
	 */
	@NonTransactional
	public void setEventPublisher(EventPublisher publisher) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.EventManager#getCurrentEvents(int, int)
	 */
	@Transactional(readOnly = true)
	public Result getCurrentEvents(int kind,int start, int limit) {
		return eventDAO.getCurrentEvents(kind,start, limit);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.EventManager#getHistoryEvents(int, int)
	 */
	@Transactional(readOnly = true)
	public Result getHistoryEvents(int kind,int start, int limit) {
		return eventDAO.getHistoryEvents(kind,start, limit);
	}
	
	@Transactional(readOnly = true)
	public Result getCollectionEvents(int kind, String tags, int start, int limit) {
		return eventDAO.getCollectionEvents(kind, tags, start, limit);
	}
	// ~ Accessors
	// ==============================================================

	/*
	 * @see com.painiu.core.logic.EventManager#getEventsDisplayAtPhotoView(int, int)
	 */
	@Transactional(readOnly = true)
	public List getEventsDisplayAtPhotoView(int start, int limit) {
		
		return eventDAO.getEventsDisplayAtPhotoView(start, limit);
	}

}
