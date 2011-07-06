/*
 * @(#)ForumManager.java  2006-2-15
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service;

import java.util.Date;
import java.util.List;

import com.painiu.core.model.Category;
import com.painiu.core.model.Comment;
import com.painiu.core.model.Forum;
import com.painiu.core.model.Post;
import com.painiu.core.model.Topic;
import com.painiu.core.model.User;
import com.painiu.core.model.Comment.Situation;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="ForumManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zola Zhou
 * @version $Id: ForumManager.java 135 2010-11-23 09:28:01Z zhangsf $
 */
public interface ForumManager {
	
	public Forum getForum(String id);
	
	public List getSystemForums();
	
	public void saveForum(Forum forum);
	
	public void removeForum(Forum forum);
	
	//==============================================================
	
	public Topic getTopic(String id);
	
	public void saveTopic(Topic topic);
	
	public void updateTopic(Topic topic);
	
	public void removeTopic(Topic topic);
	
	public void positionTopic(Topic topic, Integer position);
	
	public void lockTopic(Topic topic, Boolean locked);
	
	
	public Post getPost(String id);
	
	public void savePost(Post post);
	
	public void removePost(Post post);
	
	public List getPosts(User user, int limit);
	public List getTopics(User user, int limit);
	public Result getTopics(Category category, int start, int limit);
	public Result getTopics(User user, int start, int limit);
	public Result getTopics(int start, int limit);
	public Result getTopicsRepliedByMe(User user, int start, int limit);
	
	public Result getForumTopics(Forum forum, int start, int limit);
	public Result getSystemForumTopics(Forum forum, User user, int start, int limit);
	
	public Integer getForumTopicsSize(Forum forum, Date fromDate);
	
	public Result getUserForumTopics(Forum forum, User user, int start, int limit);
	
	public Result getForumTopics(Forum forum, String keyword, int start, int limit);
	
	public Result getSystemForumTopics(Forum forum, User user, String keyword, int start, int limit);
	
	public List getGroupForumTopics(int start, int limit);

	public List getUserGroupForumTopics(User user, Date fromDate, Date toDate, int start, int limit);

	public Result getUserGroupForumTopics(User user, int start, int limit);
	
	public void addPost(Topic topic, Post post);
	
	public void addAccessedTime(Topic topic);
	
	public List getHotTopics(Forum forum, int limit);
	
	/*For manage coments in console.
	 * */
	public Result getPosts(User user, String key, Comment.Situation situation, Date startDate, Date endDate, int start, int limit);
	
	public Result getTopics(User user, String key, Situation situation, Date startDate, Date endDate, int start, int limit);
		
 
}
