/**
 * @(#)ProfileAction.java Nov 18, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action.account;

import java.util.ArrayList;
import java.util.List;

import org.acegisecurity.providers.dao.SaltSource;
import org.acegisecurity.providers.encoding.PasswordEncoder;

import com.painiu.core.exception.UserExistsException;
import com.painiu.core.model.User;
import com.painiu.core.model.UserProfile;
import com.painiu.core.model.UserProfile.Sex;
import com.painiu.webapp.webwork.ValidationResultAware;
import com.painiu.util.DateUtils;

/**
 * <p>
 * <a href="ProfileAction.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Zhang Songfu
 * @version $Id: ProfileAction.java 97 2010-07-05 20:07:56Z zhangsf $
 */
public class ProfileAction extends BaseAccountAction implements ValidationResultAware {
	// ~ Static fields/initializers
	// =============================================

	// ~ Instance fields
	// ========================================================

	private static final long serialVersionUID = 1L;
	private User user;
	private UserProfile profile;

	private String birthdayYear;
	private int birthdayMonth;
	private int birthdayDay;

	private String city;

	private String email;
	private String username;
	private String blast;

	private String nickname;

	private String oldpassword;
	private String password;
	private String confirmPassword;

	private String purpose;

	private SaltSource saltSource;
	
	//private int sex;
	private PasswordEncoder passwordEncoder;
	private String uid;
	// ~ Methods
	// ================================================================

	public String execute() throws Exception {
		user = getCurrentUser();
		if (user == null) {
			return loginRequired();
		}
		profile = user.getProfile();
        if (profile.getBirthday() != null) {
            String dateString = DateUtils.convertDateToString(profile.getBirthday());           
            try {            	
                birthdayYear = dateString.substring(0, 4);
                if(Integer.parseInt(birthdayYear) < 2){
                	birthdayYear = "";
                }
                birthdayMonth = Integer.parseInt(dateString.substring(5, 7));
                birthdayDay = Integer.parseInt(dateString.substring(8, 10));
            } catch (NumberFormatException e) {}
        }
		return SUCCESS;
	}
	
	public String edit() throws Exception{
    	
		user = getCurrentUser();
		if (user == null) {
			return loginRequired();
		}
		profile = user.getProfile();
        if (profile.getBirthday() != null) {
            String dateString = DateUtils.convertDateToString(profile.getBirthday());           
            try {            	
                birthdayYear = dateString.substring(0, 4);
                if(Integer.parseInt(birthdayYear) < 2){
                	birthdayYear = "";
                }
                birthdayMonth = Integer.parseInt(dateString.substring(5, 7));
                birthdayDay = Integer.parseInt(dateString.substring(8, 10));
            } catch (NumberFormatException e) {}
        }
		
		return SUCCESS;
    	
	}
	
	
	public String user() throws Exception{
		assertParamExists("uid",uid);
		user = userManager.getUserByUsername(uid);
		profile = user.getProfile();
		return SUCCESS;
	}
	
