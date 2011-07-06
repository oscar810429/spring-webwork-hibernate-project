/*
 * @(#)ContainerFilter.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.filter;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.acegisecurity.AccessDeniedException;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.painiu.Painiu;
import com.painiu.cache.Cache;
import com.painiu.cache.CacheManager;
//import com.painiu.logic.CollaboratorManager;
//import com.painiu.model.PartnerDomain;
import com.painiu.session.Session;
import com.painiu.session.SessionManagerFactory;
//import com.painiu.webapp.personality.MultiDomainContext;
//import com.painiu.webapp.personality.MultiDomainContextHolder;
//import com.painiu.webapp.personality.MultiDomainContextImpl;
import com.painiu.webapp.personality.MultiDomainContext;
import com.painiu.webapp.personality.MultiDomainContextHolder;
import com.painiu.webapp.personality.MultiDomainContextImpl;
import com.painiu.webapp.wrapper.CookieFilterResponseWrapper;
import com.painiu.webapp.wrapper.EncodeURLResponseWrapper;
import com.painiu.webapp.wrapper.RemoteAddressRequestWrapper;
import com.painiu.webapp.wrapper.ReplaceSessionRequestWrapper;

/**
 * <p>
 * <a href="ContainerFilter.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ContainerFilter.java 156 2010-12-24 00:56:15Z zhangsf $
 */
public class ContainerFilter extends OncePerRequestFilter {
	
//	private static final String FILTER_APPLIED = "__cookie_domain_filter_applied";
	
	private static final String CACHE_NAME = "accesses.counter";
	
//	private static final long MAX_ACCESS_PER_SECOND = 2L;
	private static final long MAX_ACCESS_PER_MINUTE = 20L;
	
	private static final long CHECK_INTERVAL = 1000 * 60 * 2L;
	
//	private FilterConfig filterConfig;
	private boolean enableAccessProtect;
	private boolean addNoCacheHeader;
	
	//private CollaboratorManager collaboratorManager;
	
	//~ Methods ================================================================

	/**
	 * @param enableAccessProtect the enableAccessProtect to set
	 */
	public void setEnableAccessProtect(boolean enableAccessProtect) {
		this.enableAccessProtect = enableAccessProtect;
	}
	
	/**
	 * @param addNoCacheHeader the addNoCacheHeader to set
	 */
	public void setAddNoCacheHeader(boolean addNoCacheHeader) {
		this.addNoCacheHeader = addNoCacheHeader;
	}
	
	/*
	 * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		if (!(request instanceof RemoteAddressRequestWrapper)) {
			request = new RemoteAddressRequestWrapper(request);
		}

		/*if (!(response instanceof CookieFilterResponseWrapper)) {
			response = new CookieFilterResponseWrapper(response);
		}*/
	
		if (!(request instanceof ReplaceSessionRequestWrapper)) {
			request = new ReplaceSessionRequestWrapper(request, response, getFilterConfig().getServletContext());
		}
		
		if (!(response instanceof EncodeURLResponseWrapper)) {
			response = new EncodeURLResponseWrapper(request, response);
		}
		
		if (enableAccessProtect) {
			String addr = request.getRemoteAddr();
			if (addr != null) {
				if (addr.startsWith("127.0.0.")) {
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
					return;
				}
			}
			
			String uri = request.getRequestURI();
			if (uri.indexOf("/api") == -1 && uri.indexOf("upload") == -1) {
				try {
					accessProtect(request, response);
				} catch (AccessDeniedException e) {
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
					return;
				}
			}
		}
		
		if (addNoCacheHeader) {
			response.setHeader("Cache-Control", "no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
		}
		
		try {
			//initMultiDomainContext(request);
			filterChain.doFilter(request, response);
		} catch (IOException ioe) {
			throw ioe;
		} catch (ServletException se) {
			throw se;
		} finally {
			// Save session if it exists and have been modified.
			HttpSession session = request.getSession(false);

			if (session != null && session instanceof Session) {
				Session systemSession = (Session) session;
				if (systemSession.isDirty()) {
					SessionManagerFactory.getSessionManager().saveSession(systemSession);
				}
			}
		}
	}
	
