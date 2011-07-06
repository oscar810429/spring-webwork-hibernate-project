/*
 * @(#)ValidationResultInterceptor.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.interceptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ValidationAware;
import com.opensymphony.xwork.interceptor.MethodFilterInterceptor;
import com.painiu.webapp.webwork.ValidationResultAware;

/**
 * <p>
 * <a href="ValidationResultInterceptor.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ValidationResultInterceptor.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class ValidationResultInterceptor extends MethodFilterInterceptor {
	
	private static final long serialVersionUID = -8552672510924923573L;

	/**
     * @see com.opensymphony.xwork.interceptor.MethodFilterInterceptor#doIntercept(com.opensymphony.xwork.ActionInvocation)
     */
    protected String doIntercept(ActionInvocation invocation) throws Exception {
        Object action = invocation.getAction();

        if (action instanceof ValidationAware) {
            ValidationAware validationAwareAction = (ValidationAware) action;

            if (validationAwareAction.hasErrors()) {
            	if (action instanceof ValidationResultAware) {
            		onValidationError(invocation);
            	}
            }
        }

        return invocation.invoke();
    }
    
    protected void onValidationError(ActionInvocation invocation) {
    	Object action = invocation.getAction();
    	
    	String methodName = invocation.getProxy().getMethod();
		methodName = "on" + StringUtils.capitalize(methodName) + "ValidationError";
		
		Method method = null;
		try {
			method = action.getClass().getMethod(methodName, (Class[]) null);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			try {
				method = action.getClass().getMethod("onValidationError", (Class[]) null);
			} catch (SecurityException e1) {
			} catch (NoSuchMethodException e1) {
			}
		}
		
		if (method == null) {
			return;
		}
		
		try {
			method.invoke(action, (Object[]) null);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
    }
}
