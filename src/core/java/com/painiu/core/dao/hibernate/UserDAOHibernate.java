package com.painiu.core.dao.hibernate;

import java.sql.SQLException;
//import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.painiu.Painiu;
import com.painiu.core.dao.UserDAO;
import com.painiu.core.model.User;
import com.painiu.core.model.UserCookie;
import com.painiu.core.model.User.UserRank;
import com.painiu.core.persistence.UserTypes;
import com.painiu.core.search.Result;
import com.painiu.core.security.UserDetail;

/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve User objects.
 *
 * <p>
 * <a href="UserDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:zolazhou@msn.com">Zhang Songfu</a>
 */
public class UserDAOHibernate extends BaseDAOHibernate implements UserDAO, UserDetailsService {
    
    /*
     * @see com.painiu.core.dao.UserDAO#getUser(java.lang.String)
     */
    public User getUser(String userId) {
        User user = (User) getHibernateTemplate().get(User.class, userId);

        if (user == null) {
            log.warn("uh oh, user '" + userId + "' not found...");
            throw new ObjectRetrievalFailureException(User.class, userId);
        }

        return user;
    }
    
    /**
     * @see com.painiu.core.dao.UserDAO#getUserByUsername(java.lang.String)
     */
    public User getUserByUsername(String username) {
    	List list = getHibernateTemplate().find("from User u where u.username = ?", username);
        if (list != null & list.size() > 0) {
            return (User) list.get(0);
        }
        if (log.isWarnEnabled()) {
            log.warn("can not find user[username:" + username + "]");
        }
        throw new ObjectRetrievalFailureException(User.class, username);
    }
    
    public User getUserByNickname(String nickname) {
    	List list = getHibernateTemplate().find("from User u where u.nickname = ?", nickname);
        if (list != null & list.size() > 0) {
            return (User) list.get(0);
        }
        if (log.isWarnEnabled()) {
            log.warn("can not find user[username:" + nickname + "]");
        }
        throw new ObjectRetrievalFailureException(User.class, nickname);
    }
    
    /**
     * @see com.painiu.core.dao.UserDAO#getUserByEmail(java.lang.String)
     */
    public User getUserByEmail(String email) {
        List list = getHibernateTemplate().find("from User u where u.email = ?", email);
        if (list != null & list.size() > 0) {
            return (User) list.get(0);
        }
        if (log.isWarnEnabled()) {
            log.warn("can not find user[email:" + email + "]");
        }
        throw new ObjectRetrievalFailureException(User.class, email);    
    }
    
//    public User getUserByOpenID(String openid) {
//        List list = getHibernateTemplate().find("from User u where u.openid = ?", openid);
//        if (list != null & list.size() > 0) {
//            return (User) list.get(0);
//        }
//        if (log.isWarnEnabled()) {
//            log.warn("can not find user[email:" + openid + "]");
//        }
//        throw new ObjectRetrievalFailureException(User.class, openid);    
//    }
    
    /*
	 * @see com.painiu.core.dao.UserDAO#findUsers(java.lang.String, int, int)
	 */
	public Result findUsers(final String keyword, int start, int limit) {
		return findByCriteria(new CriteriaBuilder() {
			public Criteria buildCountCriteria(Session session) {
				return buildCriteria(session).setProjection(Projections.rowCount());
			}

			public Criteria buildCriteria(Session session) {
				Criteria criteria = session.createCriteria(User.class);
				
				Disjunction disj = Restrictions.disjunction();
				disj.add( Restrictions.like("username", keyword, MatchMode.START) );
				disj.add( Restrictions.like("nickname", keyword, MatchMode.START) );
//				disj.add( Restrictions.like("email", keyword, MatchMode.ANYWHERE) );
				criteria.add( disj );
				
				criteria.addOrder(Order.desc("createdDate"));
				
				return criteria;
			}
		}, start, limit);
	}

	/*
	 * @see com.painiu.core.dao.UserDAO#getRecommendUsers(java.util.Date, java.util.Date, int, int)
	 */
	public Result getRecommendUsers(Date fromDate, Date toDate, int start, int limit) {
		return getRecommendUsers(fromDate, toDate, "stat.streamViews desc", start, limit);
	} 

	/*
	 * @see com.painiu.core.dao.UserDAO#getRecommendUsers(java.util.Date, java.util.Date, int)
	 */
	public List getRecommendUsers(Date fromDate, Date toDate, int limit) {
		return getRecommendUsers(fromDate, toDate, null, -1, limit).getData();
	}
	
