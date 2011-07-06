/*
 * @(#)MultipartRequest.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.upload;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.webwork.dispatcher.multipart.MultiPartRequest;

/**
 * <p>
 * <a href="MultipartRequest.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: MultipartRequest.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class MultipartRequest extends MultiPartRequest {
	//~ Static fields/initializers =============================================

	private static final String FLASH_USER_AGENT = "Shockwave";
	
	public static final String SHOCKWAVE_TEST_REQUEST = "__shockwave_test_request_";
	
	//~ Instance fields ========================================================

    private MultiPartRequest multi;
	
	//~ Constructors ===========================================================

    /**
     * Creates a new request wrapper to handle multi-part data using methods adapted from the COS
     * multipart classes (see class description).
     *
     * @param maxSize        maximum size post allowed
     * @param saveDir        the directory to save off the file
     * @param request the request containing the multipart
     */
    public MultipartRequest(HttpServletRequest request, String saveDir, int maxSize) {
    	if (request.getContentLength() == -1) {
    		try {
    			request = new UnknownLengthRequestWrapper(request, saveDir, maxSize);
    		} catch (IOException e) {
    			e.printStackTrace();
    			// TODO do something
    		}
    	}
    	if (isShockwaveRequest(request)) {
    		if (isShockwaveTestRequest(request)) {
    			multi = new DummyMultiPartRequest(request, saveDir, maxSize);
    			request.setAttribute(SHOCKWAVE_TEST_REQUEST, Boolean.TRUE);
    		} else {
    			multi = new ShockwaveMultiPartRequest(request, saveDir, maxSize);
    		}
    	} else if (request.getParameter("monitored") != null) {
    		multi = new MonitoredMultiPartRequest(request, saveDir, maxSize);
    	} else {
    		multi = new JakartaLazyParseMultiPartRequest(request, saveDir, maxSize);
    	}
    }
	
	//~ Methods ================================================================

    private static boolean isShockwaveRequest(HttpServletRequest request) {
    	String userAgent = request.getHeader("User-Agent");
    	
    	if (userAgent != null && userAgent.indexOf(FLASH_USER_AGENT) != -1) {
    		return true;
    	}
    	
    	return false;
    }
    
    private static boolean isShockwaveTestRequest(HttpServletRequest request) {
    	return isShockwaveRequest(request) && request.getContentLength() == 0;
    }
    
	/*
	 * @see com.opensymphony.webwork.dispatcher.multipart.MultiPartRequest#getContentType(java.lang.String)
	 */
	public String[] getContentType(String fieldName) {
		return multi.getContentType(fieldName);
	}

	/*
	 * @see com.opensymphony.webwork.dispatcher.multipart.MultiPartRequest#getErrors()
	 */
	public List getErrors() {
		return multi.getErrors();
	}

	/*
	 * @see com.opensymphony.webwork.dispatcher.multipart.MultiPartRequest#getFile(java.lang.String)
	 */
	public File[] getFile(String fieldName) {
		return multi.getFile(fieldName);
	}

	/*
	 * @see com.opensymphony.webwork.dispatcher.multipart.MultiPartRequest#getFileNames(java.lang.String)
	 */
	public String[] getFileNames(String fieldName) {
		return multi.getFileNames(fieldName);
	}

	/*
	 * @see com.opensymphony.webwork.dispatcher.multipart.MultiPartRequest#getFileParameterNames()
	 */
	public Enumeration getFileParameterNames() {
		return multi.getFileParameterNames();
	}

	/*
	 * @see com.opensymphony.webwork.dispatcher.multipart.MultiPartRequest#getFilesystemName(java.lang.String)
	 */
	public String[] getFilesystemName(String fieldName) {
		return multi.getFilesystemName(fieldName);
	}

	/*
	 * @see com.opensymphony.webwork.dispatcher.multipart.MultiPartRequest#getParameter(java.lang.String)
	 */
	public String getParameter(String name) {
		return multi.getParameter(name);
	}

	/*
	 * @see com.opensymphony.webwork.dispatcher.multipart.MultiPartRequest#getParameterNames()
	 */
	public Enumeration getParameterNames() {
		return multi.getParameterNames();
	}

	/*
	 * @see com.opensymphony.webwork.dispatcher.multipart.MultiPartRequest#getParameterValues(java.lang.String)
	 */
	public String[] getParameterValues(String name) {
		return multi.getParameterValues(name);
	}
    
	//~ Accessors ==============================================================
}
