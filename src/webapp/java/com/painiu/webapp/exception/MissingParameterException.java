/**
 * @(#)MissingParameterException.java Oct 22, 2008
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.exception;

/**
 * <p>
 * <a href="MissingParameterException.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: MissingParameterException.java 6 2010-05-11 16:20:57Z zhangsf $
 */
public class MissingParameterException extends RuntimeException {
    private static final long serialVersionUID = 8003596184005996583L;
	
	private String parameterName;

	private String parameterType;


	/**
	 * Constructor for MissingServletRequestParameterException.
	 * @param parameterName the name of the missing parameter
	 * @param parameterType the expected type of the missing parameter
	 */
	public MissingParameterException(String parameterName, String parameterType) {
		super("");
		this.parameterName = parameterName;
		this.parameterType = parameterType;
	}

	public MissingParameterException(String msg) {
		super(msg);
	}

	public String getMessage() {
		return "Required " + this.parameterType + " parameter '" + this.parameterName + "' is not present";
	}

	/**
	 * Return the name of the offending parameter.
	 */
	public String getParameterName() {
		return this.parameterName;
	}

	/**
	 * Return the expected type of the offending parameter.
	 */
	public String getParameterType() {
		return this.parameterType;
	}

}