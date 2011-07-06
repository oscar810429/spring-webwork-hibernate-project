/*
 * @(#)CoUserDAOHibernate.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.painiu.core.dao.CoUserDAO;
import com.painiu.core.model.CoUser;
import com.painiu.core.model.Collaborator;
import com.painiu.core.model.User;
import com.painiu.core.model.CoUser.Id;

/**
 * <p>
 * <a href="CoUserDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: CoUserDAOHibernate.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class CoUserDAOHibernate extends BaseDAOHibernate implements CoUserDAO {
	//~ Static fields/initializers =============================================

	private static final Log log = LogFactory.getLog(CoUserDAOHibernate.class);
	
	//~ Instance fields ========================================================

	//~ Constructors ===========================================================

	//~ Methods ================================================================

	//~ Accessors ==============================================================
	
	/*
	 * @see com.painiu.core.dao.CoUserDAO#getCoUser(com.painiu.core.model.CoUser.Id)
	 */
	public CoUser getCoUser(Id id) {
		CoUser coUser = (CoUser) getHibernateTemplate().get(CoUser.class, id);
		if (coUser == null) {
			log.warn("uh, oh, CoUser[" + id + "] not found...");
        	throw new ObjectRetrievalFailureException(CoUser.class, id);
		}
		return coUser;
	}
	
	/*
	 * @see com.painiu.core.dao.CoUserDAO#getCoUser(com.painiu.core.model.User, com.painiu.core.model.Collaborator)
	 */
	public CoUser getCoUser(User user, Collaborator colla) {
		List list = getHibernateTemplate().find("from CoUser u where u.user = ? and u.colla = ?", new Object[] { user, colla });
		if (list == null || list.size() == 0) {
			return null;
		}
		return (CoUser) list.get(0);
	}

	/*
	 * @see com.painiu.core.dao.CoUserDAO#removeCoUser(com.painiu.core.model.CoUser)
	 */
	public void removeCoUser(CoUser coUser) {
		if (log.isDebugEnabled()) {
			log.debug("removing " + coUser);
		}
		
		getHibernateTemplate().delete(coUser);
	}

	/*
	 * @see com.painiu.core.dao.CoUserDAO#saveCoUser(com.painiu.core.model.CoUser)
	 */
	public void saveCoUser(CoUser coUser) {
		if (log.isDebugEnabled()) {
			log.debug("saving " + coUser);
		}
		
		getHibernateTemplate().saveOrUpdate(coUser);
	}

}
