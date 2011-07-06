/*
 * @(#)Call.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.service.api;

import java.io.Serializable;

/**
 * <p>
 * <a href="Call.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Call.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class Call implements Serializable {
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================
	
	private String targetName;
	
	private String methodName;

	private Parameters parameters;
	
	//~ Constructors ===========================================================

	public Call(String targetName, String methodName, Parameters parameters) {
		this.targetName = targetName;
		this.methodName = methodName;
		this.parameters = parameters;
	}
	
	//~ Methods ================================================================
	
	public String getFullMethodName() {
		return "mingda." + getTargetName() + "." + getMethodName();
	}
	
	//~ Accessors ==============================================================
	
	/**
	 * @return Returns the methodName.
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName The methodName to set.
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * @return Returns the parameters.
	 */
	public Parameters getParameters() {
		return parameters;
	}

	/**
	 * @param parameters The parameters to set.
	 */
	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}

	/**
	 * @return Returns the targetName.
	 */
	public String getTargetName() {
		return targetName;
	}

	/**
	 * @param targetName The targetName to set.
	 */
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	
}
