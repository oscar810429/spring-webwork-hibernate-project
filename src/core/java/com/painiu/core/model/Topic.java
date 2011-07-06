/*
 * @(#)Topic.java  2006-2-11
 * 
 * Copyright 2005 Yupoo. All rights reserved.
 */
package com.painiu.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.painiu.core.model.Comment.Situation;
import com.painiu.core.security.Personal;

/**
 * <p>
 * <a href="Topic.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zola Zhou
 * @version $Id: Topic.java 135 2010-11-23 09:28:01Z zhangsf $
 */
public class Topic extends BaseObject implements Personal {

	//~ Static fields/initializers =============================================
	private static final long serialVersionUID = -2893405472775856300L;

	public static class State extends IntegerEnum implements Serializable {

		public static final State NONE  	= new State(0);
		public static final State USER_COMMENDATORY  	= new State(1);
		
	    private State(int value) {
	    	super(value);
	    }
	    
	    public static State valueOf(int value) {
	    	return (State) IntegerEnum.valueOf(State.class, value);
	    }
	}
	//~ Instance fields ========================================================

    private String id;

    private Forum forum;
    //private Group group;

    private User author;

    private String subject;
    private String content;

    private Date postedDate;
    private Date modifiedDate;
    private User modifiedBy;
    
    private Integer position = new Integer(0);
    
    private Integer replys;
    
    private Boolean locked = Boolean.FALSE;
    private User lockedBy;

    private List posts;
    
    private State state = State.NONE;
    
    private Integer accessedTimes = new Integer(0);
    
    protected Situation situation = Situation.unchecked;
    
	//~ Constructors ===========================================================

	//~ Methods ================================================================

    public Integer getAccessedTimes() {
		return accessedTimes;
	}

	public void setAccessedTimes(Integer accessedTimes) {
		this.accessedTimes = accessedTimes;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void addPost(Post post) {
    	if (post == null) {
    		throw new IllegalArgumentException("Can not add NULL post");
    	}
    	post.setTopic(this);
    	post.setForum(getForum());
    	//post.setGroup(getGroup());
    	
    	getPosts().add(post);
    	updateReplys();
    	
    	setModifiedDate(post.getPostedDate());
    }
    
    public void removePost(Post post) {
    	if (post == null) {
    		throw new IllegalArgumentException("Can not remove NULL post");
    	}
    	getPosts().remove(post);
    	updateReplys();
    }
    
    public void updateReplys() {
    	setReplys(getPosts().size());
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
	 * @return Returns the replys.
	 */
	public Integer getReplys() {
		return replys;
	}

	/**
	 * @param replys The replys to set.
	 */
	public void setReplys(Integer replys) {
		this.replys = replys;
	}
	
	public void setReplys(int replys) {
		setReplys(new Integer(replys));
	}

	/**
	 * @return Returns the subject.
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject The subject to set.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
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
	 * @return Returns the posts.
	 */
	public List getPosts() {
		if (posts == null) {
			posts = new ArrayList();
		}
		return posts;
	}
	
	/**
	 * @param posts The posts to set.
	 */
	public void setPosts(List posts) {
		this.posts = posts;
	}

	/**
	 * @return Returns the position.
	 */
	public Integer getPosition() {
		return position;
	}

	/**
	 * @param position The position to set.
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}

	/**
	 * @return the locked
	 */
	public Boolean getLocked() {
		return locked;
	}

	/**
	 * @param locked the locked to set
	 */
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	/**
	 * @return the lockedBy
	 */
	public User getLockedBy() {
		return lockedBy;
	}

	/**
	 * @param lockedBy the lockedBy to set
	 */
	public void setLockedBy(User lockedBy) {
		this.lockedBy = lockedBy;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Topic)) {
			return false;
		}
		Topic rhs = (Topic) object;
		return new EqualsBuilder()
				.append(this.replys, rhs.replys)
				.append(this.modifiedDate, rhs.modifiedDate)
				.append(this.postedDate, rhs.postedDate)
				.append(this.subject, rhs.subject)
				.append(this.content, rhs.content)
				.append(this.position, rhs.position)
				.append(this.locked, rhs.locked)
				.append(this.lockedBy, rhs.lockedBy)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1963807983, -1256305873)
				.append(this.replys)
				.append(this.modifiedDate)
				.append(this.postedDate)
				.append(this.subject)
				.append(this.content)
				.append(this.position)
				.append(this.locked)
				.append(this.lockedBy)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("subject", this.subject).toString();
	}

	public Situation getSituation() {
		return situation;
	}

	public void setSituation(Situation situation) {
		this.situation = situation;
	}
}
