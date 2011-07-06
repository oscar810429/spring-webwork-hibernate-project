/**
 * @(#)TagDAO.java 2010-6-11
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao;

import java.util.Date;
import java.util.List;

import com.painiu.core.model.Photo;
import com.painiu.core.model.Software;
import com.painiu.core.model.SoftwareTag;
import com.painiu.core.model.Tag;
import com.painiu.core.model.User;

/**
 * <p>
 * <a href="TagDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: TagDAO.java 135 2010-11-23 09:28:01Z zhangsf $
 */

public interface TagDAO extends DAO {
	
	/**
	 * Get a tag based on tag name.
	 * 
	 * @param name
	 * @return
	 */
	public Tag getTag(String name);
	
	/**
	 * Save a new tag or an updated tag.
	 * 
	 * @param tag
	 */
	public void saveTag(Tag tag);
	
	/**
	 * Remove a tag
	 * @param name
	 */
	public void removeTag(Tag tag);

	/**
	 * Get most popular tags.
	 * 
	 * @param limit
	 * @return
	 */
	public List getPopularTags(int limit);
	
	/**
	 * Get most popular tags.
	 * 
	 * @param limit
	 * @param times
	 * @return
	 */
	public List getPopularTags(int limit, int times);

	/**
	 * Get most popular tags between afterDate and beforeDate.
	 * 
	 * @param afterDate
	 * @param beforeDate
	 * @param limit
	 * @return
	 */
	public List getPopularTags(Date afterDate, Date beforeDate, int limit);
	
	public List getPopularTags(Date afterDate, Date beforeDate, int limit,int times);
	
	public void saveSoftwareTag(SoftwareTag softwareTag);

	
	/*public PhotoTag getPhotoTag(Id id);
	
	public void savePhotoTag(PhotoTag photoTag);
	
	public void removePhotoTag(PhotoTag photoTag);*/
	
	/*Album Tag start*/
	/*public AlbumTag getAlbumTag(AlbumTag.Id id);
	
	public void saveAlbumTag(AlbumTag albumTag);
	
	public void removeAlbumTag(AlbumTag albumTag);*/
	
	//public List getAlbumTags(User user, String tagName, boolean update);
	
	/*Album Tag end*/
	
	/**
	 * 
	 * @return
	 */
	public List getSoftwareTags(Software Software);
	public void removeSoftwareTag(SoftwareTag softwareTag);
	
	/**
	 * 获得某个用户的某个标签与其照片的关联
	 * 
	 * @param userTag
	 * @return
	 */
	public List getSoftwareTags(User user, String tagName, boolean update);
	
	/**
	 * 获得某个时间段之间的说有标签。for cotags building,
	 * 不要指定太长的时间段，不然返回的相当大的List。
	 * 
	 * @param startDate
	 * @param toDate
	 * @return
	 */
	public List getSoftwareTags(Date startDate, Date toDate);
	
	/**
	 * Get all tags of the specified user.
	 * 
	 * @param user
	 * @return
	 */
	public List getUserTags(User user);

	/**
	 * Get most used tags of the specified user.
	 * 
	 * @param user
	 * @param limit
	 * @return
	 */
	public List getUserPopularTags(User user, int limit);

	public List getUserRecentTags(User user, int limit);
	
	/**
	 * Get users who use the specified tag
	 * 
	 * @param tagName
	 * @param limit
	 * @return
	 */
	public List getUsersUseTag(String tagName, int limit);

}
