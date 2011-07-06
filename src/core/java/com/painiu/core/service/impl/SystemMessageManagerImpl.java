/*
 * @(#)SystemMessageManagerImpl.java  2009-11-27
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.painiu.core.aop.transaction.NonTransactional;
import com.painiu.core.dao.SystemMessageDAO;
import com.painiu.core.service.SystemMessageManager;
import com.painiu.core.model.SystemMessage;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="SystemMessageManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SystemMessageManagerImpl.java 8 2010-05-11 16:48:01Z zhangsf $
 */
@Transactional
public class SystemMessageManagerImpl implements SystemMessageManager, InitializingBean {
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	private SystemMessageDAO systemMessageDAO;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	/*
	 * @see com.painiu.core.logic.SystemMessageManager#setSystemMessageDAO(com.painiu.core.dao.SystemMessageDAO)
	 */
	@NonTransactional
	public void setSystemMessageDAO(SystemMessageDAO systemMessageDAO) {
		this.systemMessageDAO = systemMessageDAO;
	}
	
	/*
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@NonTransactional
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(systemMessageDAO);

	} 
	
	/*
	 * @see com.painiu.core.logic.SystemMessageManager#getSystemMessage(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public SystemMessage getSystemMessage(String id) {
		return systemMessageDAO.getSystemMessage(id);
	}

	/*
	 * @see com.painiu.core.logic.SystemMessageManager#getSystemMessages(java.util.Date)
	 */
	@Transactional(readOnly=true)
	public List getSystemMessages(Date afterDate) {
		return systemMessageDAO.getSystemMessages(afterDate);
	}

	/*
	 * @see com.painiu.core.logic.SystemMessageManager#getSystemMessages(int, int)
	 */
	@Transactional(readOnly=true)
	public Result getSystemMessages(int start, int limit) {
		return systemMessageDAO.getSystemMessages(start, limit);
	}

	/*
	 * @see com.painiu.core.logic.SystemMessageManager#removeSystemMessage(com.painiu.core.model.SystemMessage)
	 */
	public void removeSystemMessage(SystemMessage systemMessage) {
		systemMessageDAO.removeSystemMessage(systemMessage);
	}

	/*
	 * @see com.painiu.core.logic.SystemMessageManager#saveSystemMessage(com.painiu.core.model.SystemMessage)
	 */
	public void saveSystemMessage(SystemMessage systemMessage) {
		systemMessageDAO.saveSystemMessage(systemMessage);
	}
}
