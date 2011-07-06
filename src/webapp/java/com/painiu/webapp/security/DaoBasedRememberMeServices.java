/*
 * @(#)DaoBasedRememberMeServices.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.security;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;
import org.acegisecurity.providers.rememberme.RememberMeAuthenticationToken;
import org.acegisecurity.ui.AuthenticationDetailsSource;
import org.acegisecurity.ui.AuthenticationDetailsSourceImpl;
import org.acegisecurity.ui.rememberme.RememberMeServices;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestUtils;

import com.painiu.core.service.UserManager;
import com.painiu.webapp.util.CookieUtils;
import com.painiu.webapp.util.RequestUtils;

/**
 * <p>
 * <a href="DaoBasedRememberMeServices.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: DaoBasedRememberMeServices.java 126 2010-11-22 13:38:14Z zhangsf $
 */
public class DaoBasedRememberMeServices implements RememberMeServices, InitializingBean, LogoutHandler {
//~ Static fields/initializers =============================================
	
	private static final Log log = LogFactory.getLog(DaoBasedRememberMeServices.class);
	
	public static final String DEFAULT_PARAMETER = "j_remember_me";
	public static final String COOKIE_KEY = "PAINIU_SECURITY_HASHED_REMEMBER_ME_COOKIE";
	
	//~ Instance fields ========================================================
	
	private AuthenticationDetailsSource authenticationDetailsSource = new AuthenticationDetailsSourceImpl();
	private UserManager userManager;
	private UserDetailsService userDetailsService;
	private String key;
    private String parameter = DEFAULT_PARAMETER;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

    public void afterPropertiesSet() throws Exception {
        Assert.hasLength(key);
        Assert.hasLength(parameter);
        Assert.notNull(userDetailsService);
    }
    
	/**
	 * @param userManager The userManager to set.
	 */
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	/**
	 * @param userDetailsService The userDetailsService to set.
	 */
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }

	/*
	 * @see org.acegisecurity.ui.rememberme.RememberMeServices#autoLogin(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public Authentication autoLogin(HttpServletRequest request,HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();

        if ((cookies == null) || (cookies.length == 0)) {
            return null;
        }

        for (int i = 0; i < cookies.length; i++) {
            if (COOKIE_KEY.equals(cookies[i].getName())) {
                String cookieValue = cookies[i].getValue();

                if (Base64.isArrayByteBase64(cookieValue.getBytes())) {
                	if (log.isDebugEnabled()) {
                		log.debug("Remember-me cookie detected");
                	}

                	String loginCookieBase64 = userManager.checkLoginCookie(cookieValue);

                	if (loginCookieBase64 != null) {
                		String loginCookie = new String(Base64.decodeBase64(loginCookieBase64.getBytes()));

                		String[] value = StringUtils.split(loginCookie, '|');

                		// Defer lookup until after expiry time checked, to possibly avoid expensive lookup
                		UserDetails userDetails;

                		try {
                			userDetails = this.userDetailsService.loadUserByUsername(value[0]);
                		} catch (UsernameNotFoundException notFound) {
                			if (log.isWarnEnabled()) {
                				log.warn("Cookie token[0] contained userId '"
                						+ value[0] + "' but was not found");
                			}
                			cancelCookie(request, response);

                			return null;
                		}

                		// Immediately reject if the user is not allowed to login
                		if (!userDetails.isAccountNonExpired()
                				|| !userDetails.isCredentialsNonExpired()
                				|| !userDetails.isEnabled()) {
                			if (log.isWarnEnabled()) {
                				log.warn("Cookie token[0] contained userId '"
                						+ value[0]
                						        + "' but account has expired, credentials have expired, or user is disabled");
                			}
                			cancelCookie(request, response);

                			return null;
                		}

                		// By this stage we have a valid token
                		if (log.isDebugEnabled()) {
                			log.debug("Remember-me cookie accepted");
                		}

                		response.addCookie(CookieUtils.makeLoginCookie(COOKIE_KEY, loginCookieBase64, CookieUtils.getCookiePath(request)));

                		RememberMeAuthenticationToken auth = new RememberMeAuthenticationToken(this.key,userDetails.getUsername(), userDetails.getAuthorities());
                		auth.setDetails(authenticationDetailsSource.buildDetails(request));

                		return auth;
                	}
                } else {
                	cancelCookie(request, response);
                }
            }
        }

        return null;
	}

	/*
	 * @see org.acegisecurity.ui.rememberme.RememberMeServices#loginFail(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void loginFail(HttpServletRequest request,
			HttpServletResponse response) {
		if (log.isInfoEnabled()) {
			log.info("Interactive authentication attempt was unsuccessful");
		}
        cancelCookie(request, response);
	}

	/*
	 * @see org.acegisecurity.ui.rememberme.RememberMeServices#loginSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.acegisecurity.Authentication)
	 */
	public void loginSuccess(HttpServletRequest request,
			HttpServletResponse response,
			Authentication successfulAuthentication) {
        // Exit if the principal hasn't asked to be remembered
        if (!ServletRequestUtils.getBooleanParameter(request, parameter, false)) {
            if (log.isDebugEnabled()) {
                log.debug(
                    "Did not send remember-me cookie (principal did not set parameter '"
                    + this.parameter + "')");
            }

            return;
        }

        // Determine username and password, ensuring empty strings
        Assert.notNull(successfulAuthentication.getPrincipal());
//        Assert.notNull(successfulAuthentication.getCredentials());

        String username;

        if (successfulAuthentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) successfulAuthentication.getPrincipal()).getUsername();
        } else {
            username = successfulAuthentication.getPrincipal().toString();
        }

        Assert.hasLength(username);

        String tokenValue = userManager.createLoginCookie(username);
        
        if (log.isDebugEnabled()) {
            log.debug("Added remember-me cookie for user '" + username + "'");
        }
        
        response.addCookie(CookieUtils.makeLoginCookie(COOKIE_KEY, tokenValue, CookieUtils.getCookiePath(request)));
	}
	
	
	
    /*
	 * @see Dec 13, 2009.security.LogoutHandler#isCommitted(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public boolean isCommitted(HttpServletRequest request, HttpServletResponse response) {
		return false;
	}

	/*
	 * @see com.painiu.webapp.security.LogoutHandler#supports(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.acegisecurity.Authentication)
	 */
	public boolean supports(HttpServletRequest request, HttpServletResponse response, Authentication auth) {
		return true;
	}
	
	/*
	 * @see com.painiu.webapp.security.LogoutHandler#logout(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.acegisecurity.Authentication)
	 */
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		if (authentication != null) {
			String userId = null;

			if (authentication.getPrincipal() instanceof UserDetails) {
				userId = ((UserDetails) authentication.getPrincipal()).getUsername();
			} else {
				userId = authentication.getPrincipal().toString();
			}

			userManager.removeLoginCookies(userId);
		}
    	
        cancelCookie(request, response);
	}
    
	public static void cancelCookie(HttpServletRequest request,HttpServletResponse response) {
		response.addCookie(CookieUtils.makeLogoutCookie(COOKIE_KEY, CookieUtils.getCookiePath(request)));
	}
}
