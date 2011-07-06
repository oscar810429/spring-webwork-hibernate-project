/*
 * @(#)SignatureAdvice.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.service.api;

import java.lang.reflect.Method;
import java.util.Set;

import org.springframework.aop.MethodBeforeAdvice;

//import com.painiu.core.model.Application;
import com.painiu.service.api.util.SignUtils;
import com.painiu.service.api.ApiAuth;
import com.painiu.service.api.ApiContextHolder;
import com.painiu.service.api.Call;

/**
 * <p>
 * <a href="SignatureAdvice.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SignatureAdvice.java 35 2010-06-01 01:53:10Z zhangsf $
 */
@SuppressWarnings("unchecked")
public class SignatureAdvice implements MethodBeforeAdvice {

private Set signMethods;
	
	/**
	 * @param signMethods The signMethods to set.
	 */
	public void setSignMethods(Set signMethods) {
		this.signMethods = signMethods;
	}
	
	/*
	 * @see org.springframework.aop.MethodBeforeAdvice#before(java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
	 */
	public void before(Method method, Object[] args, Object target) throws Throwable {
		ApiAuth auth = ApiContextHolder.getContext().getAuth();
		
		Call call = (Call) args[0];

		if (signRequired(call)) {
			SignUtils.validate(auth.getSecret(), call.getParameters());
		}
	}

	protected boolean signRequired(Call call) {
		String authSign = call.getParameters().getString(SignUtils.AUTH_SIGN_PARAMETER_NAME);
		
		if (authSign != null) {
			return true;
		}
		
		String method = call.getTargetName() + "." + call.getMethodName();
		return signMethods.contains(method);
	}
}
