/*
 * @(#)ApiContextInterceptor.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.service.api;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ObjectRetrievalFailureException;


import com.painiu.core.model.Application;
import com.painiu.core.model.Authentication;
import com.painiu.core.model.Authentication.Permission;
import com.painiu.core.model.User;
import com.painiu.core.service.ApplicationManager;
import com.painiu.core.service.AuthenticationManager;
import com.painiu.core.service.UserManager;
import com.painiu.session.Session;

/**
 * <p>
 * <a href="ApiContextInterceptor.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ApiContextInterceptor.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class ApiContextInterceptor implements MethodInterceptor {
	//~ Static fields/initializers =============================================
	
	private static final Log log = LogFactory.getLog(ApiContextInterceptor.class);
	
	//private static final String API_CONTEXT_KEY = "API_CONTEXT";
	private static final String API_AUTH_KEY = "API_AUTH_KEY";
	
	//~ Instance fields ========================================================

	private ApplicationManager applicationManager;
	private AuthenticationManager authenticationManager;
	private UserManager userManager;

	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	/*
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	public Object invoke(MethodInvocation invocation) throws Throwable {
		
		Call call = (Call) invocation.getArguments()[0];
		String apiKey = call.getParameters().getString("api_key");
		String token = call.getParameters().getString("auth_token");

		if (ApiContextHolder.getContext() != null) {
			return doProcess(invocation);
		}
		
		ApiAuth apiAuth = null;
		Authentication auth = null;
		Application app = null;
		
		User user = null;
		
		if (apiKey == null) {
			log.warn("missing api key");
			throw ApiException.INVALID_API_KEY();
		}
		
		if (token != null && !"".equals(token)) {
			if (log.isDebugEnabled()) {
				log.debug("token exists, checking session...");
			}
			
			Session session = ApiSession.getSession(token);
			
			if (log.isDebugEnabled()) {
				log.debug("try to get api context from session...");
			}
			
			apiAuth = (ApiAuth) session.getAttribute(API_AUTH_KEY);

			if (apiAuth == null) {
				if (log.isDebugEnabled()) {
					log.debug("api context not found in session, check database...");
				}
				
				try {
					auth = authenticationManager.getAuthentication(token);

					if (auth.isExpired()) {
						throw ApiException.LOGIN_FAILED_INVALID_TOKEN();
					}
					
					// read application and user now to prevent lazy loading failure.
					user = auth.getUser();
//					user.getRoles().size(); // force loading roles.
					app = auth.getApplication();
					
					if (!apiKey.equals(app.getApiKey())) {
						log.warn("token[" + token + "], api key missmatch: expect: " + app.getApiKey() + ", actual: " + apiKey);
						throw ApiException.INVALID_API_KEY();
					}
					
					if (User.State.DISABLE.equals(user.getState())) {
						log.warn("disabled user[" + user.getUsername() + "] using application[" + app.getTitle() + "] to make api call.");
						throw ApiException.USER_DISABLED();
					}
					
				} catch (ObjectRetrievalFailureException e) {
					log.warn("invalid auth token: " + token);
					throw ApiException.LOGIN_FAILED_INVALID_TOKEN();
				}
				
				apiAuth = new ApiAuth(token, user.getId(), app.getApiKey(), app.getSecret(), auth.getPermission());
				
				session.setAttribute(API_AUTH_KEY, apiAuth);
				ApiSession.saveSession(session);
			} else {
				user = userManager.getUser(apiAuth.getPrincipal());
			}
		}
			if (app == null) {
				try {
					app = applicationManager.getApplication(apiKey);
				} catch (ObjectRetrievalFailureException e) {
					log.warn("invalid api key: " + apiKey);
					throw ApiException.INVALID_API_KEY();
				}
			}
			
			if (apiAuth == null) {
				apiAuth = new ApiAuth(null, null, app.getApiKey(), app.getSecret(), Permission.NONE);
			}
			
			validateApplication(app);
			
			ApiContextHolder.setContext(new ApiContextImpl(app, user, apiAuth));
		
		return doProcess(invocation);
	}
	
	private Object doProcess(MethodInvocation invocation) throws Throwable {
		Object object = null;
		
		try {
			object = invocation.proceed();
		} finally {
			// clean up ApiContext
			ApiContextHolder.setContext(null);
		}
		
		return object;
	}
	private void validateApplication(Application app) throws ApiException {
		if (app == null) {
			throw ApiException.INVALID_API_KEY();
		}
		if (app.getExpireDate() != null && 
				System.currentTimeMillis() > app.getExpireDate().getTime()) {
			throw ApiException.EXPIRED_API_KEY();
		}
		
		if (!app.getConfigured().booleanValue()) {
			log.warn("unconfigured application[" + app.getApiKey() + "]");
			throw ApiException.UNCONFIGURED_API_KEY();
		}
	}
	

	/**
	 * @param applicationManager the applicationManager to set
	 */
	public void setApplicationManager(ApplicationManager applicationManager) {
		this.applicationManager = applicationManager;
	}

	/**
	 * @param authenticationManager the authenticationManager to set
	 */
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	/**
	 * @param userManager the userManager to set
	 */
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
}
