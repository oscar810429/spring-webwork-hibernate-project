/*
 * @(#)AuthenticationManagerImpl.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.painiu.core.aop.transaction.NonTransactional;
import com.painiu.core.dao.AuthenticationDAO;
import com.painiu.core.dao.FrobnitzDAO;
import com.painiu.core.service.AuthenticationManager;
import com.painiu.core.model.Application;
import com.painiu.core.model.Authentication;
import com.painiu.core.model.Frobnitz;
import com.painiu.core.model.User;

/**
 * <p>
 * <a href="AuthenticationManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: AuthenticationManagerImpl.java 8 2010-05-11 16:48:01Z zhangsf $
 */
@Transactional
public class AuthenticationManagerImpl implements AuthenticationManager {
	//~ Static fields/initializers =============================================

	private static final Log log = LogFactory.getLog(AuthenticationManagerImpl.class);
	
	//~ Instance fields ========================================================

	private FrobnitzDAO frobnitzDAO;
	private AuthenticationDAO authenticationDAO;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	/*
	 * @see com.painiu.core.logic.AuthenticationManager#setFrobnitzDAO(com.painiu.core.dao.FrobnitzDAO)
	 */
	@NonTransactional
	public void setFrobnitzDAO(FrobnitzDAO frobnitzDAO) {
		this.frobnitzDAO = frobnitzDAO;
	}
	/*
	 * @see com.painiu.core.logic.AuthenticationManager#setAuthenticationDAO(com.painiu.core.dao.AuthenticationDAO)
	 */
	@NonTransactional
	public void setAuthenticationDAO(AuthenticationDAO authenticationDAO) {
		this.authenticationDAO = authenticationDAO;
	}
	
	/*
	 * @see com.painiu.core.logic.AuthenticationManager#getAuthentication(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public Authentication getAuthentication(String token) {
		return authenticationDAO.getAuthentication(token);
	}

	/*
	 * @see com.painiu.core.logic.AuthenticationManager#getAuthentication(com.painiu.core.model.User, com.painiu.core.model.Application)
	 */
	@Transactional(readOnly=true)
	public Authentication getAuthentication(User user, Application app) {
		return authenticationDAO.getAuthentication(user, app);
	}
	
	/*
	 * @see com.painiu.core.logic.AuthenticationManager#getAuthentications(com.painiu.core.model.User)
	 */
	@Transactional(readOnly=true)
	public List getAuthentications(User user) {
		return authenticationDAO.getAuthentications(user);
	}

	/*
	 * @see com.painiu.core.logic.AuthenticationManager#saveAuthentication(com.painiu.core.model.Authentication)
	 */
	public void saveAuthentication(Authentication auth) {
		if (log.isDebugEnabled()) {
			log.debug("saving " + auth);
		}
		
		authenticationDAO.saveAuthentication(auth);
	}

	/*
	 * @see com.painiu.core.logic.AuthenticationManager#removeAuthentication(com.painiu.core.model.Authentication)
	 */
	public void removeAuthentication(Authentication auth) {
		if (log.isDebugEnabled()) {
			log.debug("removing " + auth);
		}
		
		authenticationDAO.removeAuthentication(auth);
	}

	/*
	 * @see com.painiu.core.logic.AuthenticationManager#getFrobnitz(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public Frobnitz getFrobnitz(String id) {
		return frobnitzDAO.getFrobnitz(id);
	}
	
	/*
	 * @see com.painiu.core.logic.AuthenticationManager#removeFrobnitz(com.painiu.core.model.Frobnitz)
	 */
	public void removeFrobnitz(Frobnitz frobnitz) {
		if (log.isDebugEnabled()) {
			log.debug("removing " + frobnitz);
		}
		
		frobnitzDAO.removeFrobnitz(frobnitz);
	}
	
	/*
	 * @see com.painiu.core.logic.AuthenticationManager#saveFrobnitz(com.painiu.core.model.Frobnitz)
	 */
	public void saveFrobnitz(Frobnitz frobnitz) {
		if (log.isDebugEnabled()) {
			log.debug("saving " + frobnitz);
		}
		
		frobnitzDAO.saveFrobnitz(frobnitz);
	}
	
}
