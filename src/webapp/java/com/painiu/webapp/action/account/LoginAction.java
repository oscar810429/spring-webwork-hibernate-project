/**
 * @(#)LoginAction.java 2009-01-10
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action.account;

import com.painiu.webapp.action.BaseAction;

/**
 * <p>
 * <a href="LoginAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zola Zhou
 * @version $Id: LoginAction.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class LoginAction extends BaseAction {
	//~ Static fields/initializers =============================================
	
	private static final long serialVersionUID = 3359934501320883052L;
	
	//~ Instance fields ========================================================
	
	private String j_uri;
	
	private boolean hideUri = false;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================
	
	public String execute() throws Exception {
		if (j_uri != null && j_uri.indexOf("account") > 0) {
			j_uri = null;
			hideUri = true;
		}
		return SUCCESS;
	}
	
	//~ Accessors ==============================================================

	/**
	 * @return the j_uri
	 */
	public String getJ_uri() {
		return j_uri;
	}

	/**
	 * @param j_uri the j_uri to set
	 */
	public void setJ_uri(String j_uri) {
		this.j_uri = j_uri;
	}

	/**
	 * @return the hideUri
	 */
	public boolean isHideUri() {
		return hideUri;
	}

}
