/**
 * @(#)HttpPostRequiredInterceptor.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.interceptor;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.Interceptor;
import com.painiu.webapp.exception.InvalidHttpMethodException;

/**
 * <p>
 * <a href="HttpPostRequiredInterceptor.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: HttpPostRequiredInterceptor.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class HttpPostRequiredInterceptor implements Interceptor {

	private static final long serialVersionUID = -5341759109138786710L;
	
	private static final String POST = "POST";

	/*
	 * @see com.opensymphony.xwork.interceptor.Interceptor#destroy()
	 */
	public void destroy() {
	}

	/*
	 * @see com.opensymphony.xwork.interceptor.Interceptor#init()
	 */
	public void init() {
	}

	/*
	 * @see com.opensymphony.xwork.interceptor.Interceptor#intercept(com.opensymphony.xwork.ActionInvocation)
	 */
	@SuppressWarnings("unchecked")
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ac = invocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) ac.get(WebWorkStatics.HTTP_REQUEST);

        if (!POST.equals(request.getMethod())) {
        	throw new InvalidHttpMethodException("Http Post is required");
        }
        
        return invocation.invoke();
	}

}