	public Result getRecommendUsers(Date fromDate, Date toDate, String orderBy, int start, int limit) {
		return find(
				"select count(u.id) from User u join u.stat stat where " +
				"stat.score > 0 and u.createdDate >= ? and u.createdDate < ?",
				"from User u join fetch u.stat as stat " +
				"where stat.score > 0 and u.createdDate >= ? and u.createdDate < ? " +
				"order by " + (orderBy == null ? "rand()" : orderBy), 
				new Object[] { fromDate, toDate },
				new Type[] { Hibernate.TIMESTAMP, Hibernate.TIMESTAMP },
				start, limit);
	}
	
	public List getRandRecommendUsers(int limit) {
		return find(null,
				"from User u join fetch u.stat as stat " +
				"where stat.score > 0 and u.buddyIcon.fileKey is not null " +
				"order by rand()", 
				null,
				null,
				-1, limit).getData();
	}
	
	/**
     * @see com.painiu.core.dao.UserDAO#saveUser(com.painiu.core.model.User)
     */
    public void saveUser(final User user) {
        if (log.isDebugEnabled()) {
            log.debug("user's username: " + user.getUsername());
        }
        
        getHibernateTemplate().saveOrUpdate(user);
        // necessary to throw a DataIntegrityViolation and catch it in UserManager
        getHibernateTemplate().flush();
    }

    /**
     * @see com.painiu.core.dao.UserDAO#removeUser(java.lang.String)
     */
    public void removeUser(String id) {
        User user = getUser(id);
        
        //removeUserCookies(user.getUsername());
        getHibernateTemplate().delete(user);
    }

    /**
     * @see com.painiu.core.dao.UserDAO#getUserCookie(com.painiu.core.model.UserCookie)
     */
    public UserCookie getUserCookie(final UserCookie cookie) {
        List cookies = getHibernateTemplate().find(
                "from UserCookie c where c.userId =? and c.cookieId=?", 
                new Object[]{cookie.getUserId(), cookie.getCookieId()});

        if (cookies.size() == 0) {
            return null;
        }

        return (UserCookie) cookies.get(0);
    }

    /* (non-Javadoc)
     * @see com.painiu.core.dao.UserDAO#removeUserCookie(com.painiu.core.model.UserCookie)
     */
    public void removeUserCookie(UserCookie cookie) {
    	getHibernateTemplate().delete(cookie);
    }
    
    /**
     * @see com.painiu.core.dao.UserDAO#removeUserCookies(java.lang.String)
     */
    public void removeUserCookies(String userId) {
        // delete any cookies associated with this user
        List cookies = getHibernateTemplate().find(
                "from UserCookie c where c.userId=?", userId);

        if ((cookies.size() > 0) && log.isDebugEnabled()) {
            log.debug("deleting " + cookies.size() + " cookies for user '" +
                      userId + "'");
        }

        getHibernateTemplate().deleteAll(cookies);
    }

    /**
     * @see com.painiu.core.dao.UserDAO#saveUserCookie(com.painiu.core.model.UserCookie)
     */
    public void saveUserCookie(UserCookie cookie) {
        getHibernateTemplate().save(cookie);
    }
    
    /** 
     * @see org.acegisecurity.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
     public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    	 User user = (User) getHibernateTemplate().execute(new HibernateCallback() {
    		 public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			 User user = (User) session.get(User.class, username);
    			
    			 if (user != null) {
    				 user.getRoles().size();
    				 user.getStat().getPrevLoginDate();
    			 }
    			 return user; 
    		 }
    	 });

    	 if (user == null) {
    		 throw new UsernameNotFoundException("user '" + username + "' not found...");
    	 }
    	 
    	 return new UserDetail(user);
     }


	/* (non-Javadoc)
	 * @see com.painiu.core.dao.UserDAO#getUserByAddress(java.lang.String, java.lang.String)
	 */
	public Result getUserByAddress(String kind, String key, int start, int limit) {
		String countSql = "select count(u.id) from User u where u.profile." +  (kind.equals("city") ?"city" : "province") + " = ?";
        String sql = "from User u where u.profile." +  (kind.equals("city") ?"city" : "province") + " = ?";
        return find(countSql, sql,
        		new Object[]{key },new Type[] {Hibernate.STRING },
    			start, limit);
	}
	

