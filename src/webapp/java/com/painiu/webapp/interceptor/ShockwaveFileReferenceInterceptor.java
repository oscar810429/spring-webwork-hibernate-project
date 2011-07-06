/*
 * @(#)ShockwaveFileReferenceInterceptor.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.interceptor;

//import javax.servlet.http.HttpServletRequest;

//import com.opensymphony.webwork.WebWorkStatics;
//import com.opensymphony.xwork.Action;
//import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.Interceptor;
//import com.mingda.webapp.upload.MultipartRequest;

/**
 * <p>
 * <a href="ShockwaveFileReferenceInterceptor.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ShockwaveFileReferenceInterceptor.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class ShockwaveFileReferenceInterceptor implements Interceptor {

	private static final long serialVersionUID = 41022261387003824L;

	/*
	 * @see com.opensymphony.xwork.interceptor.Interceptor#intercept(com.opensymphony.xwork.ActionInvocation)
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
//		ActionContext ac = invocation.getInvocationContext();
//        HttpServletRequest request = (HttpServletRequest) ac.get(WebWorkStatics.HTTP_REQUEST);

        //if (request.getAttribute(MultipartRequest.SHOCKWAVE_TEST_REQUEST) != null) {
        //	return Action.NONE;
        //}
        
        return invocation.invoke();
	}
	
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
}
