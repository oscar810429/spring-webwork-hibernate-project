/*
 * @(#)MovementDAOHibernate.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;

import org.hibernate.type.Type;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.painiu.core.dao.EventDAO;
import com.painiu.core.model.Event;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="EventDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Zhang Songfu
 * @version $Id: EventDAOHibernate.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class EventDAOHibernate extends BaseDAOHibernate implements EventDAO {

	// ~ Static fields/initializers
	// =============================================

	// ~ Instance fields
	// ========================================================

	// ~ Constructors
	// ===========================================================

	// ~ Methods
	// ================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.core.dao.EventDAO#getEventById(java.lang.String)
	 */
	public Event getEvent(String id) {
		Event event = (Event) getHibernateTemplate().get(Event.class, id);
		if (event == null) {
			log.warn("uh oh, event '" + id + "' not found...");
			throw new ObjectRetrievalFailureException(Event.class, id);
		}
		return event;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.core.dao.EventDAO#getEventByName(java.lang.String)
	 */
	public Event getEventByAlias(int kind,String alias) {
		List list = getHibernateTemplate().find(
				"from Event a where a.kind = " + kind + " and a.alias = ?", alias);
		if (list != null & list.size() > 0) {
			return (Event) list.get(0);
		}
		if (log.isWarnEnabled()) {
			log.warn("can not find event[alias:" + alias + "]");
		}
		throw new ObjectRetrievalFailureException(Event.class, alias);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.core.dao.EventDAO#findEventsByName(java.lang.String)
	 */

	public Result findEventsByName(int kind,String name, int start, int limit) {
		String countSql = "select count(e.id) from Event e where e.kind = ? and e.name = ? and e.weight > 0";
		String sql = "from Event e where  e.kind = ? and e.name = ? and e.weight > 0 order by e.eventOrder asc,e.weight desc";
		return find(countSql, sql, new Object[] {kind, name },
				new Type[] {Hibernate.INTEGER, Hibernate.STRING }, start, limit);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.core.dao.EventDAO#getEventsByStartDate(java.util.Date)
	 */
	public Result getEventsByStartDate(int kind,Date startDate, int start, int limit) {
		String countSql = "select count(e.id) from Event e where e.kind = ? and e.startDate > ? ";
		String sql = "from Event e where e.kind = ? and e.startDate > ? order by e.createdDate desc";
		return find(countSql, sql, new Object[] {kind, startDate },
				new Type[] {Hibernate.INTEGER, Hibernate.TIMESTAMP }, start, limit);
	}

	public Result getEventsEndBeforeDate(int kind,Date endDate, int start, int limit) {
		String countSql = "select count(e.id) from Event e where e.kind = ? and e.endDate < ? and e.weight > 0";
		String sql = "from Event e where  e.kind = ? and e.endDate < ? and e.weight > 0 order by e.eventOrder asc,e.weight desc";
		return find(countSql, sql, new Object[] {kind, endDate },
				new Type[] {Hibernate.INTEGER, Hibernate.TIMESTAMP }, start, limit);
	}

	public Result getEventsBetweenPeriod(int kind,Date startDate, final Date endDate,int start, int limit) {
		String countSql = "select count(e.id) from Event e where e.kind = ? and e.startDate < ? and e.endDate > ? and e.weight > 0";
		String sql = "from Event e where e.kind = ? and e.startDate < ? and e.endDate > ? and e.weight > 0 order by e.eventOrder asc,e.weight desc";
		return find(countSql, sql, new Object[] {kind, endDate, startDate },
				new Type[] {Hibernate.INTEGER, Hibernate.TIMESTAMP, Hibernate.TIMESTAMP }, start,
				limit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.core.dao.EventDAO#removeEvent(com.painiu.core.model.Event)
	 */
	public void removeEvent(Event event) {
		getHibernateTemplate().delete(event);
	}

	public void removeEventByAlias(int kind,String alias) {
		Event Event = getEventByAlias(kind,alias);
		getHibernateTemplate().delete(Event);
	}

	public void removeEvent(String id) {
		Event event = getEvent(id);
		getHibernateTemplate().delete(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.core.dao.EventDAO#saveEvent(com.painiu.core.model.Event)
	 */
	public void saveEvent(Event event) {
		if (log.isDebugEnabled()) {
			log.debug("event's name: " + event.getName());
		}

		getHibernateTemplate().saveOrUpdate(event);
		getHibernateTemplate().flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.core.dao.EventDAO#saveEvent(com.painiu.core.model.Event)
	 */
	public boolean aliasIsUnique(int kind,String alias, String id) {
		if (id == null || "".equals(id)) {
			String countSql = "select count(e.id) from Event e where e.kind = ? and e.alias = ? ";
			int number = count(countSql, new Object[] {kind, alias },
					new Type[] {Hibernate.INTEGER, Hibernate.STRING });
			if (0 < number) {
				return false;
			}
			return true;
		}
		String countSql = "select count(e.id) from Event e where e.kind = ? and e.alias = ? and e.id <> ?";
		if (0 < count(countSql, new Object[] {kind, alias, id }, new Type[] {
				Hibernate.INTEGER, Hibernate.STRING, Hibernate.STRING })) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.core.dao.EventDAO#getLastEvents(int, int)
	 */
	public Result getEvents(int kind,int start, int limit) {
		String countSql = "select count(e.id) from Event e where e.kind = ? and e.weight > 0 ";
		String sql = "from Event e  where e.kind = ? and e.weight > 0 order by e.eventOrder asc, e.createdDate desc";
		return find(countSql, sql, new Object[] {kind },
				new Type[] {Hibernate.INTEGER}, start, limit);
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.core.dao.EventDAO#getLastEvents(int, int)
	 */
	public List getLastEvents(int kind,int amount) {
		return getHibernateTemplate().find("from Event e where e.kind = " + kind + " e.weight > 0 order by e.eventOrder asc,e.createdDate desc", amount);		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.core.dao.EventDAO#getUnfinishedEvents(int, int)
	 */
	public Result getUnfinishedEvents(int kind, int start, int limit) {
		String countSql = "select count(e.id) from Event e where e.kind = ? and e.endDate > ? and e.weight > 0";
		String sql = "from Event e where e.kind = ? and e.endDate>? and e.weight > 0 order by e.eventOrder asc,e.weight desc";
		return find(countSql, sql, new Object[] {kind, new Date() },
				new Type[] {Hibernate.INTEGER, Hibernate.TIMESTAMP }, start, limit);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.EventDAO#getCurrentEvents(int, int)
	 */
	public Result getCurrentEvents(int kind,int start, int limit) {
		String countSql = "select count(e.id) from Event e where e.kind = ?  and e.weight > 0";
		String sql = "from Event e where e.kind = ?  and e.weight > 0 order by  e.eventOrder asc,e.createdDate desc";
		return find(countSql, sql, new Object[] {kind },
				new Type[] {Hibernate.INTEGER}, start, limit);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.EventDAO#getHistoryEvents(int, int)
	 */
	public Result getHistoryEvents(int kind,int start, int limit) {
		String countSql = "select count(e.id) from Event e where e.kind = ? and e.weight < 0";
		String sql = "from Event e where e.kind = ? and e.weight < 0 order by  e.createdDate desc";
		return find(countSql, sql, new Object[] {kind },
				new Type[] {Hibernate.INTEGER}, start, limit);
		
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.EventDAO#getEvents(int, java.lang.String, int, int)
	 */
	public Result getCollectionEvents(int kind, String tags, int start, int limit) {
		String countSql = "select count(e.id) from Event e where e.kind = ? and e.tags = ?  and e.weight > 0";
		String sql = "from Event e where e.kind = ? and e.tags = ? and e.weight > 0 order by  e.createdDate desc";
		return find(countSql, sql, new Object[] {kind, tags },
				new Type[] {Hibernate.INTEGER,  Hibernate.STRING}, start, limit);
	}

	/*
	 * @see com.painiu.core.dao.EventDAO#getEvents(int, int)
	 */
	public Result getEvents(int start, int limit) {
		String sql = "from Event e  " +
				"where e.weight > 0  " +
				"order by e.eventOrder asc, e.createdDate desc";
		return find(sql, start, limit);
	}

	/*
	 * @see com.painiu.core.dao.EventDAO#getEventsDisplayAtPhotoView(int, int)
	 */
	public List getEventsDisplayAtPhotoView(int start, int limit) {
		String sql = "from Event e  " +
				"where e.weight > 0 and e.eventOrder = -1  " +
				"order by e.eventOrder asc, e.createdDate desc";
		return find(sql, start, limit).getData();

	}


	// TODO Auto-generated method stub
	// ~ Accessors
	// ==============================================================

}
