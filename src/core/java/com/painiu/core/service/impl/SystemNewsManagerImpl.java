/*
 * @(#)SystemNewsManagerImpl.java  2009-11-27
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.painiu.Painiu;
import com.painiu.core.aop.transaction.NonTransactional;
import com.painiu.core.dao.SystemNewsDAO;
import com.painiu.core.service.SystemNewsManager;
import com.painiu.core.model.SystemNews;
import com.painiu.core.search.Result;
/**
 * <p>
 * <a href="SystemNewsManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu 
 * @version $Id: SystemNewsManagerImpl.java 8 2010-05-11 16:48:01Z zhangsf $
 */
@Transactional
public class SystemNewsManagerImpl implements SystemNewsManager, InitializingBean {
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	private SystemNewsDAO systemNewsDAO;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	/*
	 * @see com.painiu.core.logic.SystemNewsManager#getSystemNews(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public SystemNews getSystemNews(String id) {
		return systemNewsDAO.getSystemNews(id);
	}
	
	/*
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@NonTransactional
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(systemNewsDAO);

	} 

	/*
	 * @see com.painiu.core.logic.SystemNewsManager#getSystemNews(int, int)
	 */
	@Transactional(readOnly=true)
	public Result getSystemNews(int kind, int start, int limit) {
		return systemNewsDAO.getSystemNews(kind, start, limit);
	}
	
	@Transactional(readOnly=true)
	public Result getSystemNews(int kind, int start, int limit, int image) {
		return systemNewsDAO.getSystemNews(kind, start, limit, image);
	}
	@Transactional(readOnly=true)
	public Result getSystemNews(int kind, String pageKind, int start, int limit) {
		return systemNewsDAO.getSystemNews(kind, pageKind, start, limit);
	}
	
	/*
	 * @see com.painiu.core.logic.SystemNewsManager#getSystemNews(int, int)
	 */
	@Transactional(readOnly=true)
	public Result getSystemNews(int start, int limit) {
		return systemNewsDAO.getSystemNews(start, limit);
	}

	/*
	 * @see com.painiu.core.logic.SystemNewsManager#removeSystemNews(com.painiu.core.model.SystemNews)
	 */
	public void removeSystemNews(SystemNews systemNews) {
		systemNewsDAO.removeSystemNews(systemNews);
	}

	/*
	 * @see com.painiu.core.logic.SystemNewsManager#saveSystemNews(com.painiu.core.model.SystemNews)
	 */
	public void saveSystemNews(SystemNews systemNews) {
		systemNewsDAO.saveSystemNews(systemNews);
	}

	/*
	 * @see com.painiu.core.logic.SystemNewsManager#setSystemNewsDAO(com.painiu.core.dao.SystemNewsDAO)
	 */
	@NonTransactional
	public void setSystemNewsDAO(SystemNewsDAO systemNewsDAO) {
		this.systemNewsDAO = systemNewsDAO;
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.SystemNewsManager#getLastImportantNews()
	 */
	@Transactional(readOnly=true)
	public SystemNews getLastImportantNews() {
		List systemNewsList=systemNewsDAO.getSystemNews(Painiu.NEWS_IMPORTANT, -1, 1).getData();
		if((systemNewsList !=null )&& (systemNewsList.size()>0)) {
		  return (SystemNews)(systemNewsList.get(0));
		}
		return null;
		
	}
	
	@Transactional(readOnly=true)
	public List getLastImportantNews(int amount) {
		return systemNewsDAO.getSystemNews(Painiu.NEWS_IMPORTANT, -1, amount).getData();
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.SystemNewsManager#getLastTagNews()
	 */
	@Transactional(readOnly=true)
	public SystemNews getLastTagNews() {
		List systemNewsList=systemNewsDAO.getSystemNews(Painiu.NEWS_TAG, -1, 1).getData();
		if((systemNewsList !=null )&& (systemNewsList.size()>0)) {
		  return (SystemNews)(systemNewsList.get(0));
		}
		return null;
		
	}
	
	@Transactional(readOnly=true)
	public List getLastTagNews(int amount) {
		return systemNewsDAO.getSystemNews(Painiu.NEWS_TAG, -1, amount).getData();
	}
	
	/* (non-Javadoc)
	 * @see com.painiu.core.logic.SystemNewsManager#getLastActivityImage()
	 */
	@Transactional(readOnly=true)
	public SystemNews getLastActivityImage() {
		
		List systemNewsList=systemNewsDAO.getSystemNews(Painiu.IMAGE_ACTIVITY, -1, 1).getData();
		if((systemNewsList !=null )&& (systemNewsList.size()>0)) {
		  return (SystemNews)(systemNewsList.get(0));
		}
		return null;
		
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.SystemNewsManager#getLastIndexImage()
	 */
	@Transactional(readOnly=true)
	public SystemNews getLastIndexImage() {	
		List systemNewsList=systemNewsDAO.getSystemNews(Painiu.IMAGE_INDEX, -1, 1).getData();
		if((systemNewsList !=null )&& (systemNewsList.size()>0)) {
		  return (SystemNews)(systemNewsList.get(0));
		}
		return null;
		
	}

	@Transactional(readOnly=true)
	public List getLastIndexImage(int amount) {	
		return systemNewsDAO.getSystemImage(Painiu.IMAGE_INDEX, -1, amount).getData();
	}
	
	@Transactional(readOnly=true)
	public List getLastActivityImage(int amount){
		return systemNewsDAO.getSystemImage(Painiu.IMAGE_ACTIVITY, -1, amount).getData();
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.SystemNewsManager#getSystemImageNews(int, int)
	 */
	@Transactional(readOnly=true)
	public Result getSystemImageNews(int start, int limit) {

		return systemNewsDAO.getSystemImageNews(start, limit);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.SystemNewsManager#getSystemTextNews(int, int)
	 */
	@Transactional(readOnly=true)
	public Result getSystemTextNews(int start, int limit) {
	
		return systemNewsDAO.getSystemTextNews(start, limit);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.logic.SystemNewsManager#getLastAdvertisementNews()
	 */
	@Transactional(readOnly=true)
	public SystemNews getLastAdvertisementNews() {
		List systemNewsList=systemNewsDAO.getSystemNews(Painiu.NEWS_AD, -1, 1).getData();
		if((systemNewsList !=null )&& (systemNewsList.size()>0)) {
		  return (SystemNews)(systemNewsList.get(0));
		}
		return null;
	}
}
