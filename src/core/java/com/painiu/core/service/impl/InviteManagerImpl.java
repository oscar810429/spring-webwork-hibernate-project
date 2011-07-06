/*
 * @(#)InviteManagerImpl.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.painiu.core.aop.transaction.NonTransactional;
import com.painiu.core.dao.InviteDAO;
import com.painiu.core.model.Invite;
import com.painiu.core.model.Relation;
import com.painiu.core.model.User;
import com.painiu.core.search.Result;
import com.painiu.core.service.InviteManager;

/**
 * <p>
 * <a href="InviteManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: InviteManagerImpl.java 8 2010-05-11 16:48:01Z zhangsf $
 */
@Transactional
public class InviteManagerImpl extends BaseManager implements InviteManager, InitializingBean {
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================
	
	private InviteDAO inviteDAO;

	//~ Constructors ===========================================================

	//~ Methods ================================================================

	/*
	 * @see com.painiu.core.logic.InviteManager#setInviteDAO(com.painiu.core.dao.InviteDAO)
	 */
	@NonTransactional
	public void setInviteDAO(InviteDAO inviteDAO) {
		this.inviteDAO = inviteDAO;
	}
	
	/*
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@NonTransactional
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(inviteDAO);
    } 

	/*
	 * @see com.painiu.core.logic.InviteManager#getInvite(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public Invite getInvite(String id) {
		return inviteDAO.getInvite(id);
	}

	/*
	 * @see com.painiu.core.logic.InviteManager#getInvites(com.painiu.core.model.User)
	 */
	@Transactional(readOnly=true)
	public List getInvites(User user) {
		return inviteDAO.getInvites(user);
	}

	/*
	 * @see com.painiu.core.logic.InviteManager#saveInvite(com.painiu.core.model.Invite)
	 */
	public void saveInvite(Invite invite) {
		inviteDAO.saveInvite(invite);
	}

	/*
	 * @see com.painiu.core.logic.InviteManager#removeInvite(com.painiu.core.model.Invite)
	 */
	public void removeInvite(Invite invite) {
		inviteDAO.removeInvite(invite);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.InviteManager#getAccetedInvites(com.painiu.core.model.User, int, int)
	 */
	@Transactional(readOnly=true)
	public Result getAccetedInvites(User from, int start, int limit) {
		return inviteDAO.getInvites(from, true, start, limit);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.InviteManager#getUnaccetedInvites(com.painiu.core.model.User, int, int)
	 */
	@Transactional(readOnly=true)
	public Result getUnaccetedInvites(User from, int start, int limit) {
		return inviteDAO.getInvites(from, false, start, limit);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.InviteManager#getInviteAddress(com.painiu.core.model.User, com.painiu.core.model.Relation)
	 */
	public Invite getInviteAddress(User from, Relation relation) {
		return inviteDAO.getInviteAddress(from, relation);
	}

}
