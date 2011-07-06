/*
 * @(#)SystemNews.java Dec 13, 2009
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
 * <a href="SystemNews.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SystemNews.java 52 2010-06-15 09:28:19Z zhangsf $
 */
public class SystemNews extends BaseObject {

	//~ Static fields/initializers =============================================
	private static final long serialVersionUID = -7792630275121436598L;
	//~ Instance fields ========================================================

	private String id;
	
	private String title;
	
	private String content;
	
	private Date createdDate;
	
	private Date modifiedDate;
	
	private Integer kind;
	
	private Integer image;
	
	private Integer weight;
	
	private String url;
	
	private Integer urlBlank;
	
	private Date expiredDate;
	
	private User user;
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	//~ Accessors ==============================================================
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the expiredDate
	 */
	public Date getExpiredDate() {
		return expiredDate;
	}
	/**
	 * @param expiredDate the expiredDate to set
	 */
	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}
	public Integer getImage() {
		return image;
	}
	public void setImage(Integer image) {
		this.image = image;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the kind
	 */
	public Integer getKind() {
		return kind;
	}
	/**
	 * @param kind the kind to set
	 */
	public void setKind(Integer kind) {
		this.kind = kind;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return Returns the content.
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content The content to set.
	 */
	public void setContent(String content) {
		this.content = content;
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
	 * @return Returns the modifiedDate.
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param modifiedDate The modifiedDate to set.
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SystemNews)) {
			return false;
		}
		SystemNews rhs = (SystemNews) object;
		return new EqualsBuilder().append(this.createdDate, rhs.createdDate)
				.append(this.modifiedDate, rhs.modifiedDate)
				.append(this.title, rhs.title)
				.append(this.content, rhs.content).isEquals();
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1509247877, -179340459)
				.append(this.createdDate)
				.append(this.modifiedDate)
				.append(this.title)
				.append(this.content).toHashCode();
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("title", this.title)
				.append("content", this.content)
				.append("createdDate", this.createdDate)
				.append("modifiedDate", this.modifiedDate).toString();
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public Integer getUrlBlank() {
		return urlBlank;
	}
	public void setUrlBlank(Integer urlBlank) {
		this.urlBlank = urlBlank;
	}
}
