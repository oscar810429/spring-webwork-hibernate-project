/*
 * @(#)UserManagerImpl.java  2009-12-03
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.painiu.core.aop.transaction.NonTransactional;
import com.painiu.core.dao.UserDAO;
import com.painiu.core.dao.UserLoginIPDAO;
import com.painiu.core.dao.UserStatDAO;
import com.painiu.event.Event;
import com.painiu.event.EventListener;
import com.painiu.event.EventPublisher;
import com.painiu.event.EventPublisherAware;
//import com.painiu.event.software.BeforeRemoveSoftwareEvent;
import com.painiu.core.exception.EmailExistsException;
import com.painiu.core.exception.NicknameExistsException;
import com.painiu.core.exception.UserExistsException;
import com.painiu.core.exception.UsernameExistsException;
import com.painiu.core.model.Icon;
import com.painiu.core.model.User;
import com.painiu.core.model.UserCookie;
import com.painiu.core.model.UserProfile;
import com.painiu.core.model.UserStat;
import com.painiu.core.model.UserLoginIP;
import com.painiu.core.search.Result;
import com.painiu.core.service.UserManager;
import com.painiu.util.RandomGUID;

/**
 * Implementation of UserManager interface.
 * </p>
 * 
 * <p>
 * <a href="UserManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Zhang Songfu
 * @version $Id: UserManagerImpl.java 133 2010-11-23 08:14:43Z zhangsf $
 */
@Transactional
public class UserManagerImpl extends BaseManager implements UserManager, EventPublisherAware,EventListener, InitializingBean {


	private UserDAO dao;

	private UserStatDAO statDAO;
	
	private UserLoginIPDAO userLoginIPDAO;

	private EventPublisher eventPublisher;

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.core.event.EventPublisherAware#setEventPublisher(com.painiu.core.event.EventPublisher)
	 */
	@NonTransactional
	public void setEventPublisher(EventPublisher publisher) {
		this.eventPublisher = publisher;
	}

	/**
	 * Set the DAO for communication with the data layer.
	 * 
	 * @param dao
	 */
	@NonTransactional
	public void setUserDAO(UserDAO dao) {
		this.dao = dao;
	}

	/*
	 * @see com.painiu.core.logic.UserManager#setUserStatDAO(com.painiu.core.dao.UserStatDAO)
	 */
	@NonTransactional
	public void setUserStatDAO(UserStatDAO statDAO) {
		this.statDAO = statDAO;
	}
	
	@NonTransactional
	public void setUserLoginIPDAO(UserLoginIPDAO userLoginIPDAO) {
		this.userLoginIPDAO = userLoginIPDAO;
	}
	
	/*
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@NonTransactional
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(dao);
		Assert.notNull(statDAO);
		Assert.notNull(userLoginIPDAO);
    } 
	
	
	 @NonTransactional
	 public void onEvent(Event event){
		//if(event instanceof BeforeRemoveSoftwareEvent){
			
		//}
	 }

	/* 
	 * @see com.painiu.core.service.UserManager#getUser(java.lang.String)
	 */
	
	@Transactional(readOnly = true)
	public User getUser(String userId) {
		return dao.getUser(userId);
	}

	/*
	 * @see com.painiu.core.logic.UserManager#getUserByUsername(java.lang.String)
	 */
	@Transactional(readOnly = true)
	public User getUserByUsername(String username) {
		return dao.getUserByUsername(username);
	}

	@Transactional(readOnly = true)
	public User getUserByNickname(String nickname) {
		return dao.getUserByNickname(nickname);
	}

	/*
	 * @see com.painiu.core.logic.UserManager#getUserByEmail(java.lang.String)
	 */
	@Transactional(readOnly = true)
	public User getUserByEmail(String email) {
		return dao.getUserByEmail(email);
	}
	
	/*
	 * @see com.painiu.core.logic.UserManager#findUsers(java.lang.String, int, int)
	 */
	@Transactional(readOnly = true)
	public Result findUsers(String keyword, int start, int limit) {
		return dao.findUsers(keyword, start, limit);
	}

