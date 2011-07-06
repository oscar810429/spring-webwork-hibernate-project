/*
 * @(#)TopicDAO.java  2006-2-14
 * 
 * Copyright 2005 Yupoo. All rights reserved.
 */
package com.painiu.core.dao;

import java.util.Date;
import java.util.List;

import com.painiu.core.model.Category;
import com.painiu.core.model.Comment;
import com.painiu.core.model.Forum;
//import com.painiu.core.model.Group;
import com.painiu.core.model.Topic;
import com.painiu.core.model.User;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="TopicDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zola Zhou
 * @version $Id: TopicDAO.java 135 2010-11-23 09:28:01Z zhangsf $
 */
public interface TopicDAO extends DAO {

	public List getTopics(User user, int limit);
	public Topic getTopic(String id);
	public Result getTopics(Category cateogry, int start, int limit);
	public Result getTopics(int start, int limit);
	public Result getTopicsRepliedByMe(User user, int start, int limit);
	
	public void saveTopic(Topic topic);
	
	public void removeTopic(Topic topic);
	
	public Result getForumTopics(Forum forum, int start, int limit);
	
	public Result getUserForumTopics(Forum forum, User user, int start, int limit);
	
	public Result getTopicsByUser(User user, int start, int limit);
	
	public Result getForumTopics(Forum forum, String keyword, int start, int limit);
	
	public Integer getForumTopicsSize(Forum forum, Date fromDate);
	
	public Result getSystemForumTopics(Forum forum, User user, String keyword, int start, int limit);
	
	public List getGroupForumTopics(int start, int limit);
	
	public List getUserGroupForumTopics(User user, Date fromDate, Date toDate, int start, int limit);

	public Result getUserGroupForumTopics(User user, int start, int limit);
	
	public Result getPopularGroupTopicsByAccessTimes(int start, int limit);
	
	//public Result getCommendatoryGroupTopic(Group group,int start, int limit);
		
	public List getHotTopics(Forum forum, int limit);
	
	public Result getSystemForumTopics(Forum forum, User user, int start, int limit);
	
	/*For manage coments in console.
	 * */
	public Result getTopics(User user, String key, Comment.Situation situation, Date startDate, Date endDate, int start, int limit);
	
}