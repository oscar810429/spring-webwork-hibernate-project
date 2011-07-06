/*
 * @(#)Link.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model.link;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import com.painiu.core.model.BaseObject;
import com.painiu.core.model.User;

/**
 * <p>
 * <a href="Link.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Link.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public abstract class Link extends BaseObject {
//~ Static fields/initializers =============================================

//~ Instance fields ========================================================
	protected String id;
	
	protected String title;
	
	protected String url;
	
	protected Integer urlBlank = 0;
	
	protected Integer kind;
	
	protected User author;
	
	protected Date createdDate;
	    
	protected Date modifiedDate;
	
	protected Date expiredDate;
//~ Constructors ===========================================================

//~ Methods ================================================================

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
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

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Link)) {
			return false;
		}
		Link rhs = (Link) object;
		return new EqualsBuilder()
				.append(this.modifiedDate, rhs.modifiedDate)
				.append(this.createdDate, rhs.createdDate)
				.append(this.url, rhs.url)
				.append(this.title, rhs.title)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(1595159849, 873807081)
				.append(this.modifiedDate)
				.append(this.createdDate)
				.append(this.title)
				.append(this.id)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("title", this.title)
				.toString();
	}

	public Integer getUrlBlank() {
		return urlBlank;
	}

	public void setUrlBlank(Integer urlBlank) {
		this.urlBlank = urlBlank;
	}

	public Integer getKind() {
		return kind;
	}

	public void setKind(Integer kind) {
		this.kind = kind;
	}

//~ Accessors ==============================================================

}
