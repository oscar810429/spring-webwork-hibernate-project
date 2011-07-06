/*
 * @(#)SystemNewsDAOHibernate.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao.hibernate;

import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.type.Type;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.painiu.Painiu;
import com.painiu.core.dao.SystemNewsDAO;
import com.painiu.core.model.SystemNews;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="SystemNewsDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SystemNewsDAOHibernate.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class SystemNewsDAOHibernate extends BaseDAOHibernate implements SystemNewsDAO {

	//~ Static fields/initializers =============================================

	private static final Log log = LogFactory.getLog(SystemNewsDAOHibernate.class);
	
	//~ Instance fields ========================================================

	//~ Constructors ===========================================================

	//~ Methods ================================================================
	
	/*
	 * @see com.painiu.core.dao.SystemNewsDAO#getSystemNews(java.lang.String)
	 */
	public SystemNews getSystemNews(String id) {
		SystemNews news = (SystemNews) getHibernateTemplate().get(SystemNews.class, id);
		if (news == null) {
			log.warn("uh oh, SystemNews[" + id + "] not found...");
            throw new ObjectRetrievalFailureException(SystemNews.class, id);
		}
		
		return news;
	}

	/*
	 * @see com.painiu.core.dao.SystemNewsDAO#getSystemNews(int, int)
	 */
	public Result getSystemNews(int kind, int start, int limit) {
		
		String countSql = "select count(news.id) from SystemNews news where news.kind = ? and (news.expiredDate is null or news.expiredDate > ?)"; 

        String sql = "from SystemNews news where news.kind = ? and (news.expiredDate is null or news.expiredDate > ?) order by news.createdDate desc";

        return find(countSql, sql,
        		new Object[] { new Integer(kind), new Date() },
				new Type[] { Hibernate.INTEGER, Hibernate.DATE },
				start, limit);
        
	}
	
	public Result getSystemNews(int kind, int start, int limit, int image) {
		String countSql = "select count(news.id) from SystemNews news where news.kind = ? and news.image = ? and (news.expiredDate is null or news.expiredDate > ?)"; 

        String sql = "from SystemNews news where news.kind = ? and news.image = ? and (news.expiredDate is null or news.expiredDate > ?)  order by news.createdDate desc";
        return find(countSql, sql,
        		new Object[] { new Integer(kind), new Integer(image), new Date()},
				new Type[] { Hibernate.INTEGER , Hibernate.INTEGER, Hibernate.DATE},
				start, limit);
	}
	
	/*
	 * @see com.painiu.core.dao.SystemNewsDAO#getSystemNews(int, java.lang.String, int, int)
	 */
	public Result getSystemNews(int kind, String pageKind, int start, int limit) {
		String countSql = "select count(news.id) from SystemNews news where news.kind = ? and news.url = ? and (news.expiredDate is null or news.expiredDate > ?)"; 

        String sql = "from SystemNews news where news.kind = ? and  news.url = ? and (news.expiredDate is null or news.expiredDate > ?) order by news.createdDate desc";

        return find(countSql, sql,
        		new Object[] { new Integer(kind), new String(pageKind), new Date() },
    			new Type[] { Hibernate.INTEGER, Hibernate.STRING, Hibernate.DATE },
    			start, limit);
	}
	/*
	 * @see com.painiu.core.dao.SystemNewsDAO#getSystemNews(int, int)
	 */
	public Result getSystemNews(int start, int limit) {
		String countSql = "select count(news.id) from SystemNews news "; 

        String sql = "from SystemNews news  order by news.createdDate desc";

        return find(countSql, sql,
        		null,null,
    			start, limit);

	}


	/*
	 * @see com.painiu.core.dao.SystemNewsDAO#saveSystemNews(com.painiu.core.model.SystemNews)
	 */
	public void saveSystemNews(SystemNews systemNews) {
		if (log.isDebugEnabled()) {
			log.debug("saving " + systemNews);
		}
		getHibernateTemplate().saveOrUpdate(systemNews);
	}

	/*
	 * @see com.painiu.core.dao.SystemNewsDAO#removeSystemNews(com.painiu.core.model.SystemNews)
	 */
	public void removeSystemNews(SystemNews systemNews) {
		if (log.isDebugEnabled()) {
			log.debug("removing " + systemNews);
		}
		getHibernateTemplate().delete(systemNews);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.SystemNewsDAO#getSystemImageNews(int, int)
	 */
	public Result getSystemImageNews(int start, int limit) {
		String countSql = "select count(news.id) from SystemNews news where news.kind = " + Painiu.IMAGE_INDEX +" or news.kind = " + Painiu.IMAGE_ACTIVITY +" or news.kind = " + Painiu.JS_IMAGE_INDEX +" or news.kind = " + Painiu.JS_IMAGE_ACTIVITY +" or news.kind = " + Painiu.FJ_IMAGE_INDEX +" or news.kind = " + Painiu.FJ_IMAGE_ACTIVITY ; 
        String sql = "from SystemNews news  where news.kind = " + Painiu.IMAGE_INDEX +" or news.kind = " + Painiu.IMAGE_ACTIVITY +" or news.kind = " + Painiu.JS_IMAGE_INDEX +" or news.kind = " + Painiu.JS_IMAGE_ACTIVITY +" or news.kind = " + Painiu.FJ_IMAGE_INDEX +" or news.kind = " + Painiu.FJ_IMAGE_ACTIVITY  + " order by news.createdDate desc";
        return find(countSql, sql,
        		null,null,
    			start, limit);
	
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.SystemNewsDAO#getSystemTextNews(int, int)
	 */
	public Result getSystemTextNews(int start, int limit) {
		String countSql = "select count(news.id) from SystemNews news where news.kind between "+Painiu.NEWS_SYSTEM +" and "+Painiu.NEWS_TAG; 
        String sql = "from SystemNews news  where news.kind between "+Painiu.NEWS_SYSTEM +" and "+Painiu.NEWS_TAG +" order by news.createdDate desc";
        return find(countSql, sql,
        		null,null,
    			start, limit);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.SystemNewsDAO#getSystemImage(int, int, int)
	 */
	public Result getSystemImage(int kind, int start, int limit) {
		String countSql = "select count(news.id) from SystemNews news where news.kind = ? and  news.weight > 0"; 
        String sql = "from SystemNews news where news.kind = ? and news.weight > 0 order by news.weight desc";
        return find(countSql, sql,
        		new Object[] { new Integer(kind)},
				new Type[] { Hibernate.INTEGER},
				start, limit);
	}

}
