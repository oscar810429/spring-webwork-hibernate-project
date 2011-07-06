/*
 * @(#)PhotoUtils.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.util;

//import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
//import org.springframework.context.i18n.LocaleContextHolder;
//import com.opensymphony.xwork.util.LocalizedTextUtil;
import com.painiu.Painiu;
import com.painiu.core.model.License;
//import com.painiu.core.model.Photo;
import com.painiu.core.model.Privacy;

/**
 * <p>
 * <a href="PhotoUtils.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: PhotoUtils.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class PhotoUtils {
    /**
     * Convenience method to get the photo privacy setting from request
     * 
     * @param request
     * @return
     */
	public static Privacy getPrivacy(HttpServletRequest request) {
    	return getPrivacy(request, null);
    }
	
	public static Privacy getCommentPrivacy(HttpServletRequest request) {
    	return getPrivacy(request, "comment");
	}
	
	public static Privacy getTagPrivacy(HttpServletRequest request) {
		return getPrivacy(request, "addmeta");
	}
	
	public static Privacy getDownloadPrivacy(HttpServletRequest request) {
		return getPrivacy(request, "downloads");
	}
	
	public static Privacy getBlogPrivacy(HttpServletRequest request) {
		return getPrivacy(request, "blogthis");
	}
	
	public static Privacy getExifPrivacy(HttpServletRequest request) {
		return getPrivacy(request, "exif");
	}
	public static Privacy getAllPrivacy(HttpServletRequest request) {
		return getPrivacy(request, "all");
	}
	
	public static Privacy getPrivacy(HttpServletRequest request, String prefix) {
		String isPublic = request.getParameter(prefix == null ? "is_public" : prefix + "_is_public");
		String isContact = request.getParameter(prefix == null ? "is_contact" : prefix + "_is_contact");
		String isFriend = request.getParameter(prefix == null ? "is_friend" : prefix + "_is_friend");
		String isFamily = request.getParameter(prefix == null ? "is_family" : prefix + "_is_family");
		
		return getPrivacyValue(isPublic, isContact, isFriend, isFamily);
	}
	
	public static Privacy getPrivacyValue(String isPublic, String isContact, String isFriend, String isFamily) {
		int privacy = Painiu.PRIVACY_SYSTEM | Painiu.PRIVACY_PRIVATE;
        if (("1").equals(isPublic)) {
            privacy = Painiu.PRIVACY_PUBLIC;
        } else {
            if (("1").equals(isContact)) {
                privacy = privacy | Painiu.PRIVACY_CONTACTS;
            }
            if (("1").equals(isFriend)) {
                privacy = privacy | Painiu.PRIVACY_FRIENDS;
            }
            if (("1").equals(isFamily)) {
                privacy = privacy | Painiu.PRIVACY_FAMILY;
            }
        }
        
        return Privacy.valueOf(privacy);
	}
	
	public static License getLicense(HttpServletRequest request) {
		String license = request.getParameter("license");
		if (license == null || "".equals(license)) {
			return License.ATTRIBUTION_NONCOMMERCIAL_SHAREALIKE;
		}
		return License.valueOf(Integer.valueOf(license).intValue());
	}

	/*public static String getVisibility(Photo photo) {
		Locale locale = LocaleContextHolder.getLocale();
		
		String key = "label.visibility";
		
		if (photo.isPublic()) {
			key = key + ".everyone";
		} else {
			if (photo.isContactsVisible()) {
				key = key + ".contacts";
			}
			if (photo.isFriendsVisible()) {
				key = key + ".friends";
			}
			if (photo.isFamilyVisible()) {
				key = key + ".family";
			}
		}
		
		if (photo.isPrivate() 
				&& !photo.isContactsVisible()
				&& !photo.isFriendsVisible()
				&& !photo.isFamilyVisible()) {
			key = key + ".self";
		}
		
		return LocalizedTextUtil.findDefaultText(key, locale, new Object[] { Painiu.getAppConfig().getMediaRoot() });
	}*/
	
	public static boolean isPrivate(Privacy privacy) {
		return !isPublic(privacy);
	}
	
	public static boolean isPublic(Privacy privacy) {
		return (Painiu.PRIVACY_EVERYONE & privacy.value()) > 0;
	}
    
    public static boolean isContactsGrant(Privacy privacy) {
    	return (Painiu.PRIVACY_CONTACTS & privacy.value()) > 0;
    }
    
    public static boolean isFriendsGrant(Privacy privacy) {
    	return (Painiu.PRIVACY_FRIENDS & privacy.value()) > 0;
    }
    
    public static boolean isFamilyGrant(Privacy privacy) {
    	return (Painiu.PRIVACY_FAMILY & privacy.value()) > 0;
    }
    
    
    public static String toLinkURL(String url) {
    	String linkURL = StringUtils.replace(url, "http://photo.", "http://pic.");
    	if (linkURL.endsWith("/")) {
    		linkURL = linkURL.substring(0, linkURL.length() - 1) + ".jpg";
    	}
    	return linkURL;
    }
    
    public static String toApiURL(String url) {
    	return StringUtils.replace(url, "http://photo.", "http://photo0.");
    }
}
