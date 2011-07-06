/*
 * @(#)RelationContextHolder.java  2009-12-11
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.security;


/**
 * <p>
 * <a href="RelationContextHolder.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: RelationContextHolder.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class RelationContextHolder {
    //~ Static fields/initializers =============================================

    private static ThreadLocal<RelationContext> contextHolder = new ThreadLocal<RelationContext>();

	//~ Instance fields ========================================================

    //~ Methods ================================================================
    
	public static void setContext(RelationContext context) {
        contextHolder.set(context);
    }

	public static boolean contextExists() {
		return contextHolder.get() != null;
	}
	
    public static RelationContext getContext() {
        if (contextHolder.get() == null) {
            contextHolder.set(new RelationContextImpl());
        }
        return contextHolder.get();
    }
}
