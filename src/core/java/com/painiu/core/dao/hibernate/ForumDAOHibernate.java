/*
 * @(#)ForumDAOHibernate.java  2006-2-14
 * 
 * Copyright 2005 Yupoo. All rights reserved.
 */
package com.painiu.core.dao.hibernate;

import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.type.Type;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.painiu.Painiu;
import com.painiu.core.dao.ForumDAO;
import com.painiu.core.model.Forum;
import com.painiu.core.model.User;
import com.painiu.core.persistence.UserTypes;

/**
 * <p>
 * <a href="ForumDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zola Zhou
 * @version $Id: ForumDAOHibernate.java 139 2010-11-25 02:48:53Z zhangsf $
 */
public class ForumDAOHibernate extends BaseDAOHibernate implements ForumDAO {

	//~ Static fields/initializers =============================================

	private static final Log log = LogFactory.getLog(ForumDAOHibernate.class);
	
	//~ Instance fields ========================================================

	//~ Constructors ===========================================================

	//~ Methods ================================================================

	/*
	 * @see com.yupoo.dao.ForumDAO#getForum(java.lang.String)
	 */
	public Forum getForum(String id) {
		Forum forum = (Forum) getHibernateTemplate().get(Forum.class, id);
		
		if (forum == null) {
			log.warn("uh, oh, Forum[" + id + "] not found...");
        	throw new ObjectRetrievalFailureException(Forum.class, id);
		}
		
		return forum;
	}
	
	/*
	 * @see com.yupoo.dao.ForumDAO#getSystemForums()
	 */
	public List getSystemForums() {
		List forums = find("from Forum f where f.type = ?", 
				new Object[] { Forum.Type.SYSTEM_FORUM }, 
				new Type[] { UserTypes.forumType() });
		//Collections.sort(forums);
		
		return forums;
	}
	
	/*
	 * @see com.yupoo.dao.ForumDAO#saveForum(com.yupoo.model.Forum)
	 */
	public void saveForum(Forum forum) {
		if (log.isDebugEnabled()) {
			log.debug("Saving " + forum);
		}
		
		getHibernateTemplate().saveOrUpdate(forum);
	}

	/*
	 * @see com.yupoo.dao.ForumDAO#removeForum(com.yupoo.model.Forum)
	 */
	public void removeForum(Forum forum) {
		if (log.isDebugEnabled()) {
			log.debug("Removing " + forum);
		}
		
		getHibernateTemplate().delete(forum);
	}


}
