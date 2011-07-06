/*
 * @(#)SessionFactoryBean.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.persistence;

import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

/**
 * <p>
 * <a href="SessionFactoryBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SessionFactoryBean.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class SessionFactoryBean extends LocalSessionFactoryBean {
	
	protected Configuration newConfiguration() throws HibernateException {
		Configuration cfg = super.newConfiguration();
		
		cfg.setEntityResolver(new ImportFromClasspathEntityResolver());
		
		return cfg;
	}
}
