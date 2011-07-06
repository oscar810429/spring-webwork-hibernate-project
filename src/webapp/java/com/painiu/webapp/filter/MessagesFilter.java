/*
 * @(#)MessagesFilter.java Dec 13, 2009
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
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.painiu.Painiu;
import com.painiu.Context;
//import com.painiu.core.service.MessageManager;
import com.painiu.core.model.User;

/**
 * <p>
 * <a href="MessagesFilter.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * version $Id: MessagesFilter.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class MessagesFilter implements Filter {
	//~ Static fields/initializers =============================================
	
	private static final String FILTER_APPLIED = "__messages_filter_applied";
	
	private static final String LAST_MESSAGES_CHECK_TIMESTAMP_KEY = "__last_messsages_check_key";
	// default message check interval: 10 minutes
//	private static final long DEFAULT_MESSAGE_CHECK_INTERVAL = 1000 * 60 * 10; 

	//~ Instance fields ========================================================

	//~ Constructors ===========================================================

	//~ Methods ================================================================

	//~ Accessors ==============================================================

	/*
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException { 
	}
	
	/*
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
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
        	
        	HttpServletRequest req = (HttpServletRequest) request; 
        	HttpSession session = req.getSession();
        	
        	long messageCheckInterval = Painiu.getMessageConfig().getCheckInterval();
        	
//        	User user = (User) session.getAttribute(Yupoo.USER_KEY);
        	User user = Context.getContext().getCurrentUser();
        	
        	if (user != null) {
        		Integer inboxMessages = (Integer) session.getAttribute(Painiu.INBOX_MESSAGES_KEY);
        		Long lastCheckTimestamp = (Long) session.getAttribute(LAST_MESSAGES_CHECK_TIMESTAMP_KEY);
        		
        		if (inboxMessages == null || lastCheckTimestamp == null ||
        				(System.currentTimeMillis() - lastCheckTimestamp.longValue()) > messageCheckInterval) {
        			
        			ApplicationContext ctx =
        				WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext());
        			
        			//MessageManager messageManager = (MessageManager) ctx.getBean("messageManager");
        			//inboxMessages = messageManager.getUnreadMessagesCount(user);
        			
        			session.setAttribute(Painiu.INBOX_MESSAGES_KEY, inboxMessages);
        			
        			session.setAttribute(LAST_MESSAGES_CHECK_TIMESTAMP_KEY, 
        					new Long(System.currentTimeMillis()));
        		}
        	}
        	
        	chain.doFilter(request, response);
        }
	}

}
