/*
 * @(#)MultipartParseInterceptor.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.interceptor;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.Interceptor;
import com.painiu.webapp.upload.LazyParseMultipartRequest;

/**
 * <p>
 * <a href="MultipartParseInterceptor.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: MultipartParseInterceptor.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class MultipartParseInterceptor implements Interceptor {

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
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ac = invocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) ac.get(WebWorkStatics.HTTP_REQUEST);

//        MonitoredMultiPartRequest multi = (MonitoredMultiPartRequest) request.getAttribute(MonitoredMultiPartRequest.MULTIPART_REQUEST);
        LazyParseMultipartRequest multi = (LazyParseMultipartRequest) request.getAttribute(LazyParseMultipartRequest.LAZY_PARSE_MULTIPART_REQUEST);

        if (multi != null && !multi.isParsed()) {
        	multi.parse();
        	ac.getParameters().putAll(request.getParameterMap());
//        	request.setAttribute(MonitoredMultiPartRequest.MULTIPART_REQUEST, null);
        }
        
        return invocation.invoke();
	}

}
