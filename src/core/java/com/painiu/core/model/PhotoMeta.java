/*
 * @(#)PhotoMeta.java  2009-11-25
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.drew.metadata.Metadata;

/**
 * <p>
 * <a href="PhotoMeta.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: PhotoMeta.java 52 2010-06-15 09:28:19Z zhangsf $
 */
public class PhotoMeta extends BaseObject {

	//~ Static fields/initializers =============================================
	private static final long serialVersionUID = 5235461722868757378L;

	//~ Instance fields ========================================================
	
	private String id;
	
	private Photo photo;
	
//	private JpegSegmentData data;
	private Metadata data;
	
	//~ Constructors ===========================================================

	public PhotoMeta() {
	}
	
	//~ Methods ================================================================

	//~ Accessors ==============================================================
	
	/**
	 * @return Returns the data.
	 */
	public Metadata getData() {
		return data;
	}

	/**
	 * @param data The data to set.
	 */
	public void setData(Metadata data) {
		this.data = data;
	}

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
	 * @return Returns the photo.
	 */
	public Photo getPhoto() {
		return photo;
	}

	/**
	 * @param photo The photo to set.
	 */
	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	
	/*
	 * @see com.yupoo.model.BaseObject#toString()
	 */
	public String toString() {
		return "PhotoMeta[" + photo + "]";
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PhotoMeta)) {
			return false;
		}
		PhotoMeta rhs = (PhotoMeta) object;
		return new EqualsBuilder()
				.append(this.photo, rhs.photo).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(1465437165, -1535794847)
					.append(this.photo).toHashCode();
	}
}
