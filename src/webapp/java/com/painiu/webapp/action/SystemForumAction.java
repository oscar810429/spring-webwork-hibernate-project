/*
 * @(#)SystemForumAction.java  2006-2-18
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import com.painiu.core.service.FeatureManager;
import com.painiu.core.model.Forum;
import com.painiu.core.model.User;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="SystemForumAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SystemForumAction.java 138 2010-11-23 10:21:39Z zhangsf $
 */
public class SystemForumAction extends BaseAction {
	//~ Static fields/initializers =============================================
	private static final Log log = LogFactory.getLog(SystemForumAction.class);
	
	private static final int PAGE_SIZE = 30;
	
	private static final int FEATURE_PAGE_SIZE = 30;
	
	//~ Instance fields ========================================================

	private String id;
	
	private Forum forum;
	
	private List forums;
	
	private int page = 1;
	
	private Result result;
	
	//private FeatureManager featureManager;
	
	private Result features;
	
	// private List list;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	public String execute() throws Exception {
		assertParamExists("id", id);
		
		forum = forumManager.getForum(id);
		
		result = forumManager.getSystemForumTopics(forum, getCurrentUser(), (page - 1) * PAGE_SIZE, PAGE_SIZE);
		
		//list = forumManager.getForumTopics(forum, 0, 14).getData();
		
		return SUCCESS;
	}
	
	public String help() throws Exception {
		return SUCCESS;
	}
	
	public String feature() {
		//features = featureManager.getShowedFeatures(FEATURE_PAGE_SIZE * (page - 1), FEATURE_PAGE_SIZE);
		return SUCCESS;
	}
	
	public String myforum() throws Exception {
		
		User user = getCurrentUser();
		
		assertParamExists("id", id);
		
		forum = forumManager.getForum(id);
		
		result = forumManager.getUserForumTopics(forum, user, (page - 1) * PAGE_SIZE, PAGE_SIZE);
		
		//list = forumManager.getForumTopics(forum, 0, 14).getData();		
		return SUCCESS;
	}
	
	public String create() throws Exception {
		return SUCCESS;
	}
	
	public String edit() throws Exception {
		assertParamExists("id", id);
		
		forum = forumManager.getForum(id);
		
		return SUCCESS;
	}
	
	public String save() throws Exception {
		if (forum.getId() == null) {
			if (log.isDebugEnabled()) {
				log.debug("Creating " + forum);
			}
		
			forum.setCreatedDate(new Date());
			forum.setType(Forum.Type.SYSTEM_FORUM);
		} else {
			if (log.isDebugEnabled()) {
				log.debug("Updating " + forum);
			}
			
			Forum old = forumManager.getForum(forum.getId());
			old.setName(forum.getName());
			old.setDescription(forum.getDescription());
			old.setPosition(forum.getPosition());
			
			forum = old;
		}
			
		forumManager.saveForum(forum);
        
        saveMessage(getText("messages.forum.created"));
		
		return SUCCESS;
	}
	
	public String list() throws Exception {
		forums = forumManager.getSystemForums();
		// result = forumManager.getForumTopics(forum, (page - 1) * PAGE_SIZE, PAGE_SIZE);
		return SUCCESS;
	}
	
	public Result getForumTopics(Forum f) {
		result = forumManager.getSystemForumTopics(f, getCurrentUser(), (page - 1) * PAGE_SIZE, PAGE_SIZE);
		// return forumManager.getForumTopics(f, 0, 30).getData();
		return result;
	}
	
	//~ Accessors ==============================================================
	
	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return Returns the forums.
	 */
	public List getForums() {
		return forums;
	}

	/**
	 * @return Returns the page.
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
	 * @return Returns the result.
	 */
	public Result getResult() {
		return result;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/*public FeatureManager getFeatureManager() {
		return featureManager;
	}

	public void setFeatureManager(FeatureManager featureManager) {
		this.featureManager = featureManager;
	}*/

	public Result getFeatures() {
		return features;
	}
}
