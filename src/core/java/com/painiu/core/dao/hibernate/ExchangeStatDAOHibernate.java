/*
 * @(#)ExchangeStatDAOHibernate.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao.hibernate;

import java.sql.SQLException;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.Type;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.painiu.core.dao.ExchangeStatDAO;
import com.painiu.core.model.ExchangeStat;
import com.painiu.core.model.User;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="ExchangeStatDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ExchangeStatDAOHibernate.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class ExchangeStatDAOHibernate extends BaseDAOHibernate implements
		ExchangeStatDAO {
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	//~ Constructors ===========================================================

	//~ Methods ================================================================
	/*
	 * @see com.painiu.core.dao.ExchangeStatDAO#deleteExchangeStat(com.painiu.core.model.ExchangeStat)
	 */
	public void deleteExchangeStat(ExchangeStat stat) {
		getHibernateTemplate().delete(stat);
	}

	/*
	 * @see com.painiu.core.dao.ExchangeStatDAO#getExchangeStat(java.lang.String)
	 */
	public ExchangeStat getExchangeStat(String id) {
		ExchangeStat stat = (ExchangeStat)getHibernateTemplate().get(ExchangeStat.class, id);
		if (stat == null) {
			log.warn("uh oh, ExchangeStat '" + id + "' not found...");
			throw new ObjectRetrievalFailureException(ExchangeStat.class, id);
		}
		return stat;
	}

	/*
	 * @see com.painiu.core.dao.ExchangeStatDAO#getExchangeStat(com.painiu.core.model.User, java.lang.String)
	 */
	public ExchangeStat getExchangeStat(User user, String url) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * @see com.painiu.core.dao.ExchangeStatDAO#getExchangeStats(com.painiu.core.model.User)
	 */
	public Result getExchangeStats(User user, int start, int limit) {
		String countSql = "select count(stat.id) from ExchangeStat stat where stat.user=?";
        String sql = "from ExchangeStat stat where stat.user=?";
        return find(countSql, sql,
        		new Object[]{ user },
        		new Type[] { Hibernate.entity(User.class)},
    			start, limit);
	}

	/*
	 * @see com.painiu.core.dao.ExchangeStatDAO#saveExchangeStat(com.painiu.core.model.ExchangeStat)
	 */
	public void saveExchangeStat(ExchangeStat stat) {
		getHibernateTemplate().saveOrUpdate(stat);
	}

	/*
	 * @see com.painiu.core.dao.ExchangeStatDAO#getExchangeStats(java.util.Date, java.util.Date, int, int)
	 */
	public Result getExchangeStats(int start, int limit) {
		String countSql = "select count(stat.id) from ExchangeStat stat ";
        String sql = "from ExchangeStat stat";
        return find(countSql, sql, null, null, start, limit);
	}

	/*
	 * @see com.painiu.core.dao.ExchangeStatDAO#getTotalClickCount()
	 */
	public Integer getTotalClickCount() {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query queryObject = session.createQuery(
						"select sum(stat.clickCount) from ExchangeStat stat" ); 
				
				Object result = queryObject.uniqueResult();
				
				if (result == null) {
					result = new Integer(0);
				}
				return result;
			}
		});
	}

	/*
	 * @see com.painiu.core.dao.ExchangeStatDAO#getTotalViewCount()
	 */
	public Integer getTotalViewCount() {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query queryObject = session.createQuery(
						"select sum(stat.viewCount) from ExchangeStat stat" ); 
				
				Object result = queryObject.uniqueResult();
				
				if (result == null) {
					result = new Integer(0);
				}
				return result;
			}
		});
	}

	/*
	 * @see com.painiu.core.dao.ExchangeStatDAO#getTotalLinkCount()
	 */
	public Integer getTotalLinkCount() {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query queryObject = session.createQuery(
						"select count(stat.id) from ExchangeStat stat" ); 
				
				Object result = queryObject.uniqueResult();
				
				if (result == null) {
					result = new Integer(0);
				}
				return result;
			}
		});
	}

	
	//~ Accessors ==============================================================
}
