package com.painiu.core.model;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * This class is used to manage cookie-based authentication.
 *
 * <p>
 * <a href="UserCookie.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: UserCookie.java 7 2010-05-11 16:23:49Z zhangsf $
 */
public class UserCookie extends BaseObject {
    private static final long serialVersionUID = 4050479002315731765L;
    
//    private Long id;
    private String userId;
    private String cookieId;
    private Date createdDate;

    public UserCookie() {
        this.createdDate = new Date();
    }

    /**
     * Returns the id.
     * @return String
     *
     * @hibernate.id column="id"
     *  generator-class="increment" unsaved-value="null"
     */
//    public Long getId() {
//        return id;
//    }

    /**
     * Sets the id.
     * @param id The id to set
     */
//    public void setId(Long id) {
//        this.id = id;
//    }

    /**
     * Returns the username.
     * @return String
     *
     * @hibernate.property
     * @hibernate.column name="username" not-null="true"
     *  index="user_cookie_username_cookie_id"
     */
//    public String getUsername() {
//        return username;
//    }

    /**
     * Sets the userId.
     * @param userId The userId to set
     */
//    public void setUsername(String username) {
//        this.username = username;
//    }

    /**
	 * @return Returns the userId.
	 */
	public String getUserId() {
		return userId;
	}
	
	/**
	 * @param userId The userId to set.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
    
    /**
     * Returns the cookieId (a GUID).
     * @return String
     *
     * @hibernate.property
     * @hibernate.column name="cookie_id" not-null="true"
     *  length="100" index="user_cookie_email_cookie_id"
     */
    public String getCookieId() {
        return cookieId;
    }

    /**
     * Sets the cookieId.
     * @param rolename The cookieId to set
     */
    public void setCookieId(String cookieId) {
        this.cookieId = cookieId;
    }

    /**
     * @return Returns the createdDate.
    * @hibernate.property column="created_date" not-null="true"
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate The createdDate to set.
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Generated using Commonclipse (http://commonclipse.sf.net)
     */
    public boolean equals(Object object) {
        if (!(object instanceof UserCookie)) {
            return false;
        }

        UserCookie rhs = (UserCookie) object;

        return new EqualsBuilder().append(this.cookieId, rhs.cookieId).isEquals();
    }

    /**
     * Generated using Commonclipse (http://commonclipse.sf.net)
     */
    public int hashCode() {
        return new HashCodeBuilder(1954972321, -113979947).append(this.userId)
                                                          .append(this.createdDate)
                                                          .append(this.cookieId)
                                                          .toHashCode();
    }
    
    /**
     * Generated using Commonclipse (http://commonclipse.sf.net)
     */
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
//                .append("id", this.id)
                .append("userId", this.userId)
                .append("cookieId", this.cookieId)
                .append("createdDate", this.createdDate).toString();
    }
}
