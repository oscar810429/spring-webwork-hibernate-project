/*
 * @(#)Contact.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.painiu.core.security.Personal;

/**
 * <p>
 * <a href="Contact.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Contact.java 52 2010-06-15 09:28:19Z zhangsf $
 */
public class Contact extends BaseObject implements Personal {

	//~ Static fields/initializers =============================================
	private static final long serialVersionUID = -1660066714116459465L;

	public static class Id implements Serializable {

		String ownerId;
		String userId;

		public Id() {}

		public Id(String ownerId, String userId) {
			this.ownerId = ownerId;
			this.userId = userId;
		}

		public boolean equals(Object o) {
			if (o instanceof Id) {
				Id that = (Id) o;
				return this.ownerId.equals(that.ownerId) &&
					   this.userId.equals(userId);
			}
			return false;
		}

		public int hashCode() {
			return ownerId.hashCode() + userId.hashCode();
		}
		
		public String toString() {
			return "Contact.Id[" + ownerId + "," + userId + "]";
		}
	}
	
	//~ Instance fields ========================================================

	private Id id = new Id();
	private User owner;
	private User user;
	private Relation relation = Relation.CONTACT;
	private Relation reversedRelation = Relation.NONE;
	private Date addedDate;
	
	//~ Constructors ===========================================================

	public Contact() {}
	
	public Contact(User owner, User user, Relation relationship) {
		this(owner, user, relationship, Relation.NONE);
	}
	
	public Contact(User owner, User user, Relation relationship, Relation reversedRelationship) {
		this.id.ownerId = owner.getId();
		this.id.userId = user.getId();
        
        this.owner = owner;
        this.user = user;
		
		this.relation = relationship;
		this.reversedRelation = reversedRelationship;
		this.addedDate = new Date();
	}
	
	//~ Methods ================================================================
	
	/* (non-Javadoc)
	 * @see com.yupoo.security.Personal#getPerson()
	 */
	public User getPerson() {
		return getOwner();
	}
	
	//~ Accessors ==============================================================

	/**
	 * @return Returns the addedDate.
	 */
	public Date getAddedDate() {
		return addedDate;
	}

	/**
	 * @param addedDate The addedDate to set.
	 */
	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
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
	 * @return Returns the owner.
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * @param owner The owner to set.
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}

	/**
	 * @return Returns the relation.
	 */
	public Relation getRelation() {
		return relation;
	}
	
	/**
	 * @param relation The relation to set.
	 */
	public void setRelation(Relation relationship) {
		this.relation = relationship;
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
	 * @return Returns the reversedRelation.
	 */
	public Relation getReversedRelation() {
		return reversedRelation;
	}

	/**
	 * @param reversedRelation The reversedRelation to set.
	 */
	public void setReversedRelation(Relation reversedRelation) {
		this.reversedRelation = reversedRelation;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Contact)) {
			return false;
		}
		Contact rhs = (Contact) object;
		return new EqualsBuilder()
				.append(this.user, rhs.user)
				.append(this.relation, rhs.relation)
				.append(this.owner, rhs.owner)
				.append(this.addedDate, rhs.addedDate).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(1678590649, -897511477)
				.append(this.user)
				.append(this.relation)
				.append(this.owner)
				.append(this.addedDate).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("owner", this.owner)
				.append("user", this.user)
				.append("relation", this.relation)
				.append("addedDate", this.addedDate).toString();
	}
}
