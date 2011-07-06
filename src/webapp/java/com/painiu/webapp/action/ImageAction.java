/**
 * @(#)ImageAction.java Aug 3, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action;


/**
 * <p>
 * <a href="ImageAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ImageAction.java 6 2010-05-11 16:20:57Z zhangsf $
 */
public class ImageAction extends BaseAction{

	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================
	
	private String code;

	//~ Constructors ===========================================================

	//~ Methods ================================================================

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	public String execute() throws Exception{
	
		return SUCCESS;
    }
	
    public String image() throws Exception{
		

		return SUCCESS;
    }
    
    public String confirm() throws Exception{
    	
    	if(code.equals(getSession().getAttribute("rand"))){
    		System.out.println("Ok");
    	}else{
    		System.out.println("No");	
    	}
		return SUCCESS;
		
    }

}
