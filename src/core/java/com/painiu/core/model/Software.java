/**
 * @(#)Software.java 2010-5-16
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.painiu.util.TagUtils;
import com.painiu.event.EventSource;
import com.painiu.core.model.Commentable;
import com.painiu.core.security.PrivacyRestricted;
import com.painiu.core.model.Comment;
import com.painiu.core.model.SoftwareComment;
import com.painiu.core.model.User;

/**
 * <p>
 * <a href="Software.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Software.java 133 2010-11-23 08:14:43Z zhangsf $
 */

public class Software extends BaseObject implements EventSource, Commentable, PrivacyRestricted {


	//~ Static fields/initializers =============================================

	   private static final long serialVersionUID = 4514334471317647431L;	
	   
		public static class State extends IntegerEnum implements Serializable {

			public static final State UNDECLARED = new State(0);
		    public static final State PUBLISHED  = new State(1);
		    public static final State CANCELED   = new State(2);
		    public static final State DELETED    = new State(3);
		    public static final State BLOCKED    = new State(4);
			
		    private State(int value) {
		    	super(value);
		    }
		    
		    public static State valueOf(int value) {
		    	return (State) IntegerEnum.valueOf(State.class, value);
		    }
		}
	
	//~ Instance fields ===================================================
	
	   private String id;
	   private String name;
	   private String content;
	   private String rawTags;
	   private Date postedDate;
	   private Long timestamp;
	   private Date tokenDate;
	   private Category category;
	   private User user;
	   private String username;
	   private String platform;
	   private String osName;
	   private String devStatus;
	   private String language;
	   private String license;
	   private Set softwareTags = null;
	   private Set comments = null;
	   private State state = State.PUBLISHED;
		// permission: who can comments? who can add notes/tags?
		private SoftwarePermission permission;
        // Stat. informations
		private SoftwareStat stat;
		private Privacy privacy = Privacy.EVERYONE;


	//~ Constructors ====================================================

	//~ Methods =======================================================
	 
