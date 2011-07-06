/*
 * @(#)MovementManager.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service;

import java.util.Date;
import java.util.List;

import com.painiu.core.dao.EventDAO;
import com.painiu.core.model.Event;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="EventManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: EventManager.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public interface EventManager {
//	~ Methods ================================================================
	 public void setEventDAO(EventDAO dao);
	 
	 public Event getEvent(String id);
		
	 public Event getEventByAlias(int kind,String alias);
	    
	 public Result getEventsStartAfterDate(int kind,Date startDate,int start,int limit);
	 
	 public Result getEventsEndBeforeDate(int kind,Date endDate,int start,int limit);
		
	 public Result getEventsByName(int kind,String name, int start, int limit);
	 
	 public Result getEventsBetweenPeriod(int kind, Date startDate, Date endDate,int start,int limit);
	 
	 public Result getEvents(int kind,int start,int limit);
	 public Result getEvents(int start, int limit);
	 public List getEventsDisplayAtPhotoView(int start, int limit);
		
	 public List getLastEvents(int kind,int amount);
	 
	 public Result getHistoryEvents(int kind,int start,int limit);
		
	 public Result getCurrentEvents(int kind,int start,int limit);
		
	 public Result getUnfinishedEvents(int kind,int start,int limit);
				
	 public void saveEvent(Event Event);
		
	 public void removeEvent(Event Event);
		
	 public void removeEventByAlias(int kind,String  alias);
		
	 public void removeEvent(String  id);
	 
	 public boolean aliasIsUnique(int kind,String alias,String id);
	 
	 public Result getCollectionEvents(int kind, String tags, int start, int limit);
	 
	 
}
