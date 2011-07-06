/*
 * @(#)Event.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.event;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.painiu.core.model.BaseObject;

/**
 * <p>
 * <a href="Event.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Event.java 133 2010-11-23 08:14:43Z zhangsf $
 */
public abstract class Event extends BaseObject implements Comparable {
	//~ Static fields/initializers =============================================

//~ Instance fields ========================================================
	
	protected String id;
	protected EventSource source;
//	protected EventParameter param;
	protected Date createdDate;
	
	//~ Constructors ===========================================================

	public Event() {}
	
	public Event(EventSource source) {
		this.source = source;
//		this.param = param;
		this.createdDate = new Date();
	}
	
	//~ Methods ================================================================

	public int compareTo(Object o) {
        if (o instanceof Event) {
            //return createdDate.compareTo(((SoftwareEvent) o).getCreatedDate());
        	    return 0;
        }

        return 0;
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
	 * @return Returns the source.
	 */
	public EventSource getSource() {
		return source;
	}

	/**
	 * @param source The source to set.
	 */
	public void setSource(EventSource source) {
		this.source = source;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Event)) {
			return false;
		}
		Event rhs = (Event) object;
		return new EqualsBuilder()
				.append(this.createdDate, rhs.createdDate)
				.append(this.source, rhs.source)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(932549105, 1468814609)
				.append(this.createdDate).append(this.source).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("source", this.source)
				.append("createdDate", this.createdDate)
				.toString();
	}
	
	
}
