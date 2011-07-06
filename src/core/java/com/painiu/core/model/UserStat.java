/*
 * @(#)UserStat.java Dec 13, 2009
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
 * <a href="UserStat.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: UserStat.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class UserStat extends BaseObject {
	//~ Static fields/initializers =============================================
    private static final long serialVersionUID = -1621269411620897158L;
	//~ Instance fields ========================================================

	private String id;
	private User user;
	
	private Integer streamViews = new Integer(0);
	private Integer profileViews = new Integer(0);
	
	private Integer score = new Integer(0);
	
	private Integer logins = new Integer(0);
	private Date prevLoginDate = new Date();
	private Date lastLoginDate = new Date();
	
	private String lastLoginIP;
	
	private Boolean inactive = Boolean.FALSE;
	
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	public void increaseStreamViews() {
		setStreamViews(new Integer(streamViews.intValue() + 1));
	}
	
	public void increaseProfileViews() {
		setProfileViews(new Integer(profileViews.intValue() + 1));
	}
	
	public void increaseLogins() {
		setLogins(new Integer(logins.intValue() + 1));
	}
	
	//~ Accessors ==============================================================
	
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
	 * @return Returns the profileViews.
	 */
	public Integer getProfileViews() {
		return profileViews;
	}
	/**
	 * @param profileViews The profileViews to set.
	 */
	public void setProfileViews(Integer profileViews) {
		this.profileViews = profileViews;
	}
	/**
	 * @return Returns the streamViews.
	 */
	public Integer getStreamViews() {
		return streamViews;
	}
	/**
	 * @param streamViews The streamViews to set.
	 */
	public void setStreamViews(Integer streamViews) {
		this.streamViews = streamViews;
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
	 * @return Returns the lastLoginDate.
	 */
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * @param lastLoginDate The lastLoginDate to set.
	 */
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * @return Returns the prevLoginDate.
	 */
	public Date getPrevLoginDate() {
		return prevLoginDate;
	}

	/**
	 * @param prevLoginDate The prevLoginDate to set.
	 */
	public void setPrevLoginDate(Date prevLoninDate) {
		this.prevLoginDate = prevLoninDate;
	}

	/**
	 * @return Returns the logins.
	 */
	public Integer getLogins() {
		return logins;
	}

	/**
	 * @param logins The logins to set.
	 */
	public void setLogins(Integer logins) {
		this.logins = logins;
	}

	/**
	 * @return Returns the score.
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * @param score The score to set.
	 */
	public void setScore(Integer score) {
		this.score = score;
	}

	/**
	 * @return the lastLoginIP
	 */
	public String getLastLoginIP() {
		return lastLoginIP;
	}

	/**
	 * @param lastLoginIP the lastLoginIP to set
	 */
	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}

	/**
	 * @return the inactive
	 */
	public Boolean getInactive() {
		return inactive;
	}
	
	/**
	 * @param inactive the inactive to set
	 */
	public void setInactive(Boolean inactive) {
		this.inactive = inactive;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof UserStat)) {
			return false;
		}
		UserStat rhs = (UserStat) object;
		return new EqualsBuilder()
				.append(this.streamViews, rhs.streamViews)
				.append(this.profileViews, rhs.profileViews)
				.append(this.score, rhs.score)
				.append(this.logins, rhs.logins)
				.append(this.prevLoginDate, rhs.prevLoginDate)
				.append(this.lastLoginDate, rhs.lastLoginDate)
				.append(this.lastLoginIP, rhs.lastLoginIP)
				.append(this.id, rhs.id).isEquals();
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1024204319, 327612429)
				.append(this.streamViews)
				.append(this.profileViews)
				.append(this.score)
				.append(this.logins)
				.append(this.prevLoginDate)
				.append(this.lastLoginDate)
				.append(this.lastLoginIP)
				.append(this.id).toHashCode();
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id)
				.append("streamViews", this.streamViews)
				.append("profileViews", this.profileViews).toString();
	}
	
}
