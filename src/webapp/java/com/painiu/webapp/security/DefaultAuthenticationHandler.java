/*
 * @(#)DefaultAuthenticationHandler.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.security;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.ui.AbstractProcessingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.util.Assert;

import com.painiu.core.model.User;
import com.painiu.core.service.UserManager;
import com.painiu.webapp.util.ValidationUtils;

/**
 * <p>
 * <a href="DefaultAuthenticationHandler.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: DefaultAuthenticationHandler.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class DefaultAuthenticationHandler extends AbstractAuthenticationHandler implements InitializingBean {
	//~ Static fields/initializers =============================================

	private static final Logger log = LoggerFactory.getLogger(DefaultAuthenticationHandler.class);
	
    public static final String SECURITY_FORM_USERNAME_KEY = "j_username";
    public static final String SECURITY_FORM_PASSWORD_KEY = "j_password";
	public static final String SECURITY_FORM_URI_KEY      = "j_uri";
	public static final String SECURITY_AUTHENTICATION_FAILURE_URL_KEY = "j_fail_uri";
	
	public static final String SECURITY_LAST_USERNAME_KEY = "SECURITY_LAST_USERNAME";
	
	//~ Instance fields ========================================================

	private Properties exceptionMappings = new Properties();
	
    /** Where to redirect the browser to if authentication fails */
    protected String authenticationFailureUrl;

    /**
     * Where to redirect the browser to if authentication is successful but ACEGI_SAVED_REQUEST_KEY is
     * <code>null</code>
     */
    protected String defaultTargetUrl;

    /**
     * If <code>true</code>, will always redirect to the value of {@link #getDefaultTargetUrl} upon successful authentication,
     * irrespective of the page that caused the authentication request (defaults to <code>false</code>).
     */
    protected boolean alwaysUseDefaultTargetUrl = false;
    
    protected UserManager userManager;

    //~ Constructors ===========================================================

	//~ Methods ================================================================

    public void afterPropertiesSet() throws Exception {
        Assert.hasLength(defaultTargetUrl, "defaultTargetUrl must be specified");
        Assert.hasLength(authenticationFailureUrl, "authenticationFailureUrl must be specified");
        Assert.notNull(userManager, "userManager must be specified");
        Assert.notNull(authenticationManager, "authenticationManager must be specified");
    }
    
	/*
	 * @see com.painiu.security.AbstractAuthenticationHandler#getDefaultFilterProcessesUrl()
	 */
	@Override
	public String getDefaultFilterProcessesUrl() {
		return "/j_security_check";
	}

	/*
	 * @see com.painiu.security.AuthenticationHandler#attemptAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        // Place the last username attempted into HttpSession for views
        request.getSession().setAttribute(SECURITY_LAST_USERNAME_KEY, 
        		request.getParameter(SECURITY_FORM_USERNAME_KEY));

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
	}

	/*
	 * @see com.painiu.security.AuthenticationHandler#onSuccessfulAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.acegisecurity.Authentication)
	 */
	public void onSuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, Authentication authResult)
			throws IOException {
		
		// Don't attempt to obtain the url from the saved request if alwaysUsedefaultTargetUrl is set
        String targetUrl = alwaysUseDefaultTargetUrl ? null : obtainFullRequestUrl(request);

        if (targetUrl == null) {
            targetUrl = getDefaultTargetUrl();
        }

        if (log.isDebugEnabled()) {
            log.debug("Redirecting to target URL from HTTP Session (or default): " + targetUrl);
        }
        
        sendRedirect(request, response, targetUrl);
	}
	
    public static String obtainFullRequestUrl(HttpServletRequest request) {
    	String url = AbstractProcessingFilter.obtainFullRequestUrl(request);
    	
        if (url == null) {
        	url = request.getParameter(SECURITY_FORM_URI_KEY);
        }
        
        if (url == null) {
        	url = (String) request.getSession().getAttribute(SECURITY_FORWARD_KEY);
        }
        
        return url;
    }
    
	/*
	 * @see com.painiu.security.AuthenticationHandler#onUnsuccessfulAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.acegisecurity.AuthenticationException)
	 */
	public void onUnsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException {
		
		String failureUrl = getFailureUrl(request, response, failed);

        if (log.isDebugEnabled()) {
            log.debug("Authentication request failed: " + failed.toString());
        }
        
        sendRedirect(request, response, failureUrl);
	}
	
	public String getFailureUrl(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed) {
		if (request.getParameter(SECURITY_AUTHENTICATION_FAILURE_URL_KEY) != null) {
			return request.getParameter(SECURITY_AUTHENTICATION_FAILURE_URL_KEY);
		}
		return exceptionMappings.getProperty(failed.getClass().getName(), authenticationFailureUrl);
	}

	/*
	 * @see com.painiu.security.AuthenticationHandler#supports(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public boolean supports(HttpServletRequest request,
			HttpServletResponse response) {
		return true;
	}
	
	protected User obtainUser(HttpServletRequest request) {
		String usernameOrEmail = request.getParameter(SECURITY_FORM_USERNAME_KEY);
		
		if (usernameOrEmail == null) {
			return null;
		}
		
		User user = null;
		
		if (ValidationUtils.isEmail(usernameOrEmail)) {
			
			try {
				user = userManager.getUserByEmail(usernameOrEmail);
			} catch (DataRetrievalFailureException e) {
				if (log.isDebugEnabled()) {
					log.debug("Can not find user by email: " + usernameOrEmail);
				}
				try {
					user = userManager.getUserByUsername(usernameOrEmail);
				} catch (DataRetrievalFailureException ex) {
					if (log.isInfoEnabled()) {
						log.info("Can not find user by username: " + usernameOrEmail);
					}
				}
			}
		} else {
			try {
				user = userManager.getUserByUsername(usernameOrEmail);
			} catch (DataRetrievalFailureException e) {
				if (log.isDebugEnabled()) {
					log.debug("Can not find user by email: " + usernameOrEmail);
				}
				try {
					user = userManager.getUserByEmail(usernameOrEmail);
				} catch (DataRetrievalFailureException ex) {
					if (log.isInfoEnabled()) {
						log.info("Can not find user by username: " + usernameOrEmail);
					}
				}
			}
		}
		
		return user;
	}
	
	protected String obtainUsername(HttpServletRequest request) {
		User user = obtainUser(request);
		return user == null ? null : user.getId();
	}
	
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(SECURITY_FORM_PASSWORD_KEY);
    }

	//~ Accessors ==============================================================

	/**
	 * @return the alwaysUseDefaultTargetUrl
	 */
	public boolean isAlwaysUseDefaultTargetUrl() {
		return alwaysUseDefaultTargetUrl;
	}

	/**
	 * @param alwaysUseDefaultTargetUrl the alwaysUseDefaultTargetUrl to set
	 */
	public void setAlwaysUseDefaultTargetUrl(boolean alwaysUseDefaultTargetUrl) {
		this.alwaysUseDefaultTargetUrl = alwaysUseDefaultTargetUrl;
	}

	/**
	 * @return the authenticationFailureUrl
	 */
	public String getAuthenticationFailureUrl() {
		return authenticationFailureUrl;
	}

	/**
	 * @param authenticationFailureUrl the authenticationFailureUrl to set
	 */
	public void setAuthenticationFailureUrl(String authenticationFailureUrl) {
		this.authenticationFailureUrl = authenticationFailureUrl;
	}

	/**
	 * @return the defaultTargetUrl
	 */
	public String getDefaultTargetUrl() {
		return defaultTargetUrl;
	}

	/**
	 * @param defaultTargetUrl the defaultTargetUrl to set
	 */
	public void setDefaultTargetUrl(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}

	/**
	 * @return the exceptionMappings
	 */
	public Properties getExceptionMappings() {
		return new Properties(exceptionMappings);
	}

	/**
	 * @param exceptionMappings the exceptionMappings to set
	 */
	public void setExceptionMappings(Properties exceptionMappings) {
		this.exceptionMappings = exceptionMappings;
	}
	
	/**
	 * @return the userManager
	 */
	public UserManager getUserManager() {
		return userManager;
	}
	
	/**
	 * @param userManager the userManager to set
	 */
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
}
