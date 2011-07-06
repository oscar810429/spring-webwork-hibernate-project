package com.painiu.core.model;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Category extends BaseObject implements Comparable{

	private static final long serialVersionUID = -6739657654698620036L;
	private String id;
	private String parentId;
	private Category parentCategory;
	private String name;
	private Date createdDate;
	private Boolean privacyCreateGroup;
	private Boolean privacyCreateSubcategory;
//	private Set groupCategories;
	private Set categories = new TreeSet();
//	private Set topics;
	private Integer amount = new Integer(0);
	
//	private GroupCategory groupCategory;
//	
//
//	public GroupCategory getGroupCategory() {
//		return groupCategory;
//	}
//
//	public void setGroupCategory(GroupCategory groupCategory) {
//		this.groupCategory = groupCategory;
//	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Boolean getPrivacyCreateGroup() {
		return privacyCreateGroup;
	}

	public void setPrivacyCreateGroup(Boolean privacyCreateGroup) {
		this.privacyCreateGroup = privacyCreateGroup;
	}

	public Boolean getPrivacyCreateSubcategory() {
		return privacyCreateSubcategory;
	}

	public void setPrivacyCreateSubcategory(Boolean privacyCreateSubcategory) {
		this.privacyCreateSubcategory = privacyCreateSubcategory;
	}
	


	public Set getCategories() {
		return categories;
	}

	public void setCategories(Set categories) {
		this.categories = categories;
	}

	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}
	
   /**
	 * @return the amount
	 */
	public Integer getAmount() {
		return amount;
	}
	
	public void increaseAmount() {
		setAmount(new Integer(amount.intValue() + 1));
	}
	
	public void decreaseAmount() {
		setAmount(new Integer(amount.intValue() - 1));
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Category)) {
			return false;
		}
		Category rhs = (Category) object;
		return new EqualsBuilder()
			.append(this.parentCategory, rhs.parentCategory)
			.append(this.name, rhs.name)
			.append(this.parentId,rhs.parentId)
			.append(this.privacyCreateSubcategory,rhs.privacyCreateSubcategory)
			.append(this.privacyCreateGroup,rhs.privacyCreateGroup)
			.append(this.createdDate, rhs.createdDate)
			.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(808399351, 111415097)
			.append(this.parentCategory)
			.append(this.name)
			.append(this.parentId)
			.append(this.privacyCreateSubcategory)
			.append(this.privacyCreateGroup)
			.append(this.createdDate)
			.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
			.append("parentCategory",this.parentCategory)
			.append("name", this.name)
			.append("parentId", this.parentId)
			.append("privacyCreateSubcategory",	this.privacyCreateSubcategory)
			.append("privacyCreateGroup", this.privacyCreateGroup)
			.append("createdDate",this.createdDate)
			.toString();
	}

	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(Object object) {
		Category myClass = (Category) object;
		return new CompareToBuilder()
			.append(this.createdDate,myClass.createdDate).toComparison();
	}
}
