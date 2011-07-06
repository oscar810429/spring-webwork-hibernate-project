/*
 * @(#)AuthenticationProcessingFilter.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.security;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.event.authentication.InteractiveAuthenticationSuccessEvent;
import org.acegisecurity.ui.rememberme.NullRememberMeServices;
import org.acegisecurity.ui.rememberme.RememberMeServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.util.Assert;


/**
 * <p>
 * <a href="AuthenticationProcessingFilter.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: AuthenticationProcessingFilter.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class AuthenticationProcessingFilter implements Filter, InitializingBean, ApplicationEventPublisherAware {
	//~ Static fields/initializers =============================================
	
	private static final Logger log = LoggerFactory.getLogger(AuthenticationProcessingFilter.class);
	
	public static final String SECURITY_LAST_EXCEPTION_KEY = "SECURITY_LAST_EXCEPTION";
	
	//~ Instance fields ========================================================
	
	protected ApplicationEventPublisher eventPublisher;
	
	protected RememberMeServices rememberMeServices = new NullRememberMeServices();
	
	protected List<AuthenticationHandler> handlers;

	//~ Constructors ===========================================================
	
	//~ Methods ================================================================

	/*
	 * @see org.acegisecurity.ui.AbstractProcessingFilter#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(handlers, "handlers must be specified");
		Assert.notNull(this.rememberMeServices);
	}
	
	/*
	 * @see org.springframework.context.ApplicationEventPublisherAware#setApplicationEventPublisher(org.springframework.context.ApplicationEventPublisher)
	 */
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.eventPublisher = applicationEventPublisher;
	}

	/*
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {}

	/*
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {}

	/*
	 * @see org.acegisecurity.ui.AbstractProcessingFilter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (!(request instanceof HttpServletRequest)) {
            throw new ServletException("Can only process HttpServletRequest");
        }

        if (!(response instanceof HttpServletResponse)) {
            throw new ServletException("Can only process HttpServletResponse");
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        AuthenticationHandler handler = null;
        
        for (Iterator<AuthenticationHandler> i = handlers.iterator(); i.hasNext();) {
			AuthenticationHandler authHandler = i.next();
			if (authHandler.requiresAuthentication(httpRequest, httpResponse) && authHandler.supports(httpRequest, httpResponse)) {
				handler = authHandler;
				break;
			}
		}
        
        if (handler != null) {
        	if (log.isDebugEnabled()) {
                log.debug("Request is to process authentication");
            }
        	
            Authentication authResult;

            try {
                if (preAuthentication(httpRequest, httpResponse)) {
                	return;
                }
                
                authResult = handler.attemptAuthentication(httpRequest, httpResponse);
                
            } catch (AuthenticationException failed) {
                // Authentication failed
                unsuccessfulAuthentication(httpRequest, httpResponse, failed);

                return;
            }
            
            if (handler.isCommitted(httpRequest, httpResponse)) {
            	return;
            }

            successfulAuthentication(httpRequest, httpResponse, authResult);

            return;
        }

        chain.doFilter(request, response);
	}
	
	protected boolean preAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
		for (Iterator<AuthenticationHandler> i = handlers.iterator(); i.hasNext();) {
			AuthenticationHandler handler = i.next();
			if (handler.supports(request, response)) {
				handler.onPreAuthentication(request, response);
				
				if (handler.isCommitted(request, response)) {
					return true;
				}
			}
		}
		return false;
	}
	
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
		if (log.isDebugEnabled()) {
            log.debug("Authentication success: " + authResult.toString());
        }

        SecurityContextHolder.getContext().setAuthentication(authResult);

        if (log.isDebugEnabled()) {
            log.debug("Updated SecurityContextHolder to contain the following Authentication: '" + authResult + "'");
        }

        getRememberMeServices().loginSuccess(request, response, authResult);

        // Fire event
        if (this.eventPublisher != null) {
            eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
        }

        for (Iterator<AuthenticationHandler> i = handlers.iterator(); i.hasNext();) {
			AuthenticationHandler handler = i.next();
			if (handler.supports(request, response)) {
				handler.onSuccessfulAuthentication(request, response, authResult);
				if (handler.isCommitted(request, response)) {
					return;
				}
			}
		}
	}
	
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
		SecurityContextHolder.getContext().setAuthentication(null);

        if (log.isDebugEnabled()) {
            log.debug("Updated SecurityContextHolder to contain null Authentication");
        }

        try {
            request.getSession().setAttribute(SECURITY_LAST_EXCEPTION_KEY, failed);
        } catch (Exception ignored) {}

        getRememberMeServices().loginFail(request, response);

        for (Iterator<AuthenticationHandler> i = handlers.iterator(); i.hasNext();) {
			AuthenticationHandler handler = i.next();
			if (handler.supports(request, response)) {
				handler.onUnsuccessfulAuthentication(request, response, failed);
				
				if (handler.isCommitted(request, response)) {
					return;
				}
			}
		}
	}

	//~ Accessors ==============================================================

	/**
	 * @param handlers the handlers to set
	 */
	public void setHandlers(List<AuthenticationHandler> handlers) {
		this.handlers = handlers;
	}
	
	/**
	 * @param rememberMeServices the rememberMeServices to set
	 */
	public void setRememberMeServices(RememberMeServices rememberMeServices) {
		this.rememberMeServices = rememberMeServices;
	}
	
	/**
	 * @return the rememberMeServices
	 */
	public RememberMeServices getRememberMeServices() {
		return rememberMeServices;
	}
}
