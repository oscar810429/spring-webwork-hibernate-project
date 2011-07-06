/*
 * @(#)SystemMessageAction.java  2006-8-7
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action.console;

import java.util.Date;

import com.painiu.core.model.SystemMessage;
import com.painiu.core.search.Result;
import com.painiu.webapp.action.BaseAction;

/**
 * <p>
 * <a href="SystemMessageAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SystemMessageAction.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class SystemMessageAction extends BaseAction {
	//~ Static fields/initializers =============================================

	private static final int PAGE_SIZE = 10;
	
	//~ Instance fields ========================================================
	
	private String id;
	
	private SystemMessage message;
	
	private int page = 1;
	
	private Result result;
	
	private Date now;

	//~ Constructors ===========================================================

	//~ Methods ================================================================

	public String edit() throws Exception {
		assertParamExists("id", id);
		
		message = systemMessageManager.getSystemMessage(id);
		
		return SUCCESS;
	}
	
	public String list() throws Exception {
		result = systemMessageManager.getSystemMessages((page - 1) * PAGE_SIZE, PAGE_SIZE);
		
		now = new Date();
		
		return SUCCESS;
	}
	
	public String save() throws Exception {
		Date now = new Date();
		
		if (message.getId() == null) {
			message.setCreatedDate(now);
			
			systemMessageManager.saveSystemMessage(message);
		} else {
			SystemMessage old = systemMessageManager.getSystemMessage(message.getId());
			old.setSubject(message.getSubject());
			old.setContent(message.getContent());
			old.setExpireDate(message.getExpireDate());
			
			systemMessageManager.saveSystemMessage(old);
		}
		
		saveMessage("Message have been saved.");
		
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		assertParamExists("id", id);
		
		message = systemMessageManager.getSystemMessage(id);
		
		systemMessageManager.removeSystemMessage(message);
		
		saveMessage("Message have been deleted.");
		
		return SUCCESS;
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
	 * @return Returns the news.
	 */
	public SystemMessage getMessage() {
		return message;
	}

	/**
	 * @param news The news to set.
	 */
	public void setMessage(SystemMessage message) {
		this.message = message;
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
	 * @param result The result to set.
	 */
	public void setResult(Result result) {
		this.result = result;
	}
	/**
	 * @return Returns the now.
	 */
	public Date getNow() {
		return now;
	}
}
