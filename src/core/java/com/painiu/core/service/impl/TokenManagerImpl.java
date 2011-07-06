/*
 * @(#)TokenManagerImpl.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.painiu.core.aop.transaction.NonTransactional;
import com.painiu.core.dao.TokenDAO;
import com.painiu.core.service.TokenManager;
import com.painiu.core.model.Token;
import com.painiu.core.model.User;
import com.painiu.util.RandomGUID;

/**
 * <p>
 * <a href="TokenManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: TokenManagerImpl.java 8 2010-05-11 16:48:01Z zhangsf $
 */
@Transactional
public class TokenManagerImpl implements TokenManager {

	private TokenDAO tokenDAO;
	
	/*
	 * @see com.painiu.core.service.TokenManager#setTokenDAO(com.painiu.core.dao.TokenDAO)
	 */
	@NonTransactional
	public void setTokenDAO(TokenDAO tokenDAO) {
		this.tokenDAO = tokenDAO;
	}
	
	/*
	 * @see com.painiu.core.service.TokenManager#getToken(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public Token getToken(String id) {
		return tokenDAO.getToken(id);
	}

	/*
	 * @see com.painiu.core.service.TokenManager#getTokens(com.painiu.core.model.User)
	 */
	@Transactional(readOnly=true)
	public List getTokens(User user) {
		return tokenDAO.getTokens(user);
	}
	
	/*
	 * @see com.painiu.core.service.TokenManager#removeToken(com.painiu.core.model.Token)
	 */
	public void removeToken(Token token) {
		tokenDAO.removeToken(token);
	}

	/*
	 * @see com.painiu.core.service.TokenManager#createToken(com.painiu.core.model.User)
	 */
	public Token createToken(User user, long expire) {
		List tokens = tokenDAO.getTokens(user);
		
		for (Iterator i = tokens.iterator(); i.hasNext();) {
			Token token = (Token) i.next();
			if (token.expired()) {
				i.remove();
				tokenDAO.removeToken(token);
			}
		}
		
		if (tokens.size() > 10) {
			Collections.sort(tokens, new Comparator() {
				public int compare(Object o1, Object o2) {
					return ((Token) o1).getExpireDate().compareTo(((Token) o2).getExpireDate());
				}
			});
			for (int i = 0; i < (tokens.size() - 10); i++) {
				tokenDAO.removeToken((Token) tokens.get(i));
			}
		}
		
		Token token = new Token();
		token.setId(RandomGUID.generate());
		token.setUser(user);
		token.setCreatedDate(new Date());
		token.setExpireDate(new Date(System.currentTimeMillis() + expire));
    
    	tokenDAO.saveToken(token);
    	
    	return token;
	}
}
