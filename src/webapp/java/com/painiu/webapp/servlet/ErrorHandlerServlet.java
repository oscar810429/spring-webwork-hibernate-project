/**
 * @(#)ErrorHandlerServlet.java Oct 22, 2008
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * <a href="ErrorHandlerServlet.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ErrorHandlerServlet.java 6 2010-05-11 16:20:57Z zhangsf $
 */
public class ErrorHandlerServlet extends HttpServlet {
	//~ Static fields/initializers =============================================

	private static final long serialVersionUID = 4959804995592161781L;
	
	public static final String ERROR_EXCEPTION="javax.servlet.error.exception";
	public static final String ERROR_EXCEPTION_TYPE="javax.servlet.error.exception_type";
	public static final String ERROR_MESSAGE="javax.servlet.error.message";
	public static final String ERROR_REQUEST_URI="javax.servlet.error.request_uri";
	public static final String ERROR_SERVLET_NAME="javax.servlet.error.servlet_name";
	public static final String ERROR_STATUS_CODE="javax.servlet.error.status_code";

	//~ Instance fields ========================================================

	//~ Constructors ===========================================================

	//~ Methods ================================================================

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Integer status = (Integer) req.getAttribute(ERROR_STATUS_CODE);
		String message = (String) req.getAttribute(ERROR_MESSAGE);
		Throwable exception = (Throwable) req.getAttribute(ERROR_EXCEPTION);
		Class exceptionType = (Class) req.getAttribute(ERROR_EXCEPTION_TYPE);
		String requestedUri = (String) req.getAttribute(ERROR_REQUEST_URI);
		
		if (message != null) 
			resp.getWriter().write(message);
		else
			resp.getWriter().write("Hello, Error");
		resp.flushBuffer();
	}
}
