/*
 * @(#)TopicAction.java  2006-2-21
 * 
 * Copyright 2005 Yupoo. All rights reserved.
 */
package com.painiu.webapp.action;

import java.util.Date;
import java.util.List;

import org.acegisecurity.AccessDeniedException;

import com.painiu.core.model.Forum;
import com.painiu.core.model.Topic;
import com.painiu.core.model.User;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="TopicAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zola Zhou
 * @version $Id: TopicAction.java 138 2010-11-23 10:21:39Z zhangsf $
 */
public class TopicAction extends BaseAction {
	//~ Static fields/initializers =============================================

	private static final int PAGE_SIZE = 15;
	public static final int POSTS_SIZE = 30;
	
	//~ Instance fields ========================================================

	private String id;
	private String forumId;
	
	private Topic topic;	
	private Forum forum;
	
	private int page = 1;
	private Result result;
	
	private String keyword;
	
	private int position = 0;
	private boolean locked = false;
	
	private List list;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	/**
	 * @return the list
	 */
	public List getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List list) {
		this.list = list;
	}

	public String execute() throws Exception {
		assertParamExists("id", id);
		
		topic = forumManager.getTopic(id);
		forum = topic.getForum();
		if (forum.getType().equals(Forum.Type.SYSTEM_FORUM)) {
			currentUser = getCurrentUser();
			if (currentUser == null) {
				if (topic.getPosition() == 0) {
					throw new AccessDeniedException("access denied");
				}
			} else {
				if (!(isAdmin() || isManager() || isCs() ) ) {
					if ( !currentUser.equals(topic.getAuthor()) && topic.getPosition() == 0) {
						throw new AccessDeniedException("access denied");
					}
				}
			}
		}
		
//		list = forumManager.getForumTopics(topic.getForum(), 0, 12).getData();
		
		result = calculatePagePosts(topic, page);
		
		return SUCCESS;
	}
	
	public String edit() throws Exception {
		assertParamExists("id", id);
		
		topic = forumManager.getTopic(id);
		forum = topic.getForum();
		
		return SUCCESS;
	}
	
	public String save() throws Exception {
		if (topic.getId() == null) {
			User poster = getCurrentUser();
			Date now = new Date();
			
			forum = forumManager.getForum(forumId);
			
			topic.setAuthor(poster);
			topic.setForum(forum);
			topic.setReplys(new Integer(0));
			topic.setPostedDate(now);
			topic.setModifiedDate(now);
			
			forumManager.saveTopic(topic);
//			
			saveMessage(getText("messages.topic.created"));
			
		} else {
			Topic old = forumManager.getTopic(topic.getId());
			Date now = new Date();
			old.setSubject(topic.getSubject());
			old.setContent(topic.getContent());
			old.setModifiedDate(now);
			old.setModifiedBy(getCurrentUser());
			
			forumManager.updateTopic(old);
			
			saveMessage(getText("messages.topic.updated"));
		}
		
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		assertParamExists("id", id);
		
		topic = forumManager.getTopic(id);
		forum = topic.getForum();
		
		forumManager.removeTopic(topic);
		
		
		
		saveMessage(getText("messages.topic.deleted"));
		return SUCCESS;
	}
	
	public String search() throws Exception {
		assertParamExists("keyword", keyword);
		
		if (forumId != null) {
			forum = forumManager.getForum(forumId);
		}
		
		result = forumManager.getSystemForumTopics(forum, getCurrentUser(), keyword, (page - 1) * PAGE_SIZE, PAGE_SIZE);
		
		if (forum != null) {
			return "forum";
		}
		
		return SUCCESS;
	}
	
	
	public String position() throws Exception {
		assertParamExists("id", id);
		
		topic = forumManager.getTopic(id);
		
		if ("GET".equals(getRequest().getMethod())) {
			return INPUT;
		}
		
		forumManager.positionTopic(topic, new Integer(position));
		
		if (position > 0) {
			saveMessage(getText("messages.topic.toped"));
		} else {
			saveMessage(getText("messages.topic.untoped"));
		}
		
		if (from != null) {
			redirect(from);
			return NONE;
		}
		
		forum = topic.getForum();
		
		return SUCCESS;
	}
	
	public String lock() throws Exception {
		assertParamExists("id", id);
		
		topic = forumManager.getTopic(id);
		
		if ("GET".equals(getRequest().getMethod())) {
			return INPUT;
		}
		
		forumManager.lockTopic(topic, Boolean.valueOf(locked));
		
		if (locked) {
			saveMessage(getText("messages.topic.locked"));
		} else {
			saveMessage(getText("messages.topic.unlocked"));
		}
		
		if (from != null) {
			return redirect(from);
		}
		
		return SUCCESS;
	}
	
	public static final Result calculatePagePosts(Topic topic, int page) {
		List posts = topic.getPosts();
		
		int offset = (page - 1) * POSTS_SIZE;
		int total = posts.size();
		
		Result result = new Result();
		result.setSize(POSTS_SIZE);
		result.setOffset(offset);
		result.setTotal(total);
		
		if (total <= POSTS_SIZE) {
			result.setData(posts);
		} else {
			if (total < offset + POSTS_SIZE) {
				result.setData(posts.subList(offset, total));
			} else {
				result.setData(posts.subList(offset, offset + POSTS_SIZE));
			}
		}
		
		return result;
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
	 * @param forumId The forumId to set.
	 */
	public void setForumId(String forumId) {
		this.forumId = forumId;
	}
	
	/**
	 * @return Returns the forumId.
	 */
	public String getForumId() {
		return forumId;
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
	 * @return Returns the result.
	 */
	public Result getResult() {
		return result;
	}
	
	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page The page to set.
	 */
	public void setPage(int page) {
		this.page = page;
	}
	
	/**
	 * @return Returns the keyword.
	 */
	public String getKeyword() {
		return keyword;
	}
	
	/**
	 * @param keyword The keyword to set.
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the locked
	 */
	public boolean isLocked() {
		return locked;
	}

	/**
	 * @param locked the locked to set
	 */
	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
		
		if (this.position < 0) {
			this.position = 0;
		}
		if (this.position > 10) {
			this.position = 10;
		} 
		
	}
	
}

