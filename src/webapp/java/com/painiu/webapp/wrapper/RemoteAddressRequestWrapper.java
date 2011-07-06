/*
 * @(#)RemoteAddressRequestWrapper.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.wrapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * <a href="RemoteAddressRequestWrapper.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: RemoteAddressRequestWrapper.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class RemoteAddressRequestWrapper extends HttpServletRequestWrapper {	
	//~ Static fields/initializers =============================================
	
	private static final Log log = LogFactory.getLog(RemoteAddressRequestWrapper.class);
	
	private static final String ADDRESS_HEADER = "X-Forwarded-For";
	private static final String CDN_ADDRESS_HEADER = "Cdn-Src-Ip";
	
	private static final Pattern IP_ADDRESS = Pattern.compile("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$");

	//~ Instance fields ========================================================

	private String remoteAddress;
	
	//~ Constructors ===========================================================
	
	public RemoteAddressRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	//~ Methods ================================================================
	
	/*
	 * @see javax.servlet.ServletRequestWrapper#getRemoteAddr()
	 */
	@Override
	public String getRemoteAddr() {
		if (remoteAddress == null) {
			remoteAddress = super.getRemoteAddr();
			
			if (remoteAddress == null || remoteAddress.startsWith("192.168.") || "127.0.0.1".equals(remoteAddress) || remoteAddress.startsWith("121.")) {
				String ip = getAddressFromHeader((HttpServletRequest) getRequest());
				
				if (log.isInfoEnabled()) {
					log.info("Remote ip address from X-Forwarded-For: " + ip);
				}
				
				if (ip != null) {
					remoteAddress = ip;
				}
			}
		}
		return remoteAddress;
	}

    /**
	 * @param request
	 * @return
	 */
	private static String getAddressFromHeader(HttpServletRequest request) {
		String cdnSrcIp = request.getHeader(CDN_ADDRESS_HEADER);
		
		if (cdnSrcIp != null) {
			return cdnSrcIp;
		}
		
		String forwardedFor = request.getHeader(ADDRESS_HEADER);
		
		if (log.isInfoEnabled()) {
			log.info("X-Forwarded-For Header value: " + forwardedFor);
		}
		
		if (forwardedFor == null) {
			return null;
		}
		
		String[] parts = StringUtils.split(forwardedFor, ",");
		for (int i = 0; i < parts.length; i++) {
			String addr = StringUtils.trim(parts[i]);
			
			if (addr.startsWith("192.168.") || addr.startsWith("121.")) {
				continue;
			}
			
			Matcher matcher = IP_ADDRESS.matcher(addr);

	        if (matcher.find()) {
	        	return addr;
	        }
		}
		
		return null;
	}

}