	/*
	 * @see com.painiu.core.dao.UserDAO#findActiveUsers(java.util.Date, java.util.Date, int, int)
	 */
	public Result findActiveUsers(Date afterDate, Date beforeDate, int start, int limit, List userRanks) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(beforeDate);
		calendar.add(Calendar.DATE, 1);
		beforeDate = calendar.getTime();
		String userFilter = "";
		List objects = new ArrayList(10);
		objects.add(afterDate);
		objects.add(beforeDate);
		List types = new ArrayList(10);
		types.add(Hibernate.TIMESTAMP);
		types.add(Hibernate.TIMESTAMP);
		if (userRanks != null && userRanks.size() > 0) {
			Properties userRank = new Properties();
			userRank.put("enumClassname", UserRank.class);
			for (int i = 0; i < userRanks.size(); i++) {
				if (StringUtils.isEmpty(userFilter)) {
					userFilter = " and (u.userRank = ? ";
				} else {
					userFilter += " or u.userRank = ? ";
				}
				objects.add(userRanks.get(i));
				types.add(UserTypes.userRank());
			}
			userFilter += ") ";
		}
		Object[] srcTypes = types.toArray();
		Type[] desTypes = new Type[srcTypes.length];
		for (int i = 0; i < srcTypes.length; i++) {
			desTypes[i] = (Type)srcTypes[i];
		}
		return find(
				"select count(u.id) from User u where u.stat.lastLoginDate> ? and u.stat.lastLoginDate < ? " + userFilter,
				"from User u where u.stat.lastLoginDate> ? and u.stat.lastLoginDate < ? " + userFilter, 
				objects.toArray(),
				desTypes,
				start, limit);
	}

	/*
	 * @see com.painiu.core.dao.UserDAO#findUploadUsers(java.util.Date, java.util.Date, int, int)
	 */
	public Result findUploadUsers(Date afterDate, Date beforeDate, int start, int limit, List userRanks) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(beforeDate);
		calendar.add(Calendar.DATE, 1);
		beforeDate = calendar.getTime();
		String userFilter = "";
		List objects = new ArrayList(10);
		objects.add(afterDate);
		objects.add(beforeDate);
		List types = new ArrayList(10);
		types.add(Hibernate.TIMESTAMP);
		types.add(Hibernate.TIMESTAMP);
		if (userRanks != null && userRanks.size() > 0) {
			Properties userRank = new Properties();
			userRank.put("enumClassname", UserRank.class);
			for (int i = 0; i < userRanks.size(); i++) {
				if (StringUtils.isEmpty(userFilter)) {
					userFilter = " and (p.user.userRank = ? ";
				} else {
					userFilter += " or p.user.userRank = ? ";
				}
				objects.add(userRanks.get(i));
				types.add(UserTypes.userRank());
			}
			userFilter += ") ";
		}
		Object[] srcTypes = types.toArray();
		Type[] desTypes = new Type[srcTypes.length];
		for (int i = 0; i < srcTypes.length; i++) {
			desTypes[i] = (Type)srcTypes[i];
		}
		return find(
				"select count(distinct p.userId) from Photo p where p.postedDate > ? and p.postedDate < ? " + userFilter ,
				"select distinct(p.user) from Photo p where p.postedDate > ? and p.postedDate < ? " + userFilter,
				objects.toArray(),
				desTypes,
				start, limit);
	}

	/*
	 * @see com.painiu.core.dao.UserDAO#activate(com.painiu.core.model.User)
	 */
	public void activate(User user) {
		getHibernateTemplate().update(user);
	}

	/*
	 * @see com.painiu.core.dao.UserDAO#demotion(com.painiu.core.model.User)
	 */
	public void demotion(User user) {
		getHibernateTemplate().update(user);
		getHibernateTemplate().flush();
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.UserDAO#findCommercialUsers(java.lang.Boolean)
	 */
	public Result findCommercialUsers(Boolean isCommercial, int start, int limit) {
		String role = null;
		if (isCommercial) {
			role = "%"  + Painiu.VIP_BUSINESS_ROLE + "%" ;
		} else {
			role = "%"  + Painiu.VIP_NORMAL_ROLE + "%" ;
		}
		return find(
				"select count(u.id) from User u where u.roleNames like ? ",
				"from User u where u.roleNames like ? order by u.createdDate",
				new Object[]{role},
				new Type[]{Hibernate.STRING},
				start, limit);
	}


}
