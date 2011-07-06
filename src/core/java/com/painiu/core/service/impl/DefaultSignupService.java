/**
 * @(#)DefaultSignupService.java Nov 30, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.service.impl;

import java.util.Locale;

import org.acegisecurity.providers.dao.SaltSource;
import org.acegisecurity.providers.encoding.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.painiu.core.aop.transaction.NonTransactional;
import com.painiu.core.dao.RoleDAO;
import com.painiu.core.dao.UserDAO;
import com.painiu.core.exception.UserExistsException;
import com.painiu.core.model.User;
import com.painiu.core.model.UserPreference;
import com.painiu.core.model.UserProfile;
import com.painiu.core.model.UserStat;
import com.painiu.core.service.SignupService;
import com.painiu.Painiu;

/**
 * <p>
 * <a href="DefaultSignupService.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: DefaultSignupService.java 85 2010-07-01 19:37:02Z zhangsf $
 */
@Transactional
public class DefaultSignupService implements SignupService {

    //~ Static fields/initializers =============================================

	//~ Instance fields ========================================================
	
	private UserDAO userDAO;
	private RoleDAO roleDAO;
	
	private PasswordEncoder passwordEncoder;
	private SaltSource saltSource;

	//~ Constructors ===========================================================

	//~ Methods ================================================================
	
	
	/* (non-Javadoc)
	 * @see com.painiu.core.service.SignupService#signup(java.lang.String, java.lang.String, java.lang.String)
	 */
	public User signup(String username, String email, String password,UserProfile userProfile)
			throws UserExistsException {
          return signup(username, email, password,userProfile, true);
	}
	
	/*public User signup(String username, String email, String password,boolean localUser,
			boolean encodePassword) throws UserExistsException {

		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		
		if (encodePassword && passwordEncoder != null) {
			user.setPassword(passwordEncoder.encodePassword(password, saltSource));
		} else {
			user.setPassword(password);
		}

		user.setState(User.State.ENABLE);
        user.setStat(new UserStat());
        user.setProfile(new UserProfile());
        user.setPreference(new UserPreference());
        
        if (user.getNickname() == null) {
        	user.setNickname(user.getUsername()); // 默认用户名作为昵称，之后可以修改昵称，简化注册过程
        }
        
        if (user.getLocale() == null) {
        	user.setLocale(Locale.SIMPLIFIED_CHINESE);
        }
        
        // Set the default user role on this new user
        user.addRole(roleDAO.getRole(Painiu.FREE_ROLE));
        user.addRole(roleDAO.getRole(Painiu.UPLOADER_ROLE));
        //user.addRole(roleDAO.getRole(Painiu.CS_ROLE));
        
        userDAO.saveUser(user);
        
        return user;
	}*/

	/* (non-Javadoc)
	 * @see com.painiu.core.service.SignupService#signup(java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	public User signup(String username, String email, String password,UserProfile userProfile,boolean encodePassword) throws UserExistsException {

		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		
		if (encodePassword && passwordEncoder != null) {
			user.setPassword(passwordEncoder.encodePassword(password, saltSource));
		} else {
			user.setPassword(password);
		}

		user.setState(User.State.ENABLE);
        user.setStat(new UserStat());
        user.setProfile(userProfile);
        user.setPreference(new UserPreference());
        
        if (user.getNickname() == null) {
        	user.setNickname(user.getUsername()); // 默认用户名作为昵称，之后可以修改昵称，简化注册过程
        }
        
        if (user.getLocale() == null) {
        	user.setLocale(Locale.SIMPLIFIED_CHINESE);
        }
        
        // Set the default user role on this new user
        user.addRole(roleDAO.getRole(Painiu.FREE_ROLE));
        user.addRole(roleDAO.getRole(Painiu.UPLOADER_ROLE));
        
        userDAO.saveUser(user);
        
        return user;
	}

	/**
	 * @param userDAO the userDAO to set
	 */
	@NonTransactional
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}


	/**
	 * @param roleDAO the roleDAO to set
	 */
	@NonTransactional
	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	/**
	 * @param passwordEncoder the passwordEncoder to set
	 */
	@NonTransactional
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}


	/**
	 * @param saltSource the saltSource to set
	 */
	@NonTransactional
	public void setSaltSource(SaltSource saltSource) {
		this.saltSource = saltSource;
	}

	//~ Accessors ==============================================================

}
