package com.painiu.core.model;

import java.util.Comparator;
import java.util.Date;

import com.painiu.event.EventParameter;

/**
 * <p>
 * <a href="Tag.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Tag.java 52 2010-06-15 09:28:19Z zhangsf $
 */
public class Tag extends BaseObject implements EventParameter, Comparable {

	//~ Static fields/initializers =============================================
	private static final long serialVersionUID = -6327006919127701117L;
	
	public static final Comparator NAME_COMPARATOR = new Comparator() {
		public int compare(Object o1, Object o2) {
			return ((Tag) o1).getName().compareTo(((Tag) o2).getName());
		}
	};
	
	public static final Comparator DATE_COMPARATOR = new Comparator() {
		public int compare(Object o1, Object o2) {
			return ((Tag) o1).getCreatedDate().compareTo(((Tag) o2).getCreatedDate());
		}
	};
	
	//~ Instance fields ========================================================
	
	private String name;
	
	private Integer times = new Integer(0);
	
	private Integer users = new Integer(0);

	private Date createdDate = new Date();
	
	private Integer version;
	
	// private int tagCount = 500;
	
    //	private Set taggedPhotos = new HashSet();

	//~ Constructors ===========================================================

	public Tag() {
	}
	
	public Tag(String name) {
		this.name = name;
	}
	
	public Tag(String name, Integer times) { // when used as a value object
		this.name = name;
		this.times = times;
	}
	
	public int getLevel() {
		int result = this.getTimes().intValue() / 500 + 1;
		
		if (result > 20) {
			result = 20;
		}
		
		return result;
	}
	
	public int getLevel(int tagCount) {
		int result = this.getTimes().intValue() / tagCount + 1;
		
		if (result > 20) {
			result = 20;
		}
		
		return result;
	}
	
	public int getLevel(int minTimes, int everyLevelTimes) {
		if (everyLevelTimes <= 0 )
			everyLevelTimes = 1;
		int result = (this.getTimes().intValue() - minTimes ) / everyLevelTimes;
		
		if (result > 20) {
			result = 20;
		}
		
		return result;
	}
	
	//~ Methods ================================================================

//	public void addTaggedPhoto(PhotoTag photoTag) {
//		if (photoTag == null) {
//			throw new IllegalArgumentException("Can not add NULL PhotoTag");
//		}
//		getTaggedPhotos().add(photoTag);
//		times = new Integer(times.intValue() + 1);
//	}
	public synchronized void increase() {
		setTimes(new Integer(times.intValue() + 1));
	}
	
	public synchronized void decrease() {
		int newValue = times.intValue() - 1;
		
		if (newValue < 0) {
			newValue = 0;
		}
		setTimes(new Integer(newValue));
	}
	
	public synchronized void increaseUser() {
		setUsers(new Integer(users.intValue() + 1));
	}
	
	public synchronized void decreaseUser() {
		int newValue = users.intValue() - 1;
		
		if (newValue < 0) {
			newValue = 0;
		}
		setUsers(new Integer(newValue));
	}
	
	/*
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {
		if (o instanceof Tag) {
            return name.compareTo(((Tag) o).getName());
        }

        return 0;
	}
	
	//~ Accessors ==============================================================
	
//	public Set getTaggedPhotos() {
//		return taggedPhotos;
//	}
//
//	public void setTaggedPhotos(Set taggedPhotos) {
//		this.taggedPhotos = taggedPhotos;
//	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer count) {
		this.times = count;
	}
	
	public Integer getUsers() {
		return users;
	}
	
	public void setUsers(Integer userCount) {
		this.users = userCount;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String toString() {
		return "Tag[" + name + "]";
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Tag)) {
			return false;
		}
		Tag rhs = (Tag) object;
		return name.equals(rhs.getName());
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return name.hashCode();
	}

}
