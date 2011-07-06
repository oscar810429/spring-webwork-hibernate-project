/*
 * @(#)LazyParseMultipartRequest.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.upload;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * <a href="LazyParseMultipartRequest.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: LazyParseMultipartRequest.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public interface LazyParseMultipartRequest {
	
	//~ Static fields/initializers =============================================
	
	public static final String LAZY_PARSE_MULTIPART_REQUEST = "__lazy_parse_multipart_request_";

	//~ Methods ================================================================
	
	public HttpServletRequest getOriginalRequest();
	
	public boolean isParsed();
	
	public void parse() throws IOException;
	
}
