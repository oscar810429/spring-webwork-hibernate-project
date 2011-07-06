/*
 * @(#)InviteManager.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service;

import java.util.List;

import com.painiu.core.dao.InviteDAO;
import com.painiu.core.model.Invite;
import com.painiu.core.model.Relation;
import com.painiu.core.model.User;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="InviteManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: InviteManager.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public interface InviteManager {
	public void setInviteDAO(InviteDAO inviteDAO);
	
	public Invite getInvite(String id);
	
	public List getInvites(User user);
	
	public void saveInvite(Invite invite);
	
	public void removeInvite(Invite invite);
	
	public Result getAccetedInvites(User from, int start, int limit);
	
	public Result getUnaccetedInvites(User from, int start, int limit);
	
	public Invite getInviteAddress(User from, Relation relation);
}
