/*
 * @(#)MovementDAO.java Nov 29, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao;

import java.util.Date;
import java.util.List;
import com.painiu.core.model.Event;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="MovementDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: EventDAO.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public interface EventDAO extends DAO {
		
	public Event getEvent(String id);
	
	public Event getEventByAlias(int kind,String alias);
    
	public Result getEventsByStartDate(int kind,Date startDate,int start,int limit);
	
	public Result getEventsEndBeforeDate(int kind,Date endDate,int start,int limit);
	
	public Result getEventsBetweenPeriod(int kind, Date startDate, Date endDate,int start,int limit);
	
	public Result getEvents(int kind,int start,int limit);
	public Result getEvents(int start,int limit);
	public List getEventsDisplayAtPhotoView(int start, int limit);
	
	public Result getHistoryEvents(int kind,int start,int limit);
	
	public Result getCurrentEvents(int kind,int start,int limit);
	
	public List getLastEvents(int kind,int amount);
	
	public Result getUnfinishedEvents(int kind,int start,int limit);
	
	public Result findEventsByName(int kind, String name, int start, int limit);
	
	public void saveEvent(Event Event);
	
	public void removeEvent(Event Event);
	
	public void removeEventByAlias(int kind,String  alias);
	
	public void removeEvent(String  id);
	
	public boolean aliasIsUnique(int kind,String alias,String id);

	public Result getCollectionEvents (int kind,String tags,int start, int limit);
}

