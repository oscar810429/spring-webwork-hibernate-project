/*
 * @(#)ForumManagerImpl.java  2006-2-18
 * 
 * Copyright 2005 Yupoo. All rights reserved.
 */
package com.painiu.core.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.painiu.Context;
import com.painiu.core.aop.transaction.NonTransactional;
import com.painiu.core.dao.ForumDAO;
import com.painiu.core.dao.PostDAO;
import com.painiu.core.dao.TopicDAO;
import com.painiu.core.service.ForumManager;
import com.painiu.core.model.Category;
import com.painiu.core.model.Forum;
import com.painiu.core.model.Post;
import com.painiu.core.model.Topic;
import com.painiu.core.model.User;
import com.painiu.core.model.Comment.Situation;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="ForumManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Zola Zhou
 * @version $Id: ForumManagerImpl.java 135 2010-11-23 09:28:01Z zhangsf $
 */
@Transactional
public class ForumManagerImpl implements ForumManager {
	//~ Static fields/initializers =============================================

	private static final Log log = LogFactory.getLog(ForumManagerImpl.class);

	//~ Instance fields ========================================================

	private ForumDAO forumDAO;

	private TopicDAO topicDAO;

	private PostDAO postDAO;

	//~ Constructors ===========================================================

	//~ Methods ================================================================

	/**
	 * @param forumDAO The forumDAO to set.
	 */
	@NonTransactional
	public void setForumDAO(ForumDAO forumDAO) {
		this.forumDAO = forumDAO;
	}

	/**
	 * @param topicDAO The topicDAO to set.
	 */
	@NonTransactional
	public void setTopicDAO(TopicDAO topicDAO) {
		this.topicDAO = topicDAO;
	}

	/**
	 * @param postDAO The postDAO to set.
	 */
	@NonTransactional
	public void setPostDAO(PostDAO postDAO) {
		this.postDAO = postDAO;
	}

	/*
	 * @see com.painiu.core.logic.ForumManager#getForum(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public Forum getForum(String id) {
		return forumDAO.getForum(id);
	}

	/*
	 * @see com.painiu.core.logic.ForumManager#getSystemForums()
	 */
	@Transactional(readOnly=true)
	public List getSystemForums() {
		return forumDAO.getSystemForums();
	}

	/*
	 * @see com.painiu.core.logic.ForumManager#saveForum(com.painiu.core.model.Forum)
	 */
	public void saveForum(Forum forum) {
		if (log.isDebugEnabled()) {
			log.debug("Saving " + forum);
		}

		forumDAO.saveForum(forum);
	}

	/*
	 * @see com.painiu.core.logic.ForumManager#removeForum(com.painiu.core.model.Forum)
	 */
	public void removeForum(Forum forum) {
		if (log.isDebugEnabled()) {
			log.debug("Deleting " + forum);
		}

		forumDAO.removeForum(forum);
	}

	/*
	 * @see com.painiu.core.logic.ForumManager#getTopic(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public Topic getTopic(String id) {
		return topicDAO.getTopic(id);
	}

	/*
	 * @see com.painiu.core.logic.ForumManager#saveTopic(com.painiu.core.model.Topic)
	 */
	public void saveTopic(Topic topic) {
		if (log.isDebugEnabled()) {
			log.debug("Saving " + topic);
		}

		topicDAO.saveTopic(topic);
	}
	
	/*
	 * @see com.painiu.core.logic.ForumManager#updateTopic(com.painiu.core.model.Topic)
	 */
	public void updateTopic(Topic topic) {
		if (log.isDebugEnabled()) {
			log.debug("Saving " + topic);
		}

		topicDAO.saveTopic(topic);
	}

	/*
	 * @see com.painiu.core.logic.ForumManager#removeTopic(com.painiu.core.model.Topic)
	 */
	public void removeTopic(Topic topic) {
		if (log.isDebugEnabled()) {
			log.debug("Removing " + topic);
		}

		topicDAO.removeTopic(topic);
	}

	/*
	 * @see com.painiu.core.logic.ForumManager#positionTopic(com.painiu.core.model.Topic, java.lang.Integer)
	 */
	public void positionTopic(Topic topic, Integer position) {
		if (!topic.getPosition().equals(position)) {
			topic.setPosition(position);
			topicDAO.saveTopic(topic);
		}
	}

	/*
	 * @see com.painiu.core.logic.ForumManager#lockTopic(com.painiu.core.model.Topic)
	 */
	public void lockTopic(Topic topic, Boolean locked) {
		if (!topic.getLocked().equals(locked)) {
			topic.setLocked(locked);
			topic.setLockedBy(Context.getContext().getCurrentUser());
			topicDAO.saveTopic(topic);
		}
	}

	/*
	 * @see com.painiu.core.logic.ForumManager#getPost(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public Post getPost(String id) {
		return postDAO.getPost(id);
	}

	/*
	 * @see com.painiu.core.logic.ForumManager#removePost(com.painiu.core.model.Post)
	 */
	public void removePost(Post post) {
		if (log.isDebugEnabled()) {
			log.debug("Removing " + post);
		}

		Topic topic = post.getTopic();
		topic.removePost(post);
		topicDAO.saveTopic(topic);
	}

	/*
	 * @see com.painiu.core.logic.ForumManager#savePost(com.painiu.core.model.Post)
	 */
	public void savePost(Post post) {
		if (log.isDebugEnabled()) {
			log.debug("Saving " + post);
		}

		postDAO.savePost(post);
	}

	/*
	 * @see com.painiu.core.logic.ForumManager#getForumTopics(java.lang.String, int, int)
	 */
	@Transactional(readOnly=true)
	public Result getForumTopics(Forum forum, int start, int limit) {
		return topicDAO.getForumTopics(forum, start, limit);
	}

