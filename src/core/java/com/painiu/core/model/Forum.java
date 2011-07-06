/*
 * @(#)Forum.java  2006-2-11
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * <a href="Forum.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zola Zhou
 * @version $Id: Forum.java 135 2010-11-23 09:28:01Z zhangsf $
 */
public class Forum extends BaseObject implements Comparable {

	//~ Static fields/initializers =============================================
	private static final long serialVersionUID = 139456551786704413L;

	public static class Type extends IntegerEnum implements Serializable {

		public static final Type SYSTEM_FORUM = new Type(0);
	    public static final Type GROUPR_FORUM = new Type(1);
	    public static final Type LOCATION_FORUM = new Type(2);
	    
	    private Type(int value) {
	    	super(value);
	    }
	    
	    public static Type valueOf(int value) {
	    	return (Type) IntegerEnum.valueOf(Type.class, value);
	    }
	}
	
	//~ Instance fields ========================================================

	private String id;
	private String name;
	private String description;	
	private Date createdDate;
	private Integer position;
	private Type type = Type.GROUPR_FORUM;
	
	//private Group group;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

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
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the position.
	 */
	public Integer getPosition() {
		return position;
	}

	/**
	 * @param position The position to set.
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}
	
	/**
	 * @return Returns the type.
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @param type The type to set.
	 */
	public void setType(Type type) {
		this.type = type;
	}
	
	
	//~ Implementation of Comparable ==============================================

	/*
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
    public int compareTo(Object o) {
        if (o instanceof Forum) {
            return position.compareTo(((Forum) o).getPosition());
        }

        return 0;
    }

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Forum)) {
			return false;
		}
		Forum rhs = (Forum) object;
		return new EqualsBuilder()
				.append(this.createdDate, rhs.createdDate)
				.append(this.description, rhs.description)
				.append(this.name, rhs.name).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1647681001, 1504840305)
				.append(this.createdDate)
				.append(this.description)
				.append(this.name).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("name", this.name)
				.append("description", this.description).toString();
	}

	
}
