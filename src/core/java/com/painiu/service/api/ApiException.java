/*
 * @(#)ApiException.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.service.api;

/**
 * <p>
 * <a href="ApiException.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ApiException.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class ApiException extends Exception {
	//~ Static fields/initializers =============================================

//	public static final ApiException INVALID_SIGNATURE          = new ApiException("96", "Invalid signature");
//	public static final ApiException MISSING_SIGNATURE          = new ApiException("97", "Missing signature");
//	public static final ApiException LOGIN_FAILED_INVALID_TOKEN = new ApiException("98", "Login failed / Invalid auth token");
//	public static final ApiException NOT_AUTHORIZED             = new ApiException("99", "User not logged in / Insufficient permissions");
//	public static final ApiException INVALID_API_KEY            = new ApiException("100", "Invalid API Key");
//	public static final ApiException SERVICE_UNAVAILABLE        = new ApiException("105", "Service currently unavailable");
//	public static final ApiException METHOD_NOT_FOUND           = new ApiException("112", "Method not found");
//	public static final ApiException INVALID_XMLRPC_CALL        = new ApiException("115", "Invalid XML-RPC Method Call");
//	public static final ApiException INVALIDE_ARGUMENT          = new ApiException("124", "Invalid argument");
//	public static final ApiException MISSING_REQUIRED_ARGUMENT  = new ApiException("125", "Missing required argument");
//	public static final ApiException EXPIRED_API_KEY            = new ApiException("126", "Expired API Key");
//	public static final ApiException UNCONFIGURED_API_KEY       = new ApiException("127", "Unconfigured API Key");
//	public static final ApiException USER_DISABLED              = new ApiException("128", "User disabled");
	
	public static ApiException INVALID_SIGNATURE() {
		return new ApiException("96", "Invalid signature");
	}
	public static ApiException MISSING_SIGNATURE() {
		return new ApiException("97", "Missing signature");
	}
	public static ApiException LOGIN_FAILED_INVALID_TOKEN() {
		return new ApiException("98", "Login failed / Invalid auth token");
	}
	public static ApiException NOT_AUTHORIZED() {
		return new ApiException("99", "User not logged in / Insufficient permissions");
	}
	public static ApiException INVALID_API_KEY() {
		return new ApiException("100", "Invalid API Key");
	}
	public static ApiException SERVICE_UNAVAILABLE() {
		return new ApiException("105", "Service currently unavailable");
	}
	public static ApiException METHOD_NOT_FOUND() {
		return new ApiException("112", "Method not found");
	}
	public static ApiException INVALID_XMLRPC_CALL() {
		return new ApiException("115", "Invalid XML-RPC Method Call");
	}
	public static ApiException INVALIDE_ARGUMENT() {
		return new ApiException("124", "Invalid argument");
	}
	public static ApiException MISSING_REQUIRED_ARGUMENT() {
		return new ApiException("125", "Missing required argument");
	}
	public static ApiException EXPIRED_API_KEY() {
		return new ApiException("126", "Expired API Key");
	}
	public static ApiException UNCONFIGURED_API_KEY() {
		return new ApiException("127", "Unconfigured API Key");
	}
	public static ApiException USER_DISABLED() {
		return new ApiException("128", "User disabled");
	}
	
	//~ Instance fields ========================================================

	private String code;
	
	private String message;
	
	private String detail;
	
	private Call call;
	
	//~ Constructors ===========================================================

	public ApiException(String code, String message) {
		this(code, message, null);
	}
	
	public ApiException(String code, String message, String detail) {
		this.code = code;
		this.message = message;
		this.detail = detail;
	}
	
	//~ Methods ================================================================

	//~ Accessors ==============================================================

	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code The code to set.
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message The message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * @return the call
	 */
	public Call getCall() {
		return call;
	}

	/**
	 * @param call the call to set
	 */
	public void setCall(Call call) {
		this.call = call;
	}

	/*
	 * @see java.lang.Throwable#toString()
	 */
	public String toString() {
		return getMessage();
	}
	
	/**
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	}
	
	/**
	 * @param detail the detail to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}
}
