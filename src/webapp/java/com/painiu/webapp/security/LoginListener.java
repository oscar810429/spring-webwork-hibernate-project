/*
 * @(#)LoginListener.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.security;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.acegisecurity.Authentication;
import org.acegisecurity.event.authentication.InteractiveAuthenticationSuccessEvent;
import org.acegisecurity.ui.WebAuthenticationDetails;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.painiu.core.model.SystemMessage;
import com.painiu.core.model.User;
import com.painiu.core.model.UserLoginIP;
import com.painiu.core.model.UserStat;
import com.painiu.core.security.UserDetail;
import com.painiu.core.service.UserManager;
import com.painiu.core.service.SystemMessageManager;
import com.painiu.core.service.MessageEngine;

/**
 * <p>
 * <a href="LoginListener.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: LoginListener.java 56 2010-06-15 10:51:02Z zhangsf $
 */
public class LoginListener implements ApplicationListener {
	//~ Static fields/initializers =============================================

	private static final Logger logger = LoggerFactory.getLogger(LoginListener.class);
	
	//~ Instance fields ========================================================
	
	private UserManager userManager;
	private SystemMessageManager systemMessageManager;
	private MessageEngine messageEngine;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	
	/*
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof InteractiveAuthenticationSuccessEvent) {
			InteractiveAuthenticationSuccessEvent authEvent = (InteractiveAuthenticationSuccessEvent) event;
			Authentication auth = authEvent.getAuthentication();
			
			User user = null;
			UserStat stat = null;
			
			if (auth.getPrincipal() instanceof UserDetail) {
				user = ((UserDetail) auth.getPrincipal()).getUser();
				stat = user.getStat();
            } else {
            	user = userManager.getUser(auth.getPrincipal().toString());
            	stat = userManager.getUserStat(user);
            }
			
			Date now = new Date(authEvent.getTimestamp());
			Date prevLogin = stat.getLastLoginDate();
			if (stat.getInactive().booleanValue()) {
				try {
					//YPFS.enableUser(user.getUsername());
					stat.setInactive(Boolean.FALSE);
				} catch (Exception e) {
					logger.error("Error occurred while enable user:" + user.getUsername(), e);
				}
			}
			
			stat.increaseLogins();
			stat.setPrevLoginDate(prevLogin);
			stat.setLastLoginDate(now);
			
			Object details = auth.getDetails();
			
			if (details != null && details instanceof WebAuthenticationDetails) {
				stat.setLastLoginIP(((WebAuthenticationDetails) details).getRemoteAddress());
			}
			
			if (logger.isDebugEnabled()) {
				logger.debug("update user[" + user.getEmail() + "] login stat: logins=" + stat.getLogins() + "; lastLoginDate=" + stat.getLastLoginDate());
		    }
			
			userManager.saveUserStat(stat);
			user.setStat(stat);
			
			UserLoginIP userLoginIP = new UserLoginIP();
			userLoginIP.setUser(user);
			userLoginIP.setLoginDate(now);
			userLoginIP.setLoginIP(stat.getLastLoginIP());
			userManager.saveUserLoginIP(userLoginIP);
			
			/*if (user.isInRole(Painiu.VIP_BUSINESS_ROLE) || user.isInRole(Painiu.VIP_NORMAL_ROLE)) {
				Date deadline = user.getVipDeadline(); 
				if (deadline == null || new Date().after(deadline)) {
					user = userManager.getUser(user.getId());
					userManager.autoDemotion(user);
					List inactiveRecord = paymentManager.getUserInactivePurchaseRecords(user, -1, -1).getData();
					if (inactiveRecord != null && inactiveRecord.size() > 0) {
						Collections.reverse( inactiveRecord );
						PurchaseRecord purchaseRecord = (PurchaseRecord)inactiveRecord.get(0);
						paymentManager.activate(user, purchaseRecord.getCode());
					} else {
						PNFS.setUserVip(user.getUsername(), 0);
						PNFS.setUserMaxFlow(user.getUsername(), FlowmeterConfig.getFreeUserMaxOutFlowmeter());
						if (deadline != null) {
							user.setVipDeadline(null);
							userManager.saveUser(user);
						}
						
					}
				}
			}*/
			// check system messages
			List messages = systemMessageManager.getSystemMessages(stat.getPrevLoginDate());
			
			if (messages.size() > 0) {
				for (Iterator i = messages.iterator(); i.hasNext();) {
					SystemMessage msg = (SystemMessage) i.next();
					if (isChosenUser(user, msg)) {
						//messageEngine.sendMessage(user, msg);
					}
				}
			}
			
		}
	}

	
	/**
	 * @param userManager the userManager to set
	 */
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}


	/**
	 * @param systemMessageManager the systemMessageManager to set
	 */
	public void setSystemMessageManager(SystemMessageManager systemMessageManager) {
		this.systemMessageManager = systemMessageManager;
	}
	
	private boolean isChosenUser(User user, SystemMessage msg) {
		if (StringUtils.isBlank(msg.getTargetUserType())) {
			return true;
		}
		String[] roles = StringUtils.split(msg.getTargetUserType(), ",");
		boolean result = false;
		for(int i = 0; i < roles.length; i++) {
			if ("freeman".equals(roles[i])) {
				if (!user.isVIP()) {
					result = true;
				} else {
					result = false;
				}
				break;
			}
			if (user.isInRole(roles[i])) {
				result = true;
				break;
			}
		}
		
		return result;
	}

	/**
	 * @param messageEngine the messageEngine to set
	 */
	public void setMessageEngine(MessageEngine messageEngine) {
		this.messageEngine = messageEngine;
	}
}
