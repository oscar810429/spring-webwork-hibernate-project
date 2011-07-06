/*
 * @(#)UserRoleAction.java  2006-7-11
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action.console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.painiu.Painiu;
//import com.painiu.core.model.Flowmeter;
import com.painiu.core.model.Icon;
//import com.painiu.core.model.MeltApply;
import com.painiu.core.model.Role;
import com.painiu.core.model.User;
//import com.painiu.core.model.VIPOption;
import com.painiu.util.URLUtils;
import com.painiu.webapp.action.BaseAction;
//import com.painiu.core.ypfs.YPFSException;
//import com.painiu.core.ypfs.files.IconFile;

/**
 * <p>
 * <a href="UserRoleAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zola Zhou
 * @version $Id: UserRoleAction.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class UserRoleAction extends BaseAction {
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	private String person;
	private String apply;

	private String role;
	
	private List roles;
	
	private User user;
	
	private long bandwidth;
	
	private List inactiveRecord;
	
	//private Flowmeter flowmeter;
	
	//~ Constructors ===========================================================
	
	//~ Methods ================================================================

	public String execute() throws Exception {
		//assertParamExists("person", person);
		if("".equals(person) || person==null){
		 user = userManager.getUser(this.getCurrentUser().getId());
		}else{
		 user = userManager.getUser(person);	
		}
		roles = getAvailableRoles(user);
		//inactiveRecord = paymentManager.getUserInactivePurchaseRecords(user, -1, -1).getData();
		//flowmeter = flowmeterManager.getFlowmeter(user);
		return SUCCESS;
	}
	
	public String block() throws Exception {
		assertParamExists("person", person);
		
		user = userManager.getUser(person);
		
		if (user.isInRole(Painiu.PRO_ROLE)) {
			saveError("Operation failed: User[" + user.getNickname() + "] is VIP user.");
		} else if (user.isInRole(Painiu.FROZEN_ROLE)) {
			saveError("User[" + user.getNickname() + "] already blocked.");
		} else {
			user.addRole(roleManager.getRole(Painiu.FROZEN_ROLE));
			//userManager.setUserRoles(user, user.getRoles());
			
			//photoManager.blockUserPhotos(user);
			
			// send notification PainiuMail
			Map model = new HashMap(1);
			model.put("to", user);
			//messageEngine.sendMessage(null, user, model, "messages/account_freezed.ftl", user.getLocale());
			
			saveMessage("User[" + user.getNickname() + "] has been successfully blocked.");
		}

		return redirect(from != null ? from : URLUtils.getUserHomeURL(user));
	}
	
	public String unblock() throws Exception {
		//MeltApply meltApply = null;
		
		if (apply != null) {
			//meltApply = applyManager.getMeltApply(apply);
			//user = meltApply.getUser();
		} else {
			assertParamExists("person", person);
			user = userManager.getUser(person);
			//meltApply = applyManager.getMeltApply(user);
		}
		
		if (!user.isInRole(Painiu.FROZEN_ROLE)) {
			saveError("User[" + user.getNickname() + "] is not blocked.");
		} else {
			user.removeRole(roleManager.getRole(Painiu.FROZEN_ROLE));
			//userManager.setUserRoles(user, user.getRoles());
			
			//if (meltApply != null) {
			//	applyManager.removeMeltApply(meltApply);
			//}
			
			// TODO: unblock user's photos?
			// photoManager.unblockUserPhotos(user);
			
			// send notification PainiuMail
			Map model = new HashMap(1);
			model.put("to", user);
			//messageEngine.sendMessage(null, user, model, "messages/account_unfreezed.ftl", user.getLocale());
			
			saveMessage("User[" + user.getNickname() + "] has been successfully unblocked.");
		}
		
		return redirect(from != null ? from : URLUtils.getUserHomeURL(user));
	}
	
	public String save() throws Exception {
		assertParamExists("person", person);
		
		user = userManager.getUser(person);
	
		String[] roleNames = StringUtils.split(role, ",");
		
		roles = new ArrayList(roleNames.length);
		for (int i = 0; i < roleNames.length; i++) {
			roles.add(roleManager.getRole(roleNames[i]));
		}
		
		Set roleSet = new HashSet(roles);
		boolean blockPhotos = !user.isInRole(Painiu.FROZEN_ROLE) && roleSet.contains(Painiu.FROZEN_ROLE);
		
		userManager.setUserRoles(user, roleSet);
		
		if (blockPhotos) {
			//photoManager.blockUserPhotos(user);
		}
		
		saveMessage("Role editing is succeed.");
		
		return redirect(URLUtils.getUserHomeURL(user));
	}
	
	public String bandwidth() throws Exception {
		assertParamExists("person", person);
		
		if (bandwidth > 0) {
			bandwidth = bandwidth * 1024 * 1024;

			user = userManager.getUser(person);
			/*VIPOption vipOption = null;
			if (isVIP()) {
				try {
					vipOption = userManager.getVIPOption(person);
				} catch (ObjectRetrievalFailureException oe) {
					
				}
			}

			Flowmeter flowmeter = flowmeterManager.getFlowmeter(user);
			flowmeter.setMaximum(new Long(bandwidth));
			flowmeterManager.saveFlowmeter(flowmeter);
			user.getStat().setMonthLimit(new Long(bandwidth));
			
			userManager.saveUserStat(user.getStat());
			if (vipOption != null) {
				vipOption.setUploadFlowmeter(new Long(bandwidth));
				userManager.saveVIPOption(vipOption);
			}*/
			
			saveMessage("Bandwidth editing succeed.");
			
			return redirect(URLUtils.getUserHomeURL(user));
		}
		
		addActionError("Bandwidth must greater than zero.");
		return INPUT;
	}
	
	public String deleteBuddyicon() {
		assertParamExists("person", person);
		user = userManager.getUser(person);
		//Icon old = user.getBuddyIcon();
		user.setBuddyIcon(null);
		userManager.saveUser(user);
//		SecurityUtils.setCurrentUser(user);
//		getSession().setAttribute(Painiu.USER_KEY, user);
		saveMessage("The user buddyicon has been deleted");
		//deleteIcon(old);
		return SUCCESS;
	}
	private List getAvailableRoles(User user) {
		List availableRoles = (List) getSession().getServletContext().getAttribute(Painiu.AVAILABLE_ROLES);
		
		// user.getRoles().contains(role) always return false. why?
		Set existsRoleNames = new HashSet(user.getRoles().size());
		for (Iterator i = user.getRoles().iterator(); i.hasNext();) {
			Role exist = (Role) i.next();
			existsRoleNames.add(exist.getName());
		}
		
		List result = new ArrayList();
		for (Iterator i = availableRoles.iterator(); i.hasNext();) {
			Role theRole = (Role) i.next();
			
			if (!existsRoleNames.contains(theRole.getName())) {
				result.add(theRole);
			}
		}
		return result;
	}
	
	protected void deleteIcon(Icon icon) {
		/*if (icon != null && icon.getUsername() != null && icon.getFileKey() != null) {
			IconFile iconFile = new IconFile(icon.getUsername(), icon.getFileKey());
	        
			try {
				iconFile.delete();
			} catch (YPFSException e) {
				log.warn("YPFSException: " + e.getMessage());
			}
		}*/
	}
	
	public String repairUploadFlow() {
		/*boolean result = paymentManager.repairUploadFlowBug();
		if (result) {
			saveMessage("Repairing upload flow success!");
		} else {
			saveMessage("Repairing upload flow fail!");
		}*/
		return SUCCESS;
	}
	

	/*public Flowmeter getFlowmeter() {
		return flowmeter;
	}*/
	//~ Accessors ==============================================================
	
	/**
	 * @return Returns the person.
	 */
	public String getPerson() {
		return person;
	}

	/**
	 * @param person The person to set.
	 */
	public void setPerson(String person) {
		this.person = person;
	}
	/**
	 * @return the apply
	 */
	public String getApply() {
		return apply;
	}
	/**
	 * @param apply the apply to set
	 */
	public void setApply(String apply) {
		this.apply = apply;
	}
	/**
	 * @return Returns the role.
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role The role to set.
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return Returns the roles.
	 */
	public List getRoles() {
		return roles;
	}

	/**
	 * @param roles The roles to set.
	 */
	public void setRoles(List roles) {
		this.roles = roles;
	}
	/**
	 * @return Returns the user.
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @return the bandwidth
	 */
	public long getBandwidth() {
		return bandwidth;
	}

	/**
	 * @param bandwidth the bandwidth to set
	 */
	public void setBandwidth(long bandwidth) {
		this.bandwidth = bandwidth;
	}

	/**
	 * @return the inactiveRecord
	 */
	public List getInactiveRecord() {
		return inactiveRecord;
	}
}
