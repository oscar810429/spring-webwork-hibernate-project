/**
 * @(#)SoftwareVote.java 2010-6-13
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.painiu.webapp.util.DateUtils;

/**
 * <p>
 * <a href="SoftwareVote.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SoftwareVote.java 135 2010-11-23 09:28:01Z zhangsf $
 */

public class SoftwareVote extends BaseObject{

	//~ Static fields/initializers =============================================
	private static final long serialVersionUID = 4179614737347926978L;
	
	public static class Id implements Serializable{
		String softwareId;

//		String userId;
		
		String voteId;
		
		Date voteDate = DateUtils.getISODateFormat(new Date());

		String voteIp;
		
		public Id() {}

		public Id(String photoId){
			this.softwareId = softwareId;
		}
		
		public Id(String photoId, String voteId, Date voteDate, String voteIp) {
			this.softwareId = softwareId;
			this.voteId = voteId;
			this.voteDate = voteDate;
			this.voteIp = voteIp;
		}

		public boolean equals(Object o) {
			if (o instanceof Id) {
				Id that = (Id) o;
				return this.softwareId.equals(that.softwareId) &&
					   this.voteId.equals(that.voteId) &&
					   this.voteDate.equals(that.voteDate) && 
					   this.voteIp.equals(that.voteIp);
			}
			return false;
		}

		public int hashCode() {
			return softwareId.hashCode() + 
					voteId.hashCode() +
					voteDate.hashCode() + 
					voteIp.hashCode();
		}
		
		public String toString() {
			return "SoftwareVote.Id[" + softwareId + "," + voteId + ","+voteDate+"," +voteIp +"]";
		}
	}

	//~ Instance fields ===================================================
	
	private Id id = new Id();
	private Software software;
	private User user;
	private String softwareId;
	private Integer score;
	private String userId;
	private String voteId;
	private String voteIp;
	


	//~ Constructors ====================================================
	
	public SoftwareVote() {
		
	}
	
	public SoftwareVote(Software software, User user) {
		this.software = software;
		this.user = user;
		
		this.id.softwareId = software.getId();
	}

	//~ Methods =======================================================


	/**
	 * @return the id
	 */
	public Id getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Id id) {
		this.id = id;
	}

	/**
	 * @return the software
	 */
	public Software getSoftware() {
		return software;
	}

	/**
	 * @param software the software to set
	 */
	public void setSoftware(Software software) {
		this.software = software;
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
	 * @return the softwareId
	 */
	public String getSoftwareId() {
		return softwareId;
	}

	/**
	 * @param softwareId the softwareId to set
	 */
	public void setSoftwareId(String softwareId) {
		this.softwareId = softwareId;
	}

	/**
	 * @return the score
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(Integer score) {
		this.score = score;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the voteId
	 */
	public String getVoteId() {
		return voteId;
	}

	/**
	 * @param voteId the voteId to set
	 */
	public void setVoteId(String voteId) {
		this.voteId = voteId;
	}

	/**
	 * @return the voteIp
	 */
	public String getVoteIp() {
		return voteIp;
	}

	/**
	 * @param voteIp the voteIp to set
	 */
	public void setVoteIp(String voteIp) {
		this.voteIp = voteIp;
	}

	
	//~ Accessors ======================================================
	
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SoftwareVote)) {
			return false;
		}
		SoftwareVote rhs = (SoftwareVote) object;
		return new EqualsBuilder()
				.append(this.user, rhs.user)
				.append(this.score, rhs.score)
				.append(this.software, rhs.software)
				.append(this.voteIp, rhs.voteIp)
						.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-576253229, -214754223)
				.append(this.user)
				.append(this.score)
				.append(this.software)
				.append(this.voteIp).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
			.append("software", this.software)
			.append("id", this.id)
			.append("score", this.score)
			.append("user",	this.user)
			.append("voteId", this.voteId)
			.append("softwareId", this.softwareId)
			.append("voteIp", this.voteIp)
			.append("userId",this.userId).toString();
	}

}
