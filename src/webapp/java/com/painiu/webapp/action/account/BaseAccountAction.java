package com.painiu.webapp.action.account;


import com.painiu.core.model.User;
import com.painiu.webapp.action.BaseAction;

public class BaseAccountAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	protected User user;

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

}
