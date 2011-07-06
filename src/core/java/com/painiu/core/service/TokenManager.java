/*
 * @(#)TokenManager.java  2009-7-10
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service;

import java.util.List;

import com.painiu.core.dao.TokenDAO;
import com.painiu.core.model.Token;
import com.painiu.core.model.User;

/**
 * <p>
 * <a href="TokenManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zola Zhou
 * @version $Id: TokenManager.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public interface TokenManager {
	
	public void setTokenDAO(TokenDAO tokenDAO);
	
	public Token getToken(String id);
	
	public List getTokens(User user);
	
//	public void saveToken(Token token);
	
	public void removeToken(Token token);
	
	public Token createToken(User user, long expire);
}
