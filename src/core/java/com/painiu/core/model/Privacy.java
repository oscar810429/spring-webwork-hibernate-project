/*
 * @(#)Privacy.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.io.Serializable;
import org.springframework.context.i18n.LocaleContextHolder;

import com.opensymphony.xwork.util.LocalizedTextUtil;
import com.painiu.Painiu;


/**
 * <p>
 * <a href="Privacy.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Privacy.java 52 2010-06-15 09:28:19Z zhangsf $
 */
public class Privacy extends IntegerEnum implements Serializable {
	
	private static final long serialVersionUID = 4849051969654992635L;
	public static final Privacy EVERYONE   = new Privacy(Painiu.PRIVACY_PUBLIC);
	public static final Privacy CONTACTS   = new Privacy(Painiu.PRIVACY_PRIVATE | Painiu.PRIVACY_CONTACTS);
	public static final Privacy FRIENDS    = new Privacy(Painiu.PRIVACY_PRIVATE | Painiu.PRIVACY_FRIENDS);
	public static final Privacy FAMILY     = new Privacy(Painiu.PRIVACY_PRIVATE | Painiu.PRIVACY_FAMILY);
	public static final Privacy CONTACTS_FRIENDS = new Privacy (Painiu.PRIVACY_PRIVATE | Painiu.PRIVACY_CONTACTS | Painiu.PRIVACY_FRIENDS);
	public static final Privacy CONTACTS_FAMILYS = new Privacy (Painiu.PRIVACY_PRIVATE | Painiu.PRIVACY_CONTACTS | Painiu.PRIVACY_FAMILY);
	public static final Privacy FRIENDS_FAMILYS = new Privacy (Painiu.PRIVACY_PRIVATE | Painiu.PRIVACY_FRIENDS | Painiu.PRIVACY_FAMILY);
	public static final Privacy NON_PUBLIC = new Privacy(Painiu.PRIVACY_PRIVATE | Painiu.PRIVACY_CONTACTS | Painiu.PRIVACY_FRIENDS | Painiu.PRIVACY_FAMILY);
	public static final Privacy PRIVATE    = new Privacy(Painiu.PRIVACY_PRIVATE);

	
	private static final Map privacies = new HashMap();
	
	private Privacy(int value) {
		super(value);
	}
    
    public static Privacy valueOf(int value) {
    	Privacy privacy = (Privacy) IntegerEnum.valueOf(Privacy.class, value);
    	if (privacy == null) {
    		privacy = (Privacy) privacies.get(new Integer(value));
    	}
    	
    	if (privacy == null) {
    		privacy = new Privacy(value);
    		privacies.put(new Integer(value), privacy);
    	}
    	
    	return privacy;
    }
    
    public boolean grant(Relation relation) {
    	return (value() & relation.value()) > 0;
    }
    
    public boolean isPrivate() {
    	return !isPublic();
    }
    
    public boolean isPublic() {
    	return (Painiu.PRIVACY_EVERYONE & this.value()) > 0;
    }
    
    public boolean isContactsVisible() {
    	return (Painiu.PRIVACY_CONTACTS & this.value()) > 0;
    }
    
    public boolean isFriendsVisible() {
    	return (Painiu.PRIVACY_FRIENDS & this.value()) > 0;
    }
	
    public boolean isFamilyVisible() {
    	return (Painiu.PRIVACY_FAMILY & this.value()) > 0;
    }
    
	
	public String getVisibility() {
		Locale locale = LocaleContextHolder.getLocale();
		
		String key = "label.all.visibility";
		
		if (isPublic()) {
			key = key + ".everyone";
		} else {
			if (isContactsVisible()) {
				key = key + ".contacts";
			}
			if (isFriendsVisible()) {
				key = key + ".friends";
			}
			if (isFamilyVisible()) {
				key = key + ".family";
			}
		}
		
		if (isPrivate() 
				&& !isContactsVisible()
				&& !isFriendsVisible()
				&& !isFamilyVisible()) {
			key = key + ".self";
		}
		
		return LocalizedTextUtil.findDefaultText(key, locale);
	}
}
