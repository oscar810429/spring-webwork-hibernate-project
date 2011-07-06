/*
 * @(#)ApiContextHolder.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.service.api;


/**
 * <p>
 * <a href="ApiContextHolder.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ApiContextHolder.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class ApiContextHolder {
    //~ Static fields/initializers =============================================

    private static ThreadLocal contextHolder = new ThreadLocal();

    //~ Methods ================================================================

	public static void setContext(ApiContext context) {
        contextHolder.set(context);
    }

    public static ApiContext getContext() {
    	return (ApiContext) contextHolder.get();
    }
}
