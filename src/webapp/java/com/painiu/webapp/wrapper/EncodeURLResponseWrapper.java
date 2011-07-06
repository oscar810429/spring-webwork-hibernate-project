/*
 * @(#)EncodeURLResponseWrapper.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.wrapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * <a href="EncodeURLResponseWrapper.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: EncodeURLResponseWrapper.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class EncodeURLResponseWrapper extends HttpServletResponseWrapper {

	//	~ Static fields/initializers =============================================

	private static final String DEFAULT_ENCODING = "UTF-8";
	
	private static final String SESSION_PARAM_NAME = ";jsessionid=";
	
	//~ Instance fields ========================================================

	private HttpServletRequest request;
	//	private HttpServletResponse response;
	
	private String encoding = null;
	
	//~ Constructors ===========================================================
	
	public EncodeURLResponseWrapper(HttpServletRequest request, HttpServletResponse response) {
		super(response);
	
		this.request = request;
//		this.response = response;
		
		this.encoding = request.getCharacterEncoding();
		
		if (encoding == null) {
			encoding = DEFAULT_ENCODING;
		}
	}
	

	//~ Methods ================================================================
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServletResponseWrapper#encodeURL(java.lang.String)
	 */
	public String encodeURL(String url) {

		String absolute = toAbsolute(url);
		if (isEncodeable(absolute)) {
			// W3c spec clearly said 
			if (url.equalsIgnoreCase("")){
				url = absolute;
			}
			return (toEncoded(url, request.getSession().getId()));
		}
		return url;
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServletResponseWrapper#encodeRedirectURL(java.lang.String)
	 */
	public String encodeRedirectURL(String url) {
		if (isEncodeable(toAbsolute(url))) {
			return (toEncoded(url, request.getSession().getId()));
		}
		return url;
	}
	
    /**
     * Return <code>true</code> if the specified URL should be encoded with
     * a session identifier.  This will be true if all of the following
     * conditions are met:
     * <ul>
     * <li>The request we are responding to asked for a valid session
     * <li>The requested session ID was not received via a cookie
     * <li>The specified URL points back to somewhere within the web
     *     application that is responding to this request
     * </ul>
     *
     * @param network Absolute URL to be validated
     */
    protected boolean isEncodeable(final String location) {

        if (location == null)
            return false;

        // Is this an intra-document reference?
        if (location.startsWith("#"))
            return false;

        // Are we in a valid session that is not using cookies?
        final HttpSession session = request.getSession(false);
        
        if (session == null)
            return false;
        
        if (request.isRequestedSessionIdFromCookie())
            return false;
        
        return doIsEncodeable(session, location);
    }
    
    private boolean doIsEncodeable(HttpSession session, String location) {
    	// Is this a valid absolute URL?
    	URL url = null;
    	try {
    		url = new URL(location);
    	} catch (MalformedURLException e) {
    		return false;
    	}

    	// Does this URL match down to (and including) the context path?
    	if (!request.getScheme().equalsIgnoreCase(url.getProtocol()))
    		return false;
    	
    	if (!request.getServerName().equalsIgnoreCase(url.getHost()))
    		return false;
    	
    	int serverPort = request.getServerPort();
    	if (serverPort == -1) {
    		if ("https".equals(request.getScheme()))
    			serverPort = 443;
    		else
    			serverPort = 80;
    	}
    	int urlPort = url.getPort();
    	if (urlPort == -1) {
    		if ("https".equals(url.getProtocol()))
    			urlPort = 443;
    		else
    			urlPort = 80;
    	}
    	if (serverPort != urlPort)
    		return false;

    	String contextPath = request.getContextPath();
    	if (contextPath != null) {
    		String file = url.getFile();
    		if ((file == null) || !file.startsWith(contextPath))
    			return false;
    		if( file.indexOf(SESSION_PARAM_NAME + session.getId()) >= 0 )
    			return false;
    	}

//  	This URL belongs to our web application, so it is encodeable
    	return true;

    }

    /**
     * Return the specified URL with the specified session identifier
     * suitably encoded.
     *
     * @param url URL to be encoded with the session id
     * @param sessionId Session id to be included in the encoded URL
     */
    protected String toEncoded(String url, String sessionId) {

        if ((url == null) || (sessionId == null))
            return url;

        String path = url;
        String query = "";
        String anchor = "";
        int question = url.indexOf('?');
        if (question >= 0) {
            path = url.substring(0, question);
            query = url.substring(question);
        }
        int pound = path.indexOf('#');
        if (pound >= 0) {
            anchor = path.substring(pound);
            path = path.substring(0, pound);
        }
        StringBuffer sb = new StringBuffer(path);
        if( sb.length() > 0 ) { // jsessionid can't be first.
            sb.append(SESSION_PARAM_NAME);
            sb.append(sessionId);
        }
        sb.append(anchor);
        sb.append(query);
        return sb.toString();

    }
    
    /**
     * Convert (if necessary) and return the absolute URL that represents the
     * resource referenced by this possibly relative URL.  If this URL is
     * already absolute, return it unchanged.
     *
     * @param network URL to be (possibly) converted and then returned
     *
     * @exception IllegalArgumentException if a MalformedURLException is
     *  thrown when converting the relative URL to an absolute one
     */
    private String toAbsolute(String location) {

        if (location == null)
            return (location);

        boolean leadingSlash = location.startsWith("/");

        if (leadingSlash || !hasScheme(location)) {

        	StringBuffer sb = new StringBuffer();
        	
            String scheme = request.getScheme();
            String name = request.getServerName();
            int port = request.getServerPort();

            try {
                sb.append(scheme).append("://").append(name);
                
                if ((scheme.equals("http") && port != 80)
                    || (scheme.equals("https") && port != 443)) {
                    sb.append(':').append(port);
                }
                if (!leadingSlash) {
                    String relativePath = request.getRequestURI();
                    int pos = relativePath.lastIndexOf('/');
                    relativePath = relativePath.substring(0, pos);
                    
                    String encodedURI = URLEncoder.encode(relativePath, encoding);
                    
                    sb.append(encodedURI).append('/');
                }
                sb.append(location);
            } catch (IOException e) {
                throw new IllegalArgumentException(location);
            }

            return sb.toString();

        }

        return location;
    }
    
    /**
     * Determine if a URI string has a <code>scheme</code> component.
     */
    private boolean hasScheme(String uri) {
        int len = uri.length();
        for(int i=0; i < len ; i++) {
            char c = uri.charAt(i);
            if(c == ':') {
                return i > 0;
            } else if(!isSchemeChar(c)) {
                return false;
            }
        }
        return false;
    }
    
    /**
     * Determine if the character is allowed in the scheme of a URI.
     * See RFC 2396, Section 3.1
     */
    public static boolean isSchemeChar(char c) {
        return Character.isLetterOrDigit(c) ||
            c == '+' || c == '-' || c == '.';
    }
}
