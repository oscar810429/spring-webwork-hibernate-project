/*
 * @(#)BaseTarget.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.service.api.methods;

//import com.mingda.core.service.CategoryManager;
import com.painiu.core.service.AreaManager;
import com.painiu.core.service.UserManager;
import com.painiu.service.api.ApiException;
import com.painiu.service.api.Target;

/**
 * <p>
 * <a href="BaseTarget.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: BaseTarget.java 41 2010-06-10 17:30:08Z zhangsf $
 */
public class BaseTarget implements Target {
	//~ Static fields/initializers =============================================
	
	//~ Instance fields ========================================================
	
	protected UserManager userManager;
	protected AreaManager areaManager;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================


	protected void assertParamExists(Object obj) throws ApiException {
		if (obj == null) {
			throw ApiException.MISSING_REQUIRED_ARGUMENT();
		}
	}
	
	protected void assertParamValid(boolean expression) throws ApiException {
		if (!expression) {
			throw ApiException.INVALIDE_ARGUMENT();
		}
	}
	
	//~ Accessors ==============================================================
	
	/**
	 * @param userManager The userManager to set.
	 */
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	/**
	 * @param areaManager the areaManager to set
	 */
	public void setAreaManager(AreaManager areaManager) {
		this.areaManager = areaManager;
	}

	
	
	
}
