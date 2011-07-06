package com.painiu.core.model;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * <a href="PhotoStat.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: SoftwareStat.java 135 2010-11-23 09:28:01Z zhangsf $
 */
public class SoftwareStat extends BaseObject {

	//~ Static fields/initializers =============================================
	
	private static final long serialVersionUID = 5511383049390457954L;

	//~ Instance fields ========================================================

	private String id;
	
	private Software software;
    
	
	private Integer views = new Integer(0);
	
	private Integer notes = new Integer(0);
	
	private Integer comments = new Integer(0);
	
	private Integer favorites = new Integer(0);
	
	private Integer interests = new Integer(0);
	
	private Integer score = new Integer(0); // 

//	private Integer albums = new Integer(0);
//	private Integer groups = new Integer(0);
	
	//~ Constructors ===========================================================
	
	//~ Methods ================================================================
	
    private void updateInterests() {
        setInterests(views.intValue() + comments.intValue() * 5 + notes.intValue() * 5 + favorites.intValue() * 10 + score.intValue() * 20);
    }
    
	//~ Accessors ==============================================================

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getComments() {
		return comments;
	}

	public void setComments(Integer comments) {
		this.comments = comments;
        this.updateInterests();
	}

	public void increaseComments() {
		setComments(this.comments.intValue() + 1);
	}
	
	public void decreaseComments() {
		setComments(this.comments.intValue() - 1);
	}
	
	public void setComments(int comments) {
		this.setComments(new Integer(comments));
	}

	public Integer getNotes() {
		return notes;
	}

	public void setNotes(Integer notes) {
		this.notes = notes;
        this.updateInterests();
	}
	
	public void setNotes(int notes) {
		this.setNotes(new Integer(notes));
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
        this.updateInterests();
	}
	
	public void increaseViews() {
		this.setViews(views.intValue() + 1);
	}
	
	public void setViews(int views) {
		this.setViews(new Integer(views));
	}
	
	public Integer getInterests() {
		return this.interests;
	}
	
	public void setInterests(Integer interests) {
		this.interests = interests;
	}
	
	public void setInterests(int interests) {
		this.setInterests(new Integer(interests));
	}

	/**
	 * @return Returns the favorites.
	 */
	public Integer getFavorites() {
		return favorites;
	}
	/**
	 * @param favorites The favorites to set.
	 */
	public void setFavorites(Integer favorites) {
		this.favorites = favorites;
        this.updateInterests();
	}
	public void increaseFavorites() {
		this.setFavorites(new Integer(favorites.intValue() + 1));
	}
	public void decreaseFavorites() {
		if (favorites.intValue() > 0) {
			this.setFavorites(new Integer(favorites.intValue() - 1));
		}
	}
	
	/**
	 * @return Returns the score.
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * @param score The score to set.
	 */
	public void setScore(Integer score) {
		this.score = score;
		this.updateInterests();
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
		if (!(object instanceof SoftwareStat)) {
			return false;
		}
		SoftwareStat rhs = (SoftwareStat) object;
		return this.id.equals(rhs.getId());
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(163435109, 1179424419).append(this.id)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("views", this.views)
				.append("interests", this.interests)
				.append("comments", this.comments)
				.append("favorites", this.favorites)
				.append("notes", this.notes)
				.append("score", this.score).toString();
	}

}
