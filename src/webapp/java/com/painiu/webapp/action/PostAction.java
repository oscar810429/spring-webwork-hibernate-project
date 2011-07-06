/*
 * @(#)PostAction.java  2006-2-21
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action;

import java.util.Date;
import java.util.List;

import com.painiu.core.exception.MissingParameterException;
import com.painiu.core.model.Forum;
import com.painiu.core.model.Post;
import com.painiu.core.model.Topic;
import com.painiu.core.model.User;

/**
 * <p>
 * <a href="PostAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zola Zhou
 * @version $Id: PostAction.java 138 2010-11-23 10:21:39Z zhangsf $
 */
public class PostAction extends BaseAction {
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	private String id;
	
	private String forumId;
	private Forum forum;
	
	private String topicId;
	private Topic topic;
	
	private String subject;
	
	private String content;
	
	private Post post;
	
	private int page;
	
	//~ Constructors ===========================================================
	
	//~ Methods ================================================================
	
	public String create() throws Exception {
		if (forumId == null && topicId == null) {
			throw new MissingParameterException("forumId|topicId");
		}
		
		if (forumId != null) {
			forum = forumManager.getForum(forumId);
			return "topic";
		}
		
		if (topicId != null) {
			topic = forumManager.getTopic(topicId);
			forum = topic.getForum();
		}
		
		return SUCCESS;
	}
	
	public String edit() throws Exception{
		assertParamExists("id", id);
		
		post = forumManager.getPost(id);
		topic = post.getTopic();
		forum = topic.getForum();
		
		return SUCCESS;
	}
	
	public String save() throws Exception {
		if (post.getId() == null) {
			User poster = getCurrentUser();
			Date now = new Date();
			topic = forumManager.getTopic(topicId);
			
			post.setAuthor(poster);
			post.setTopic(topic);
			post.setPostedDate(now);
			post.setModifiedDate(now);

			forumManager.addPost(topic, post);
			saveMessage(getText("messages.post.created"));
			
			page = calculatePostPosition(topic, post);
		} else {
			Post old = forumManager.getPost(post.getId());
			old.setContent(post.getContent());
			old.setModifiedDate(new Date());
			old.setModifiedBy(getCurrentUser());
			
			forumManager.savePost(old);
			saveMessage(getText("messages.post.updated"));
			
			page = calculatePostPosition(old.getTopic(), old);
		}
		
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		assertParamExists("id", id);
		
		post = forumManager.getPost(id);
		topic = post.getTopic();
		
		forumManager.removePost(post);
		
		saveMessage(getText("messages.post.deleted"));
		
		return SUCCESS;
	}
	
	public static int calculatePostPosition(Topic topic, Post post) {
		List posts = topic.getPosts();
		
		int position = 0;
		
		for (int i = 0; i < posts.size(); i++) {
			Post current = (Post) posts.get(i);
			if (post.getId().equals(current.getId())) {
				position = i;
				break;
			}
		}
		
		position += 1;
		
		int index = position / TopicAction.POSTS_SIZE;
		
		if (position % TopicAction.POSTS_SIZE == 0) {
			return index;
		}
		return index + 1;
	}
	
	//~ Accessors ==============================================================
	
	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return Returns the post.
	 */
	public Post getPost() {
		return post;
	}
	
	/**
	 * @param post The post to set.
	 */
	public void setPost(Post post) {
		this.post = post;
	}

	/**
	 * @return Returns the content.
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content The content to set.
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return Returns the subject.
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject The subject to set.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return Returns the forum.
	 */
	public Forum getForum() {
		return forum;
	}

	/**
	 * @param forum The forum to set.
	 */
	public void setForum(Forum forum) {
		this.forum = forum;
	}

	/**
	 * @return Returns the forumId.
	 */
	public String getForumId() {
		return forumId;
	}

	/**
	 * @param forumId The forumId to set.
	 */
	public void setForumId(String forumId) {
		this.forumId = forumId;
	}

	/**
	 * @return Returns the topic.
	 */
	public Topic getTopic() {
		return topic;
	}

	/**
	 * @param topic The topic to set.
	 */
	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	/**
	 * @return Returns the topicId.
	 */
	public String getTopicId() {
		return topicId;
	}

	/**
	 * @param topicId The topicId to set.
	 */
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}
	
}
