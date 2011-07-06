/**
 * @(#)TagsManager.java 2010-6-11
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service;

import java.util.Date;
import java.util.List;

import com.painiu.core.dao.TagDAO;
import com.painiu.core.model.Photo;
//import com.painiu.core.model.Software;
import com.painiu.core.model.User;

/**
 * <p>
 * <a href="TagsManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: TagsManager.java 135 2010-11-23 09:28:01Z zhangsf $
 */

public interface TagsManager {
	
    public void setTagDAO(TagDAO tagDAO);
	
	/**
	 * Get all time most popular tags.
	 * 
	 * @param limit
	 * @return
	 */
	public List getPopularTags(int limit);
	
	/**
	 * Get all time most popular tags.
	 * 
	 * @param limit
	 * @param times
	 * @return
	 */
	public List getPopularTags(int limit, int times);
	
	/**
	 * Get most popular tags between afterDate and beforeDate.
	 * 
	 * @param beforeDate
	 * @param afterDate
	 * @param limit
	 * @return
	 */
	public List getPopularTags(Date afterDate, Date beforeDate, int limit);
	
	/**
	 * Get most popular tags between afterDate and beforeDate.
	 * 
	 * @param beforeDate
	 * @param afterDate
	 * @param limit
	 * @param times
	 * @return
	 */
	public List getPopularTags(Date afterDate, Date beforeDate, int limit,int times);
	/**
	 * Get all tags of the specified Software
	 * 
	 * @param software
	 * @return
	 */
	/*public List getSoftwareTags(Software software);*/
	
	public List getSoftwareTags(User user, String tagName, boolean update);
	
	/**
	 * Get all tags of the specified User
	 * 
	 * @param user
	 * @return
	 */
	public List getUserTags(User user);
	
	/**
	 * Get most popular tags of the specified user. 
	 * 
	 * @param user
	 * @param limit
	 * @return
	 */
	public List getUserPopularTags(User user, int limit);
	
	public List getUserRecentTags(User user, int limit);
	
	/**
	 * Get users who use the specified tag. 
	 *  
	 * @param tagName
	 * @param limit
	 * @return
	 */
	public List getUsersUseTag(String tagName, int limit);

}
