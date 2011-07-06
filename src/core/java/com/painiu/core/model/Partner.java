/*
 * @(#)Partner.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
/**
 * <p>
 * <a href="Partner.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Partner.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class Partner {
	private String id;
	private String name;
	private String key;
	private String returnUrl;
	private String ypUrl;
	private String ypKey;
	private String ypReturnUrl;
	private String token;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getYpKey() {
		return ypKey;
	}
	public void setYpKey(String ypKey) {
		this.ypKey = ypKey;
	}
	public String getYpReturnUrl() {
		return ypReturnUrl;
	}
	public void setYpReturnUrl(String ypReturnUrl) {
		this.ypReturnUrl = ypReturnUrl;
	}
	public String getYpUrl() {
		return ypUrl;
	}
	public void setYpUrl(String ypUrl) {
		this.ypUrl = ypUrl;
	}
	
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Partner)) {
			return false;
		}
		Partner rhs = (Partner) object;
		return new EqualsBuilder()
				.append(this.name, rhs.name)
				.append(this.key, rhs.key)
				.append(this.ypUrl, rhs.ypUrl)
				.append(this.ypKey, rhs.ypKey)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(1595159849, 873807081)
				.append(this.name)
				.append(this.key)
				.append(this.ypUrl)
				.append(this.ypKey)
				.append(this.id)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("name", this.name)
				.toString();
	}

}
