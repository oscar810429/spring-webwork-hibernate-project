/*
 * @(#)BaseAspect.aj Nov 28, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.aop;

/**
 * <p>
 * <a href="BaseAspect.aj.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: BaseAspect.aj 8 2010-05-11 16:48:01Z zhangsf $
 */
public abstract aspect BaseAspect {

	declare precedence : *RelationContextAspect, *PrivacyRestrictedAspect, *SecurityAspect;
	
	public pointcut model() : execution(* com.painiu.core.model..*.*(..));
	
	public pointcut dao() : execution(* com.painiu.core.dao..*.*(..));
	
	public pointcut service() : execution(* com.painiu.core.service..*.*(..));
	
}
