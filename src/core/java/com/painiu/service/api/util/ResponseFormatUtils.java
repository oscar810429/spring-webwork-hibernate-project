/*
 * @(#)ResponseFormatUtils.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.service.api.util;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import com.painiu.service.api.ResponseFormat;
import com.painiu.service.api.format.JSONResponseFormat;
import com.painiu.service.api.format.RestResponseFormat;
import com.painiu.service.api.format.XmlRpcResponseFormat;

/**
 * <p>
 * <a href="ResponseFormatUtils.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ResponseFormatUtils.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class ResponseFormatUtils {
	
	public static final String REST = "rest";
	public static final String JSON = "json";
	public static final String XMLRPC = "xmlrpc";
	
	private static final ResponseFormat REST_FORMAT = new RestResponseFormat();
	private static final ResponseFormat JSON_FORMAT = new JSONResponseFormat();
	private static final ResponseFormat XMLRPC_FORMAT = new XmlRpcResponseFormat();
	
	public static void prepareHttpServletResponseObject(HttpServletResponse response, String format, int contentLength) {
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.addHeader("Content-Length", String.valueOf(contentLength));
		
		if (REST.equals(format)) {
			response.setContentType(REST_FORMAT.getContentType());
		} else if (JSON.equals(format)) {
			response.setContentType(JSON_FORMAT.getContentType());
		} else if (XMLRPC.equals(format)) {
			response.setContentType(XMLRPC_FORMAT.getContentType());
		}
	}
	
	public static void format(Writer out, Object object, String format) throws IOException {
		if (REST.equals(format)) {
			REST_FORMAT.format(out, object);
		} else if (JSON.equals(format)) {
			JSON_FORMAT.format(out, object);
		} else if (XMLRPC.equals(format)) {
			XMLRPC_FORMAT.format(out, object);
		}
	}
	
	public static String getFormat(String format, String defaultFormat) {
		if (format == null) {
			return defaultFormat;
		}
		
		if (!REST.equals(format) && !JSON.equals(format) && !XMLRPC.equals(format)) {
			return defaultFormat;
		}
		
		return format;
	}
}