	/*
	 * @see com.painiu.core.logic.UserManager#getActiveUsers(java.util.Date,
	 *      java.util.Date, int, int)
	 */
	@Transactional(readOnly = true)
	public Result getActiveUsers(Date afterDate, Date beforeDate, int start,
			int limit, List userRanks) {
		return dao.findActiveUsers(afterDate, beforeDate, start, limit,
				userRanks);
	}

	/**
	 * @see com.painiu.core.service.UserManager#saveUser(com.painiu.core.model.User)
	 */
	public void saveUser(User user) throws UsernameExistsException,
			EmailExistsException, UserExistsException {
		try {
			dao.saveUser(user);
		} catch (DataIntegrityViolationException e) {
			((HibernateDaoSupport) dao).getHibernateTemplate().clear();

			try {
				dao.getUserByUsername(user.getUsername());
				throw new UsernameExistsException(user.getUsername());
			} catch (ObjectRetrievalFailureException ex) {
			}

			try {
				dao.getUserByEmail(user.getEmail());
				throw new EmailExistsException(user.getEmail());
			} catch (ObjectRetrievalFailureException ex) {
			}
			
			try {
				dao.getUserByNickname(user.getNickname());
				throw new NicknameExistsException(user.getNickname());
			} catch (ObjectRetrievalFailureException ex) {}

			throw new UserExistsException("User '" + user.getUsername()
					+ "' already exists!");
		}
	}

	/*
	 * @see com.painiu.core.logic.UserManager#saveBlast(com.painiu.core.model.User,
	 *      java.lang.String)
	 */
	public void saveBlast(User user, String blast) {
		user.setBlast(blast);
		dao.saveUser(user);
	}

	/*
	 * @see com.painiu.core.logic.UserManager#saveBuddyIcon(com.painiu.core.model.User,
	 *      com.painiu.core.model.Icon)
	 */
	public void saveBuddyIcon(User user, Icon icon) {
		user.setBuddyIcon(icon);
		dao.saveUser(user);
	}

	/*
	 * @see com.painiu.core.logic.UserManager#saveCity(com.painiu.core.model.User,
	 *      java.lang.String)
	 */
	public void saveCity(User user, UserProfile profile) {
		UserProfile old = user.getProfile();
//		String oldLocation = "";
//
//		if (StringUtils.isNotEmpty(old.getCity())) {
//			oldLocation = old.getCity();
//		} else if (StringUtils.isNotEmpty(old.getProvince())
//				&& StringUtils.isEmpty(old.getCity())) {
//			oldLocation = old.getProvince();
//		} else if (StringUtils.isNotEmpty(old.getCountry())
//				&& StringUtils.isEmpty(old.getProvince())) {
//			oldLocation = old.getCountry();
//		}
//
//		if (StringUtils.isNotEmpty(oldLocation)) {
//			
//				City city = null;
//				try {
//					city = groupDAO.getCityGroupByName(oldLocation);
//				}catch(Exception e) {
//					if (log.isDebugEnabled()) {
//						log.debug(e);
//					}
//					//e.printStackTrace();
//				}
//				
//				if (city != null) {
//					
////					GroupUser gu = groupUserDAO.getGroupUser(new Id(group.getId(),user.getId()));
//					
//					GroupUser groupUser = new GroupUser(city, user);
//					groupUserDAO.removeGroupUser(groupUser);
//				}
//			
//		}

		old.setCountry(profile.getCountry());
		old.setProvince(profile.getProvince());
		old.setCity(profile.getCity());

//		String network = "";
//		if (StringUtils.isNotEmpty(profile.getCity())) {
//			network = profile.getCity();
//		} else if (StringUtils.isNotEmpty(profile.getProvince())
//				&& StringUtils.isEmpty(profile.getCity())) {
//			network = profile.getProvince();
//		} else if (StringUtils.isNotEmpty(profile.getCountry())
//				&& StringUtils.isEmpty(profile.getProvince())) {
//			network = profile.getCountry();
//		}
//
//		if (StringUtils.isNotEmpty(network)) {
//			
//			
//			City city = null;
//			
//			try {
//				city = groupDAO.getCityGroupByName(network);
//			}catch(Exception e) {
//				if (log.isDebugEnabled()) {
//					log.debug(e);
//				}
//			}
//			
//			if (city != null) {
//				
//				GroupUser groupUser = new GroupUser(city, user, Yupoo.GROUP_MEMBER);
//				groupUserDAO.saveGroupUser(groupUser);
//			}
//			
//		}

		user.setProfile(old);
		dao.saveUser(user);
	}

