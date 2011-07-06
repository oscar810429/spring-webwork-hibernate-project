/**
 * @(#)SoftwareTag.java 2010-6-7
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * <p>
 * <a href="SoftwareTag.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SoftwareTag.java 135 2010-11-23 09:28:01Z zhangsf $
 */

public class SoftwareTag extends BaseObject {

	//~ Static fields/initializers =============================================
	
	private static final long serialVersionUID = -8365663485005567199L;

	public static class Id implements Serializable {

		String softwareId;

		String tagName;

		public Id() {}

		public Id(String softwareId, String tagName) {
			this.softwareId = softwareId;
			this.tagName = tagName;
		}

		public boolean equals(Object o) {
			if (o instanceof Id) {
				Id that = (Id) o;
				return this.softwareId.equals(that.softwareId) &&
					   this.tagName.equals(that.tagName);
			}
			return false;
		}

		public int hashCode() {
			return softwareId.hashCode() + tagName.hashCode();
		}
		
		public String toString() {
			return "SoftwareTag.Id[" + softwareId + "," + tagName + "]";
		}
	}

	//~ Instance fields ===================================================
	
	private Id id = new Id();
	
	private Software software;
	private Tag tag;
	private User user;
	private User owner;
	
	private String softwareId;
	private String tagName;
	private String userId;
	private String ownerId;
	
	private Date taggedDate;

	//~ Constructors ====================================================
	
	public SoftwareTag(Software software, Tag tag, User user){
	    this.software = software;
		this.tag = tag;
		this.user = user;
		this.owner = software.getUser();
		this.softwareId = software.getId();
		this.tagName = tag.getName();
		this.userId = user.getId();
		this.ownerId = this.owner.getId();

		this.taggedDate = new Date();
		
		// Set identifier values
		this.id.softwareId = software.getId();
		this.id.tagName = tag.getName();
		
		// Guarantee referential integrity
		software.addSoftwareTag(this);
//		tag.increase();
//		tag.addTaggedPhoto(this);
	}
	
	public SoftwareTag(){
		
		
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
	 * @return the tag
	 */
	public Tag getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(Tag tag) {
		this.tag = tag;
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
	 * @return the owner
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(User owner) {
		this.owner = owner;
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
	 * @return the tagName
	 */
	public String getTagName() {
		return tagName;
	}

	/**
	 * @param tagName the tagName to set
	 */
	public void setTagName(String tagName) {
		this.tagName = tagName;
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
	 * @return the ownerId
	 */
	public String getOwnerId() {
		return ownerId;
	}

	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * @return the taggedDate
	 */
	public Date getTaggedDate() {
		return taggedDate;
	}

	/**
	 * @param taggedDate the taggedDate to set
	 */
	public void setTaggedDate(Date taggedDate) {
		this.taggedDate = taggedDate;
	}



	//~ Accessors ======================================================
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1809369609, -411528829)
				.append(this.softwareId)
				.append(this.tagName)
				.append(this.userId)
				.append(this.taggedDate)
				.toHashCode();
	}

	
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SoftwareTag)) {
			return false;
		}
		SoftwareTag rhs = (SoftwareTag) object;
		return new EqualsBuilder()
				.append(this.softwareId, rhs.softwareId)
				.append(this.tagName, rhs.tagName)
				.append(this.userId, rhs.userId)
				.append(this.taggedDate, rhs.taggedDate).isEquals();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new StringBuffer()
				.append("SoftwareTag[software:")
				.append(softwareId).append(", tag:")
				.append(tagName).append("]").toString();
	}

}
