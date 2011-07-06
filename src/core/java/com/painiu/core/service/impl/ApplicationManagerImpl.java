/*
 * @(#)ApplicationManagerImpl.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.painiu.core.aop.transaction.NonTransactional;
import com.painiu.core.dao.ApplicationDAO;
import com.painiu.core.service.ApplicationManager;
import com.painiu.core.model.Application;
import com.painiu.core.model.User;

/**
 * <p>
 * <a href="ApplicationManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ApplicationManagerImpl.java 8 2010-05-11 16:48:01Z zhangsf $
 */
@Transactional
public class ApplicationManagerImpl implements ApplicationManager {
	//~ Static fields/initializers =============================================

	private static final Log log = LogFactory.getLog(ApplicationManagerImpl.class);
	
	//~ Instance fields ========================================================

	private ApplicationDAO applicationDAO;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	/*
	 * @see com.painiu.core.logic.ApplicationManager#setApplicationDAO(com.painiu.core.dao.ApplicationDAO)
	 */
	@NonTransactional
	public void setApplicationDAO(ApplicationDAO applicationDAO) {
		this.applicationDAO = applicationDAO;
	}
	
	/*
	 * @see com.painiu.core.logic.ApplicationManager#getApplication(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public Application getApplication(String apiKey) {
		return applicationDAO.getApplication(apiKey);
	}
	
	/*
	 * @see com.painiu.core.logic.ApplicationManager#getApplication(java.lang.Long)
	 */
	@Transactional(readOnly=true)
	public Application getApplication(Long seq) {
		return applicationDAO.getApplication(seq);
	}

	/*
	 * @see com.painiu.core.logic.ApplicationManager#getApplications(com.painiu.core.model.User)
	 */
	@Transactional(readOnly=true)
	public List getApplications(User user) {
		return applicationDAO.getApplications(user);
	}
	
	/*
	 * @see com.painiu.core.logic.ApplicationManager#saveApplication(com.painiu.core.model.Application)
	 */
	public void saveApplication(Application application) {
		if (log.isDebugEnabled()) {
			log.debug("saving " + application);
		}
		
		applicationDAO.saveApplication(application);
	}

	/*
	 * @see com.painiu.core.logic.ApplicationManager#removeApplication(com.painiu.core.model.Application)
	 */
	public void removeApplication(Application application) {
		if (log.isDebugEnabled()) {
			log.debug("removing " + application);
		}
		
		applicationDAO.removeApplication(application);
	}

}
