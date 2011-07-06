/*
 * @(#)Post.java  2006-2-11
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.painiu.core.model.Comment.Situation;
import com.painiu.core.security.Personal;

/**
 * <p>
 * <a href="Post.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zola Zhou
 * @version $Id: Post.java 135 2010-11-23 09:28:01Z zhangsf $
 */
public class Post extends BaseObject implements Comparable, Personal {

	//~ Static fields/initializers =============================================
	private static final long serialVersionUID = 8352462225230195411L;

	//~ Instance fields ========================================================
	
    private String id;
    
    private Post parent;
    
    private Topic topic;
    private Forum forum;
    //private Group group;
    
    private User author;
//    private String authorName;
    
    private String content;
    
    private Date postedDate;
    private Date modifiedDate;
    private User modifiedBy;
    
    protected Situation situation = Situation.unchecked;
    
	//~ Constructors ===========================================================

	//~ Methods ================================================================

    public Situation getSituation() {
		return situation;
	}

	public void setSituation(Situation situation) {
		this.situation = situation;
	}

	public boolean modified() {
    	return !this.getPostedDate().equals(this.getModifiedDate());
    }
    
    /* (non-Javadoc)
     * @see com.yupoo.security.Personal#getPerson()
     */
    public User getPerson() {
    	return getAuthor();
    }
    
	//~ Accessors ==============================================================
	
	/**
	 * @return Returns the content.
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content The content to set.
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return Returns the forum.
	 */
	public Forum getForum() {
		return forum;
	}

	/**
	 * @param forum The forum to set.
	 */
	public void setForum(Forum forum) {
		this.forum = forum;
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
	 * @return Returns the modifiedDate.
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate The modifiedDate to set.
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the modifiedBy
	 */
	public User getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return Returns the parent.
	 */
	public Post getParent() {
		return parent;
	}

	/**
	 * @param parent The parent to set.
	 */
	public void setParent(Post parent) {
		this.parent = parent;
	}

	/**
	 * @return Returns the postedDate.
	 */
	public Date getPostedDate() {
		return postedDate;
	}

	/**
	 * @param postedDate The postedDate to set.
	 */
	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	/**
	 * @return Returns the author.
	 */
	public User getAuthor() {
		return author;
	}
	
	/**
	 * @param author The author to set.
	 */
	public void setAuthor(User author) {
		this.author = author;
	}

	/**
	 * @return Returns the topic.
	 */
	public Topic getTopic() {
		return topic;
	}
	
	/**
	 * @param topic The topic to set.
	 */
	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	//~ Implementation of Comparable ==============================================

	/*
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
    public int compareTo(Object o) {
        if (o instanceof Post) {
            return postedDate.compareTo(((Post) o).getPostedDate());
        }

        return 0;
    }

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Post)) {
			return false;
		}
		Post rhs = (Post) object;
		return new EqualsBuilder()
				.append(this.modifiedDate, rhs.modifiedDate)
				.append(this.postedDate, rhs.postedDate)
				.append(this.topic, rhs.topic)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(270629411, 438221513)
				.append(this.modifiedDate)
				.append(this.postedDate)
				.append(this.topic)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append(this.id).toString();
	}
}