    public String save() throws Exception {
    	User person = userManager.getUser(getCurrentUser().getId()); // this is necessary
        UserProfile old = person.getProfile();
        String dateString;
        if (birthdayYear != null && !"".equals(birthdayYear.trim())) {   
        	try {
        		int year = Integer.parseInt(birthdayYear);
        		if (birthdayYear.length() == 4 && year>999 && year <10000 && birthdayMonth>0 && birthdayMonth<13 && birthdayDay>0 && birthdayDay<32) {
        			dateString = birthdayYear + "-" + birthdayMonth + "-" + birthdayDay;
        			old.setBirthday(DateUtils.convertStringToDate(dateString));
        		} else {
        			addActionError(getText("errors.birthday"));
        			return INPUT;
        		}

        	} catch(Exception e) {
        		addActionError(getText("errors.birthday"));
        		return INPUT;
        	}
        } else {
        	old.setBirthday(null);
        }
        if (org.apache.commons.lang.StringUtils.isBlank(profile.getName())) {
        	old.setName(null);
        } else {
        	old.setName(profile.getName());
        }
        int footprint = old.getFootprint();
        old.setSex(profile.getSex());
        old.setMarriageState(profile.getMarriageState());
        old.setOccupation(profile.getOccupation());
        old.setMsn(profile.getMsn());
        old.setOicq(profile.getOicq());
        old.setFootprint(1);
        old.setGtalk(profile.getGtalk());
        old.setSkype(profile.getSkype());
        old.setProvince(profile.getProvince());
        old.setCity(profile.getCity());
        old.setPostal(profile.getPostal());
        old.setMobile(profile.getMobile());
        if(profile.getWebsite()!= null && !"".equals(profile.getWebsite().trim())&& profile.getWebsite().toLowerCase().indexOf("http://")!=0) {
        	profile.setWebsite("http://" + profile.getWebsite());
        }
        old.setWebsite(profile.getWebsite());
        
        /*if ( Blacklist.getBlacklist().isBlacklisted(profile.getWebsiteName())) {
        	addActionError(getText("errors.websitename.in.blacklist"));
       	 	return INPUT;
       }*/
        
        old.setWebsiteName(profile.getWebsiteName());
        
        /*if ( Blacklist.getBlacklist().isBlacklisted(profile.getDescription())) {
        	 addActionError(getText("errors.description.in.blacklist"));
        	 return INPUT;
        }*/
        
        old.setDescription(profile.getDescription());
        
        person.setProfile(old);
        
        user = person;
        profile = old;
        
        try {
	        //userManager.updateUserProfile(old);
	        //SecurityUtils.setCurrentUser(person);
	        //getSession().setAttribute(Painiu.USER_KEY, person);
        	userManager.saveUser(person);

	        if (footprint == 0) {
	        	saveMessage(getText("messages.profile.saved.person"));
	        	return redirect("/profile/admin");
	        }
	        saveMessage(getText("messages.profile.saved"));
	        return SUCCESS;
        }
        catch (UserExistsException e) {
        	if (log.isWarnEnabled()) {
        		log.warn(e.getMessage());
        	}
            List<String> args = new ArrayList<String>(1);
            args.add(user.getNickname());
            
            addActionError(getText("errors.nickname.existing",args));

            return INPUT;
        }
    }
    public String save_nickname() {
    	User person = userManager.getUser(getCurrentUser().getId());
    	/*if ( Blacklist.getBlacklist().isBlacklisted(nickname)) {
       	 	saveActionError(getText("errors.nickname.in.blacklist"));
       	 	return INPUT;
    	}*/
    	if (org.apache.commons.lang.StringUtils.isNotEmpty(nickname)) {
    		person.setNickname(nickname);
    	}else {
    		saveError(getText("messages.nickname.should.not.null"));
    		return INPUT;
    	}
    	try{
    		userManager.saveUser(person);
    	}catch(Exception e){
    		saveError(getText("messages.nickname.is.exists"));
    		return INPUT;
    	}
    	currentUser = getCurrentUser();
        addActionMessage(getText("messages.nickname.saved"));
        return SUCCESS;
    }

    public String save_city() throws Exception {
    	User person = userManager.getUser(getCurrentUser().getId()); // this is necessary
        if (profile.getCountry().equals("0")) {
        	profile.setCity("");
        	profile.setProvince("");
        }else if ( !profile.getCountry().equals(getText("china")) ) {
        	profile.setProvince("");
        	profile.setCity(city);
        }else if (profile.getProvince() == null){
        	profile.setProvince("");
        }
        
        /*if ( Blacklist.getBlacklist().isBlacklisted(profile.getCity())) {
       	 	addActionError(getText("errors.city.in.blacklist"));
       	 	return INPUT;
        }*/
        UserProfile old = person.getProfile();
        old.setCountry(profile.getCountry());
        old.setProvince(profile.getProvince());
        if(profile.getCountry().equals(getText("china"))){
        	old.setCity(profile.getCity());
        }else {
        	old.setCity(city);
        	old.setProvince("");
        }  
        
        user = person;
        userManager.saveUser(user);
        saveMessage(getText("messages.profile.saved"));
        return SUCCESS;
    }

    
    public String save_blast() throws Exception {
    	User person = userManager.getUser(getCurrentUser().getId()); // this is necessary
    	/*if ( Blacklist.getBlacklist().isBlacklisted(blast)) {
    		saveActionError(getText("errors.blast.in.blacklist"));
         	return INPUT;
        }*/
        person.setBlast(blast);
        user = person;
        profile = person.getProfile();
        //userManager.saveUser(user);
        //userManager.updateBlast(user);
        //SecurityUtils.setCurrentUser(user);
        //saveMessage(getText("messages.profile.saved"));
        return SUCCESS;
        //return redirect(URLUtils.getUserProfileURL(user));
    }
    public String save_email() throws Exception {
    	User user = userManager.getUser(getCurrentUser().getId());
    	profile = user.getProfile();
    	String oldEmail = user.getEmail();
    	
    	try {
         // passport.updateEmail(user.getUsername(), email);
    		
    		user.setEmail(email);
    		
        //  userManager.updateEmail(user);           
        //  SecurityUtils.setCurrentUser(user);
        //  getSession().setAttribute(Yupoo.USER_KEY, user);
            
           // saveMessage(getText("messages.profile.email.saved"));
    	} catch (Exception e) {
        	//if (logger.isWarnEnabled()) {
        	//	logger.warn(e.getMessage());
        	//}
            List<String> args = new ArrayList<String>(1);
            args.add(email);
            addActionError(getText("errors.email.existing", args));
            
            user.setEmail(oldEmail);
            return INPUT;
        }
        
    	return SUCCESS;
    }
    public String save_password() throws Exception {
    	try {  
	    	User user = userManager.getUser(getCurrentUser().getId());
	    	String newPassword = passwordEncoder.encodePassword(password, saltSource);
	    	String oldPassword = passwordEncoder.encodePassword(oldpassword, saltSource);
	    	if (user.getPassword().equals(oldPassword)){
	    		user.setPassword(newPassword);
	    	}else{
	    		 addActionError(getText("errors.password.notequals"));
	    		 return INPUT;
	    	}
	    	userManager.saveUser(user);
        } catch (Exception e) {
        	addActionError(getText("errors.password.exception"));
        	return INPUT;
        }
 
    	return SUCCESS;
    }
    