	   /**
		 * @return the id
		 */
		public String getId() {
			return id;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(String id) {
			this.id = id;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the content
		 */
		public String getContent() {
			return content;
		}

		/**
		 * @param content the content to set
		 */
		public void setContent(String content) {
			this.content = content;
		}

		/**
		 * @return the category
		 */
		public Category getCategory() {
			return category;
		}

		/**
		 * @param category the category to set
		 */
		public void setCategory(Category category) {
			this.category = category;
		}

		/**
		 * @return the platform
		 */
		public String getPlatform() {
			return platform;
		}

		/**
		 * @param platform the platform to set
		 */
		public void setPlatform(String platform) {
			this.platform = platform;
		}

		/**
		 * @return the osName
		 */
		public String getOsName() {
			return osName;
		}

		/**
		 * @param osName the osName to set
		 */
		public void setOsName(String osName) {
			this.osName = osName;
		}

		/**
		 * @return the devStatus
		 */
		public String getDevStatus() {
			return devStatus;
		}

		/**
		 * @param devStatus the devStatus to set
		 */
		public void setDevStatus(String devStatus) {
			this.devStatus = devStatus;
		}

		/**
		 * @return the language
		 */
		public String getLanguage() {
			return language;
		}

		/**
		 * @param language the language to set
		 */
		public void setLanguage(String language) {
			this.language = language;
		}

		/**
		 * @return the license
		 */
		public String getLicense() {
			return license;
		}

		/**
		 * @param license the license to set
		 */
		public void setLicense(String license) {
			this.license = license;
		}
		
          /**
			 * @return the rawTags
			 */
		public String getRawTags() {
				return rawTags;
			}

		 /**
			 * @param rawTags the rawTags to set
			 */
		 public void setRawTags(String rawTags) {
				this.rawTags = rawTags;
		}

		/**
		   * @return the user
		   */	
		public User getUser() {
			return user;
		}

	    /**
	      * @param user the user to set
		  */
		public void setUser(User user) {
			this.user = user;
		}

	   /**
		 * @return the softwareTags
		 */
		public Set getSoftwareTags() {
			if (softwareTags == null) {
				softwareTags = new TreeSet();
			}
			return softwareTags;
		}

		/**
		 * @param softwareTags the softwareTags to set
		 */
		public void setSoftwareTags(Set softwareTags) {
			this.softwareTags = softwareTags;
		}

		/**
		 * @return the comments
		 */
		public Set getComments() {
			return comments;
		}

		/**
		 * @param comments the comments to set
		 */
		public void setComments(Set comments) {
			this.comments = comments;
		}

		/**
		 * @return the state
		 */
		public State getState() {
			return state;
		}

		/**
		 * @param state the state to set
		 */
		public void setState(State state) {
			this.state = state;
		}

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
		
		
	    public boolean containsTag(String tagName) {
	    	for (Iterator i = getSoftwareTags().iterator(); i.hasNext();) {
				SoftwareTag tag = (SoftwareTag) i.next();
				if (tag.getTagName().equals(tagName)) {
					return true;
				}
			}
	    	return false;
	    }
	    
	    public boolean addSoftwareTag(SoftwareTag softwareTag) {
	    	  if (softwareTag == null) {
	    		throw new IllegalArgumentException("Can not add NULL SoftwareTag");
	    	 }
	    	
	    	for (Iterator i = getSoftwareTags().iterator(); i.hasNext();) {
	    		    SoftwareTag tag = (SoftwareTag) i.next();
				if (tag.getTagName().equals(softwareTag.getTagName())) {
					return false;
				}
			}
	    	   getSoftwareTags().add(softwareTag);
	    	   return true;
	    }
	    
	    public void removeSoftwareTag(SoftwareTag softwareTag) {
	    	    getSoftwareTags().remove(softwareTag);
	    }
	    
	    
		/**
		 * @return the permission
		 */
		public SoftwarePermission getPermission() {
			return permission;
		}

		/**
		 * @param permission the permission to set
		 */
		public void setPermission(SoftwarePermission permission) {
			permission.setSoftware(this);
			this.permission = permission;
		}

		/**
		 * @return the stat
		 */
		public SoftwareStat getStat() {
			return stat;
		}

		/**
		 * @param stat the stat to set
		 */
		public void setStat(SoftwareStat stat) {
			stat.setSoftware(this);
			this.stat = stat;
		}

		   /**
			 * @return the tokenDate
			 */
			public Date getTokenDate() {
				return tokenDate;
			}

			/**
			 * @param tokenDate the tokenDate to set
			 */
			public void setTokenDate(Date tokenDate) {
				this.tokenDate = tokenDate;
			}	
		
	
     //~ Accessors ======================================================


	/**
	 * @return the postedDate
	 */
	public Date getPostedDate() {
		return postedDate;
	}

	/**
	 * @param postedDate the postedDate to set
	 */
	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	/**
	 * @return the timestamp
	 */
	public Long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the privacy
	 */
	public Privacy getPrivacy() {
		return privacy;
	}

	/**
	 * @param privacy the privacy to set
	 */
	public void setPrivacy(Privacy privacy) {
		this.privacy = privacy;
	}
	
	
    public String getTagsAsString() {
    	return TagUtils.toString(getSoftwareTags());
    }

	
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Software)) {
			return false;
		}
		Software rhs = (Software) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id).isEquals();
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(1678590649, -897511477)
				.append(this.id).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id).toString();
	}
	
	public void addComment(Comment comment) {
		if (comment == null) {
			throw new IllegalArgumentException("Can not add NULL comment");
		}
		
		SoftwareComment pc = (SoftwareComment) comment;
		pc.setTarget(this);
		pc.setSoftware(this);
		if (getComments().add(pc)) {
			this.getStat().setComments(getComments().size());
		}
	}

	public void removeComment(Comment comment) {
		if (getComments().remove(comment)) {
			this.getStat().setComments(getComments().size());
		}
	}
	
	public User getPerson() {
		return getUser();
	}
	
	
	
	

}
