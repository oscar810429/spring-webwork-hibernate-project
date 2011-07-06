/*
 * @(#)Feature.java 2007-12-12
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * <a href="Feature.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author 5jxiang
 * @version $Id: Feature.java 135 2010-11-23 09:28:01Z zhangsf $
 */
public class Feature {
	//~ Static fields/initializers =============================================
	
	//~ Instance fields ========================================================
	private String id;
	
	private String title;
	
	private String content;
	
	private Date releasedDate;
	
	private Date createdDate;
	
	private Date modifiedDate;
	
	private Integer kind;
	
	private Integer order;
	
	private String url;
	
	private Integer urlBlank;

	//~ Constructors ===========================================================
	
	//~ Methods ================================================================

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getKind() {
		return kind;
	}

	public void setKind(Integer kind) {
		this.kind = kind;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getUrlBlank() {
		return urlBlank;
	}

	public void setUrlBlank(Integer urlBlank) {
		this.urlBlank = urlBlank;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Feature)) {
			return false;
		}
		Feature rhs = (Feature) object;
		return new EqualsBuilder().append(
				this.createdDate, rhs.createdDate).append(this.kind, rhs.kind)
				.append(this.title, rhs.title).append(this.url, rhs.url)
				.append(this.id, rhs.id)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(851193151, 2000048451).appendSuper(
				super.hashCode()).append(this.kind)
				.append(this.title).append(this.id).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("title", this.title)
				.append("content", this.content).toString();
	}

	public Date getReleasedDate() {
		return releasedDate;
	}

	public void setReleasedDate(Date releasedDate) {
		this.releasedDate = releasedDate;
	}
	
	//~ Accessors ==============================================================

}
