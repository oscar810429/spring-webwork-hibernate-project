/*
 * @(#)AuthenticationDAOHibernate.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao.hibernate;

import java.util.List;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.painiu.core.dao.AuthenticationDAO;
import com.painiu.core.model.Application;
import com.painiu.core.model.Authentication;
import com.painiu.core.model.User;

/**
 * <p>
 * <a href="AuthenticationDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: AuthenticationDAOHibernate.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class AuthenticationDAOHibernate extends BaseDAOHibernate implements AuthenticationDAO {

	/*
	 * @see com.painiu.core.dao.AuthenticationDAO#getAuthentication(java.lang.String)
	 */
	public Authentication getAuthentication(String token) {
		Authentication auth = (Authentication) getHibernateTemplate().get(Authentication.class, token);
		
		if (auth == null) {
			log.warn("uh, oh, Authentication[" + token + "] not found...");
        	throw new ObjectRetrievalFailureException(Authentication.class, token);
		}
		
		return auth;
	}

	/*
	 * @see com.painiu.core.dao.AuthenticationDAO#getAuthentication(com.painiu.core.model.User, com.painiu.core.model.Application)
	 */
	public Authentication getAuthentication(User user, Application application) {
		List list = getHibernateTemplate().find(
				"from Authentication auth where auth.user = ? and auth.application = ?", 
				new Object[] {user, application});
		
		if (list.size() > 0) {
			return (Authentication) list.get(0);
		}
		
		return null;
	}
	
	/*
	 * @see com.painiu.core.dao.AuthenticationDAO#getAuthenticationByFrob(java.lang.String)
	 */
	public Authentication getAuthenticationByFrob(String frob) {
		List list = getHibernateTemplate().find(
				"from Authentication auth where auth.frob = ?", frob);
		
		if (list.size() > 0) {
			return (Authentication) list.get(0);
		}
		
		throw new ObjectRetrievalFailureException(Authentication.class, frob);
	}

	/*
	 * @see com.painiu.core.dao.AuthenticationDAO#getAuthentications(com.painiu.core.model.User)
	 */
	public List getAuthentications(User user) {
		return getHibernateTemplate().find(
				"from Authentication auth join fetch auth.application where auth.user = ? order by auth.authDate desc ", user);
	}

	/*
	 * @see com.painiu.core.dao.AuthenticationDAO#saveAuthentication(com.painiu.core.model.Authentication)
	 */
	public void saveAuthentication(Authentication authentication) {
		if (log.isDebugEnabled()) {
			log.debug("Saving " + authentication);
		}
		
		getHibernateTemplate().saveOrUpdate(authentication);
	}

	/*
	 * @see com.painiu.core.dao.AuthenticationDAO#removeAuthentication(com.painiu.core.model.Authentication)
	 */
	public void removeAuthentication(Authentication authentication) {
		if (log.isDebugEnabled()) {
			log.debug("Removing " + authentication);
		}
		
		getHibernateTemplate().delete(authentication);
	}

}
