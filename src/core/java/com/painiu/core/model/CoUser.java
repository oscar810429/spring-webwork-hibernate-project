/*
 * @(#)CoUser.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * <a href="CoUser.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: CoUser.java 52 2010-06-15 09:28:19Z zhangsf $
 */
public class CoUser extends BaseObject {

	//~ Static fields/initializers =============================================
	private static final long serialVersionUID = -393291570469142381L;

	public static class Id implements Serializable {

		String collaUserId;

		String collaId;

		public Id() {}

		public Id(String collaId, String collaUserId) {
			this.collaId = collaId;
			this.collaUserId = collaUserId;
		}

		public boolean equals(Object o) {
			if (o instanceof Id) {
				Id that = (Id) o;
				return this.collaId.equals(that.collaId) &&
					   this.collaUserId.equals(that.collaUserId);
			}
			return false;
		}

		public int hashCode() {
			return collaId.hashCode() + collaUserId.hashCode();
		}
		
		public String toString() {
			return "CoUser.Id[" + collaId + "," + collaUserId + "]";
		}
	}
	
	//~ Instance fields ========================================================
	
	private Id id = new Id();
	
	private String collaUserId;
	private Collaborator colla;
	private User user;
	
	private Date createdDate;

	//~ Constructors ===========================================================
	
	public CoUser() {}
	
	public CoUser(Collaborator colla, String collaUserId, User user) {
		this.id.collaUserId = collaUserId;
		this.id.collaId = colla.getId();
		this.collaUserId = collaUserId;
		this.user = user;
		this.colla = colla;
		this.createdDate = new Date(); 
	}
	
	//~ Methods ================================================================

	//~ Accessors ==============================================================
	
	/**
	 * @return Returns the colla.
	 */
	public Collaborator getColla() {
		return colla;
	}

	/**
	 * @param colla The colla to set.
	 */
	public void setColla(Collaborator collaborator) {
		this.colla = collaborator;
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
	 * @return the collaUserId
	 */
	public String getCollaUserId() {
		return collaUserId;
	}
	
	/**
	 * @param collaUserId the collaUserId to set
	 */
	public void setCollaUserId(String collaUserId) {
		this.collaUserId = collaUserId;
	}
	
	/**
	 * @return Returns the id.
	 */
	public Id getId() {
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(Id id) {
		this.id = id;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-792452421, -1198179413)
				.append(this.createdDate).append(this.user)
				.append(this.collaUserId)
				.append(this.colla).toHashCode();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof CoUser)) {
			return false;
		}
		CoUser rhs = (CoUser) object;
		return new EqualsBuilder()
				.append(this.createdDate, rhs.createdDate)
				.append(this.user, rhs.user)
				.append(this.collaUserId, rhs.collaUserId)
				.append(this.colla, rhs.colla).isEquals();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("user", this.user)
				.append("colla", this.colla)
				.append("collaUserId", this.collaUserId)
				.append("createdDate", this.createdDate).toString();
	}

}
