/*
 * @(#)TokenDAOHibernate.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.painiu.core.dao.TokenDAO;
import com.painiu.core.model.Token;
import com.painiu.core.model.User;

/**
 * <p>
 * <a href="TokenDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: TokenDAOHibernate.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class TokenDAOHibernate extends BaseDAOHibernate implements TokenDAO {

	private static final Log log = LogFactory.getLog(TokenDAOHibernate.class);
	
	/*
	 * @see com.painiu.core.dao.TokenDAO#getToken(java.lang.String)
	 */
	public Token getToken(String id) {
		Token token = (Token) getHibernateTemplate().get(Token.class, id);
		if (token == null) {
			log.warn("uh, oh, token[" + id + "] not found...");
        	throw new ObjectRetrievalFailureException(Token.class, id);
		}
		return token;
	}

	/*
	 * @see com.painiu.core.dao.TokenDAO#getTokens(com.painiu.core.model.User)
	 */
	public List getTokens(User user) {
		return getReadOnlyHibernateTemplate().find("from Token t where t.user = ? order by t.createdDate", user);
	}
	
	/*
	 * @see com.painiu.core.dao.TokenDAO#removeToken(com.painiu.core.model.Token)
	 */
	public void removeToken(Token token) {
		if (log.isDebugEnabled()) {
			log.debug("removing " + token);
		}
		getHibernateTemplate().delete(token);
	}

	/*
	 * @see com.painiu.core.dao.TokenDAO#saveToken(com.painiu.core.model.Token)
	 */
	public void saveToken(Token token) {
		if (log.isDebugEnabled()) {
			log.debug("saving" + token);
		}
		getHibernateTemplate().save(token);
	}

}
