/*
 * @(#)DynamicFreemarkerResult.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.views.freemarker;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;

/**
 * <p>
 * <a href="DynamicFreemarkerResult.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: DynamicFreemarkerResult.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class DynamicFreemarkerResult extends FreemarkerResult {
	//~ Static fields/initializers =============================================

	public static final String LOCALTION_KEY = "__dynamic_freemerker_result_location_";
	
	//~ Instance fields ========================================================
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	/*
	 * @see com.opensymphony.webwork.dispatcher.WebWorkResultSupport#conditionalParse(java.lang.String, com.opensymphony.xwork.ActionInvocation)
	 */
	@Override
	protected String conditionalParse(String param, ActionInvocation invocation) {
		final Object action = invocation.getAction();
		if (action instanceof DynamicResultAction) {
			param = ((DynamicResultAction) action).getResultLocation();
		} else {
			ActionContext ac = invocation.getInvocationContext();
			if (ac.get(LOCALTION_KEY) != null) {
				param = (String) ac.get(LOCALTION_KEY);
			}
		}
		return super.conditionalParse(param, invocation);
	}
	
	//~ Accessors ==============================================================

}
