/*
 * @(#)ApiDispatcherServlet.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.servlet;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlrpc.ParseFailed;
import org.apache.xmlrpc.XmlRpc;
import org.apache.xmlrpc.XmlRpcRequestProcessor;
import org.apache.xmlrpc.XmlRpcServerRequest;

import com.opensymphony.webwork.dispatcher.DispatcherUtils;
import com.opensymphony.webwork.dispatcher.mapper.ActionMapping;
import com.opensymphony.webwork.dispatcher.multipart.MultiPartRequest;
import com.opensymphony.xwork.ActionContext;
import com.painiu.Context;
import com.painiu.core.model.User;
import com.painiu.service.api.ApiAuth;
import com.painiu.service.api.ApiContextImpl;
//import com.painiu.webapp.action.PhotoUploadAction;
import com.painiu.service.api.ApiContext;
import com.painiu.service.api.ApiContextHolder;
import com.painiu.service.api.ApiException;
import com.painiu.service.api.ApiObject;
import com.painiu.service.api.Call;
import com.painiu.service.api.Parameters;
import com.painiu.service.api.util.ResponseFormatUtils;
import com.painiu.webapp.upload.LazyParseMultipartRequest;

/**
 * <p>
 * <a href="ApiDispatcherServlet.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ApiDispatcherServlet.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class ApiDispatcherServlet extends BaseServlet {
	//~ Static fields/initializers =============================================

	private static final Log log = LogFactory.getLog(ApiDispatcherServlet.class);
	
	static {
		if (XmlRpc.getDefaultInputEncoding() == null) {
			XmlRpc.setDefaultInputEncoding("UTF-8");
		}
	}
	
	//~ Instance fields ========================================================

	private ServletConfig config;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================
	
	/*
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		this.config = config;
	}

	/*
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		
		if (pathInfo.length() < 6) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return ;
		}
		
		if (req.getParameter("self") != null) {
			setupApiContext(req);
		}
		
		//setupApiContext(req);
	    
		pathInfo = pathInfo.substring(1, pathInfo.length() - 1);
		
		String[] parts = StringUtils.split(pathInfo, '/');
		
		boolean debug = parts.length > 1 && "debug".equals(parts[1]);
		
		if ("rest".equals(parts[0])) {
			doRest(req, resp, debug);
		} else if ("xmlrpc".equals(parts[0])) {
			doXmlrpc(req, resp, debug);
		} else {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
	
	private void setupApiContext(HttpServletRequest req) {		
		ApiAuth auth = (ApiAuth) req.getSession().getAttribute("api_auth");
		User user = Context.getContext().getCurrentUser();
		
		if (auth == null) {
			auth = Context.newApiAuth(user);
			req.getSession().setAttribute("api_auth", auth);
		}
		
		ApiContextHolder.setContext(new ApiContextImpl(null, user, auth));
	}
	
	/**
	 * @param req
	 * @param resp
	 */
	private void doRest(HttpServletRequest req, HttpServletResponse resp, boolean debug) 
	throws IOException {
		
		if (log.isDebugEnabled()) {
			log.debug("requestURI:" + req.getRequestURI());
			log.debug("queryString:" + req.getQueryString());
		}
		
		String[] array = parseTargetMethod(req.getParameter("method"));
		
		if (log.isDebugEnabled()) {
			log.debug("array[0]:" + array[0]);
			log.debug("array[1]:" + array[1]);
		}
		
		Call call = new Call(array[0], array[1], new Parameters(req.getParameterMap()));
		invokeMethod(req, resp, call, "rest", debug);
	}
	
	/**
	 * @param req
	 * @param resp
	 */
	private void doXmlrpc(HttpServletRequest req, HttpServletResponse resp, boolean debug)
	throws IOException {
		
		XmlRpcServerRequest xmlRpcReq = null;
		try {
			xmlRpcReq = new XmlRpcRequestProcessor().decodeRequest(req.getInputStream());
		} catch (ParseFailed e) {
			log.warn("Invalid XML-RPC request");
			
			response(resp, ApiException.INVALID_XMLRPC_CALL(), 
					ResponseFormatUtils.getFormat(req.getParameter("format"), "xmlrpc"));
			return;
		}
		
		String[] array = parseTargetMethod(xmlRpcReq.getMethodName());
		
		// prepare parameters
		Map map = new HashMap();
		map.put("method", xmlRpcReq.getMethodName());
		
		Vector vector = xmlRpcReq.getParameters();
		
		for (Iterator i = vector.iterator(); i.hasNext();) {
			map.putAll((Map) i.next());
		}
		
		Call call = new Call(array[0], array[1], new Parameters(map));
		
		invokeMethod(req, resp, call, "xmlrpc", debug);
	}
	
	private void invokeMethod(HttpServletRequest req, HttpServletResponse resp, Call call, String defaultFormat, boolean debug) 
	throws IOException {
		Object result = null;
		
		try {
			result = getApiHandler().execute(call);
		} catch (ApiException e) {
			if (log.isInfoEnabled()) {
				log.info("ApiException: " + e.getCode() + ", " + e.getMessage());
			}
			result = e;
		}
		
		if (debug && result instanceof ApiException) {
			((ApiException) result).setCall(call);
		}
		
		String format = ResponseFormatUtils.getFormat(call.getParameters().getString("format"), defaultFormat);
		
		response(resp, result, format);
	}
	
	private void response(HttpServletResponse response, Object object, String format) 
	throws IOException {
		StringWriter buffer = new StringWriter();
		ResponseFormatUtils.format(buffer, object, format);
		
		byte[] content = buffer.getBuffer().toString().getBytes("UTF-8");

		ResponseFormatUtils.prepareHttpServletResponseObject(response, format, content.length);
		
		response.getOutputStream().write(content);
		response.flushBuffer();
	}

	private static String[] parseTargetMethod(String apiMethod) {
		String target = null;
		String method = null;
		
		String[] array = StringUtils.split(apiMethod, '.');
		if (array == null || array.length < 3) {
			target = "nonExistsTarget";
			method = "nonExistsMethod";
		} else {
			target = StringUtils.join(ArrayUtils.subarray(array, 1, array.length - 1), '.');
			method = array[array.length - 1];
		}
		
		return new String[] { target, method };
	}
}
