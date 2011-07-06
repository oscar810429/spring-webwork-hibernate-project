/*
 * @(#)Context.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.painiu.core.model.Area;
import com.painiu.core.model.User;
import com.painiu.core.model.Authentication.Permission;
import com.painiu.core.security.SecurityUtils;
import com.painiu.core.service.AbuseManager;
import com.painiu.core.service.UserManager;
import com.painiu.core.service.AreaManager;
import com.painiu.service.api.ApiAuth;
import com.painiu.util.RandomGUID;
import com.painiu.webapp.personality.DomainContext;
import com.painiu.webapp.personality.DomainContextHolder;
import com.painiu.webapp.personality.DomainContextImpl;
import com.painiu.webapp.personality.MultiDomainContextHolder;

/**
 * Request Context
 * 
 * <p>
 * <a href="Context.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Context.java 150 2010-12-08 03:48:09Z zhangsf $
 */
public class Context implements Serializable {
	//~ Static fields/initializers =============================================

	private static final Log log = LogFactory.getLog(Context.class);
	
	static ThreadLocal threadLocal = new ContextThreadLocal();
	
	//~ Instance fields ========================================================
	
	private Map context;

	private ServletContext servletContext;
	
	private HttpServletRequest request;
	
	// Spring ApplicationContext
	private WebApplicationContext webApplicationContext;
	
	private User user;
	
	private Locale locale;

	//~ Constructors ===========================================================

	/**
	 * Creates a new Context initialized with another context.
	 *
	 * @param context a context map.
	 */
	public Context(Map context) {
		this.context = context;
	}

	//~ Methods ================================================================

	public void initContext(HttpServletRequest request, ServletContext servletContext) {
		this.request = request;
		this.servletContext = servletContext;

		// Get root application context
		this.webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		if (webApplicationContext == null) {
			throw new IllegalStateException("No WebApplicationContext found: no ContextLoaderListener registered?");
		}

		user = SecurityUtils.getCurrentUser();
		
		initLocale();
		
		//initDomainContext();
		initApiContext();
	}
	
	private void initDomainContext() {
//		String serverName = request.getServerName();
		String serverName = request.getHeader("Host");
		if (serverName == null) {
			serverName = request.getServerName();
		}

		String areaname = parseAreaname(serverName);
    	
    	if (areaname != null) {
    		if (log.isDebugEnabled()) {
    			log.debug("Personality url: " + serverName);
    		}

    		HttpSession session = request.getSession();
    		
    		DomainContext domainContext = (DomainContext) session.getAttribute(Painiu.DOMAIN_CONTEXT);
    		
    		if (domainContext != null && domainContext.getArea() != null 
    				&& domainContext.getArea().getEname().equals(areaname)) {
    			DomainContextHolder.setContext(domainContext);
    		} else {
				AreaManager areaManager = (AreaManager) webApplicationContext.getBean("areaManager");
				
    			if (log.isDebugEnabled()) {
					log.debug("Look up domain Region[areaname:" + areaname + "]");
				}

				Area domainArea = null;
				
				try {
					domainArea = areaManager.getArea(areaname);
				} catch (ObjectRetrievalFailureException e) {
					if (log.isWarnEnabled()) {
						log.warn("Bad personality url[" + serverName + "], user not found");
					}
				}
				
				if (domainArea != null) {
					domainContext = new DomainContextImpl(domainArea);
					DomainContextHolder.setContext(domainContext);
					
					session.setAttribute(Painiu.DOMAIN_CONTEXT, domainContext);
				}
    		}
    	}
	}
	
	private void initLocale() {
		// Determine locale to use for this RequestContext.
		HttpSession session = request.getSession();
		
		if (user != null) {
    		//User userInSession = (User) session.getAttribute(HospGroup.USER_KEY);
    		locale = user.getLocale();
    		
    		/*if (userInSession == null || !user.equals(userInSession)) {
    			session.setAttribute(HospGroup.USER_KEY, user);
    			if (locale != null) {
    				// Setup JSTL fmt default locale. 
    				Config.set(session, Config.FMT_LOCALE, locale);
    			}
    		}*/
    		
    		if (locale != null) {
    			LocaleContextHolder.setLocale(locale);
    		}
    	} else {
    		locale = request.getLocale();
    	}
    	
    	if (LocaleContextHolder.getLocaleContext() == null) {
    		// use locale from request/browser.
    		LocaleContextHolder.setLocale(locale);
    	}
	}
	
	private void initApiContext() {
		if (request.getRequestURI().indexOf(Painiu.API_ENDPOINT) == -1) {
			HttpSession session = request.getSession();
			
			ApiAuth apiAuth = (ApiAuth) session.getAttribute("api_auth");
			
			if (apiAuth == null || (apiAuth.getPrincipal() == null && user != null)) {
				apiAuth = newApiAuth(user);
				session.setAttribute("api_auth", apiAuth);
			}
		}
	}
	
