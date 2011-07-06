/*
 * @(#)AbuseManagerImpl.java Apr 7, 2008
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service.impl;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import com.painiu.core.aop.transaction.NonTransactional;
import com.painiu.core.dao.AbuseDAO;
import com.painiu.core.service.AbuseManager;
import com.painiu.core.model.Abuse;
import com.painiu.core.model.Abuse.State;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="AbuseManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Gofeeling
 * @version $Id: AbuseManagerImpl.java 68 2010-06-17 15:54:41Z zhangsf $
 */
@Transactional
public class AbuseManagerImpl implements AbuseManager {
	
	private AbuseDAO abuseDAO;

	/**
	 * @param abuseDAO the abuseDAO to set
	 */
	@NonTransactional
	public void setAbuseDAO(AbuseDAO abuseDAO) {
		this.abuseDAO = abuseDAO;
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.service.AbuseManager#findAbuseList(int, int)
	 */
	@Transactional(readOnly=true)
	public Result findAbuseList(int kind, State state, int start, int limit) {
		return abuseDAO.findAbuseList(kind, state, start, limit);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.serivce.AbuseManager#getAbuse(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public Abuse getAbuse(String id) {
		return abuseDAO.getAbuse(id);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.service.AbuseManager#removeAbuse(com.painiu.core.model.Abuse)
	 */
	public void removeAbuse(Abuse abuse) {
		abuseDAO.removeAbuse(abuse);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.service.AbuseManager#save(com.painiu.core.model.Abuse)
	 */
	public void save(Abuse abuse) {
		abuseDAO.save(abuse);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.service.AbuseManager#findAbuseResult(int, com.painiu.core.model.Abuse.State, java.util.Date, java.util.Date, int, int)
	 */
	@Transactional(readOnly=true)
	public Result findAbuseResult(int kind, State state, Date startDate, Date endDate, int start, int limit) {
		return abuseDAO.findAbuseResult(kind, state, startDate, endDate, start, limit);
	}

}
