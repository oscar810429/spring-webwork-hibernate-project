/*
 * @(#)UserPreference.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;


/**
 * <p>
 * <a href="UserPreference.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: UserPreference.java 52 2010-06-15 09:28:19Z zhangsf $
 */
public class UserPreference extends BaseObject {
    
	//~ Static fields/initializers =============================================
	private static final long serialVersionUID = -4722515692426588903L;
    
    //~ Instance fields ========================================================

    private String id;
    private User user;
    
    private Privacy privacy = Privacy.EVERYONE;
    private Privacy comment = Privacy.EVERYONE;
    private Privacy tag = Privacy.EVERYONE;
    private Privacy note = Privacy.EVERYONE;
    private Privacy blog = Privacy.EVERYONE;
    private Privacy download = Privacy.EVERYONE;
    private License license = License.ATTRIBUTION_NONCOMMERCIAL_SHAREALIKE;
    private Privacy exif = Privacy.EVERYONE;
    private int layout = 0;
    
    private String safetyLevel = "0";
    private String contentType = "0";
    private int customWidth = 0;
    private int customHeight = 0;
    
    
    //~ Methods ================================================================

    
    //~ Accessors ==============================================================
    
	/**
	 * @return Returns the exif.
	 */
	public Privacy getExif() {
		return exif;
	}

	/**
	 * @param exif The exif to set.
	 */
	public void setExif(Privacy exif) {
		this.exif = exif;
	}

	/**
	 * @return Returns the blog.
	 */
	public Privacy getBlog() {
		return blog;
	}

	/**
	 * @param blog The blog to set.
	 */
	public void setBlog(Privacy blog) {
		this.blog = blog;
	}

	/**
	 * @return Returns the comment.
	 */
	public Privacy getComment() {
		return comment;
	}

	/**
	 * @param comment The comment to set.
	 */
	public void setComment(Privacy comment) {
		this.comment = comment;
	}

	/**
	 * @return Returns the download.
	 */
	public Privacy getDownload() {
		return download;
	}

	/**
	 * @param download The download to set.
	 */
	public void setDownload(Privacy download) {
		this.download = download;
	}

	/**
	 * @return Returns the license.
	 */
	public License getLicense() {
		return license;
	}

	/**
	 * @param license The license to set.
	 */
	public void setLicense(License license) {
		this.license = license;
	}

	/**
	 * @return Returns the note.
	 */
	public Privacy getNote() {
		return note;
	}

	/**
	 * @param note The note to set.
	 */
	public void setNote(Privacy note) {
		this.note = note;
	}

	/**
	 * @return Returns the tag.
	 */
	public Privacy getTag() {
		return tag;
	}

	/**
	 * @param tag The tag to set.
	 */
	public void setTag(Privacy tag) {
		this.tag = tag;
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
     * @return Returns the user.
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user The user to set.
     */
    public void setUser(User user) {
        this.user = user;
    }

    
    /*
     * @see com.yupoo.model.BaseObject#toString()
     */
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * @see com.yupoo.model.BaseObject#equals(java.lang.Object)
     */
    public boolean equals(Object o) {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * @see com.yupoo.model.BaseObject#hashCode()
     */
    public int hashCode() {
        // TODO Auto-generated method stub
        return 0;
    }

	/**
	 * @return Returns the privacy.
	 */
	public Privacy getPrivacy() {
		return privacy;
	}

	/**
	 * @param privacy The privacy to set.
	 */
	public void setPrivacy(Privacy privacy) {
		this.privacy = privacy;
	}

	/**
	 * @return the layout
	 */
	public int getLayout() {
		return layout;
	}

	/**
	 * @param layout the layout to set
	 */
	public void setLayout(int layout) {
		this.layout = layout;
	}

	/**
	 * @return the searchContentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param searchContentType the searchContentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the searchSafetyLevel
	 */
	public String getSafetyLevel() {
		return safetyLevel;
	}

	/**
	 * @param searchSafetyLevel the searchSafetyLevel to set
	 */
	public void setSafetyLevel(String safetyLevel) {
		this.safetyLevel = safetyLevel;
	}

	/**
	 * @return the customWidth
	 */
	public int getCustomWidth() {
		return customWidth;
	}

	/**
	 * @param customWidth the customWidth to set
	 */
	public void setCustomWidth(int customWidth) {
		this.customWidth = customWidth;
	}

	/**
	 * @return the customHeight
	 */
	public int getCustomHeight() {
		return customHeight;
	}

	/**
	 * @param customHeight the customHeight to set
	 */
	public void setCustomHeight(int customHeight) {
		this.customHeight = customHeight;
	}
	
	

}
