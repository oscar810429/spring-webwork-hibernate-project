package com.painiu.core.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

//import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.log4j.Logger;

import com.painiu.Painiu;
import com.painiu.event.EventParameter;
import com.painiu.event.EventSource;
import com.painiu.core.security.Personal;

/**
 * User class - also used to generate the Hibernate mapping file.
 *
 * <p><a href="User.java.html"><i>View Source</i></a></p>
 *
 * @author Zhang Songfu
 * @version $Id: User.java 52 2010-06-15 09:28:19Z zhangsf $
 */
public class User extends BaseObject implements EventSource, EventParameter, Personal {

	//~ Static fields/initializers =============================================
    private static final long serialVersionUID = -9210215924521366079L;

	private static final Logger logger = Logger.getLogger(User.class);

	public static class State extends IntegerEnum implements Serializable {

		public static final State DISABLE = new State(0);
		public static final State ENABLE  = new State(1);

		private State(int value) {
			super(value);
		}

		public static State valueOf(int value) {
			return (State) IntegerEnum.valueOf(State.class, value);
		}
	}
	
	public static class UserRank extends IntegerEnum implements Serializable {
		
		public static final UserRank PRODUCT 		= new UserRank(0);
		public static final UserRank OTHER 			= new UserRank(1);
		public static final UserRank GREEN 			= new UserRank(2);
		public static final UserRank POPULAR 		= new UserRank(3);
		public static final UserRank COMMENDATORY  	= new UserRank(4);
		public static final UserRank SENIOR 		= new UserRank(5);
		public static final UserRank CHECK_LATER 	= new UserRank(6);
		public static final UserRank BLOCKED		= new UserRank(7);
		
		private UserRank(int value) {
			super(value);
		}
		public static UserRank valueOf(int value) {
			return (UserRank) IntegerEnum.valueOf(UserRank.class, value);
		}
	}

	//~ Instance fields ========================================================

	private String id;

    private String username; // assigned primary key
    private String password;
    private String confirmPassword;
    private String email;
    //private String buddyIcon;
    private Icon buddyIcon;
    private String nickname;
    private String blast;
    private Locale locale = Locale.SIMPLIFIED_CHINESE;

    private Date createdDate = new Date();
    private State state = State.ENABLE;

    private Set roles = new HashSet(3);

    private UserStat stat;
    private UserProfile profile;
    private UserPreference  preference;

    private Set roleNames = new HashSet(3); // should not be here, but i have no choice :(
    
    private UserRank userRank = UserRank.GREEN;


	//~ Constructors ===========================================================


	public User() {}

    public User(String id) {
        this.id = id;
    }

    //~ Methods ================================================================
    
    public boolean isInRole(String roleName) {
    	return this.roleNames.contains(roleName);
    }
    
    public boolean isVIP() {
    	return isInRole(Painiu.VIP_BUSINESS_ROLE) || isInRole(Painiu.VIP_NORMAL_ROLE);
    }


    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

    public void setUsername(String username) {
		this.username = username;
	}

	public String getBlast() {
		return blast;
	}

	public void setBlast(String blast) {
		this.blast = blast;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set getRoles() {
		return roles;
	}

	public void setRoles(Set roles) {
		this.roles = roles;
		this.updateRoles();
	}

	public void addRole(Role role) {
		this.getRoles().add(role);
		this.updateRoles();
	}
	
	public void activateVip(Role role) {
		Role frozen = null;
		for (Iterator i = roles.iterator(); i.hasNext();) {
			frozen = (Role) i.next();
			if ( Painiu.FROZEN_ROLE.equals(frozen.getName()) ) {
				break;
			}
			frozen = null;
		}
		if (frozen != null) {
			this.getRoles().remove(frozen);
		}
		this.getRoles().add(role);
		this.updateRoles();
	}
	
	public void removeRole(Role role) {
		if (this.getRoles().remove(role)) {
			this.updateRoles();
		}
	}
	
	public void autoDemotion(Role role) {
		if (this.getRoles().remove(role)) {
			this.updateRoles();
		}
	}
	
	private void updateRoles() {
		Set set = new HashSet(roles.size());
		for (Iterator i = roles.iterator(); i.hasNext();) {
			Role role = (Role) i.next();
			set.add(role.getName());
		}
		this.setRoleNames(set);
	}

	/**
	 * @return Returns the state.
	 */
	public State getState() {
		return state;
	}

	/**
	 * @param state The state to set.
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * @return Returns the locale.
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * @param locale The locale to set.
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/**
	 * @return Returns the stat.
	 */
	public UserStat getStat() {
		return stat;
	}

	/**
	 * @param stat The stat to set.
	 */
	public void setStat(UserStat stat) {
		stat.setUser(this);
		this.stat = stat;
	}

    /**
     * @return Returns the profile.
     */
    public UserProfile getProfile() {
        return profile;
    }

    /**
     * @param profile The profile to set.
     */
    public void setProfile(UserProfile profile) {
        profile.setUser(this);
        this.profile = profile;
    }

    /**
	 * @return the roleNames
	 */
	public Set getRoleNames() {
		return roleNames;
	}

	/**
	 * @param roleNames the roleNames to set
	 */
	public void setRoleNames(Set roleNames) {
		this.roleNames = roleNames;
	}
	
	/**
	 * Implementation of Personal
	 */
	public User getPerson() {
		return this;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(888280531, -203524539)
				.append(this.password).append(this.nickname)
				.append(this.createdDate).append(this.blast)
				.append(this.id).append(this.locale)
				.append(this.email).append(this.state)
				.append(this.buddyIcon).toHashCode();

	}

    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object o) {
    	if (this == o) return true;
        if (!(o instanceof User)) return false;

        final User user = (User) o;

        if (email != null ? !email.equals(user.getEmail()) : user.getEmail() != null) return false;

        return true;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new StringBuffer().append("User[")
                .append(this.getUsername()).append(": ")
                .append(this.getEmail()).append("]").toString();
    }

