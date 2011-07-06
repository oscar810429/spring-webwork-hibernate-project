/*
 * @(#)AbuseDAOHibernate.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao.hibernate;

import java.util.Date;

import org.hibernate.Hibernate;
import org.hibernate.type.Type;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.painiu.core.dao.AbuseDAO;
import com.painiu.core.model.Abuse;
import com.painiu.core.model.Abuse.State;
import com.painiu.core.persistence.UserTypes;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="AbuseDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: AbuseDAOHibernate.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class AbuseDAOHibernate extends BaseDAOHibernate implements AbuseDAO {

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.AbuseDAO#findAbuseList(int, int)
	 */
	public Result findAbuseList(int kind, State state, int start, int limit) {		
		return find(
				"select count(abuse.id) from Abuse abuse where abuse.kind = " + kind + " and abuse.state = ? " ,
				"from Abuse abuse where abuse.kind = " + kind + " and abuse.state = ? order by abuse.createDate desc ",
				new Object[]{state},new Type[]{UserTypes.abuseStateType()},start, limit);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.AbuseDAO#getAbuse(java.lang.String)
	 */
	public Abuse getAbuse(String id) {
		Abuse abuse = (Abuse)getHibernateTemplate().get(Abuse.class,id);
		if (abuse == null) {
			log.warn("uh, oh, Abuse[" + id + "] not found...");
        	throw new ObjectRetrievalFailureException(Abuse.class, id);
		}
		return abuse;
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.AbuseDAO#save(com.painiu.core.model.Abuse)
	 */
	public void save(Abuse abuse) {
		if (log.isDebugEnabled()) {
			log.debug("saving " + abuse);
		}
		
		getHibernateTemplate().saveOrUpdate(abuse);

	}
	
	public void removeAbuse(Abuse abuse) {
		if (log.isDebugEnabled()) {
			log.debug("removeR " + abuse);
		}
		
		getHibernateTemplate().delete(abuse);

	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.AbuseDAO#findAbuseResult(int, com.painiu.core.model.Abuse.State, java.util.Date, java.util.Date, int, int)
	 */
	public Result findAbuseResult(int kind, State state, Date startDate, Date endDate, int start, int limit) {
		return find(
				"select count(abuse.id) from Abuse abuse where abuse.kind = " + kind + " and abuse.state = ? and abuse.createDate > ? and abuse.createDate < ?" ,
				"from Abuse abuse where abuse.kind = " + kind + " and abuse.state = ? and abuse.createDate > ? and abuse.createDate < ? order by abuse.createDate desc ",
				new Object[]{state, startDate, endDate},new Type[]{UserTypes.abuseStateType(), Hibernate.TIMESTAMP, Hibernate.TIMESTAMP},start, limit);
	}

}
