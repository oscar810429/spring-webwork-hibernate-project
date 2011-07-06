/**
 * @(#)FlowmeterInterceptor.java 2010-3-14
 * 
 * Copyright 2009 Mingda. All rights reserved.
 */
package com.painiu.webapp.interceptor;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.AccessDeniedException;

import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.webwork.dispatcher.multipart.MultiPartRequestWrapper;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ValidationAware;
import com.opensymphony.xwork.interceptor.Interceptor;
import com.opensymphony.xwork.util.LocalizedTextUtil;

import com.painiu.Painiu;
import com.painiu.core.model.User;
import com.painiu.core.security.SecurityUtils;

/**
 * <p>
 * <a href="FlowmeterInterceptor.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: FlowmeterInterceptor.java 6 2010-05-11 16:20:57Z zhangsf $
 */

public class FlowmeterInterceptor implements Interceptor {
	
	//~ Static fields/initializers =============================================

    public static final String UPLOADED_FILE_LENGTH_KEY = "__uploaded_file_length_";
	
	//~ Instance fields ========================================================
    
    protected boolean count = true;
	//protected FlowmeterManager flowmeterManager;

	//~ Constructors ===========================================================

	//~ Methods ================================================================
    
    /**
	 * @param flowmeterManager The flowmeterManager to set.
	 */
    //public void setFlowmeterManager(FlowmeterManager flowmeterManager) {
    //	this.flowmeterManager = flowmeterManager;
   // }
    /**
	 * @param count the count to set
	 */
	public void setCount(boolean count) {
		this.count = count;
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

        final Object action = invocation.getAction();
        ValidationAware validation = null;

        if (action instanceof ValidationAware) {
            validation = (ValidationAware) action;
        }
        
        User user = SecurityUtils.getCurrentUser();
        
        if (user == null) {
        	// should not happen
        	throw new AccessDeniedException("Non authenticated user");
        }
        
        //Flowmeter flowmeter = flowmeterManager.getFlowmeter(user);
        
        //if (action instanceof FlowmeterAware) {
        //	((FlowmeterAware) action).setFlowmeter(flowmeter);
        //}
        
        if (!count && !user.isInRole(Painiu.FROZEN_ROLE)) {
        	return invocation.invoke();
        }
        
        //long left = flowmeter.getMaximum().longValue() - flowmeter.getUsed().longValue();

       long contentLength = request.getContentLength();
        
       // if (request instanceof MultiPartRequestWrapper) {
        	//if (contentLength > left) {
        	//	if (validation != null) {
        	//		validation.addActionError(getTextMessage("errors.flowmeter.overflow", new Object[]{new Long(contentLength), new Long(left)}, ActionContext.getContext().getLocale()));
        	//		return Action.INPUT;
        	//	}
        	//}
       // }
        
        // invoke action
        String result = invocation.invoke();

        Long fileLength = (Long) ac.get(UPLOADED_FILE_LENGTH_KEY);
        long length = fileLength != null ? fileLength.longValue() : contentLength;
        
        //if (length > 0) {
        	// save flowmeter
        //	flowmeter.use(length);
        //	flowmeterManager.saveFlowmeter(flowmeter);
        //}
        
        return result;
	}

	// todo: move this to utilities
	private static final String DEFAULT_MESSAGE = "no.message.found";
	
    protected String getTextMessage(String messageKey, Object[] args, Locale locale) {
        if (args == null || args.length == 0) {
            return LocalizedTextUtil.findText(this.getClass(), messageKey, locale);
        }
		return LocalizedTextUtil.findText(this.getClass(), messageKey, locale, DEFAULT_MESSAGE, args);
    }
    
    /*
	 * @see com.opensymphony.xwork.interceptor.Interceptor#destroy()
	 */
	public void destroy() {
	}
	
	
	//~ Accessors ==============================================================


}
