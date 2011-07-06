/*
 * @(#)SystemMessageManager.java  2009-11-27
 * 
 * Copyright 2008 Mingda. All rights reserved.
 */
package com.painiu.core.service;

import java.util.Date;
import java.util.List;

import com.painiu.core.dao.SystemMessageDAO;
import com.painiu.core.model.SystemMessage;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="SystemMessageManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SystemMessageManager.java 7 2010-05-11 16:23:49Z zhangsf $
 */
public interface SystemMessageManager {
	
	public void setSystemMessageDAO(SystemMessageDAO systemMessageDAO);
	
	public SystemMessage getSystemMessage(String id);
	
	public List getSystemMessages(Date afterDate);
	
	public Result getSystemMessages(int start, int limit);
	
	public void saveSystemMessage(SystemMessage systemMessage);
	
	public void removeSystemMessage(SystemMessage systemMessage);
	
}
