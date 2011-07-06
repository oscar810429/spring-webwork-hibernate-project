/**
 * @(#)TProduct.java 2011-3-2
 * 
 * Copyright 2008 365Net. All rights reserved.
 */
package com.painiu.core.model;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * <a href="TProduct.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id$
 */

public class TProduct extends BaseObject{


	//~ Static fields/initializers =============================================
	private static final long serialVersionUID = -7645940140327419043L;
	//~ Instance fields ===================================================
	
	private Integer id;
	private String tuanId;
	private String webSite;
	private String title;
	private String imageUrl;
	private String webUrl;
	private String siteUrl;
	private Date startTime;	
	private Date endTime;
	private Double basePrice;
	private Double salePrice;
	private Double discount;
	private Integer amount;	
	private String cityName;
	private String pyName;
	
	

	//~ Constructors ====================================================
	
	public TProduct(){}

	//~ Methods =======================================================
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the tuanId
	 */
	public String getTuanId() {
		return tuanId;
	}
	/**
	 * @param tuanId the tuanId to set
	 */
	public void setTuanId(String tuanId) {
		this.tuanId = tuanId;
	}
	/**
	 * @return the webSite
	 */
	public String getWebSite() {
		return webSite;
	}
	/**
	 * @param webSite the webSite to set
	 */
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}
	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	/**
	 * @return the webUrl
	 */
	public String getWebUrl() {
		return webUrl;
	}
	/**
	 * @param webUrl the webUrl to set
	 */
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	/**
	 * @return the siteUrl
	 */
	public String getSiteUrl() {
		return siteUrl;
	}
	/**
	 * @param siteUrl the siteUrl to set
	 */
	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}
	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the basePrice
	 */
	public Double getBasePrice() {
		return basePrice;
	}
	/**
	 * @param basePrice the basePrice to set
	 */
	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}
	/**
	 * @return the salePrice
	 */
	public Double getSalePrice() {
		return salePrice;
	}
	/**
	 * @param salePrice the salePrice to set
	 */
	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}
	/**
	 * @return the discount
	 */
	public Double getDiscount() {
		return discount;
	}
	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	/**
	 * @return the amount
	 */
	public Integer getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	/**
	 * @return the cityMame
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @param cityMame the cityMame to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * @return the pyName
	 */
	public String getPyName() {
		return pyName;
	}
	/**
	 * @param pyName the pyName to set
	 */
	public void setPyName(String pyName) {
		this.pyName = pyName;
	}	
	
	/*
	 * @see com.mingda.core.model.BaseObject#equals()
	 */
	
	public boolean equals(Object object) {
		if (!(object instanceof TProduct)) {
			return false;
		}
		TProduct rhs = (TProduct) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id).isEquals();
	}

	/*
	 * @see com.mingda.core.model.BaseObject#hashCode()
	 */

	public int hashCode() {
		return new HashCodeBuilder(-576253229, -214754223)
		.append(this.id).toHashCode();
	}

	/*
	 * @see com.mingda.core.model.BaseObject#toString()
	 */
	
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id).toString();
	}


	//~ Accessors ======================================================
	
	
}
