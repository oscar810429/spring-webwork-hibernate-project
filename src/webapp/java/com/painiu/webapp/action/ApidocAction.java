/**
 * @(#)ApidocsAction.java 2010-7-4
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action;

/**
 * <p>
 * <a href="ApidocsAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ApidocAction.java 138 2010-11-23 10:21:39Z zhangsf $
 */

public class ApidocAction extends BaseAction{

	//~ Static fields/initializers =============================================

	//~ Instance fields ===================================================
	
	private String api;

	//~ Constructors ====================================================

	//~ Methods =======================================================
	
	public String execute() throws Exception{
		
		return SUCCESS;
	}

	/**
	 * @return the api
	 */
	public String getApi() {
		return api;
	}

	/**
	 * @param api the api to set
	 */
	public void setApi(String api) {
		this.api = api;
	}
	
	

	//~ Accessors ======================================================

}
