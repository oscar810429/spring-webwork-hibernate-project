/*
 * @(#)TokenDAO.java  2009-11-28
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao;

import java.util.List;

import com.painiu.core.model.Token;
import com.painiu.core.model.User;

/**
 * <p>
 * <a href="TokenDAO.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: TokenDAO.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public interface TokenDAO extends DAO {
	
	public Token getToken(String id);
	
	public List getTokens(User user);
	
	public void saveToken(Token token);
	
	public void removeToken(Token token);
}
