/*
 * @(#)DomainContextHolder.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.personality;

/**
 * <p>
 * <a href="DomainContextHolder.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: DomainContextHolder.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class DomainContextHolder {
    //~ Static fields/initializers =============================================

    private static ThreadLocal contextHolder = new ThreadLocal();

    //~ Methods ================================================================

	public static void setContext(DomainContext context) {
        contextHolder.set(context);
    }

    public static DomainContext getContext() {
    	return (DomainContext) contextHolder.get();
    }
}
