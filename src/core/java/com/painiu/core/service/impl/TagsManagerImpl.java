/**
 * @(#)TagManagerImpl.java 2010-6-11
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.painiu.core.aop.transaction.NonTransactional;
import com.painiu.core.dao.TagDAO;
import com.painiu.core.model.Photo;
//import com.painiu.core.model.Software;
import com.painiu.core.model.User;
import com.painiu.core.service.TagsManager;

/**
 * <p>
 * <a href="TagManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: TagsManagerImpl.java 135 2010-11-23 09:28:01Z zhangsf $
 */

public class TagsManagerImpl implements TagsManager {

	//~ Static fields/initializers =============================================

	//~ Instance fields ===================================================
	
	private TagDAO tagDAO;

	/**
	 * @param tagDAO the tagDAO to set
	 */
	@NonTransactional
	public void setTagDAO(TagDAO tagDAO) {
		this.tagDAO = tagDAO;
	}

	//~ Constructors ====================================================

	//~ Methods =======================================================
	

	/*
	 * @see com.yupoo.logic.TagsManager#getPopularTags(int)
	 */
	@Transactional(readOnly = true)
	public List getPopularTags(int limit) {
		return tagDAO.getPopularTags(limit);
	}

	/*
	 * @see com.yupoo.logic.TagsManager#getPopularTags(java.util.Date,
	 *      java.util.Date, int)
	 */
	@Transactional(readOnly = true)
	public List getPopularTags(Date afterDate, Date beforeDate, int limit) {
		return tagDAO.getPopularTags(afterDate, beforeDate, limit);
	}

	/*
	 * @see com.yupoo.logic.TagsManager#getPhotoTags(com.yupoo.model.Photo)
	 */
	/*@Transactional(readOnly = true)
	public List getSoftwareTags(Software software) {
		return tagDAO.getSoftwareTags(software);
	}*/

	/*
	 * @see com.yupoo.logic.TagsManager#getPhotoTags(com.yupoo.model.User,
	 *      java.lang.String)
	 */
	@Transactional(readOnly = true)
	public List getSoftwareTags(User user, String tagName, boolean update) {
		return tagDAO.getSoftwareTags(user, tagName, update);
	}

	/*
	 * @see com.yupoo.logic.TagsManager#getUserTags(com.yupoo.model.User)
	 */
	@Transactional(readOnly = true)
	public List getUserTags(User user) {
		return tagDAO.getUserTags(user);
	}

	/*
	 * @see com.yupoo.logic.TagsManager#getUserPopularTags(com.yupoo.model.User,
	 *      int)
	 */
	@Transactional(readOnly = true)
	public List getUserPopularTags(User user, int limit) {
		return tagDAO.getUserPopularTags(user, limit);
	}

	/*
	 * @see com.yupoo.logic.TagsManager#getUserRecentTags(com.yupoo.model.User,
	 *      int)
	 */
	@Transactional(readOnly = true)
	public List getUserRecentTags(User user, int limit) {
		return tagDAO.getUserRecentTags(user, limit);
	}

	/*
	 * @see com.yupoo.logic.TagsManager#getUsersUseTag(java.lang.String, int)
	 */
	@Transactional(readOnly = true)
	public List getUsersUseTag(String tagName, int limit) {
		return tagDAO.getUsersUseTag(tagName, limit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yupoo.logic.TagsManager#getPopularTags(java.util.Date,
	 *      java.util.Date, int, int)
	 */
	@Transactional(readOnly = true)
	public List getPopularTags(Date afterDate, Date beforeDate, int limit,
			int times) {
		return tagDAO.getPopularTags(afterDate, beforeDate, limit, times);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yupoo.logic.TagsManager#getPopularTags(int, int)
	 */
	@Transactional(readOnly = true)
	public List getPopularTags(int limit, int times) {
		return tagDAO.getPopularTags(limit, times);
	}

	//~ Accessors ======================================================
	
	

}
