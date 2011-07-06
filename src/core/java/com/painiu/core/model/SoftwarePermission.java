package com.painiu.core.model;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * <a href="PhotoPermission.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zola Zhou
 * @version $Id: SoftwarePermission.java 135 2010-11-23 09:28:01Z zhangsf $
 */
public class SoftwarePermission extends BaseObject {

	//~ Static fields/initializers =============================================
	private static final long serialVersionUID = 3524621714671755291L;

	//~ Instance fields ========================================================

	private String id;
	
	private Software software;
	
	private Privacy comment = Privacy.EVERYONE;
	
	private Privacy tag = Privacy.NON_PUBLIC;
	
	private Privacy note = Privacy.NON_PUBLIC;
	
	private Privacy download = Privacy.EVERYONE;
	
	private Privacy blog	= Privacy.EVERYONE;
	
	private Privacy exif 	= Privacy.EVERYONE;

	//~ Constructors ===========================================================

	public SoftwarePermission() {
	}
	
	public SoftwarePermission(Privacy privacy) {
		if (!privacy.grant(Relation.NONE)) {
			this.comment = privacy;
			this.tag = privacy;
			this.note = privacy;
			this.download = privacy;
			this.blog = privacy;
		}
	}
	
	public SoftwarePermission(Privacy comment, Privacy tag, Privacy note, Privacy download, Privacy blog) {
		this.comment = comment;
		this.tag = tag;
		this.note = note;
		this.download = download;
		this.blog = blog;
	}
	
	//~ Methods ================================================================

	//~ Accessors ==============================================================
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	 * @return the software
	 */
	public Software getSoftware() {
		return software;
	}

	/**
	 * @param software the software to set
	 */
	public void setSoftware(Software software) {
		this.software = software;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SoftwarePermission)) {
			return false;
		}
		SoftwarePermission rhs = (SoftwarePermission) object;
		return this.id.equals(rhs.getId());
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1811659111, 1433468111)
				.append(this.id).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("tag", this.tag)
				.append("download", this.download)
				.append("comment", this.comment)
				.append("note", this.note)
				.append("blog", this.blog).toString();
	}

	
}
