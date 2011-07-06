/*
 * @(#)ExtendedHibernateDaoSupport.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateAccessor;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * <p>
 * <a href="ExtendedHibernateDaoSupport.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ExtendedHibernateDaoSupport.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class ExtendedHibernateDaoSupport extends HibernateDaoSupport {
	
	private HibernateTemplate readOnlyHibernateTemplate = null;
	
	public void setReadOnlySessionFactory(SessionFactory sessionFactory) {
		this.readOnlyHibernateTemplate = new HibernateTemplate(sessionFactory);
		this.readOnlyHibernateTemplate.setFlushMode(HibernateAccessor.FLUSH_NEVER);
	}
	
	public HibernateTemplate getReadOnlyHibernateTemplate() {
		return (readOnlyHibernateTemplate == null) ? getHibernateTemplate() : readOnlyHibernateTemplate;
	}
}
