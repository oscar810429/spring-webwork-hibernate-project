/*
 * @(#)Application.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.painiu.core.model.Authentication.Permission;

/**
 * <p>
 * <a href="Application.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Application.java 52 2010-06-15 09:28:19Z zhangsf $
 */
public class Application extends BaseObject {

	private static final long serialVersionUID = 349772444582196670L;

	//~ Static fields/initializers =============================================
	
	public static class Type extends IntegerEnum implements Serializable {

		public static final Type YUPOO        = new Type(0);
		public static final Type WEB_APP      = new Type(1);
	    public static final Type DESKTOP_APP  = new Type(2);
	    public static final Type MOBILE_APP   = new Type(3);
	    
	    private Type(int value) {
	    	super(value);
	    }
	    
	    public static Type valueOf(int value) {
	    	return (Type) IntegerEnum.valueOf(Type.class, value);
	    }
	}
	
	// public static final Application YUPOO = new Application(Yupoo.YUPOO_API_KEY);
	
	//~ Instance fields ========================================================
	
	private String apiKey;
	private Long sequence;
	private User user;
	private String title;
	private String description;
	private String secret;
	private Icon logo; // optional
	private String url; // optional
	private Type type = Type.WEB_APP;
	private String callbackUrl; // required only if the type is WEB_BASED 
	private Permission permission = Permission.READ; // required only if the type is MOBILE_APP
	private Date createdDate;
	private Date expireDate;
	private Boolean configured = Boolean.FALSE;

	public Application() {}
	
	public Application(String apiKey) {
		this.apiKey = apiKey;
	}
	
	//~ Accessors ==============================================================

	/**
	 * @return Returns the secret.
	 */
	public String getSecret() {
		return secret;
	}

	/**
	 * @param secret The secret to set.
	 */
	public void setSecret(String secret) {
		this.secret = secret;
	}

	/**
	 * @return Returns the sequence.
	 */
	public Long getSequence() {
		return sequence;
	}

	/**
	 * @param sequence The sequence to set.
	 */
	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	/**
	 * @return Returns the apiKey.
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * @param apiKey The apiKey to set.
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	/**
	 * @return Returns the callbackUrl.
	 */
	public String getCallbackUrl() {
		return callbackUrl;
	}

	/**
	 * @param callbackUrl The callbackUrl to set.
	 */
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the logo.
	 */
	public Icon getLogo() {
		return logo;
	}

	/**
	 * @param logo The logo to set.
	 */
	public void setLogo(Icon logo) {
		this.logo = logo;
	}

	/**
	 * @return Returns the title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return Returns the type.
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @param type The type to set.
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * @return Returns the url.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url The url to set.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return Returns the createdDate.
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate The createdDate to set.
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return Returns the user.
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user The user to set.
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return Returns the expireDate.
	 */
	public Date getExpireDate() {
		return expireDate;
	}

	/**
	 * @param expireDate The expireDate to set.
	 */
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	/**
	 * @return Returns the configured.
	 */
	public Boolean getConfigured() {
		return configured;
	}

	/**
	 * @param configured The configured to set.
	 */
	public void setConfigured(Boolean configged) {
		this.configured = configged;
	}
	
	/**
	 * @return Returns the permission.
	 */
	public Permission getPermission() {
		return permission;
	}

	/**
	 * @param permission The permission to set.
	 */
	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Application)) {
			return false;
		}
		Application rhs = (Application) object;
		return new EqualsBuilder()
				.append(this.apiKey, rhs.apiKey).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-624434707, 275978387)
				.append(this.apiKey).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("title", this.title).toString();
	}
}
