/*
 * @(#)AbuseDAO.java Nov 29, 2009
 * 
 * Copyright 2009 Mingda. All rights reserved.
 */
package com.painiu.core.dao;

import java.util.Date;

import com.painiu.core.model.Abuse;
import com.painiu.core.model.Abuse.State;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="AbuseDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: AbuseDAO.java 7 2010-05-11 16:23:49Z zhangsf $
 */
public interface AbuseDAO extends DAO {
	
	public Abuse getAbuse(String id);
    
    public Result findAbuseList(int kind, State state, int start, int limit);
    
    public Result findAbuseResult(int kind, State state, Date startDate, Date endDate, int start, int limit);
    
    public void save(Abuse abuse);
    
    public void removeAbuse(Abuse abuse);
}
