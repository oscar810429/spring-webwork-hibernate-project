/*
 * @(#)SystemNewsManager.java  2009-11-27
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service;

import java.util.List;

import com.painiu.core.dao.SystemNewsDAO;
import com.painiu.core.model.SystemNews;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="SystemNewsManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SystemNewsManager.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public interface SystemNewsManager {
	
	public void setSystemNewsDAO(SystemNewsDAO systemNewsDAO);
	
	public SystemNews getSystemNews(String id);
	
	public Result getSystemNews(int kind, int start, int limit);
	
	public Result getSystemImageNews(int start, int limit);
	
	public Result getSystemTextNews(int start, int limit);
	
	public Result getSystemNews(int kind, int start, int limit, int image);
	
	public Result getSystemNews(int kind, String pageKind, int start, int limit);
	
	public Result getSystemNews(int start, int limit);
	
	public SystemNews getLastImportantNews();
	
	public SystemNews getLastAdvertisementNews();
	
	public List getLastImportantNews(int amount);
	
	public SystemNews getLastTagNews();
	
	public List getLastTagNews(int amount);
	
	public SystemNews getLastIndexImage();
	
	public List getLastIndexImage(int amount);
	
	public SystemNews getLastActivityImage();
	
	public List getLastActivityImage(int amount);
	
	public void saveSystemNews(SystemNews systemNews);
	
	public void removeSystemNews(SystemNews systemNews);
}
