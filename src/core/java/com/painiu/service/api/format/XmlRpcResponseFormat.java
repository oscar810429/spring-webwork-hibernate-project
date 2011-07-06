/*
 * @(#)XmlRpcResponseFormat.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.service.api.format;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.xmlrpc.XmlRpcResponseProcessor;
import org.dom4j.Document;
import org.dom4j.Element;

import com.painiu.service.api.ApiException;
import com.painiu.service.api.ApiObject;
import com.painiu.service.api.ResponseFormat;

/**
 * <p>
 * <a href="XmlRpcResponseFormat.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: XmlRpcResponseFormat.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class XmlRpcResponseFormat extends RestResponseFormat implements ResponseFormat {
	
	public static final String CONTENT_TYPE = "text/xml; charset=utf-8";
	public static final String ENCODING = "UTF-8";
	
	/*
	 * @see com.painiu.core.service.api.ResponseFormat#getContentType()
	 */
	public String getContentType() {
		return CONTENT_TYPE;
	}

	/*
	 * @see com.painiu.core.service.api.ResponseFormat#format(java.io.Writer, java.lang.Object)
	 */
	public void format(Writer out, Object object) throws IOException {
		XmlRpcResponseProcessor processor = new XmlRpcResponseProcessor();
		
		String result = null;

		if (object instanceof ApiException) {
			ApiException apiEx = (ApiException) object;
			Exception e = apiEx;
			
			if (apiEx.getCall() != null) {
				e = new DebuggableApiException(apiEx);
			}
			
			result = new String(processor.encodeException(e, ENCODING, Integer.parseInt(apiEx.getCode())), ENCODING);
			
		} else if (object == null || object instanceof ApiObject) {
			String restResult = null;
			
			if (object == null) {
				restResult = "";
			} else {
				Document doc = createDocument();
				Element root = doc.addElement("rsp");
				createElement(root, (ApiObject) object);
				
				restResult = root.asXML();
				
				restResult = restResult.substring(5, restResult.length() - 6);
			}
			
			try {
				result = new String(processor.encodeResponse(restResult, ENCODING), ENCODING);
			} catch (Exception e) {
				// should not happen
				e.printStackTrace();
			}
			
		} else {
			throw new IllegalArgumentException("unsupported object");
		}
		
		out.write(result);
	}

	@SuppressWarnings({ "unchecked", "serial" })
	static class DebuggableApiException extends Exception {
		ApiException exception;
		DebuggableApiException(ApiException e) {
			this.exception = e;
		}
		
		/*
		 * @see java.lang.Throwable#toString()
		 */
		@Override
		public String toString() {
			StringWriter out = new StringWriter();
			try {
				writeError(out, exception);
			} catch (IOException e) {
				return exception.toString();
			}
			return out.getBuffer().toString();
		}
	}
}
