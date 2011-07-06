/*
 * @(#)PhotoAddress.java  2009-11-27
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.painiu.Painiu;
import com.painiu.webapp.personality.MultiDomainContextHolder;
//import com.painiu.pnfs.files.PhotoFile;

/**
 * <p>
 * <a href="PhotoAddress.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: PhotoAddress.java 59 2010-06-15 11:43:42Z zhangsf $
 */
public class PhotoAddress extends BaseObject implements Serializable {


	//~ Static fields/initializers =============================================
	private static final long serialVersionUID = -1599856122295745584L;

	//~ Instance fields ========================================================

	// base filename (without extension)
	protected String filename;
	
	// host number of which this photo file is stored.
	protected Integer host;
	
	// file directory
	protected String dir;
	
	// original file's filename suffix
	protected String secret;
	
	// PNFS attributes ---------------- testing -----------------------------
	protected String username;
	protected String fileKey;
	// PNFS 
	
	//~ Constructors ===========================================================

	public PhotoAddress() {
	}
	
	public PhotoAddress(Integer host, String dir, String filename, String secret) {
		this.host = host;
		this.dir = dir;
		this.filename = filename;
		this.secret = secret;
	}
	
	// PNFS attributes ---------------- testing -----------------------------
	public PhotoAddress(String username, String fileKey, String secret) {
		this.username = username;
		this.fileKey = fileKey;
		this.secret = secret;
	}
	// PNFS
	
	
	//~ Methods ================================================================

    public String getURL(String v) {
        String suffix = ".jpg";
        
        if (Painiu.PHOTO_MEDIUM.equals(v)) {
            suffix = Painiu.SUFFIX_MEDUIM;
        } else if (Painiu.PHOTO_LARGE.equals(v)) {
          	suffix = secret != null ? ("_" + secret + ".jpg") : Painiu.SUFFIX_LARGE;
        //} else if (Painiu.PHOTO_LARGE.equals(v)) {
        //  	suffix = Painiu.SUFFIX_LARGE;
        //} else if (secret.equals(v)) {
        //  	suffix = "_" + secret + ".jpg";  	  	
        } else if (Painiu.PHOTO_SMALL.equals(v)) {
            suffix = Painiu.SUFFIX_SMALL;
        } else if (Painiu.PHOTO_THUMB.equals(v)) {
        	   suffix = Painiu.SUFFIX_THUMB;
        } else if (Painiu.PHOTO_SQUARE.equals(v)) {
        	   suffix = Painiu.SUFFIX_SQUARE;
        }

        return generateURL(filename + suffix);
    }
    
    public String getMetaURL() {
    	return generateURL(filename + "_" + secret + Painiu.SUFFIX_META);
    }
    
    private String generateURL(String fullname) {
        String pattern = Painiu.getPhotoConfig().getUrlPattern();
    	//String pattern = MultiDomainContextHolder.getContext().getPhotoURLPattern();
    	
        String url = StringUtils.replace(pattern, "${host}", host.toString());
        url = StringUtils.replace(url, "${dir}", dir);
        url = StringUtils.replace(url, "${filename}", fullname);
        return url;
    }
    
    // PNFS ---------------- testing -----------------------------
    
    public String getURL(Photo.Size v) {
    	String suffix = v == Photo.Size.ORIGINAL ? secret : v.getName();
    	return getURL(username, fileKey, suffix);
    }
    
    private static String getURL(String username, String fileKey, String suffix) {
    	String pattern = Painiu.getPhotoConfig().getUrlPattern();
    	//String pattern = MultiDomainContextHolder.getContext().getPhotoURLPattern();
        String url = StringUtils.replace(pattern, "${dir}", username);
        url = StringUtils.replace(url, "${filename}", fileKey);
        url = StringUtils.replace(url, "${suffix}", suffix);
        return url;
    }
    
    //public PhotoFile makeFile() {
    //	return new PhotoFile(dir, filename, secret);
    //}
    
    /**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * @return the fileKey
	 */
	public String getFileKey() {
		return fileKey;
	}
	
	/**
	 * @param fileKey the fileKey to set
	 */
	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}
    
	//~ Accessors ==============================================================

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Integer getHost() {
		return host;
	}

	public void setHost(Integer host) {
		this.host = host;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PhotoAddress)) {
			return false;
		}
		PhotoAddress rhs = (PhotoAddress) object;
		return new EqualsBuilder()
				.append(this.host, rhs.host)
				.append(this.filename, rhs.filename)
				.append(this.dir, rhs.dir)
				.append(this.secret, rhs.secret).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(1778548403, -1545479795)
				.append(this.host)
				.append(this.filename)
				.append(this.dir)
				.append(this.secret).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("host", this.host)
				.append("dir", this.dir)
				.append("filename",this.filename).toString();
	}
}
