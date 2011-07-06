/*
 * @(#)SystemMessageDAO.java  2009-11-27
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao;

import java.util.Date;
import java.util.List;

import com.painiu.core.model.SystemMessage;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="SystemMessageDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SystemMessageDAO.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public interface SystemMessageDAO extends DAO {

	public SystemMessage getSystemMessage(String id);
	
	public List getSystemMessages(Date afterDate);
	
	public Result getSystemMessages(int start, int limit);
	
	public void saveSystemMessage(SystemMessage systemMessage);
	
	public void removeSystemMessage(SystemMessage systemMessage);
}
