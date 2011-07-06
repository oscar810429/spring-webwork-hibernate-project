/**
 * @(#)SignupAction.java Jul 28, 2008
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.action.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.ProviderManager;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.painiu.Context;
import com.painiu.core.exception.EmailExistsException;
import com.painiu.core.exception.UserExistsException;
import com.painiu.core.exception.UsernameExistsException;
import com.painiu.core.model.User;
import com.painiu.core.model.UserProfile;
import com.painiu.core.model.UserProfile.Sex;
import com.painiu.core.service.SignupService;
import com.painiu.util.DateUtils;
import com.painiu.webapp.action.BaseAction;
import com.painiu.webapp.exception.NeedRedirectionException;
import com.painiu.webapp.security.AbstractAuthenticationHandler;
import com.google.code.kaptcha.Constants;
//import com.painiu.webapp.util.Blacklist;
//import com.painiu.webapp.util.URLUtils;

/**
 * <p>
 * <a href="SignupAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zola Zhou
 * @version $Id: SignupAction.java 85 2010-07-01 19:37:02Z zhangsf $
 */
public class SignupAction extends BaseAction {
	//~ Static fields/initializers =============================================

	private static final long serialVersionUID = -6716175527077069969L;

	//~ Instance fields ========================================================

	private SignupService signupService;
	
	private User user;
    private String readPerm;
    private String confirmPassword;
    private String verifyCode;
	private String birthdayYear;
	private String birthdayMonth;
	private String birthdayDay;
	private Integer gender;
	private String userProvince;
	private String userCity;

	//~ Constructors ===========================================================

	//~ Methods ================================================================


	public String execute() {
        /*if ("GET".equals(getRequest().getMethod())) {
        	if (invite != null) {
        		inviteObj = inviteManager.getInvite(invite);
        	}
            return INPUT;
        }*/
		
        if ("GET".equals(getRequest().getMethod())) {
            return INPUT;
        }
         return SUCCESS;
    }
    
    public String confirm() {
      	user = getCurrentUser();
    	    //Date now = new Date();
		//Calendar calendar = Calendar.getInstance();
		//calendar.add(Calendar.DATE, -3);
		//interestingPhotos = photoManager.getInterestingnessPhotos(calendar.getTime(), now, 8);
       	return SUCCESS;
    }
	
    public String save() throws Exception {
     	try {
        	    String kaptchaExpected = (String)this.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            String kaptchaReceived = verifyCode;

            if (kaptchaReceived == null || !kaptchaReceived.equalsIgnoreCase(kaptchaExpected))
            {
            	  addActionError(getText("errors.kaptchcode"));
              return INPUT;
            }else{
    		      if ("on".equals(readPerm)) {
    			   return signup(user, null); 
    		      }
    		      addActionError(getText("errors.perm.unchecked"));
			  return INPUT;
            }  
			
    	   } catch (UserExistsException e) {
    		  List<String> args = new ArrayList<String>(2);
    		  if (e instanceof UsernameExistsException) {
    			args.add(user.getUsername());
    			addActionError(getText("errors.username.existing", args));
    		  } else if (e instanceof EmailExistsException) {
    			args.add(user.getEmail());
    			addActionError(getText("errors.email.existing", args));
    	  	  } else {
    			args.add(user.getUsername());
    			args.add(user.getEmail());
    			addActionError(getText("errors.user.existing", args));
    		  }
            
            user.setId(null);
            user.setPassword(null);
            
            return INPUT;
       	} catch (NeedRedirectionException e) {
    		  return redirect(e.getRedirectTo());
    	    }
    }

