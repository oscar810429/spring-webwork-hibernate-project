/*
 * @(#)PostDAOHibernate.java  2006-2-21
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.type.Type;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.painiu.core.dao.PostDAO;
import com.painiu.core.model.Comment;
import com.painiu.core.model.Post;
import com.painiu.core.model.User;
import com.painiu.core.model.Comment.Situation;
import com.painiu.core.persistence.UserTypes;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="PostDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zola Zhou
 * @version $Id: PostDAOHibernate.java 135 2010-11-23 09:28:01Z zhangsf $
 */
public class PostDAOHibernate extends BaseDAOHibernate implements PostDAO {
	//~ Static fields/initializers =============================================
	private static final Log log = LogFactory.getLog(PostDAOHibernate.class);
	
	//~ Instance fields ========================================================

	//~ Constructors ===========================================================

	//~ Methods ================================================================

	
	public List getPosts(User user, int limit){
		return find("from Post post where post.author=?  and post.situation <> ?  order by post.modifiedDate desc",
				new Object[] { user, Comment.Situation.deleted},
				new Type[] { Hibernate.entity(User.class), UserTypes.commentSituationType()}, 
				0, limit).getData();
	}
	/*
	 * @see com.painiu.core.dao.PostDAO#getPost(java.lang.String)
	 */
	public Post getPost(String id) {
		Post post = (Post) getHibernateTemplate().get(Post.class, id);
		
		if (post == null) {
			log.warn("uh, oh, Post[" + id + "] not found...");
        	throw new ObjectRetrievalFailureException(Post.class, id);
		}
		
		return post;
	}

	/*
	 * @see com.painiu.core.dao.PostDAO#savePost(com.painiu.core.model.Post)
	 */
	public void savePost(Post post) {
		if (log.isDebugEnabled()) {
			log.debug("Saving " + post);
		}
		
		getHibernateTemplate().saveOrUpdate(post);
	}

	/*
	 * @see com.painiu.core.dao.PostDAO#removePost(com.painiu.core.model.Post)
	 */
	public void removePost(Post post) {
		if (log.isDebugEnabled()) {
			log.debug("Removing " + post);
		}
		
		getHibernateTemplate().delete(post);
	}
	/* (non-Javadoc)
	 * @see com.painiu.core.dao.PostDAO#getPosts(com.painiu.core.model.User, java.lang.String, com.painiu.core.model.Comment.Situation, java.util.Date, java.util.Date, int, int)
	 */
	public Result getPosts(User user, String key, Situation situation, Date startDate, Date endDate, int start, int limit) {
		if(user != null) {
			if(StringUtils.isNotEmpty(key)){
				key = "%" + key + "%";
				return find("select count(c.id) from Post c where c.author = ? and c.situation = ? and c.postedDate > ? and c.postedDate < ? and c.content like ?",
						"from Post c where c.author = ? and c.situation = ? and c.postedDate > ? and c.postedDate < ? and c.content like ? order by c.postedDate", 
						new Object[] { user , situation, startDate, endDate, key},
						new Type[] { Hibernate.entity(User.class), UserTypes.commentSituationType(), Hibernate.TIMESTAMP, Hibernate.TIMESTAMP, Hibernate.STRING },
						start, limit);
			}
			return find("select count(c.id) from Post c where c.author = ? and c.situation = ? and c.postedDate > ? and c.postedDate < ? ",
					"from Post c where c.author = ? and c.situation = ? and c.postedDate > ? and c.postedDate < ?  order by c.postedDate", 
					new Object[] { user , situation, startDate, endDate},
					new Type[] { Hibernate.entity(User.class), UserTypes.commentSituationType(), Hibernate.TIMESTAMP, Hibernate.TIMESTAMP },
					start, limit);
			
		}
		if(StringUtils.isNotEmpty(key)){
			key = "%" + key + "%";
			return find("select count(c.id) from Post c where c.situation = ? and c.postedDate > ? and c.postedDate < ? and c.content like ?",
					"from Post c where c.situation = ? and c.postedDate > ? and c.postedDate < ? and c.content like ? order by c.postedDate", 
					new Object[] { situation, startDate, endDate, key},
					new Type[] { UserTypes.commentSituationType(), Hibernate.TIMESTAMP, Hibernate.TIMESTAMP, Hibernate.STRING },
					start, limit);
		}
		return find("select count(c.id) from Post c where c.situation = ? and c.postedDate > ? and c.postedDate < ? ",
				"from Post c where c.situation = ? and c.postedDate > ? and c.postedDate < ?  order by c.postedDate", 
				new Object[] { situation, startDate, endDate},
				new Type[] { UserTypes.commentSituationType(), Hibernate.TIMESTAMP, Hibernate.TIMESTAMP },
				start, limit);
	}

}
