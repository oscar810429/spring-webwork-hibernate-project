/*
 * @(#)SystemMessage.java  2009-11-27
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
 * <a href="SystemMessage.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SystemMessage.java 52 2010-06-15 09:28:19Z zhangsf $
 */
public class SystemMessage extends BaseObject {

	//~ Static fields/initializers =============================================
	private static final long serialVersionUID = -629193900047669942L;

	//~ Instance fields ========================================================

	private String id;
	
	private String subject;
	private String content;
	
	private Date createdDate;
	private Date expireDate;

	private String targetUserType;
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	//~ Accessors ==============================================================
	
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
	 * @return Returns the subject.
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject The subject to set.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getTargetUserType() {
		return targetUserType;
	}
	public void setTargetUserType(String targetUserType) {
		this.targetUserType = targetUserType;
	}
	
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SystemMessage)) {
			return false;
		}
		SystemMessage rhs = (SystemMessage) object;
		return new EqualsBuilder().append(this.createdDate, rhs.createdDate)
				.append(this.content, rhs.content)
				.append(this.expireDate, rhs.expireDate)
				.append(this.subject, rhs.subject).isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(4535181, 1521728123)
				.append(this.createdDate).append(this.content)
				.append(this.expireDate).append(this.subject).toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("subject", this.subject)
				.append("createdDate", this.createdDate)
				.append("expireDate", this.expireDate)
				.toString();
	}

}
