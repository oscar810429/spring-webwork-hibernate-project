/*
 * @(#)UserStatDAOHibernate.java  2009-12-03
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao.hibernate;

import java.util.Calendar;
import java.util.Date;

import org.hibernate.Hibernate;
import org.hibernate.type.Type;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.painiu.core.dao.UserLoginIPDAO;
import com.painiu.core.model.User;
import com.painiu.core.model.UserLoginIP;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="UserStatDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: UserLoginIPDAOHibernate.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class UserLoginIPDAOHibernate extends BaseDAOHibernate implements UserLoginIPDAO {

	/*
	 * @see com.painiu.core.dao.UserStatDAO#getUserStat(com.painiu.core.model.User)
	 */
	public Result getUserLoginIPs(User user, Date fromDate, Date toDate, int start, int limit) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(toDate);
		calendar.add(Calendar.DATE, 1);
		toDate = calendar.getTime();
		String countSql = "select count(loginIP.id) from UserLoginIP loginIP where loginIP.user = ? and loginIP.loginDate >= ? and loginIP.loginDate <= ? ";
        String sql = "from UserLoginIP loginIP join fetch loginIP.user where loginIP.user = ? and loginIP.loginDate >= ? and loginIP.loginDate <= ?";
        return find(countSql, sql,
        		new Object[]{user, fromDate, toDate },
        		new Type[] { Hibernate.entity(User.class), Hibernate.TIMESTAMP, Hibernate.TIMESTAMP},
    			start, limit);
	}
	
	/*
	 * @see com.painiu.core.dao.UserStatDAO#getUserStat(java.lang.String)
	 */
	public UserLoginIP getUserLoginIP(String id) {
		UserLoginIP userLoginIP = (UserLoginIP) getHibernateTemplate().get(UserLoginIP.class, id);

        if (userLoginIP == null) {
            log.warn("uh oh, UserLoginIP '" + id + "' not found...");
            throw new ObjectRetrievalFailureException(UserLoginIP.class, id);
        }

        return userLoginIP;
	}

	/*
	 * @see com.painiu.core.dao.UserStatDAO#saveUserStat(com.painiu.core.model.UserStat)
	 */
	public void saveUserLoginIP(UserLoginIP userLoginIP) {
		getHibernateTemplate().saveOrUpdate(userLoginIP);
	}

}
