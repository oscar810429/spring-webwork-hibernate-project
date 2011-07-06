/**
 * @(#)HomeAction.java Jul 26, 2008
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action;

import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.TaskRejectedException;

import com.painiu.core.model.User;
import com.painiu.core.model.UserStat;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="HomeAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: MyHomeAction.java 6 2010-05-11 16:20:57Z zhangsf $
 */
public class MyHomeAction extends BaseAction {
	//~ Static fields/initializers =============================================

	private static final long serialVersionUID = 7992251151773467212L;
	
	private User user;
	private Result result;
	private int page = 1 ;
	private int cpage = 1;
	private String username;
	private int id;
	private TaskExecutor taskExecutor;

	//~ Instance fields ========================================================
	
	private String timenow;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	public String execute() throws Exception {
		try {
			taskExecutor.execute(new Runnable() {
				public void run() {
					log.debug("this test task...");
				}
			});
		} catch (TaskRejectedException e) {
			log.error("Can not execute task[SaveAccess]: " + e.getMessage());
		}
		
		return SUCCESS;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return this.user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the result
	 */
	public Result getResult() {
		return this.result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(Result result) {
		this.result = result;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return this.page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the cpage
	 */
	public int getCpage() {
		return this.cpage;
	}

	/**
	 * @param cpage the cpage to set
	 */
	public void setCpage(int cpage) {
		this.cpage = cpage;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	
	//~ Accessors ==============================================================
	
}
