/*
 * @(#)CotagDAOHibernate.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao.hibernate;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.painiu.core.dao.CotagDAO;
import com.painiu.core.model.Cotag;
import com.painiu.core.model.Cotag.Id;

/**
 * <p>
 * <a href="CotagDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: CotagDAOHibernate.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class CotagDAOHibernate extends BaseDAOHibernate implements CotagDAO {

	/*
	 * @see com.painiu.core.dao.CotagDAO#getCoTag(com.painiu.core.model.Cotag.Id)
	 */
	public Cotag getCotag(Id id) {
		return (Cotag) getHibernateTemplate().get(Cotag.class, id);
	}

	/*
	 * @see com.painiu.core.dao.CotagDAO#saveCoTag(com.painiu.core.model.Cotag)
	 */
	public void saveCotag(Cotag cotag) {
		getHibernateTemplate().saveOrUpdate(cotag);
	}
	
	/*
	 * @see com.painiu.core.dao.CotagDAO#saveCotags(java.util.Collection)
	 */
	public void saveCotags(Collection cotags) {
		for (Iterator i = cotags.iterator(); i.hasNext();) {
			Cotag cotag = (Cotag) i.next();
			saveCotag(cotag);
		}
		getHibernateTemplate().flush();
		getHibernateTemplate().clear();
	}

	/*
	 * @see com.painiu.core.dao.CotagDAO#removeCoTag(com.painiu.core.model.Cotag)
	 */
	public void removeCotag(Cotag cotag) {
		getHibernateTemplate().delete(cotag);
	}

	/*
	 * @see com.painiu.core.dao.CotagDAO#getCoTags(java.lang.String, int)
	 */
	public List getCotags(String tag, int limit) {
		return find("from Cotag ct where ct.tagName = ? order by ct.count desc", 
				tag, 0, limit).getData();
	}

}