	public static ApiAuth newApiAuth(User user) {
		String token = user == null ? null : RandomGUID.generate();
		String principal = user == null ? null : user.getId();
		
		return new ApiAuth(token, principal, 
				RandomGUID.generate(), RandomStringUtils.randomAlphanumeric(16).toLowerCase(),
				Permission.ALL);
	}
	
	
	private static String parseUsername(String serverName) {
		String domain = MultiDomainContextHolder.getContext().getDomain();
		String username = null;
		if (serverName.length() > domain.length() && serverName.endsWith(domain)) {
			username = serverName.substring(0, serverName.indexOf(domain) - 1);
		}
		if ("".equals(username) || "www".equals(username) || "api".equals(username)) {
			username = null;
		}
		return username;
	}
	
	private static String parseAreaname(String serverName) {
		String domain = MultiDomainContextHolder.getContext().getDomain();
		String areaname = null;
		if (serverName.length() > domain.length() && serverName.endsWith(domain)) {
			areaname = serverName.substring(0, serverName.indexOf(domain) - 1);
		}
		if ("".equals(areaname) || "www".equals(areaname) || "api".equals(areaname)) {
			areaname = null;
		}
		return areaname;
	}
	
	//~ Accessors ==============================================================

    /**
     * Sets the action context for the current thread.
     *
     * @param context the action context.
     */
    public static void setContext(Context context) {
        threadLocal.set(context);
    }

    /**
     * Returns the ActionContext specific to the current thread.
     *
     * @return the ActionContext for the current thread.
     */
    public static Context getContext() {
        Context context = (Context) threadLocal.get();

        if (context == null) {
        	context = new Context(new HashMap());
            setContext(context);
        }

        return context;
    }

    /**
     * Sets the action's context map.
     *
     * @param contextMap the context map.
     */
    public void setContextMap(Map contextMap) {
        getContext().context = contextMap;
    }

    /**
     * Gets the context map.
     *
     * @return the context map.
     */
    public Map getContextMap() {
        return context;
    }

    /**
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}
	
	/**
	 * @param request the request to set
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	/**
	 * @return the servletContext
	 */
	public ServletContext getServletContext() {
		return servletContext;
	}
	
	/**
	 * @param servletContext the servletContext to set
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	/**
	 * @return the webApplicationContext
	 */
	public WebApplicationContext getWebApplicationContext() {
		return webApplicationContext;
	}
	
	/**
	 * @param webApplicationContext the webApplicationContext to set
	 */
	public void setWebApplicationContext(
			WebApplicationContext webApplicationContext) {
		this.webApplicationContext = webApplicationContext;
	}
	
	public static ApplicationContext getApplicationContext() {
		return (ApplicationContext) getContext().get(Painiu.APPLICATION_CONTEXT_KEY);
	}
	
	public static void setApplicationContext(ApplicationContext applicationContext) {
		getContext().put(Painiu.APPLICATION_CONTEXT_KEY, applicationContext);
	}
	
	public User getCurrentUser() {
		return user;
	}
	
	public void setCurrentUser(User user) {
		this.user = user;
	}
    
    /**
     * Sets the Locale for the current action.
     *
     * @param locale the Locale for the current action.
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * Gets the Locale of the current request. If no locale was ever specified the platform's
     * {@link java.util.Locale#getDefault() default locale} is used.
     *
     * @return the Locale of the current action.
     */
    public Locale getLocale() {
        if (locale == null) {
        	locale = request.getLocale();
        	
        	if (locale == null) {
        		locale = Locale.getDefault();
        	}
            setLocale(locale);
        }

        return locale;
    }
    
    /**
     * Returns a value that is stored in the current ActionContext by doing a lookup using the value's key.
     *
     * @param key the key used to find the value.
     * @return the value that was found using the key or <tt>null</tt> if the key was not found.
     */
    public Object get(Object key) {
        return context.get(key);
    }
    
    /**
     * Stores a value in the current ActionContext. The value can be looked up using the key.
     *
     * @param key   the key of the value.
     * @param value the value to be stored.
     */
    public void put(Object key, Object value) {
        context.put(key, value);
    }

	public String getMessage(MessageSourceResolvable resolvable) throws NoSuchMessageException {
		return this.webApplicationContext.getMessage(resolvable, getLocale());
	}

	public String getMessage(String code, Object[] args) throws NoSuchMessageException {
		return this.webApplicationContext.getMessage(code, args, getLocale());
	}

	public String getMessage(String code, Object[] args, String defaultMessage) {
		return this.webApplicationContext.getMessage(code, args, defaultMessage, getLocale());
	}


	private static class ContextThreadLocal extends ThreadLocal {
        protected Object initialValue() {
            return new Context(new HashMap());
        }
    }
	
}
