/*
 * @(#)ForumDAO.java  2006-2-14
 * 
 * Copyright 2005 Yupoo. All rights reserved.
 */
package com.painiu.core.dao;

import java.util.List;

import com.painiu.core.model.Forum;
import com.painiu.core.model.User;

/**
 * <p>
 * <a href="ForumDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zola Zhou
 * @version $Id: ForumDAO.java 135 2010-11-23 09:28:01Z zhangsf $
 */
public interface ForumDAO extends DAO {
	
	public Forum getForum(String id);
	
	public List getSystemForums();
	
	public void saveForum(Forum forum);
	
	public void removeForum(Forum forum);

}
