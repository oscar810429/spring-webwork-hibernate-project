/*
 * @(#)Comment.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.painiu.event.EventParameter;
import com.painiu.core.security.Personal;

/**
 * <p>
 * <a href="Comment.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Comment.java 52 2010-06-15 09:28:19Z zhangsf $
 */
public abstract class Comment extends BaseObject implements EventParameter, Comparable, Personal {
	
	private static final long serialVersionUID = 4797548069325713026L;

	//~ Static fields/initializers =============================================
	public static class Situation extends IntegerEnum implements Serializable {

		public static final Situation checked  	= new Situation(0);
		public static final Situation unchecked 		= new Situation(1);
		public static final Situation deleted 		= new Situation(2);
		
		
		public Situation(int value) {
	    	super(value);
	    }
	    
	    public static Situation valueOf(int value) {
	    	return (Situation) IntegerEnum.valueOf(Situation.class, value);
	    }
	    
	}
	
	//~ Instance fields ========================================================

    protected String id;

    protected Commentable target;

    protected Comment parent;
    
    protected List children;
    
    protected User author;
    
//    protected String authorName;

    protected String content;

    protected Date postedDate;
    
    protected Date modifiedDate;
    
    protected User modifiedBy;

    protected User targetOwner;
    
    protected Situation situation = Situation.unchecked;
    
	//~ Constructors ===========================================================

	//~ Methods ================================================================
    
    public Situation getSituation() {
		return situation;
	}

	public void setSituation(Situation situation) {
		this.situation = situation;
	}

	public User getPerson() {
    	return getAuthor();
    }
    
    /*
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object o) {
        if (o instanceof Comment) {
            return postedDate.compareTo(((Comment) o).getPostedDate());
        }

        return 0;
    }
    
    public boolean modified() {
    	return !this.getPostedDate().equals(this.getModifiedDate());
    }

	//~ Accessors ==============================================================

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
	public Comment getParent() {
		return parent;
	}

	/**
	 * @param parent The parent to set.
	 */
	public void setParent(Comment parent) {
		this.parent = parent;
	}

	/**
	 * @return Returns the target.
	 */
	public Commentable getTarget() {
		return target;
	}
	
	/**
	 * @param target The target to set.
	 */
	public void setTarget(Commentable target) {
		this.target = target;
		this.setTargetOwner(target.getUser());
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
	 * @return Returns the children.
	 */
	public List getChildren() {
		return children;
	}

	/**
	 * @param children The children to set.
	 */
	public void setChildren(List children) {
		this.children = children;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Comment)) {
			return false;
		}
		Comment rhs = (Comment) object;
		return new EqualsBuilder()
				.append(this.modifiedDate, rhs.modifiedDate)
				.append(this.postedDate, rhs.postedDate)
				.append(this.content, rhs.content)
				.append(this.target, rhs.target)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(1595159849, 873807081)
				.append(this.modifiedDate)
				.append(this.postedDate)
				.append(this.content)
				.append(this.target)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("target", this.target)
				.toString();
	}

	/**
	 * @return the targetOwner
	 */
	public User getTargetOwner() {
		return targetOwner;
	}

	/**
	 * @param targetOwner the targetOwner to set
	 */
	public void setTargetOwner(User targetOwner) {
		this.targetOwner = targetOwner;
	}

	
}