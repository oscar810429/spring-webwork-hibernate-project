/*
 * @(#)UserSecurityAspect.aj 2010-03-15
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.aop.security;

import  com.painiu.core.aop.BaseAspect;
import  com.painiu.core.service.UserManager;
import  com.painiu.core.model.User;
import  com.painiu.core.model.UserProfile;
import  com.painiu.core.model.User.UserRank;
//import  com.painiu.core.model.User.UserType;
import  com.painiu.core.security.advices.UserSecurity;

/**
 * <p>
 * <a href="UserSecurityAspect.aj.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: UserSecurityAspect.aj 75 2010-06-21 02:08:45Z zhangsf $
 */
public aspect UserSecurityAspect extends BaseAspect {

	private UserSecurity security = UserSecurity.getSecurity();
	
	private pointcut editUserRole():
		execution(* User.setRoles(..)) || execution(* User.addRole(..)) || execution(* User.removeRole(..));
	
	before(): editUserRole() {
		security.editUserRole((User) thisJoinPoint.getTarget());
	}
	
	before(User user): execution(* UserManager.saveUser(..)) && args(user) {
    	     security.saveUser(user);
    }
	
	before(User user, String blast) :
		execution(* UserManager.saveBlast(..)) && args(user, blast) {
		security.saveUser(user);
	}
    
    before(): execution(* UserManager.removeUser(String)) {
    	    security.removeUser();
    }
    
    before(): execution(* UserManager.setUserScore(User, int)) {
    	    security.setUserScore();
    }
    
    before(User user, UserProfile profile) : 
    	execution(* UserManager.saveCity(..)) && args(user, profile)
    	|| execution(* UserManager.saveInterests(..)) && args(user, profile)  {
    	security.saveUser(user);
    }
    
    before(): execution(* UserManager.setUserRank(User, UserRank)) {
//    	security.setUserRank();
    }
    
    before(): execution(* UserManager.setUserType(User, UserType)) {
    	    security.setUserType();
    }
    
    before(): execution(* UserManager.getUserLoginIPs(User, int, int)) || execution(* UserManager.getUserLoginIP(String)){
    	    security.getOrSetUserLoginIP();
    }
    
    //before(): execution(* UserManager.mergeUser(User, User)) {
    //	security.mergeUser();
    //}
}
