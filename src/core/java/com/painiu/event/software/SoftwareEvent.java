/**
 * @(#)SoftwareEvent.java 2010-11-23
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.event.software;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


import com.painiu.core.model.Software;
import com.painiu.core.model.User;
import com.painiu.event.Event;

/**
 * <p>
 * <a href="SoftwareEvent.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SoftwareEvent.java 135 2010-11-23 09:28:01Z zhangsf $
 */

public class SoftwareEvent extends Event{

	//~ Static fields/initializers =============================================
	
	private static final String TYPE_NAME = "software";


	//~ Instance fields ===================================================
		
    protected Software software;
	protected User owner; 
	protected User user; // 谁触发了这个事件


	//~ Constructors ====================================================
	
	public SoftwareEvent() {
	}
	
	public SoftwareEvent(Software software) {
		this(software, null);
	}
	
	public SoftwareEvent(Software software, User user) {
		super(software);
		this.software = software;
		this.owner = software.getUser();
		this.user = user;
	}


	//~ Methods =======================================================
	
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
	

	//~ Accessors ======================================================
	
	
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SoftwareEvent)) {
			return false;
		}
		SoftwareEvent rhs = (SoftwareEvent) object;
		return new EqualsBuilder()
				.appendSuper(super.equals(object))
				.append(this.user, rhs.user)
				.append(this.owner, rhs.owner)
				.append(this.software, rhs.software).isEquals();
	}

	

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-302847595, -617482931)
				.appendSuper(super.hashCode())
				.append(this.user)
				.append(this.owner)
				.append(this.software).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("software", this.software)
				.append("createdDate", this.createdDate)
				.append("user", this.user)
				.append("owner", this.owner).toString();
	}


}
