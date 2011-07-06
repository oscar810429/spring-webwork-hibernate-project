/*
 * @(#)PermissionAdvice.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.service.api;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.aop.MethodBeforeAdvice;
import com.painiu.core.model.Authentication.Permission;

/**
 * <p>
 * <a href="PermissionAdvice.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: PermissionAdvice.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class PermissionAdvice implements MethodBeforeAdvice {

private Map permsMap;
	
	/*
	 * @see org.springframework.aop.MethodBeforeAdvice#before(java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
	 */
	public void before(Method method, Object[] args, Object target) throws Throwable {
		Call call = (Call) args[0];
		
		Permission requiredPerms = requiredPerms(call);
		
		ApiAuth auth = ApiContextHolder.getContext().getAuth();
		if (auth == null) {
			if (!Permission.NONE.equals(requiredPerms)) {
				throw ApiException.NOT_AUTHORIZED();
			}
		} else {
			Permission perms = auth.getPerm();
			
			if (perms.compareTo(requiredPerms) < 0) {
				throw ApiException.NOT_AUTHORIZED();
			}
		}
	}
	
	protected Permission requiredPerms(Call call) {
		String method = call.getTargetName() + "." + call.getMethodName();
		
		Permission perms = (Permission) permsMap.get(method);
		if (perms == null) {
			perms = Permission.NONE;
		}
		return perms;
	}
	
	/**
	 * @param permsMap The permsMap to set.
	 */
	public void setPerms(Set set) {
		this.permsMap = new HashMap();
		
		for (Iterator i = set.iterator(); i.hasNext();) {
			String[] pair = StringUtils.split(i.next().toString(), ":");
			String key = pair[0];
			String value = pair[1];
			if ("none".equals(value)) {
				permsMap.put(key, Permission.NONE);
			} else if ("read".equals(value)) {
				permsMap.put(key, Permission.READ);
			} else if ("write".equals(value)) {
				permsMap.put(key, Permission.WRITE);
			} else if ("delete".equals(Permission.DELETE)) {
				permsMap.put(key, Permission.DELETE);
			} else if ("all".equals(Permission.ALL)) {
				permsMap.put(key, Permission.ALL);
			}
		}
	}
}
