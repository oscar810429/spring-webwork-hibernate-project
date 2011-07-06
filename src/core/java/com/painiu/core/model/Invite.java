/*
 * @(#)Invite.java  2009-11-13
 * 
 * Copyright 2009 Mingda. All rights reserved.
 */
package com.painiu.core.model;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * <a href="Invite.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Invite.java 52 2010-06-15 09:28:19Z zhangsf $
 */
public class Invite extends BaseObject {

	//~ Static fields/initializers =============================================
	private static final long serialVersionUID = 3152965774467144736L;
	//~ Instance fields ========================================================

	private String id;
	private User from;
	private User to;
	private String email;
	private String name;
	private Relation relation = Relation.CONTACT;
	private String message;
	private Date invitedDate;
	private Date acceptedDate;
	//private Group group;

	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	//~ Accessors ==============================================================
	
	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return Returns the from.
	 */
	public User getFrom() {
		return from;
	}
	/**
	 * @param from The from to set.
	 */
	public void setFrom(User from) {
		this.from = from;
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
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message The message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
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
	 * @return Returns the relation.
	 */
	public Relation getRelation() {
		return relation;
	}
	/**
	 * @param relation The relation to set.
	 */
	public void setRelation(Relation relation) {
		this.relation = relation;
	}
	/**
	 * @return Returns the to.
	 */
	public User getTo() {
		return to;
	}
	/**
	 * @param to The to to set.
	 */
	public void setTo(User to) {
		this.to = to;
	}
	/**
	 * @return Returns the invitedDate.
	 */
	public Date getInvitedDate() {
		return invitedDate;
	}
	/**
	 * @param invitedDate The invitedDate to set.
	 */
	public void setInvitedDate(Date invitedDate) {
		this.invitedDate = invitedDate;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Invite)) {
			return false;
		}
		Invite rhs = (Invite) object;
		return new EqualsBuilder()
				.append(this.message, rhs.message)
				.append(this.email, rhs.email)
				.append(this.from, rhs.from)
				.append(this.relation, rhs.relation)
				.append(this.invitedDate, rhs.invitedDate)
				.append(this.name, rhs.name).isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(365933777, 1385019193)
				//.append(this.group).append(this.message)
		        .append(this.message)
				.append(this.email).append(this.from)
				.append(this.relation)
				.append(this.invitedDate)
				.append(this.name).toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("relation", this.relation)
				.append("name", this.name)
				.append("email", this.email)
				.append("from", this.from)
				.append("invitedDate", this.invitedDate)
				.append("message", this.message).toString();
	}
	public Date getAcceptedDate() {
		return acceptedDate;
	}
	public void setAcceptedDate(Date acceptedDate) {
		this.acceptedDate = acceptedDate;
	}

	
}
