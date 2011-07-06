/*
 * @(#)Cotag.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * <a href="Cotag.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Cotag.java 52 2010-06-15 09:28:19Z zhangsf $
 */
public class Cotag extends BaseObject {

	//~ Static fields/initializers =============================================
	private static final long serialVersionUID = 7833265084477707551L;

	public static class Id implements Serializable {

		String tagName;

		String cotagName;

		public Id() {}

		public Id(String tagName, String cotagName) {
			this.tagName = tagName;
			this.cotagName = cotagName;
		}

		public boolean equals(Object o) {
			if (o instanceof Id) {
				Id that = (Id) o;
				return this.cotagName.equals(that.cotagName) &&
					   this.tagName.equals(that.tagName);
			}
			return false;
		}

		public int hashCode() {
			return cotagName.hashCode() + tagName.hashCode();
		}
		
		public String toString() {
			return "Cotag.Id[" + tagName + "," + cotagName + "]";
		}
	}
	
	//~ Instance fields ========================================================

	private Id id = new Id();
	
	private Tag tag;
	private Tag cotag;
	private String tagName;
	private String cotagName;
	private Integer count = new Integer(0);
	
	//~ Constructors ===========================================================

	public Cotag() {}
	
	public Cotag(String tagName, String cotagName) {
		this.id.tagName = tagName;
		this.id.cotagName = cotagName;
	}
	
	public Cotag(Tag tag, Tag cotag) {
		this.id.tagName = tag.getName();
		this.id.cotagName = cotag.getName();
		this.tag = tag;
		this.cotag = cotag;
	}
	
	//~ Methods ================================================================

	public void increaseCount() {
		this.setCount(new Integer(getCount().intValue() + 1));
	}
	
	//~ Accessors ==============================================================

	/**
	 * @return Returns the cotag.
	 */
	public Tag getCotag() {
		return cotag;
	}

	/**
	 * @param cotag The cotag to set.
	 */
	public void setCotag(Tag coTag) {
		this.cotag = coTag;
	}

	/**
	 * @return Returns the cotagName.
	 */
	public String getCotagName() {
		return cotagName;
	}

	/**
	 * @param cotagName The cotagName to set.
	 */
	public void setCotagName(String coTagName) {
		this.cotagName = coTagName;
	}

	/**
	 * @return Returns the count.
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * @param count The count to set.
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * @return Returns the id.
	 */
	public Id getId() {
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(Id id) {
		this.id = id;
	}

	/**
	 * @return Returns the tag.
	 */
	public Tag getTag() {
		return tag;
	}

	/**
	 * @param tag The tag to set.
	 */
	public void setTag(Tag tag) {
		this.tag = tag;
	}

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
		if (!(object instanceof Cotag)) {
			return false;
		}
		Cotag rhs = (Cotag) object;
		return new EqualsBuilder().append(this.count, rhs.count)
				.append(this.cotagName, rhs.cotagName)
				.append(this.tagName, rhs.tagName).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(688716621, 1358438299)
				.append(this.count).append(this.cotagName)
				.append(this.tagName).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("tagName", this.tagName)
				.append("cotagName", this.cotagName)
				.append("count", this.count).toString();
	}
	
}
