/*
 * @(#)ApplicationDAOHibernate.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao.hibernate;

import java.util.List;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.painiu.core.dao.ApplicationDAO;
import com.painiu.core.model.Application;
import com.painiu.core.model.User;

/**
 * <p>
 * <a href="ApplicationDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ApplicationDAOHibernate.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class ApplicationDAOHibernate extends BaseDAOHibernate implements ApplicationDAO {

	/*
	 * @see com.painiu.core.dao.ApplicationDAO#getApplication(java.lang.Long)
	 */
	public Application getApplication(Long sequence) {
		List list = getHibernateTemplate().find(
				"from Application app where app.sequence = ?", sequence);
		
		if (list.size() == 0) {
			log.warn("uh, oh, Application[" + sequence + "] not found...");
        	throw new ObjectRetrievalFailureException(Application.class, sequence);
		}
		
		return (Application) list.get(0);
	}

	/*
	 * @see com.painiu.core.dao.ApplicationDAO#getApplication(java.lang.String)
	 */
	public Application getApplication(String apiKey) {
		Application app = (Application) getHibernateTemplate().get(Application.class, apiKey);
		
		if (app == null) {
			log.warn("uh, oh, Application[" + apiKey + "] not found...");
        	throw new ObjectRetrievalFailureException(Application.class, apiKey);
		}
		
		return app;
	}
	
//	public Authentication getAuthentication(String token) {
//		Authentication app = (Authentication) getHibernateTemplate().get(Authentication.class, token);
//		
//		if (app == null) {
//			log.warn("uh, oh, Authentication[" + token + "] not found...");
//        	throw new ObjectRetrievalFailureException(Authentication.class, token);
//		}
//		
//		return app;
//	}

	/*
	 * @see com.painiu.core.dao.ApplicationDAO#getApplications(com.painiu.core.model.User)
	 */
	public List getApplications(User user) {
		return getHibernateTemplate().find(
				"from Application app where app.user = ? order by app.createdDate desc", user);
	}
	
//	public List getAuthApplications(User user) {
//		return getHibernateTemplate().find("from Authentication auth join fetch auth.application where auth.user = ? order by auth.authDate desc", user);
//	}

	/*
	 * @see com.painiu.core.dao.ApplicationDAO#saveApplication(com.painiu.core.model.Application)
	 */
	public void saveApplication(Application application) {
		if (log.isDebugEnabled()) {
			log.debug("Saving " + application);
		}
		
		getHibernateTemplate().saveOrUpdate(application);
		getHibernateTemplate().flush();
		
		if (application.getSequence() == null) {
			getHibernateTemplate().refresh(application);
		}
	}

	/*
	 * @see com.painiu.core.dao.ApplicationDAO#removeApplication(com.painiu.core.model.Application)
	 */
	public void removeApplication(Application application) {
		if (log.isDebugEnabled()) {
			log.debug("Removing " + application);
		}
		
		getHibernateTemplate().delete(application);
	}
	
//	public void removeAuthentication(Authentication authentication) {
//		if (log.isDebugEnabled()) {
//			log.debug("Removing " + authentication);
//		}
//		
//		getHibernateTemplate().delete(authentication);
//	}

}
