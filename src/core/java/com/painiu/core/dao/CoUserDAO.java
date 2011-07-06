/*
 * @(#)CoUserDAO.java Dec 13, 2009
 * 
 * Copyright 2009 Painiu. All rights reserved.
 */
package com.painiu.core.dao;

import com.painiu.core.model.CoUser;
import com.painiu.core.model.Collaborator;
import com.painiu.core.model.User;

/**
 * <p>
 * <a href="CoUserDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: CoUserDAO.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public interface CoUserDAO extends DAO {

	public CoUser getCoUser(CoUser.Id id);
	
	public CoUser getCoUser(User user, Collaborator colla);
	
	public void saveCoUser(CoUser coUser);
	
	public void removeCoUser(CoUser coUser);
}
