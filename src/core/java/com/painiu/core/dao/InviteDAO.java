/*
 * @(#)InviteDAO.java  2009-11-13
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao;

import java.util.List;

import com.painiu.core.model.Invite;
import com.painiu.core.model.Relation;
import com.painiu.core.model.User;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="InviteDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: InviteDAO.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public interface InviteDAO extends DAO {

	public Invite getInvite(String id);
	
	public List getInvites(User user);
	
	public Result getInvites(User from, boolean isAcceted, int start, int limit);
	
	public void saveInvite(Invite invite);
	
	public void removeInvite(Invite invite);
	
	public Invite getInviteAddress(User from, Relation relation);
}
