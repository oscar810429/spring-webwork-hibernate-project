/*
 * @(#)SystemMessageDAOHibernate.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.painiu.core.dao.SystemMessageDAO;
import com.painiu.core.model.SystemMessage;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="SystemMessageDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SystemMessageDAOHibernate.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class SystemMessageDAOHibernate extends BaseDAOHibernate implements SystemMessageDAO {

	/*
	 * @see com.painiu.core.dao.SystemMessageDAO#getSystemMessage(java.lang.String)
	 */
	public SystemMessage getSystemMessage(String id) {
		SystemMessage message = (SystemMessage) getHibernateTemplate().get(SystemMessage.class, id);
		
		if (message == null) {
			throw new ObjectRetrievalFailureException(SystemMessage.class, id);
		}
		
		return message;
	}

	/*
	 * @see com.painiu.core.dao.SystemMessageDAO#getSystemMessages(java.util.Date)
	 */
	public List getSystemMessages(Date afterDate) {
		return getReadOnlyHibernateTemplate().find(
				"from SystemMessage msg where msg.createdDate > ? " +
				"and msg.expireDate > ? order by msg.createdDate", 
				new Object[] {afterDate, new Date()});
	}

	/*
	 * @see com.painiu.core.dao.SystemMessageDAO#getSystemMessages(int, int)
	 */
	public Result getSystemMessages(int start, int limit) {
		return find("from SystemMessage msg order by msg.createdDate desc", start, limit);
	}

	/*
	 * @see com.painiu.core.dao.SystemMessageDAO#removeSystemMessage(com.painiu.core.model.SystemMessage)
	 */
	public void removeSystemMessage(SystemMessage systemMessage) {
		getHibernateTemplate().delete(systemMessage);
	}

	/*
	 * @see com.painiu.core.dao.SystemMessageDAO#saveSystemMessage(com.painiu.core.model.SystemMessage)
	 */
	public void saveSystemMessage(SystemMessage systemMessage) {
		getHibernateTemplate().saveOrUpdate(systemMessage);
	}

}