	protected static void accessProtect(HttpServletRequest request, HttpServletResponse response) {
		String remoteAddr = request.getRemoteAddr();
		if (remoteAddr == null) {
			return;
		}

		long count = 0;
		long firstAccessTime = 0;
		long lastAccessTime = 0;
		long thisAccessTime = System.currentTimeMillis();
		
		Cache cache = CacheManager.getCache(CACHE_NAME);
		String value = (String) cache.get(remoteAddr);
		
		if (value == null) {
			// first time or cache expired
			count++;
			firstAccessTime = lastAccessTime = thisAccessTime;
			saveCounter(cache, remoteAddr, count, firstAccessTime, lastAccessTime);
			return;
		}
		
		String[] parts = StringUtils.split(value, '-');
		
		try {
			count = Long.parseLong(parts[0]);
			firstAccessTime = Long.parseLong(parts[1]);
			lastAccessTime = Long.parseLong(parts[2]);

			long interval = lastAccessTime - firstAccessTime;
			if (interval >= CHECK_INTERVAL) {
				boolean forbidden = (remoteAddr.startsWith("113.107.103.")) ? false : (count * 1000 * 60 / interval > MAX_ACCESS_PER_MINUTE); 
//				boolean forbidden = (count * 1000 * 60 / interval > MAX_ACCESS_PER_MINUTE);
				
				if (forbidden) {
					// Too fast, slow it down.
					count++;
					lastAccessTime = thisAccessTime;
					saveCounter(cache, remoteAddr, count, firstAccessTime, lastAccessTime);
					throw new AccessDeniedException("Ohoh, slow down please");
				}
				// Normal accesses, clear the counter
				count = 1;
				firstAccessTime = lastAccessTime = thisAccessTime;
			} else {
				count++;
				lastAccessTime = thisAccessTime;
			}
			
		} catch (NumberFormatException e) {
			count++;
			firstAccessTime = lastAccessTime = thisAccessTime;
		}
		
		saveCounter(cache, remoteAddr, count, firstAccessTime, lastAccessTime);
	}
	
	private static void saveCounter(Cache cache, String remoteAddr, long count, long firstAccessTime, long lastAccessTime) {
		cache.put(remoteAddr, StringUtils.join(new Object[] { count, firstAccessTime, lastAccessTime }, '-'));
	}
	
	//it must be ahead of initDomainContext;
	private void initMultiDomainContext(HttpServletRequest request) {
		String serverName = request.getHeader("Host");
		if (serverName == null) {
			serverName = request.getServerName();
		}
		
		MultiDomainContext multiDomainContext =  new MultiDomainContextImpl();
		/*List domains = getCollaboratorManager().getPartnerDomains();
		for (Iterator i = domains.iterator(); i.hasNext();) {
			PartnerDomain domain = (PartnerDomain)i.next();
			if (serverName.endsWith(domain.getDomain())) {
				multiDomainContext.setDomain(domain.getDomain());
				multiDomainContext.setUserDomainPattern(domain.getUserDomainPattern());
				multiDomainContext.setFlashDomain(domain.getFlashDomain());
				multiDomainContext.setHost(domain.getHost());
				multiDomainContext.setMediaRoot(domain.getMediaRoot());
				multiDomainContext.setPhotoURLPattern(domain.getPhotoURLPattern());
				multiDomainContext.setIconURLPattern(domain.getIconURLPattern());
				multiDomainContext.setPartnerName(domain.getPartnerName());
				break;
			}
		}*/
		if (multiDomainContext.getDomain() == null) {
			if (serverName.endsWith(Painiu.DOMAIN_DEV)) {
				multiDomainContext.setDomain("painiu.com");
				multiDomainContext.setUserDomainPattern("http://${username}.painiu.com");
				multiDomainContext.setFlashDomain("http://f.painiu.com");
				multiDomainContext.setHost("www.painiu.com");
				multiDomainContext.setMediaRoot("http://www.painiu.com");
				multiDomainContext.setPhotoURLPattern("http://photo.painiu.com/${dir}/${filename}/${suffix}/");
				multiDomainContext.setIconURLPattern("http://ico.painiu.com/${user}/${filename}/");
				multiDomainContext.setPartnerName("Painiu");
			} else {
				multiDomainContext.setDomain("painiu.com");
				multiDomainContext.setUserDomainPattern("http://${username}.painiu.com");
				multiDomainContext.setFlashDomain("http://f.painiu.com");
				multiDomainContext.setHost("www.painiu.com");
				multiDomainContext.setMediaRoot("http://www.painiu.com");
				multiDomainContext.setPhotoURLPattern("http://photo.painiu.com/${dir}/${filename}/${suffix}/");
				multiDomainContext.setIconURLPattern("http://ico.painiu.com/${user}/${filename}/");
				multiDomainContext.setPartnerName("Paniu");
			}
		}
		MultiDomainContextHolder.setContext(multiDomainContext);
	}

    protected WebApplicationContext getWebApplicationContext() {
    	return WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
    }
    
	/*protected CollaboratorManager getCollaboratorManager() {
		if (collaboratorManager == null) {
			collaboratorManager = (CollaboratorManager) getWebApplicationContext().getBean("collaboratorManager");
		}
		return collaboratorManager;
	}*/
}
