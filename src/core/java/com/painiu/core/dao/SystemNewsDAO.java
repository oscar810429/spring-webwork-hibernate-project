/*
 * @(#)SystemNewsDAO.java  2009-11-27
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao;
import com.painiu.core.model.SystemNews;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="SystemNewsDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SystemNewsDAO.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public interface SystemNewsDAO extends DAO {
	
	public SystemNews getSystemNews(String id);
	
	public Result getSystemNews(int kind, int start, int limit);
	
	public Result getSystemTextNews(int start, int limit); //get the news list;
	
	public Result getSystemImageNews(int start, int limit); //get the image list;
	
	public Result getSystemNews(int kind, String pageKind, int start, int limit);
	
	public Result getSystemImage(int kind,int start,int limit);
	
	public Result getSystemNews(int kind, int start, int limit, int image);
	
	public Result getSystemNews(int start, int limit);
	
	public void saveSystemNews(SystemNews systemNews);
	
	public void removeSystemNews(SystemNews systemNews);
	
}
