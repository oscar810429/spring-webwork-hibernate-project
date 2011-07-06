/*
 * @(#)Message.java Dec 13, 2009
 * 
 * Copyright 2009 Mingda. All rights reserved.
 */
package com.painiu.core.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.painiu.core.model.Comment.Situation;
import com.painiu.core.security.Personal;

/**
 * <p>
 * <a href="Message.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Message.java 52 2010-06-15 09:28:19Z zhangsf $
 */
public class Message extends BaseObject implements Personal {

	//~ Static fields/initializers =============================================
	private static final long serialVersionUID = 390225162211581664L;

	public static class State extends IntegerEnum implements Serializable {

	    public static final State UNREAD  = new State(0);
	    public static final State READ    = new State(1);
	    public static final State REPLIED = new State(2);
	    
	    private State(int value) {
	    	super(value);
	    }
	    
	    public static State valueOf(int value) {
	    	return (State) IntegerEnum.valueOf(State.class, value);
	    }
	}
	
	//~ Instance fields ========================================================

	private String id;
	
	private User owner;
	
	private User sender;
	
	private User recipient;
	
	private String subject;
	
	private String content;
	
	private Date sentDate;
	
//	private Integer status;
	private State state = State.UNREAD;
	
    protected Situation situation = Situation.unchecked;
	
	//~ Constructors ===========================================================
	
	//~ Methods ================================================================
	
	public Situation getSituation() {
		return situation;
	}

	public void setSituation(Situation situation) {
		this.situation = situation;
	}

	public boolean isUnread() {
		return getState().equals(State.UNREAD);
	}
	
	public boolean isRead() {
		return !isUnread();
	}
	
	public boolean isReplied() {
		return getState().equals(State.REPLIED);
	}
	
	public User getPerson() {
		return getOwner();
	}
	
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
	 * @return Returns the recipient.
	 */
	public User getRecipient() {
		return recipient;
	}

	/**
	 * @param recipient The recipient to set.
	 */
	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}

	/**
	 * @return Returns the sentDate.
	 */
	public Date getSentDate() {
		return sentDate;
	}

	/**
	 * @param sentDate The sentDate to set.
	 */
	public void setSentDate(Date sendDate) {
		this.sentDate = sendDate;
	}

	/**
	 * @return Returns the sender.
	 */
	public User getSender() {
		return sender;
	}

	/**
	 * @param sender The sender to set.
	 */
	public void setSender(User sender) {
		this.sender = sender;
	}

	/**
	 * @return Returns the state.
	 */
	public State getState() {
		return state;
	}

	/**
	 * @param state The state to set.
	 */
	public void setState(State state) {
		this.state = state;
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Message)) {
			return false;
		}
		Message rhs = (Message) object;
		return new EqualsBuilder()
				.append(this.sender, rhs.sender)
				.append(this.content, rhs.content)
				.append(this.state, rhs.state)
				.append(this.subject, rhs.subject)
				.append(this.sentDate, rhs.sentDate)
				.append(this.owner, rhs.owner)
				.append(this.recipient, rhs.recipient).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(2060036329, -1639636603)
				.append(this.sender)
				.append(this.content)
				.append(this.state)
				.append(this.subject)
				.append(this.owner)
				.append(this.sentDate)
				.append(this.recipient).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("subject", this.subject)
				.append("owner", this.owner)
				.append("sender", this.sender)
				.append("recipient", this.recipient)
				.append("content", this.content)
				.append("sentDate", this.sentDate)
				.append("state", this.state).toString();
	}
}