    public String signup(User user, String invite) throws UserExistsException, NeedRedirectionException {
       	String username = user.getUsername();
    	    String email = user.getEmail();
    	    String password = user.getPassword();
    	    UserProfile userProfile = new UserProfile();
    	    String dateString;
        if (birthdayYear != null && !"".equals(birthdayYear.trim())
            		&& birthdayMonth != null && !"".equals(birthdayMonth.trim())
            		&& birthdayDay != null && !"".equals(birthdayDay.trim())) {   
            	try {
            		int year = Integer.parseInt(birthdayYear);
            		int month = Integer.parseInt(birthdayMonth);
            		int day = Integer.parseInt(birthdayDay);
            		if (birthdayYear.length() == 4 && year>999 && year <10000 && month>0 && month<13 && day>0 && day<32) {
            			dateString = birthdayYear + "-" + birthdayMonth + "-" + birthdayDay;
            			userProfile.setBirthday(DateUtils.convertStringToDate(dateString));
            		} 
            	} catch(Exception e) {
            		e.printStackTrace();
            		log.debug(e.getMessage());
            	}
         } else {
            	   userProfile.setBirthday(null);
        }
        
        userProfile.setProvince(userProvince);
        userProfile.setCity(userCity);
        userProfile.setSex(Sex.valueOf(gender.intValue()));

        user = signupService.signup(username, email, password,userProfile);

    	   // log user in automatically
       Authentication auth = new UsernamePasswordAuthenticationToken(user.getId(), password);
    	   try {
    		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(this.getSession().getServletContext());
        if (ctx != null) {
    			ProviderManager authenticationManager = (ProviderManager) ctx.getBean("authenticationManager");
    			SecurityContextHolder.getContext().setAuthentication(authenticationManager.doAuthentication(auth));
    		 }
    	   } catch (NoSuchBeanDefinitionException n) {
    		// ignore, should only happen when testing
    	  }

    	// Send an account information e-mail
    	Map<String, Object> model = new HashMap<String, Object>(2);
    	model.put("user", user);
    	model.put("password", password);
    	messageEngine.sendMail(user, model, "mails/signup.ftl", user.getLocale());

    	String redirectTo = null;

    	if (invite != null) {
    		//redirectTo = URLUtils.getURL("/invites/accept?signup=true&id=" + invite);
    	} else if (getFrom() != null) {
    		redirectTo = getFrom();
    	} else if (getSession().getAttribute(AbstractAuthenticationHandler.SECURITY_FORWARD_KEY) != null) {
    		redirectTo = (String) getSession().getAttribute(AbstractAuthenticationHandler.SECURITY_FORWARD_KEY);
    		// keep session small
    		getSession().removeAttribute(AbstractAuthenticationHandler.SECURITY_FORWARD_KEY);
    	}

    	if (redirectTo != null) {
    		throw new NeedRedirectionException(redirectTo);
    	}

    	return SUCCESS;
    }

    
    
	//~ Accessors ==============================================================
    
    /**
	 * @param signupService the signupService to set
	 */
	public void setSignupService(SignupService signupService) {
		this.signupService = signupService;
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
	 * @param readPerm the readPerm to set
	 */
	public void setReadPerm(String readPerm) {
		this.readPerm = readPerm;
	}
	
	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}
	
	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * @return the verifyCode
	 */
	public String getVerifyCode() {
		return verifyCode;
	}

	/**
	 * @param verifyCode the verifyCode to set
	 */
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	/**
	 * @return the birthdayYear
	 */
	public String getBirthdayYear() {
		return birthdayYear;
	}

	/**
	 * @param birthdayYear the birthdayYear to set
	 */
	public void setBirthdayYear(String birthdayYear) {
		this.birthdayYear = birthdayYear;
	}

	/**
	 * @return the birthdayMonth
	 */
	public String getBirthdayMonth() {
		return birthdayMonth;
	}

	/**
	 * @param birthdayMonth the birthdayMonth to set
	 */
	public void setBirthdayMonth(String birthdayMonth) {
		this.birthdayMonth = birthdayMonth;
	}

	/**
	 * @return the birthdayDay
	 */
	public String getBirthdayDay() {
		return birthdayDay;
	}

	/**
	 * @param birthdayDay the birthdayDay to set
	 */
	public void setBirthdayDay(String birthdayDay) {
		this.birthdayDay = birthdayDay;
	}

	/**
	 * @return the gender
	 */
	public Integer getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(Integer gender) {
		this.gender = gender;
	}

	/**
	 * @return the userProvince
	 */
	public String getUserProvince() {
		return userProvince;
	}

	/**
	 * @param userProvince the userProvince to set
	 */
	public void setUserProvince(String userProvince) {
		this.userProvince = userProvince;
	}

	/**
	 * @return the userCity
	 */
	public String getUserCity() {
		return userCity;
	}

	/**
	 * @param userCity the userCity to set
	 */
	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}


	
	
	
}