    public String admin() throws Exception {
    	
		user = getCurrentUser();
		if (user == null) {
			return loginRequired();
		}
		profile = user.getProfile();
		
        if (profile.getBirthday() != null) {
            String dateString = DateUtils.convertDateToString(profile.getBirthday());           
            try {            	
                birthdayYear = dateString.substring(0, 4);
                if(Integer.parseInt(birthdayYear) < 2){
                	birthdayYear = "";
                }
                birthdayMonth = Integer.parseInt(dateString.substring(5, 7));
                birthdayDay = Integer.parseInt(dateString.substring(8, 10));
            } catch (NumberFormatException e) {}
        }
    	
    	
    	return SUCCESS;
    }
	// ~ Accessors
	// ==============================================================

	/**
	 * @return Returns the user.
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            The user to set.
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return Returns the profile.
	 */
	public UserProfile getProfile() {
		return profile;
	}

	/**
	 * @param profile
	 *            The profile to set.
	 */
	public void setProfile(UserProfile profile) {
		this.profile = profile;
	}

	/**
	 * @return Returns the birthdayDay.
	 */
	public int getBirthdayDay() {
		return birthdayDay;
	}

	/**
	 * @param birthdayDay
	 *            The birthdayDay to set.
	 */
	public void setBirthdayDay(int birthdayDay) {
		this.birthdayDay = birthdayDay;
	}

	/**
	 * @return Returns the birthdayMonth.
	 */
	public int getBirthdayMonth() {
		return birthdayMonth;
	}

	/**
	 * @param birthdayMonth
	 *            The birthdayMonth to set.
	 */
	public void setBirthdayMonth(int birthdayMonth) {
		this.birthdayMonth = birthdayMonth;
	}

	/**
	 * @return Returns the birthdayYear.
	 */
	public String getBirthdayYear() {
		return birthdayYear;
	}

	/**
	 * @param birthdayYear
	 *            The birthdayYear to set.
	 */
	public void setBirthdayYear(String birthdayYear) {
		this.birthdayYear = birthdayYear;
	}

	/**
	 * @return Returns the confirmPassword.
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword
	 *            The confirmPassword to set.
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * @return Returns the oldpassword.
	 */
	public String getOldpassword() {
		return oldpassword;
	}

	/**
	 * @param oldpassword
	 *            The oldpassword to set.
	 */
	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}

	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return Returns the username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            The username to set.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public String getBlast() {
		return blast;
	}

	public void setBlast(String blast) {
		this.blast = blast;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.painiu.webapp.action.ValidationResultAware#onError()
	 */
	public void onError() {
		try {
			execute();
		} catch (Exception e) {

		}
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the purpose
	 */
	public String getPurpose() {
		return purpose;
	}

	/**
	 * @param purpose
	 *            the purpose to set
	 */
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}


	/*public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}*/
	
	/**
	 * @param passwordEncoder the passwordEncoder to set
	 */
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	
}