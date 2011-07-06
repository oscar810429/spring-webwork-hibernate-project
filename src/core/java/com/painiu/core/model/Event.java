/*
 * @(#)Movement.java 2007-8-1
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * <a href="Movement.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author 5jxiang
 * @version $Id: Event.java 41 2010-06-10 17:30:08Z zhangsf $
 */
public class Event {
//	~ Static fields/initializers =============================================

	//~ Instance fields ========================================================
    private String id;
	
	private String name;
	
	private Integer kind;
	
	private String alias;
	
	private String tags;
	
	private String logo;
	
	private String smallLogo;
	
	private String url;
	
	private Integer weight = new Integer(1);
	
	private Integer eventOrder = new Integer(1);
	
	private String description;
	
	//private Group group;
	
	private Date startDate;
	
	private Date endDate;
	
	private Date createdDate;
	
	private Date modifiedDate;
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	//~ Accessors ==============================================================
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/*public Group getGroup() {
		
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}*/

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
	
	
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Token)) {
			return false;
		}
		Event act = (Event) object;
		return new EqualsBuilder()
				.append(this.name, act.name)
				.append(this.alias, act.alias)
				.append(this.tags, act.tags)
				.append(this.logo, act.logo)
				.append(this.url, act.url)
				.append(this.startDate, act.startDate)
				.append(this.endDate, act.endDate)
				.append(this.weight, act.weight)
				.append(this.kind, act.kind)
				.append(this.eventOrder, act.eventOrder)
				.append(this.createdDate, act.createdDate).isEquals();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id).toString();
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getEventOrder() {
		return eventOrder;
	}

	public void setEventOrder(Integer eventOrder) {
		this.eventOrder = eventOrder;
	}

	public Integer getKind() {
		return kind;
	}

	public void setKind(Integer kind) {
		this.kind = kind;
	}

	public String getSmallLogo() {
		return smallLogo;
	}

	public void setSmallLogo(String smallLogo) {
		this.smallLogo = smallLogo;
	}

	
}

