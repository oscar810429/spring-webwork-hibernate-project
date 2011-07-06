/*
 * @(#)LogoutFilter.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * <p>
 * <a href="LogoutFilter.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id
 */
public class LogoutFilter implements Filter {
	//~ Static fields/initializers =============================================

	private static final Logger log = LoggerFactory.getLogger(LogoutFilter.class);
	
	//~ Instance fields ========================================================
	
    private String filterProcessesUrl = "/account/logout";
    
	protected String logoutSuccessUrl;
	protected LogoutHandler[] handlers;
	
	//~ Constructors ===========================================================
    
	public LogoutFilter(String logoutSuccessUrl, LogoutHandler[] handlers) {
        this.logoutSuccessUrl = logoutSuccessUrl;
        this.handlers = handlers;
    }
    
	//~ Methods ================================================================

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	throws IOException, ServletException {
		if (!(request instanceof HttpServletRequest)) {
			throw new ServletException("Can only process HttpServletRequest");
		}

		if (!(response instanceof HttpServletResponse)) {
			throw new ServletException("Can only process HttpServletResponse");
		}

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		if (requiresLogout(httpRequest, httpResponse)) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			if (log.isDebugEnabled()) {
				log.debug("Logging out user '" + auth + "' and redirecting to logout page");
			}

			for (int i = 0; i < handlers.length; i++) {
				LogoutHandler handler = handlers[i];
				
				if (handler.supports(httpRequest, httpResponse, auth)) {
					handler.logout(httpRequest, httpResponse, auth);
					
					if (handler.isCommitted(httpRequest, httpResponse)) {
						return;
					}
				}
			}

			sendRedirect(httpRequest, httpResponse, getSuccessUrl(httpRequest));

			return;
		}

		chain.doFilter(request, response);
	}
    /**
     * Not used. Use IoC container lifecycle methods instead.
     *
     * @param arg0 ignored
     *
     * @throws ServletException ignored
     */
    public void init(FilterConfig config) throws ServletException {}
    
    /**
     * Not used. Use IoC container lifecycle methods instead.
     */
    public void destroy() {}
    
    /**
     * Allow subclasses to modify when a logout should tak eplace.
     *
     * @param request the request
     * @param response the response
     *
     * @return <code>true</code> if logout should occur, <code>false</code> otherwise
     */
    protected boolean requiresLogout(HttpServletRequest request, HttpServletResponse response) {
        String uri = request.getRequestURI();
        int pathParamIndex = uri.indexOf(';');

        if (pathParamIndex > 0) {
            // strip everything after the first semi-colon
            uri = uri.substring(0, pathParamIndex);
        }

        return uri.endsWith(request.getContextPath() + filterProcessesUrl);
    }

    /**
     * Allow subclasses to modify the redirection message.
     *
     * @param request the request
     * @param response the response
     * @param url the URL to redirect to
     *
     * @throws IOException in the event of any failure
     */
    protected void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url)
        throws IOException {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = request.getContextPath() + url;
        }

        response.sendRedirect(response.encodeRedirectURL(url));
    }

    protected String getSuccessUrl(HttpServletRequest request) {
    	if (request.getParameter("forward") != null) {
    		return request.getParameter("forward");
    	}
    	return logoutSuccessUrl;
    }

	//~ Accessors ==============================================================

	/**
	 * @return the handlers
	 */
	public LogoutHandler[] getHandlers() {
		return handlers;
	}

	/**
	 * @param handlers the handlers to set
	 */
	public void setHandlers(LogoutHandler[] handlers) {
		this.handlers = handlers;
	}

	/**
	 * @return the logoutSuccessUrl
	 */
	public String getLogoutSuccessUrl() {
		return logoutSuccessUrl;
	}

	/**
	 * @param logoutSuccessUrl the logoutSuccessUrl to set
	 */
	public void setLogoutSuccessUrl(String logoutSuccessUrl) {
		this.logoutSuccessUrl = logoutSuccessUrl;
	}
	
    public void setFilterProcessesUrl(String filterProcessesUrl) {
        Assert.hasText(filterProcessesUrl, "FilterProcessesUrl required");
        this.filterProcessesUrl = filterProcessesUrl;
    }
}
