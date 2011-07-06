/*
 * @(#)CriteriaBuilder.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 * <p>
 * <a href="CriteriaBuilder.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: CriteriaBuilder.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public interface CriteriaBuilder {
	
	public Criteria buildCountCriteria(Session session);
	
	public Criteria buildCriteria(Session session);
}
