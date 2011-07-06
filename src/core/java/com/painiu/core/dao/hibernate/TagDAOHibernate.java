/**
 * @(#)TagDAOHibernate.java 2010-6-11
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao.hibernate;


import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.painiu.core.dao.TagDAO;
import com.painiu.core.model.Software;
import com.painiu.core.model.SoftwareTag;
import com.painiu.core.model.Tag;
import com.painiu.core.model.User;
import com.painiu.core.model.User.UserRank;

/**
 * <p>
 * <a href="TagDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: TagDAOHibernate.java 135 2010-11-23 09:28:01Z zhangsf $
 */

public class TagDAOHibernate extends BaseDAOHibernate implements TagDAO {

	//~ Static fields/initializers =============================================

	private static final Log log = LogFactory.getLog(TagDAOHibernate.class);
	
	//~ Instance fields ========================================================

	//~ Constructors ===========================================================

	//~ Methods ================================================================

    
	public Tag getTag(String name) {
		return (Tag) getHibernateTemplate().get(Tag.class, name);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.painiu.core.dao.TagDAO#saveTag(com.painiu.core.model.Tag)
	 */


	public void saveTag(Tag tag) {
		if (log.isDebugEnabled()) {
			log.debug("Saving " + tag);
		}
		getHibernateTemplate().saveOrUpdate(tag);
	}

	/*
	 * (non-Javadoc)
	 * @see com.painiu.core.dao.TagDAO#removeTag(com.painiu.core.model.Tag)
	 */

	public void removeTag(Tag tag) {
		if (log.isDebugEnabled()) {
			log.debug("Removing " + tag);
		}
		
		getHibernateTemplate().delete(tag);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.painiu.core.dao.TagDAO#getPopularTags(int)
	 */


	public List getPopularTags(int limit) {
		return find("from Tag as tag order by tag.times desc", limit);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.painiu.core.dao.TagDAO#getPopularTags(int, int)
	 */
	
	public List getPopularTags(int limit, int times) {
		return find("from Tag as tag where tag.times > " + times + "order by tag.times desc", limit);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.painiu.core.dao.TagDAO#getPopularTags(java.util.Date, java.util.Date, int)
	 */
	

	public List getPopularTags(Date afterDate, Date beforeDate, int limit) {
		return getPopularTags(afterDate, beforeDate, limit, -1);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.painiu.core.dao.TagDAO#getPopularTags(java.util.Date, java.util.Date, int, int)
	 */

	public List getPopularTags(Date afterDate, Date beforeDate, int limit, int times) {
		return find(
				"select new Tag(tagName, count(SoftwareId)) " +
				"from SoftwareTag where taggedDate > ? and taggedDate < ? " + 
				"group by tagName " + (times > 0 ? "having col_1_0_ > " + times : "") +
				"order by col_1_0_ desc", // wait, col_1_0_, what is it? it is hibernate generated alias of count(SoftwareTag.Software)
				                          // see: http://bugs.mysql.com/bug.php?id=5478
				new Object[] { afterDate, beforeDate }, 0, limit).getData();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.painiu.core.dao.TagDAO#getUserPopularTags(com.painiu.core.model.User, int)
	 */
	
	public List getUserPopularTags(User user, int limit) {
		return find(
				"select new Tag(tagName, count(SoftwareId)) from SoftwareTag where ownerId = ? " +
				"group by tagName " +
				"order by col_1_0_ desc", // wait, col_1_0_, what is it? it is hibernate generated alias of count(Software)
                							// see: http://bugs.mysql.com/bug.php?id=5478
				new Object[] { user.getId() }, 0, limit).getData();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.painiu.core.dao.TagDAO#getUserRecentTags(com.painiu.core.model.User, int)
	 */
	

	public List getUserRecentTags(User user, int limit) {
		return find(
				"select new Tag(tagName, count(SoftwareId)) from SoftwareTag where ownerId = ? " +
				"group by tagName " +
				"order by taggedDate desc", 
				new Object[] { user.getId() }, 0, limit).getData();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.painiu.core.dao.TagDAO#getUsersUseTag(java.lang.String, int)
	 */


	public List getUsersUseTag(String tagName, int limit) {
		return find(
				"select distinct owner from SoftwareTag tag join tag.owner owner where tag.tagName = ? " +
				" and (owner.userRank = ? or owner.userRank = ? or owner.userRank = ?)" +
				" order by tag.taggedDate desc", 
				new Object[] { tagName, UserRank.POPULAR,
						UserRank.COMMENDATORY,
						UserRank.SENIOR }, 0, limit).getData();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.painiu.core.dao.TagDAO#getUserTags(com.painiu.core.model.User)
	 */


	public List getUserTags(User user) {
		return getReadOnlyHibernateTemplate().find(
				"select new Tag(tagName, count(SoftwareId)) from SoftwareTag where ownerId = ? group by tagName", user.getId());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.painiu.core.dao.TagDAO#getSoftwareTags(java.util.Date, java.util.Date)
	 */


	public List getSoftwareTags(Date startDate, Date toDate) {
		return getReadOnlyHibernateTemplate().find(
				"from SoftwareTag tag where tag.taggedDate >= ? and tag.taggedDate < ?", 
				new Object[] { startDate, toDate });
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.painiu.core.dao.TagDAO#getSoftwareTags(com.painiu.core.model.Software)
	 */

	public List getSoftwareTags(Software Software) {
		return getReadOnlyHibernateTemplate().find(
				"from SoftwareTag tag join fetch tag.user where tag.Software = ? order by tag.taggedDate", Software);
	}

	/*
	 * (non-Javadoc)
	 * @see com.painiu.core.dao.TagDAO#getSoftwareTags(com.painiu.core.model.User, java.lang.String, boolean)
	 */

	public List getSoftwareTags(User user, String tagName, boolean update) {
		HibernateTemplate htp = update ? getHibernateTemplate() : getReadOnlyHibernateTemplate();
		return htp.find(
				"from SoftwareTag tag where tag.owner = ? and tag.tagName = ?",
				new Object[] { user, tagName });
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.painiu.core.dao.TagDAO#saveSoftwareTag(com.painiu.core.model.SoftwareTag)
	 */
     public void saveSoftwareTag(SoftwareTag softwareTag){
		if (log.isDebugEnabled()) {
			log.debug("Saving " + softwareTag);
		}
		
		getHibernateTemplate().save(softwareTag);

	}
	
	/*
	 * 
	 * @see com.painiu.core.dao.TagDAO#removeSoftwareTag(com.painiu.core.model.SoftwareTag)
	 */

	public void removeSoftwareTag(SoftwareTag softwareTag) {
		if (log.isDebugEnabled()) {
			log.debug("Removing " + softwareTag);
		}
		
		getHibernateTemplate().delete(softwareTag);
		
	}


}