	/**
	 * @return the userRank
	 */
	public UserRank getUserRank() {
		return userRank;
	}

	/**
	 * @param userRank the userRank to set
	 */
	public void setUserRank(UserRank userRank) {
		this.userRank = userRank;
	}
	
	public Icon getBuddyIcon() {
		return buddyIcon;
	}

	public void setBuddyIcon(Icon buddyIcon) {
		this.buddyIcon = buddyIcon;
	}
	
	
	 public boolean hasBuddyIcon() {
			if (logger.isDebugEnabled()) {
				logger.debug("hasBuddyIcon1() - start"); 
			}

	    	if (Photo.USE_PNFS) {
				boolean returnboolean = (buddyIcon != null && buddyIcon.getFileKey() != null);
				if (logger.isDebugEnabled()) {
					logger.debug("hasBuddyIcon2() - end"); 
				}
	    		return returnboolean;
	    	}
			boolean returnboolean = (buddyIcon != null && buddyIcon.getHost() != null && buddyIcon.getDir() != null && buddyIcon.getFilename() != null);
			if (logger.isDebugEnabled()) {
				logger.debug("hasBuddyIcon3() - end"); 
			}
	    	return returnboolean;
	    }

	    public String getBuddyIconURL() {
			if (logger.isDebugEnabled()) {
				logger.debug("getBuddyIconURL1() - start"); 
			}

	    	if (hasBuddyIcon()) {
				String returnString = buddyIcon.getURL();
				if (logger.isDebugEnabled()) {
					logger.debug("getBuddyIconURL2() - end"); 
				}
	    		return returnString;
	    	}
			String returnString = Icon.getDefaultURL();
			if (logger.isDebugEnabled()) {
				logger.debug("getBuddyIconURL3() - end"); 
			}
	    	return returnString;
	    }
	    
	    public boolean hasAccountIcon() {
			if (logger.isDebugEnabled()) {
				logger.debug("hasAccountIcon() - start"); 
			}

			boolean returnboolean = isInRole(Painiu.PRO_ROLE) || isInRole(Painiu.ADMIN_ROLE) || isInRole(Painiu.MANAGER_ROLE) || isInRole(Painiu.HOT_ROLE) || isInRole(Painiu.VOLUNTEER_ROLE);
			if (logger.isDebugEnabled()) {
				logger.debug("hasAccountIcon() - end"); 
			}
	    	return returnboolean;
	    }
	    
	    public String getAccountIconURL() {
			if (logger.isDebugEnabled()) {
				logger.debug("getAccountIconURL() - start"); 
			}

	    	if (isInRole(Painiu.PRO_ROLE)) {
				String returnString = Icon.getProIconURL();
				if (logger.isDebugEnabled()) {
					logger.debug("getAccountIconURL() - end"); 
				}
	    		return returnString;
	    	} else if (isInRole(Painiu.ADMIN_ROLE) || isInRole(Painiu.MANAGER_ROLE)) {
				String returnString = Icon.getStaffIconURL();
				if (logger.isDebugEnabled()) {
					logger.debug("getAccountIconURL() - end"); 
				}
	    		return returnString;
	    	} else if (isInRole(Painiu.VOLUNTEER_ROLE)) {
				String returnString = Icon.getVolunteerIconURL();
				if (logger.isDebugEnabled()) {
					logger.debug("getAccountIconURL() - end"); 
				}
	    		return returnString;
	    	} else if (isInRole(Painiu.HOT_ROLE)) {
				String returnString = Icon.getHotIconURL();
				if (logger.isDebugEnabled()) {
					logger.debug("getAccountIconURL() - end"); 
				}
	    		return returnString;
	    	}

			if (logger.isDebugEnabled()) {
				logger.debug("getAccountIconURL() - end"); 
			}
	    	return null;
	    }
	    
	    public String getAccountIconDesc() {
			if (logger.isDebugEnabled()) {
				logger.debug("getAccountIconDesc() - start"); 
			}

	    	if (isInRole(Painiu.PRO_ROLE)) {
				if (logger.isDebugEnabled()) {
					logger.debug("getAccountIconDesc() - end"); 
				}
	    		return "base.user.pro";
	    	} else if (isInRole(Painiu.ADMIN_ROLE) || isInRole(Painiu.MANAGER_ROLE)) {
				if (logger.isDebugEnabled()) {
					logger.debug("getAccountIconDesc() - end"); 
				}
	    		return "base.user.staff";
	    	} else if (isInRole(Painiu.VOLUNTEER_ROLE)) {
				if (logger.isDebugEnabled()) {
					logger.debug("getAccountIconDesc() - end"); 
				}
	    		return "base.user.volunteer";
	    	} else if (isInRole(Painiu.HOT_ROLE)) {
				if (logger.isDebugEnabled()) {
					logger.debug("getAccountIconDesc() - end"); 
				}
	    		return "base.user.hot";
	    	}

			if (logger.isDebugEnabled()) {
				logger.debug("getAccountIconDesc() - end"); 
			}
	    	return "base.user.none";
	    }

		/**
		 * @return the preference
		 */
		public UserPreference getPreference() {
			return preference;
		}

		/**
		 * @param preference the preference to set
		 */
		public void setPreference(UserPreference preference) {
			preference.setUser(this);
			this.preference = preference;
		}

       



	
}
