/*
 * @(#)SmsConfig.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.config;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * <a href="SmsConfig.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SmsConfig.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public class SmsConfig {
	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	private String gateway;
	
	private String host;
	
	private String username;
	
	private String password;
	
	private String encoding;
	
	private Map smsCodeGroup = new HashMap();
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	public void setSmsCodeGroup(String code, String group) {
		smsCodeGroup.put(code, group); 
	}
	
	public String getSmsCodeGroup(String code) {
		if (smsCodeGroup.containsKey(code)) {
			return (String) smsCodeGroup.get(code);
		}
		
		return new String("");
	}
	//~ Accessors ==============================================================

	/**
	 * @return the gateway
	 */
	public String getGateway() {
		return gateway;
	}

	/**
	 * @param gateway the gateway to set
	 */
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the encoding
	 */
	public String getEncoding() {
		return encoding;
	}
	
	/**
	 * @param encoding the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * @return the smsCodeGroup
	 */
	public Map getSmsCodeGroup() {
		return smsCodeGroup;
	}

	/**
	 * @param smsCodeGroup the smsCodeGroup to set
	 */
	public void setSmsCodeGroup(Map smsCodeGroup) {
		this.smsCodeGroup = smsCodeGroup;
	}
}
