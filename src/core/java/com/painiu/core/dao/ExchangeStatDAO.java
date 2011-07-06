/*
 * @(#)ExchangeStatDAO.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao;

import com.painiu.core.model.ExchangeStat;
import com.painiu.core.model.User;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="ExchangeStatDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ExchangeStatDAO.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public interface ExchangeStatDAO extends DAO {
	public void saveExchangeStat(ExchangeStat stat);
	public void deleteExchangeStat(ExchangeStat stat);
	public ExchangeStat getExchangeStat(String id);
	public ExchangeStat getExchangeStat(User user, String url);
	public Result getExchangeStats(User user, int start, int limit);
	public Result getExchangeStats(int start, int limit);
	public Integer getTotalViewCount();
	public Integer getTotalClickCount();
	public Integer getTotalLinkCount();
}
