/*
 * @(#)AbuseManager.java Apr 7, 2007
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service;

import java.util.Date;

import com.painiu.core.dao.AbuseDAO;
import com.painiu.core.model.Abuse;
import com.painiu.core.model.Abuse.State;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="AbuseManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author zhangsf
 * @version $Id: AbuseManager.java 68 2010-06-17 15:54:41Z zhangsf $
 */
public interface AbuseManager {
	
	public void setAbuseDAO(AbuseDAO abuseDAO);
	
	public Abuse getAbuse(String id);
    
    public Result findAbuseList(int kind, State state, int start, int limit);
    
    public Result findAbuseResult(int kind, State state, Date startDate, Date endDate, int start, int limit);
    
    public void save(Abuse abuse);
    
    public void removeAbuse(Abuse abuse);
}
