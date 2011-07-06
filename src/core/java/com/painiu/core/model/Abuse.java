/*
 * @(#)Abuse.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * <a href="Abuse.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Abuse.java 52 2010-06-15 09:28:19Z zhangsf $
 */
public class Abuse extends BaseObject {
		
	private static final long serialVersionUID = -7234304251914179153L;

	public static class State extends IntegerEnum implements Serializable {

		public static final State DISABLE = new State(0);
		public static final State ENABLE  = new State(1);

		private State(int value) {
			super(value);
		}

		public static State valueOf(int value) {
			return (State) IntegerEnum.valueOf(State.class, value);
		}
	}
	
	public static final int ABUSE_KIND_DEFAULT = 0;
	public static final int ABUSE_KIND_TAOBAO = 1;
	
	private User user;
	private String id;
	private String email;
	private String url;
	private String subject;
	private String content;
	private Integer kind = new Integer(ABUSE_KIND_DEFAULT);
	private Date createDate = new Date();
	private State state;
	

	public Integer getKind() {
		return kind;
	}

	public void setKind(Integer kind) {
		this.kind = kind;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
	
		if (getClass() != obj.getClass())
			return false;
		final Abuse other = (Abuse) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 0;
		result = PRIME * result + ((content == null) ? 0 : content.hashCode());
		result = PRIME * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = PRIME * result + ((email == null) ? 0 : email.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((state == null) ? 0 : state.hashCode());
		result = PRIME * result + ((subject == null) ? 0 : subject.hashCode());
		result = PRIME * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.yupoo.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
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

}
