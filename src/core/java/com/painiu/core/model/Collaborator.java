/*
 * @(#)Collaborator.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * <a href="Collaborator.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Collaborator.java 52 2010-06-15 09:28:19Z zhangsf $
 */
public class Collaborator extends BaseObject {

	//~ Static fields/initializers =============================================
	private static final long serialVersionUID = 8604365513672010682L;
	//~ Instance fields ========================================================

	private String id;
	private String secret;
	private String name;
	private String description;
	private String homeURL;
	private String logoURL;
//	private String userURL;
//	private String bindURL;
//	private String bindMessage;
//	private String siteId;
	private Set hostAddresses = new HashSet(3);
	private Date createdDate;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	//~ Accessors ==============================================================

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
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the secret
	 */
	public String getSecret() {
		return secret;
	}

	/**
	 * @param secret the secret to set
	 */
	public void setSecret(String secret) {
		this.secret = secret;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the homeURL
	 */
	public String getHomeURL() {
		return homeURL;
	}

	/**
	 * @param homeURL the homeURL to set
	 */
	public void setHomeURL(String homeURL) {
		this.homeURL = homeURL;
	}

	/**
	 * @return the hostAddresses
	 */
	public Set getHostAddresses() {
		return hostAddresses;
	}

	/**
	 * @param hostAddresses the hostAddresses to set
	 */
	public void setHostAddresses(Set hostAddresses) {
		this.hostAddresses = hostAddresses;
	}

	/**
	 * @return the logoURL
	 */
	public String getLogoURL() {
		return logoURL;
	}

	/**
	 * @param logoURL the logoURL to set
	 */
	public void setLogoURL(String logoURL) {
		this.logoURL = logoURL;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1354124391, -549174865)
				.append(this.id).toHashCode();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Collaborator)) {
			return false;
		}
		Collaborator rhs = (Collaborator) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id).isEquals();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("name", this.name)
				.append("description", this.description).toString();
	}

	
}
