/*
 * @(#)MultiDomainContextHolder.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.personality;

/**
 * <p>
 * <a href="MultiDomainContextHolder.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: MultiDomainContextHolder.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class MultiDomainContextHolder {

	//~ Static fields/initializers =============================================
	private static ThreadLocal contextHolder = new ThreadLocal();
	
	//~ Instance fields ========================================================

	//~ Constructors ===========================================================

	//~ Methods ================================================================
	public static void setContext(MultiDomainContext context) {
        contextHolder.set(context);
    }

    public static MultiDomainContext getContext() {
    	return (MultiDomainContext) contextHolder.get();
    }
	//~ Accessors ==============================================================
}
