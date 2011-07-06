/**
 * @(#)HomeAction.java Mar 7, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action.console;

import com.painiu.webapp.action.BaseAction;

/**
 * <p>
 * <a href="HomeAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: HomeAction.java 6 2010-05-11 16:20:57Z zhangsf $
 */
public class HomeAction extends BaseAction{
	
	private static final long serialVersionUID = -2816495371472665497L;


	public String execute() throws Exception {
		
		return SUCCESS;
	}
	
	public String adminmenu() {
		
		return SUCCESS;
	}
	
	
	public String adminwelcome() {
		
		return SUCCESS;
	}

}
