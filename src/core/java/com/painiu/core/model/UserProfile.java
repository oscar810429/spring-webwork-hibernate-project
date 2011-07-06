/*
 * @(#)UserProfile.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * <a href="UserProfile.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: UserProfile.java 52 2010-06-15 09:28:19Z zhangsf $
 */
public class UserProfile extends BaseObject {

	//~ Static fields/initializers =============================================
    private static final long serialVersionUID = 7095417919507437332L;

    public static class Sex extends IntegerEnum implements Serializable {
        
        public static final Sex SECRET  = new Sex(0);
        public static final Sex FEMALE  = new Sex(1);
        public static final Sex MALE    = new Sex(2);
        public static final Sex OTHER   = new Sex(3);
        
        private Sex(int value) {
            super(value);
        }
        
        public static Sex valueOf(int value) {
            return (Sex) IntegerEnum.valueOf(Sex.class, value);
        }
    }
    
    //~ Instance fields ========================================================

    private String id;
    private User user;
    private String name;
    private Sex sex = Sex.SECRET;
    private Integer marriageState = 3;
    private Date birthday;
    private String description;
    
    private String country;
    private String province;
    private String city;
    private String street;
    private String postal;
    
    private String mobile;
    
    private String website;
    private String websiteName;
    private String msn;
    private String oicq;
    private String gtalk;
    private String skype;
    
    private Integer occupation = 0;
    private Integer footprint = 0; //record whether the user profile is modified
    
    private String interestedBook;
    private String interestedMusic;
    private String interestedMovie;
    private String interestedSport;
    private String interestedPerson;
    private String interestedBrand;
    private String interestedCollege;
    private String interestedOther;
    
    
    //~ Constructors ===========================================================

    //~ Methods ================================================================
    
    //~ Accessors ==============================================================

    public String getInterestedBook() {
		return interestedBook;
	}

	public void setInterestedBook(String interestedBook) {
		this.interestedBook = interestedBook;
	}

	public String getInterestedBrand() {
		return interestedBrand;
	}

	public void setInterestedBrand(String interestedBrand) {
		this.interestedBrand = interestedBrand;
	}

	public String getInterestedCollege() {
		return interestedCollege;
	}

	public void setInterestedCollege(String interestedCollege) {
		this.interestedCollege = interestedCollege;
	}

	public String getInterestedMovie() {
		return interestedMovie;
	}

	public void setInterestedMovie(String interestedMovie) {
		this.interestedMovie = interestedMovie;
	}

	public String getInterestedMusic() {
		return interestedMusic;
	}

	public void setInterestedMusic(String interestedMusic) {
		this.interestedMusic = interestedMusic;
	}

	public String getInterestedOther() {
		return interestedOther;
	}

	public void setInterestedOther(String interestedOther) {
		this.interestedOther = interestedOther;
	}

	public String getInterestedPerson() {
		return interestedPerson;
	}

	public void setInterestedPerson(String interestedPerson) {
		this.interestedPerson = interestedPerson;
	}

	public String getInterestedSport() {
		return interestedSport;
	}

	public void setInterestedSport(String interestedSport) {
		this.interestedSport = interestedSport;
	}

	/**
     * @return Returns the birthday.
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday The birthday to set.
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * @return Returns the city.
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city The city to set.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return Returns the country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country The country to set.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
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
     * @return Returns the mobile.
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile The mobile to set.
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return Returns the msn.
     */
    public String getMsn() {
        return msn;
    }

    /**
     * @param msn The msn to set.
     */
    public void setMsn(String msn) {
        this.msn = msn;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }


    public Integer getOccupation() {
		return occupation;
	}

	public void setOccupation(Integer occupation) {
		this.occupation = occupation;
	}

	/**
     * @return Returns the oicq.
     */
    public String getOicq() {
        return oicq;
    }

    /**
     * @param oicq The oicq to set.
     */
    public void setOicq(String oicq) {
        this.oicq = oicq;
    }

    /**
     * @return Returns the postal.
     */
    public String getPostal() {
        return postal;
    }

    /**
     * @param postal The postal to set.
     */
    public void setPostal(String postalCode) {
        this.postal = postalCode;
    }

    /**
     * @return Returns the province.
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param province The province to set.
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * @return Returns the sex.
     */
    public Sex getSex() {
        return sex;
    }

    /**
     * @param sex The sex to set.
     */
    public void setSex(Sex sex) {
        this.sex = sex;
    }

    /**
     * @return Returns the street.
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param street The street to set.
     */
    public void setStreet(String street) {
        this.street = street;
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

    /**
     * @return Returns the website.
     */
    public String getWebsite() {
        return website;
    }

    /**
     * @param website The website to set.
     */
    public void setWebsite(String website) {
        this.website = website;
    }
    
    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object object) {
        if (!(object instanceof UserProfile)) {
            return false;
        }
        UserProfile rhs = (UserProfile) object;
        return new EqualsBuilder()
                .append(this.user, rhs.user).isEquals();
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this)
                    .append("user", this.user).toString();
    }

	/**
	 * @return Returns the gtalk.
	 */
	public String getGtalk() {
		return gtalk;
	}

	/**
	 * @param gtalk The gtalk to set.
	 */
	public void setGtalk(String gtalk) {
		this.gtalk = gtalk;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-946859949, 1239643797).append(this.user).append(this.mobile).append(
				this.occupation).append(this.street).append(this.province)
				.append(this.interestedPerson).append(this.msn).append(this.sex)
				.append(this.country).append(this.description).append(this.marriageState)
				.append(this.oicq).append(this.name).append(this.gtalk).append(
						this.city).append(this.website).append(this.birthday)
				.append(this.postal).toHashCode();
	}

	public Integer getMarriageState() {
		return marriageState;
	}

	public void setMarriageState(Integer marriageState) {
		this.marriageState = marriageState;
	}

	public Integer getFootprint() {
		return footprint;
	}

	public void setFootprint(Integer footprint) {
		this.footprint = footprint;
	}

	public String getWebsiteName() {
		return websiteName;
	}

	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}

	public String getSkype() {
		return skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}

}
