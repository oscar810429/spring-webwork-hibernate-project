/*
 * @(#)UserStatDAOHibernate.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao.hibernate;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.painiu.core.dao.UserStatDAO;
import com.painiu.core.model.User;
import com.painiu.core.model.UserStat;

/**
 * <p>
 * <a href="UserStatDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: UserStatDAOHibernate.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class UserStatDAOHibernate extends BaseDAOHibernate implements UserStatDAO {

	/*
	 * @see com.painiu.core.dao.UserStatDAO#getUserStat(com.painiu.core.model.User)
	 */
	public UserStat getUserStat(User user) {
		return getUserStat(user.getId());
	}
	
	/*
	 * @see com.painiu.core.dao.UserStatDAO#getUserStat(java.lang.String)
	 */
	public UserStat getUserStat(String userId) {
		UserStat stat = (UserStat) getHibernateTemplate().get(UserStat.class, userId);

        if (stat == null) {
            log.warn("uh oh, UserStat '" + userId + "' not found...");
            throw new ObjectRetrievalFailureException(UserStat.class, userId);
        }

        return stat;
	}

	/*
	 * @see com.painiu.core.dao.UserStatDAO#saveUserStat(com.painiu.core.model.UserStat)
	 */
	public void saveUserStat(UserStat stat) {
		getHibernateTemplate().saveOrUpdate(stat);
	}

}