	/*
	 * @see com.painiu.core.logic.UserManager#saveInterests(com.painiu.core.model.User,
	 *      com.painiu.core.model.UserProfile)
	 */
	public void saveInterests(User user, UserProfile profile) {
		user.setProfile(profile);
		dao.saveUser(user);
	}

	/**
	 * @see com.painiu.core.service.UserManager#removeUser(java.lang.String)
	 */
	public void removeUser(String id) {
		if (log.isDebugEnabled()) {
			log.debug("removing user: " + id);
		}

		dao.removeUser(id);
	}

	/**
	 * @see com.painiu.core.service.UserManager#checkLoginCookie(java.lang.String)
	 */
	public String checkLoginCookie(String value) {
		value = new String(Base64.decodeBase64(value.getBytes()));
		// value = com.painiu.core.util.StringUtils.decodeString(value);

		String[] values = StringUtils.split(value, "|");

		// in case of empty userId in cookie, return null
		if (values.length < 2) {
			return null;
		}

		if (log.isDebugEnabled()) {
			log.debug("looking up cookieId: " + values[1]);
		}

		UserCookie cookie = new UserCookie();
		cookie.setUserId(values[0]);
		cookie.setCookieId(values[1]);
		cookie = dao.getUserCookie(cookie);

		if (cookie != null) {
			if (log.isDebugEnabled()) {
				log.debug("cookieId lookup succeeded, generating new cookieId");
			}

			dao.removeUserCookies(values[0]);

			return createLoginCookie(values[0]);
		}

		if (log.isDebugEnabled()) {
			log.debug("cookieId lookup failed, returning null");
		}

		return null;
	}

	/**
	 * @see com.painiu.core.service.UserManager#createLoginCookie(java.lang.String)
	 */
	public String createLoginCookie(String userId) {
		UserCookie cookie = new UserCookie();
		cookie.setUserId(userId);

		return saveLoginCookie(cookie);
	}

	/**
	 * Convenience method to set a unique cookie id and save to database
	 * 
	 * @param cookie
	 * @return
	 */
	private String saveLoginCookie(UserCookie cookie) {
		cookie.setCookieId(new RandomGUID().toString());
		dao.saveUserCookie(cookie);

		String loginCookie = cookie.getUserId() + "|" + cookie.getCookieId();

		return new String(Base64.encodeBase64(loginCookie.getBytes()));
	}

	/**
	 * @see com.painiu.core.service.UserManager#removeLoginCookies(java.lang.String)
	 */	

	public void removeLoginCookies(String userId) {
		dao.removeUserCookies(userId);
	}

	/*
	 * @see com.painiu.core.logic.UserManager#getUserStat(com.painiu.core.model.User)
	 */
	
	@Transactional(readOnly = true)
	public UserStat getUserStat(User user) {
		return statDAO.getUserStat(user);
	}

	/*
	 * @see com.painiu.core.logic.UserManager#saveUserStat(com.painiu.core.model.UserStat)
	 */
	public void saveUserStat(UserStat stat) {
		statDAO.saveUserStat(stat);
	}

	/*
	 * @see com.painiu.core.logic.UserManager#updateUserScore(com.painiu.core.model.User,
	 *      int)
	 */
	public void setUserScore(User user, int score) {
		UserStat stat = user.getStat();
		stat.setScore(new Integer(score));
		statDAO.saveUserStat(stat);
	}