	/*
	 * @see com.painiu.core.logic.ForumManager#getForumTopics(com.painiu.core.model.Forum, java.lang.String, int, int)
	 */
	@Transactional(readOnly=true)
	public Result getForumTopics(Forum forum, String keyword, int start, int limit) {
		return topicDAO.getForumTopics(forum, keyword, start, limit);
	}
	
	@Transactional(readOnly=true)
	public Result getUserForumTopics(Forum forum, User user, int start, int limit) {
		return topicDAO.getUserForumTopics(forum,  user, start, limit);
	}

	/*
	 * @see com.painiu.core.logic.ForumManager#getSystemForumTopics(com.painiu.core.model.Forum, java.lang.String, int, int)
	 */
	@Transactional(readOnly=true)
	public Result getSystemForumTopics(Forum forum, User user, String keyword, int start, int limit) {
		return topicDAO.getSystemForumTopics(forum, user, keyword, start, limit);
	}

	/*
	 * @see com.painiu.core.logic.ForumManager#getGroupForumTopics(com.painiu.core.model.User, int, int)
	 */
	@Transactional(readOnly=true)
	public List getGroupForumTopics(int start, int limit) {
		return topicDAO.getGroupForumTopics(start, limit);
	}

	/*
	 * @see com.painiu.core.logic.ForumManager#getUserGroupForumTopics(com.painiu.core.model.User, java.net.Date, java.net.Date, int, int)
	 */
	@Transactional(readOnly=true)
	public List getUserGroupForumTopics(User user, Date fromDate, Date toDate, int start, int limit) {
		return topicDAO.getUserGroupForumTopics(user, fromDate, toDate, start, limit);
	}
	
	/*
	 * @see com.painiu.core.logic.ForumManager#getUserGroupForumTopics(com.painiu.core.model.User, int, int)
	 */
	@Transactional(readOnly=true)
	public Result getUserGroupForumTopics(User user, int start, int limit) {
		return topicDAO.getUserGroupForumTopics(user, start, limit);
	}

	/*
	 * @see com.painiu.core.logic.ForumManager#addPost(com.painiu.core.model.Topic, com.painiu.core.model.Post)
	 */
	public void addPost(Topic topic, Post post) {
		topic.addPost(post);
		topicDAO.saveTopic(topic);
	}

	@Transactional(readOnly=true)
	public List getPosts(User user, int limit) {
		return postDAO.getPosts(user, limit);
	}

	@Transactional(readOnly=true)
	public List getTopics(User user, int limit) {
		return topicDAO.getTopics(user, limit);
	}

	/*
	 * @see com.painiu.core.logic.ForumManager#getPosts(com.painiu.core.model.Category, int, int)
	 */
	@Transactional(readOnly=true)
	public Result getTopics(Category category, int start, int limit) {
		return topicDAO.getTopics(category, start, limit);
	}

	/*
	 * @see com.painiu.core.logic.ForumManager#getTopics(com.painiu.core.model.User, int, int)
	 */
	@Transactional(readOnly=true)
	public Result getTopics(User user, int start, int limit) {
		return topicDAO.getTopicsByUser(user, start, limit);
	}

	/*
	 * @see com.painiu.core.logic.ForumManager#getTopics(int, int)
	 */
	@Transactional(readOnly=true)
	public Result getTopics(int start, int limit) {
		return topicDAO.getTopics(start, limit);
	}

    /*
	 * @see com.painiu.core.logic.ForumManager#getTopicsRepliedByMe(com.painiu.core.model.User, int, int)
	 */
	@Transactional(readOnly=true)
	public Result getTopicsRepliedByMe(User user, int start, int limit) {
		return topicDAO.getTopicsRepliedByMe(user, start, limit);
	}

	@Transactional(readOnly=true)
	public Integer getForumTopicsSize(Forum forum, Date fromDate) {
		return topicDAO.getForumTopicsSize(forum, fromDate);
	}

	/*
	 * @see com.painiu.core.logic.ForumManager#addAccessedTime(com.painiu.core.model.Topic)
	 */
	public void addAccessedTime(Topic topic) {
		topic.setAccessedTimes(topic.getAccessedTimes()+1);
		topicDAO.saveTopic(topic);
		
	}

	/*
	 * @see com.painiu.core.logic.ForumManager#getHotTopics(com.painiu.core.model.Forum, int)
	 */
	@Transactional(readOnly=true)
	public List getHotTopics(Forum forum, int limit) {
		return topicDAO.getHotTopics(forum, limit);
	}

	/*
	 * @see com.painiu.core.logic.ForumManager#getSystemForumTopics(com.painiu.core.model.Forum, com.painiu.core.model.User, int, int)
	 */
	@Transactional(readOnly=true)
	public Result getSystemForumTopics(Forum forum, User user, int start, int limit) {
		return topicDAO.getSystemForumTopics(forum, user, start, limit);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.ForumManager#getPosts(com.painiu.core.model.User, java.lang.String, com.painiu.core.model.Comment.Situation, java.util.Date, java.util.Date, int, int)
	 */
	@Transactional(readOnly=true)
	public Result getPosts(User user, String key, Situation situation, Date startDate, Date endDate, int start, int limit) {
		return postDAO.getPosts(user, key, situation, startDate, endDate, start, limit);
	}
	
	@Transactional(readOnly=true)
	public Result getTopics(User user, String key, Situation situation, Date startDate, Date endDate, int start, int limit) {
		return topicDAO.getTopics(user, key, situation, startDate, endDate, start, limit);
	}
	
}
