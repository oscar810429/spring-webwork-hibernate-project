/*
 * @(#)ContextFilter.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.painiu.Context;
import com.painiu.core.model.User;
import com.painiu.core.model.UserStat;
import com.painiu.core.security.RelationContextHolder;
import com.painiu.core.service.UserManager;
import com.painiu.webapp.personality.DomainContextHolder;
import com.painiu.webapp.personality.MultiDomainContextHolder;
import com.painiu.webapp.wrapper.PreferenceRequestWrapper;

/**
 * Store user information in session if authentication exists and it is 
 * not an anonymous authentication.
 * 
 * <ul>
 * <li>Setup API context.</li>
 * <li>Setup user preference.</li>
 * </ul>
 * 
 * <p>
 * <a href="ContextFilter.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ContextFilter.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class ContextFilter implements Filter {
	//~ Static fields/initializers =============================================
	
	private static final Log log = LogFactory.getLog(ContextFilter.class);

	private static final String FILTER_APPLIED = "__context_filter_applied";

	//~ Instance fields ========================================================

	private FilterConfig filterConfig;
	
	private TaskExecutor taskExecutor;
	private UserManager userManager;
	
	//~ Methods ================================================================

	/*
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
//		domain = Yupoo.getConfig().getAppConfig().getDomain();
		this.filterConfig = filterConfig;
		
		ApplicationContext ctx =
			WebApplicationContextUtils.getRequiredWebApplicationContext(this.filterConfig.getServletContext());
		
		taskExecutor = (TaskExecutor) ctx.getBean("taskExecutor");
		
		if (taskExecutor == null) {
			throw new RuntimeException("ContextFilter require a TaskExecutor named 'taskExecutor' in app context to working correctly");
		}
		
		userManager = (UserManager) ctx.getBean("userManager");
		
		if (userManager == null) {
			throw new RuntimeException("ContextFilter need a bean called 'userManager'");
		}
	}
	

	/*
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		if ((request != null) && (request.getAttribute(FILTER_APPLIED) != null)) {
            // ensure that filter is only applied once per request			
            chain.doFilter(request, response);
        } else {
        	request.setAttribute(FILTER_APPLIED, Boolean.TRUE);
        	
        	Context.getContext().initContext((HttpServletRequest) request, filterConfig.getServletContext());
        	
        	if (!(request instanceof PreferenceRequestWrapper)) {
        		request = new PreferenceRequestWrapper((HttpServletRequest) request);
        	}
        	
        	//if (DomainContextHolder.getContext() != null) {
        	//	increaseUserStat();
        	//}
        	
        	try {
                chain.doFilter(request, response);
            } finally {
            	// clean up Contexts
            	cleanUp();
            }
        }
	}
	
	/*private void increaseUserStat() {
		final User domainUser = DomainContextHolder.getContext().getUser();
    	
    	if (domainUser != null) {
    		if (!domainUser.equals(Context.getContext().getCurrentUser())) {
    			try {
    				taskExecutor.execute(new Runnable() {
    					public void run() {
    						UserStat stat = userManager.getUserStat(domainUser);
    						stat.increaseStreamViews();
    						userManager.saveUserStat(stat);
    					}
    				});
    			} catch (TaskRejectedException e) {
    				log.error("Can not execute task[SaveAccess]: " + e.getMessage());
    			}
    		}
    	}
	}*/

	/**
	 * Clean contexts
	 */
	protected static void cleanUp() {
		LocaleContextHolder.setLocale(null);
		DomainContextHolder.setContext(null);
		MultiDomainContextHolder.setContext(null);
		
		if (RelationContextHolder.contextExists()) {
			RelationContextHolder.setContext(null);
		}
		
		Context.setContext(null);
	}
	
	/*
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
	}

}
