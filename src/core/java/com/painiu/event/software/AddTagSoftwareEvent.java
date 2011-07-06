/**
 * @(#)AddTagSoftwareEvent.java 2010-11-23
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.event.software;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.painiu.core.model.Software;
import com.painiu.core.model.User;

/**
 * <p>
 * <a href="AddTagSoftwareEvent.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: AddTagSoftwareEvent.java 135 2010-11-23 09:28:01Z zhangsf $
 */

public class AddTagSoftwareEvent extends SoftwareEvent{

	//~ Static fields/initializers =============================================

	private static final String TYPE_NAME = "tag";
	
	//~ Instance fields ========================================================

//	protected PhotoTag tag;
	
	protected String tagName;
	
	//~ Constructors ===========================================================

	public AddTagSoftwareEvent() {
	
	}
	
	public AddTagSoftwareEvent(Software software, String tagName, User user) {
		super(software, user);
//		this.tag = tag;
		this.tagName = tagName;
	}
	
	//~ Methods ================================================================


	public String getType() {
		return TYPE_NAME;
	}
	
	//~ Accessors ==============================================================
	
	/**
	 * @return Returns the tagName.
	 */
	public String getTagName() {
		return tagName;
	}

	/**
	 * @param tagName The tagName to set.
	 */
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof AddTagSoftwareEvent)) {
			return false;
		}
		AddTagSoftwareEvent rhs = (AddTagSoftwareEvent) object;
		return new EqualsBuilder().appendSuper(super.equals(object))
				.append(this.tagName, rhs.tagName).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-58568135, -275440661)
				.appendSuper(super.hashCode())
				.append(this.tagName).toHashCode();
	}

	

}
