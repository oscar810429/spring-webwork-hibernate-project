/*
 * @(#)BaseServlet.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.painiu.core.service.UserManager;
import com.painiu.service.api.Handler;

/**
 * <p>
 * <a href="BaseServlet.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: BaseServlet.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class BaseServlet extends HttpServlet {
	
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================
	
	//protected UserManager userManager;
	protected Handler handler;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================
    
	protected void saveMessage(HttpServletRequest request, String msg) {
        List messages = (List) request.getSession().getAttribute("messages");
        if (messages == null) {
            messages = new ArrayList();
        }
        messages.add(msg);
        request.getSession().setAttribute("messages", messages);
    }
    
    /*protected UserManager getUserManager() {
    	if (userManager == null) {
    		userManager = (UserManager) getWebApplicationContext().getBean("userManager");
    	}
    	return userManager;
	}*/
    
    
    protected Handler getApiHandler() {
    	if (handler == null) {
    		handler = (Handler) getWebApplicationContext().getBean("apiHandler");
    	}
    	return handler;
    }
    
    protected WebApplicationContext getWebApplicationContext() {
    	return WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
    }
}