	/*
	 * @see com.painiu.core.logic.UserManager#updateUserPassword(com.painiu.core.model.User,
	 *      java.lang.String)
	 */
	public void setUserPassword(User user, String password) {
		user.setPassword(password);
		dao.saveUser(user);
	}

	// public void setUserOpenID(User user, String openid) {
	// user.setOpenid(openid);
	// dao.saveUser(user);
	// }
	//	
	// public void setUserOpenIDPass(User user, String password) {
	// user.setOpenidPass(password);
	// dao.saveUser(user);
	// }

	/*
	 * @see com.painiu.core.logic.UserManager#setUserRoles(com.painiu.core.model.User,
	 *      java.util.Set)
	 */
	public void setUserRoles(User user, Set roles) {
		user.setRoles(roles);

		dao.saveUser(user);

		publishUpdateRolesUserEvent(user);
	}

	/*
	 * @see com.painiu.core.logic.UserManager#getRecommendUsers(java.util.Date,
	 *      java.util.Date, int, int)
	 */
	@Transactional(readOnly = true)
	public Result getRecommendUsers(Date fromDate, Date toDate, int start,
			int limit) {
		return dao.getRecommendUsers(fromDate, toDate, start, limit);
	}

	@Transactional(readOnly = true)
	public Result getRecommendUsers(Date fromDate, Date toDate, String orderby,
			int start, int limit) {
		return dao.getRecommendUsers(fromDate, toDate, orderby, start, limit);
	}

	/*
	 * @see com.painiu.core.logic.UserManager#getRecommendUsers(java.util.Date,
	 *      java.util.Date, int)
	 */
	@Transactional(readOnly = true)
	public List getRecommendUsers(Date fromDate, Date toDate, int limit) {
		return dao.getRecommendUsers(fromDate, toDate, limit);
	}

	/*
	 * @see com.painiu.core.logic.UserManager#setUserType(com.painiu.core.model.User,
	 *      com.painiu.core.model.User.UserType)
	 */
	//public void setUserType(User user, UserType userType) {
	//	user.setUserType(userType);

	//	dao.saveUser(user);
	//}

	/***************************************************************************
	 * Events
	 **************************************************************************/

	private void publishUpdateRolesUserEvent(User user) {
		//eventPublisher.publishEvent(new UpdateRolesUserEvent(user));
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.core.logic.UserManager#getUsersByCity(java.lang.String, int,
	 *      int)
	 */
	@Transactional(readOnly = true)
	public Result getUsersByCity(String city, int start, int limit) {
		return dao.getUserByAddress("city", city, start, limit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.core.logic.UserManager#getUsersByProvince(java.lang.String,
	 *      int, int)
	 */
	@Transactional(readOnly = true)
	public Result getUsersByProvince(String province, int start, int limit) {
		return dao.getUserByAddress("province", province, start, limit);
	}
	
	
	/*
	 * @see com.painiu.core.service.UserManager#getUserLoginIP(java.lang.String)
	 */
	@Transactional(readOnly = true)
	public UserLoginIP getUserLoginIP(String id) {
		return userLoginIPDAO.getUserLoginIP(id);
	}

	/*
	 * @see com.painiu.core.service.UserManager#getUserLoginIPs(com.yupoo.model.User)
	 */
	@Transactional(readOnly = true)
	public Result getUserLoginIPs(User user, Date fromDate, Date toDate,
			int start, int limit) {
		return userLoginIPDAO.getUserLoginIPs(user, fromDate, toDate, start,
				limit);
	}

	/*
	 * @see com.painiu.core.service.UserManager#saveUserLoginIP(com.yupoo.model.UserLoginIP)
	 */
	public void saveUserLoginIP(UserLoginIP userLoginIP) {
		userLoginIPDAO.saveUserLoginIP(userLoginIP);
	}

	

}
