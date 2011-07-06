/*
 * Created on Sep 20, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.painiu.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.painiu.Painiu;
import com.painiu.core.model.Token;
import com.painiu.core.model.User;
import com.painiu.util.StringUtils;

/**
 * Action class to send password hints to registered users.
 *
 * <p>
 * <a href="PasswordHintAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 */
public class PasswordHintAction extends BaseAction{
	//~ Static fields/initializers =============================================

	private static final long ONE_DAY = 1000 * 60 * 60 * 24;
	
	//~ Instance fields ========================================================
	
	private String email;
	
	private String token;
	
	private String password;
	private String confirmPassword;
	private String dialog_id;
	private String callback;

	//~ Constructors ===========================================================

	//~ Methods ================================================================
    
    public String send() throws Exception {
    	if ("GET".equals(getRequest().getMethod())) {
    		if (token != null) {
    			return "reset";
    		}
    		return INPUT;
    	}
    	
        // ensure that the username has been sent
        if (email == null) {
            log.warn("Username not specified, notifying user that it's a required field.");

            addActionError(getText("errors.email.required"));
            return INPUT;
        }
        
        if (log.isDebugEnabled()) {
            log.debug("Processing Password Hint...");
        }
        
        User user = null;
        
        try {
        	user = userManager.getUserByEmail(email);
        } catch (ObjectRetrievalFailureException e) {
        	List args = new ArrayList(1);
        	args.add(email);
        	addActionError(getText("errors.user.notfound", args));
        	return INPUT;
        }
        
        Token token = tokenManager.createToken(user, ONE_DAY);
        
        Map model = new HashMap(2);
        model.put("user", user);
        model.put("token", token.getId());
        
        messageEngine.sendMail(user, model, "mails/password_hint.ftl", getLocale());
        
        return SUCCESS;
    }
    
    public String create() throws Exception {
    	
    	    return SUCCESS;
    }
    
    public String reset() throws Exception {
    	assertParamExists("token", token);
    	
    	if ("GET".equals(getRequest().getMethod())) {
    		return INPUT;
    	}
    	
        if (log.isDebugEnabled()) {
            log.debug("Reset password ...");
        }
        
        Token tokenObj = tokenManager.getToken(token);
        
        if (tokenObj == null || tokenObj.getUser() == null) {
        	addActionError(getText("errors.invalid.token"));
        	return INPUT;
        }
        
        if (tokenObj.expired()) {
        	tokenManager.removeToken(tokenObj);
        	addActionError(getText("errors.expired.token"));
        	return INPUT;
        }
        
        String encodedPassword = password;
        
        Boolean encrypt = (Boolean) Painiu.get(Painiu.ENCRYPT_PASSWORD_KEY);
        
        if (encrypt != null && encrypt.booleanValue()) {
            String algorithm = (String) Painiu.get(Painiu.ENC_ALGORITHM_KEY);
    
            if (algorithm == null) { // should only happen for test case
                if (log.isDebugEnabled()) {
                    log.debug("assuming testcase, setting algorithm to 'SHA'");
                }
                algorithm = "SHA";
            }
        
            encodedPassword = StringUtils.encodePassword(password, algorithm);
        }
        
        //passport.updatePassword(tokenObj.getUser().getUsername(), password);
        userManager.setUserPassword(tokenObj.getUser(), encodedPassword);
        
        tokenManager.removeToken(tokenObj);
        
        saveMessage(getText("messages.password.reset"));
        
    	return SUCCESS;
    }
    
    
    /**
     * @param email The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @return Returns the token.
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token The token to set.
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return Returns the confirmPassword.
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}
	/**
	 * @param confirmPassword The confirmPassword to set.
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * @return the dialog_id
	 */
	public String getDialog_id() {
		return dialog_id;
	}

	/**
	 * @param dialogId the dialog_id to set
	 */
	public void setDialog_id(String dialogId) {
		dialog_id = dialogId;
	}

	/**
	 * @return the callback
	 */
	public String getCallback() {
		return callback;
	}

	/**
	 * @param callback the callback to set
	 */
	public void setCallback(String callback) {
		this.callback = callback;
	}
	
	
	
	
	
}
