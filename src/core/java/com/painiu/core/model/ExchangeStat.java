/*
 * @(#)ExchangeStat.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * <p>
 * <a href="ExchangeStat.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ExchangeStat.java 52 2010-06-15 09:28:19Z zhangsf $
 */
public class ExchangeStat extends BaseObject {

	//~ Static fields/initializers =============================================
     private static final long serialVersionUID = 7476091472777586185L;
	//~ Instance fields ========================================================
	private String id;
	private User user;
	private String url;
	private Integer viewCount = new Integer(0);
	private Integer clickCount = new Integer(0);
	private String sourceIp;
	//~ Constructors ===========================================================
	
	//~ Methods ================================================================
	
	//~ Accessors ==============================================================
	/**
	 * @return the clickCount
	 */
	public Integer getClickCount() {
		return clickCount;
	}

	/**
	 * @param clickCount the clickCount to set
	 */
	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the sourceIp
	 */
	public String getSourceIp() {
		return sourceIp;
	}

	/**
	 * @param sourceIp the sourceIp to set
	 */
	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the viewCount
	 */
	public Integer getViewCount() {
		return viewCount;
	}

	/**
	 * @param viewCount the viewCount to set
	 */
	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("viewCount", this.viewCount)
				.append("user", this.user).append("url", this.url).append(
						"clickCount", this.clickCount).append("sourceIp",
						this.sourceIp).toString();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-2007664927, -2042535799).append(this.sourceIp).append(this.user)
				.append(this.url).append(this.viewCount)
				.append(this.clickCount).toHashCode();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ExchangeStat)) {
			return false;
		}
		ExchangeStat rhs = (ExchangeStat) object;
		return new EqualsBuilder().append(
				this.sourceIp, rhs.sourceIp).append(this.user, rhs.user)
				.append(this.url, rhs.url)
				.append(this.viewCount, rhs.viewCount).append(this.clickCount,
						rhs.clickCount).isEquals();
	}

}
