/*
 * @(#)SystemNewsAction.java  2006-7-25
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action.console;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.painiu.webapp.action.BaseAction;
import com.painiu.Painiu;
import com.painiu.core.model.SystemNews;
import com.painiu.core.model.User;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="SystemNewsAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zola Zhou
 * @version $Id: SystemNewsAction.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class SystemNewsAction extends BaseAction {
	//~ Static fields/initializers =============================================

	private static final int PAGE_SIZE = 15;
	private static final int IMAGE_PAGE_SIZE = 8;
	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	
	//~ Instance fields ========================================================
	
	private String id;
	
	private SystemNews news;
	
	private int page = 1;
	
	private Result result;
	
	private int kind = -1;

	private List categories;
	
	private String expiredDate;
	
	private String username;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String addNewsAd()throws Exception {
		//categories = categoryManager.getGroupCategories();
		return SUCCESS;
	}

	public String edit() throws Exception {
		assertParamExists("id", id);
		
		news = systemNewsManager.getSystemNews(id);
		
		return SUCCESS;
	}
	
	public String editAd() throws Exception {
		assertParamExists("id", id);
		
		news = systemNewsManager.getSystemNews(id);
		//categories = categoryManager.getGroupCategories();
		return SUCCESS;
	}
	public String list() throws Exception {
		if(kind >= 0) {
			result = systemNewsManager.getSystemNews(kind,(page - 1) * PAGE_SIZE, PAGE_SIZE);
			//categories = categoryManager.getGroupCategories();
		}
		return SUCCESS;
	}
	public String textList() throws Exception {
		if(kind >= 0) {
			result = systemNewsManager.getSystemNews(kind,(page - 1) * PAGE_SIZE, PAGE_SIZE);
			//categories = categoryManager.getGroupCategories();
		}
		return SUCCESS;
	}
	public String imageList() throws Exception {
		result = systemNewsManager.getSystemImageNews((page - 1) * IMAGE_PAGE_SIZE, IMAGE_PAGE_SIZE);
		//categories = categoryManager.getGroupCategories();
		return SUCCESS;
	}
	public String save() throws Exception {
		Date now = new Date();
		User user = null;
		if (StringUtils.isNotEmpty(username)) {
			if (username.length() == 32) {
				try {
					user = userManager.getUser(username);
				} catch (ObjectRetrievalFailureException e) {
					user = userManager.getUserByUsername(username);
				}
			} else {
				user = userManager.getUserByUsername(username);
			}
		}
		
		if (StringUtils.isNotEmpty(expiredDate)) {
			if (parseDate(expiredDate) != null) {
				news.setExpiredDate(parseDate(expiredDate));
			}
		}
		
		if (news.getId() == null) {
			news.setCreatedDate(now);
			news.setModifiedDate(now);
			if(user != null) {
				news.setUser(user);
			}
			systemNewsManager.saveSystemNews(news);
		} else {
			SystemNews old = systemNewsManager.getSystemNews(news.getId());
			old.setContent(news.getContent());
			old.setUrl(news.getUrl());
			old.setTitle(news.getTitle());
			old.setKind(news.getKind());
			old.setUrlBlank(news.getUrlBlank());
			old.setWeight(news.getWeight());
			if(news.getKind() == Painiu.IMAGE_ACTIVITY && news.getCreatedDate() != null) {
				old.setCreatedDate(news.getCreatedDate());
			}
			old.setImage(news.getImage());
			old.setModifiedDate(now);
			old.setExpiredDate(news.getExpiredDate());
			if(user != null) {
				old.setUser(user);
			}
			systemNewsManager.saveSystemNews(old);
		}
		
		saveMessage("The system item has been saved.");
		
		return SUCCESS;
	}
	
	
	public String delete() throws Exception {
		assertParamExists("id", id);
		
		news = systemNewsManager.getSystemNews(id);
		
		systemNewsManager.removeSystemNews(news);
		
		saveMessage("News have been deleted.");
		
		return SUCCESS;
	}
	
	
	private static Date parseDate(String dateStr) {
		try {
			return DF.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
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
	public SystemNews getNews() {
		return news;
	}

	/**
	 * @param news The news to set.
	 */
	public void setNews(SystemNews news) {
		this.news = news;
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
	 * @return the kind
	 */
	public int getKind() {
		return kind;
	}

	/**
	 * @param kind the kind to set
	 */
	public void setKind(int kind) {
		this.kind = kind;
	}
	
	/**
	 * @return the categories
	 */
	public List getCategories() {
		return categories;
	}
	/**
	 * @param categories the categories to set
	 */
	public void setCategories(List categories) {
		this.categories = categories;
	}

	/**
	 * @return the expiredDate
	 */
	public String getExpiredDate() {
		return expiredDate;
	}

	/**
	 * @param expiredDate the expiredDate to set
	 */
	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}
}
