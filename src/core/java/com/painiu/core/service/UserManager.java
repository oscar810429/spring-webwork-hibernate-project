/*
 * @(#)UserManager.java  2009-12-03
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */

package com.painiu.core.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.painiu.core.dao.UserDAO;
import com.painiu.core.dao.UserLoginIPDAO;
import com.painiu.core.dao.UserStatDAO;
import com.painiu.core.exception.UserExistsException;
import com.painiu.core.model.Icon;
import com.painiu.core.model.User;
import com.painiu.core.model.UserLoginIP;
import com.painiu.core.model.UserProfile;
import com.painiu.core.model.UserStat;
import com.painiu.core.search.Result;


/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 * <p><a href="UserManager.java.html"><i>View Source</i></a></p>
 *
 * @author Zhang Songfu
 * @version $Id: UserManager.java 8 2010-05-11 16:48:01Z zhangsf $ 
 */

public interface UserManager {
	
    //~ Methods ================================================================
	
    /*UserDao setter*/
	
    public void setUserDAO(UserDAO dao);
    
    public void setUserStatDAO(UserStatDAO statDAO);
    
    public void setUserLoginIPDAO(UserLoginIPDAO userLoginIPDAO);
    
    /*User general*/
    
    public User getUser(String userId);
    
    public User getUserByUsername(String username);
    
    public User getUserByNickname(String nickname);
    
    public User getUserByEmail(String email);
    
    public Result findUsers(String keyword, int start, int limit);
    
    public Result getActiveUsers(Date afterDate, Date beforeDate,int start, int limit, List userRanks);
    //public Result findUploadUsers(Date afterDate, Date beforeDate,int start, int limit, List userRanks);

    public void saveUser(User user) throws UserExistsException;

    public void saveBlast(User user, String blast);
    
    public void saveBuddyIcon(User user, Icon icon);
    
    public void saveInterests(User user, UserProfile profile);
    
    public void saveCity(User user, UserProfile profile);

    public void removeUser(String userId);
    
    public String checkLoginCookie(String value);
 
    public String createLoginCookie(String userId);
    
    public void removeLoginCookies(String userId);
    
    public UserStat getUserStat(User user);
    
    public void saveUserStat(UserStat stat);
    
    public void setUserScore(User user, int score);

    public void setUserPassword(User user, String password);
    // public void setUserOpenID(User user, String openid);
    // public void setUserOpenIDPass(User user, String password);
    public void setUserRoles(User user, Set roles);
    
    
    public Result getRecommendUsers(Date fromDate, Date toDate, int start, int limit);
    
    public Result getRecommendUsers(Date fromDate, Date toDate, String orderby, int start, int limit);
	
	public List getRecommendUsers(Date fromDate, Date toDate, int limit);
	
	//public List getRandRecommendUsers(int limit);
    
    //public UserPreference getUserPreference(String userId);
    
    //public void saveUserPreference(UserPreference userPreference);
    
    //public UserEmailConfig getUserEmailConfig(User user);
    
    //public UserEmailConfig getUserEmailConfig(String email);
    
   //public void saveUserEmailConfig(UserEmailConfig config); 
    
    public Result getUsersByCity(String city, int start, int limit); 
    
    public Result getUsersByProvince(String province, int start, int limit); 
    
    //public void setUserRank(User user, UserRank userRank);
    
    //public void setUserType(User user, UserType userType);
    
    //public void saveUserSearchPreference(UserPreference userPreference);
    
    //public List getFriendBirthdayUsers(User user, Date date);
    
    public void saveUserLoginIP(UserLoginIP userLoginIP);
    
    public Result getUserLoginIPs(User user, Date fromDate, Date toDate, int start, int limit);
    
    public UserLoginIP getUserLoginIP(String id);
}
