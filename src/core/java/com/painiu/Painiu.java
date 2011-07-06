/**
 * @(#)Painiu.java Oct 22, 2008
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import com.painiu.Painiu;
import com.painiu.config.ApplicationConfig;
import com.painiu.config.BlacklistConfig;
import com.painiu.config.CacheConfig;
import com.painiu.config.Configuration;
import com.painiu.config.I18NConfig;
import com.painiu.config.IconConfig;
import com.painiu.config.MessageConfig;
import com.painiu.config.PhotoConfig;
import com.painiu.config.SetConfig;
import com.painiu.config.SmsConfig;
import com.painiu.config.WebWorkConfig;

/**
 * <p>
 * <a href="Painiu.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Painiu.java 61 2010-06-15 12:05:16Z zhangsf $
 */
public class Painiu {
		//~ Static fields/initializers =============================================
		// environment property
	    public static final String FILE_ENCODING = getSystemProperty("file.encoding");
	    public static final String FILE_SEPARATOR = getSystemProperty("file.separator");
	    public static final String LINE_SEPARATOR = getSystemProperty("line.separator");
	    public static final String PATH_SEPARATOR = getSystemProperty("path.separator");
	    public static final String USER_HOME = getSystemProperty("user.home") + FILE_SEPARATOR;
	    
	    public static final String APPLICATION_CONTEXT_KEY = "painiu_application_context";

	    // publish time
	    public static final long Painiu_TIME = 1117555200000L;
	    
	    // role names
	    public static final String ADMIN_ROLE = "admin";
	    public static final String MANAGER_ROLE = "manager";
	    public static final String VOLUNTEER_ROLE = "volunteer";    
	    public static final String PRO_ROLE   = "proman";
	    public static final String HOT_ROLE   = "hotman";
	    public static final String FREE_ROLE  = "freeman";
	    public static final String UPLOADER_ROLE  = "uploader";
	    public static final String FROZEN_ROLE = "frozenman";
	    public static final String CS_ROLE = "cs";
	    public static final String VIP_NORMAL_ROLE 	= "vip_normal";
	    public static final String VIP_BUSINESS_ROLE 	= "vip_business";
	    public static final String AGENT_ROLE 	= "agent";
	    public static final String FINANCE_ROLE 	= "finance";
	    
	    public static final String AGENT_MANAGER_ROLE 	= "agent_manager";
	    

	    // group members
	    public static final String GROUP_ADMIN  = "admin";
	    public static final String GROUP_MEMBER = "member";
	    
	    // user account
	    public static final String USER_KEY = "currentUser";
	    public static final String AVAILABLE_ROLES = "availableRoles";
	    public static final String REGISTERED = "registered";
	    public static final String LOGIN_COOKIE = "sessionId";
	    public static final String DOMAIN_CONTEXT = "domainContext";
	    public static final String CLUSTER_NODE_IP = "clusterNodeIP";
	    public static final String MULTI_DOMAIN_CONTEXT = "multiDomainContext";
	    
	    // photo constants
	    public static final String PHOTO_SQUARE    = "square";
	    public static final String PHOTO_THUMB     = "thumb";
	    public static final String PHOTO_SMALL     = "small";
	    public static final String PHOTO_MEDIUM    = "medium";
	    public static final String PHOTO_LARGE     = "large";

	    public static final int PHOTO_SQUARE_SIZE    = 75;
	    public static final int PHOTO_THUMB_SIZE     = 100;
	    public static final int PHOTO_SMALL_SIZE     = 240;
	    public static final int PHOTO_MEDIUM_SIZE    = 500;
	    public static final int PHOTO_LARGE_SiZE = 1024;

	    public static final String SUFFIX_SQUARE    = "_s.jpg";
	    public static final String SUFFIX_THUMB     = "_t.jpg";
	    public static final String SUFFIX_SMALL     = "_m.jpg";
	    public static final String SUFFIX_MEDUIM    = ".jpg";
	    public static final String SUFFIX_LARGE     = "_o.jpg";
	    
	    public static final String SUFFIX_META      = ".meta";

	    // photo upload flowmeter types
	    public static final String FLOWMETER_PHOTO_COUNT  = "count";
	    public static final String FLOWMETER_FILE_LENGTH  = "length";
	    
	    // photo privacy
	    public static final int PRIVACY_NONE  		= 0;
	    public static final int PRIVACY_EVERYONE    = 1 << 0;
	    public static final int PRIVACY_CONTACTS    = 1 << 1;
	    public static final int PRIVACY_FRIENDS     = 1 << 2;
	    public static final int PRIVACY_FAMILY      = 1 << 3;
	    public static final int PRIVACY_PRIVATE     = 1 << 4;
	    public static final int PRIVACY_SYSTEM      = 1 << 5;
	    public static final int PRIVACY_PUBLIC      = PRIVACY_EVERYONE | PRIVACY_CONTACTS | PRIVACY_FRIENDS | PRIVACY_FAMILY | PRIVACY_PRIVATE | PRIVACY_SYSTEM;
	    
	    // creative types
	    public static final int CREATIVE_AUTHOR     = 1;
	    public static final int CREATIVE_REFERENCED = 2;

	    // message status
	    //  public static final int MESSAGE_STATUS_UNREAD  = 0;
	    //  public static final int MESSAGE_STATUS_READ    = 1;
	    //  public static final int MESSAGE_STATUS_REPLY   = 2;
	    
	    // session attribute keys for photo batch operation
	    //  public static final String PHOTO_BATCH_IDS_KEY = "photo_batch_operation_ids_key";

		// painiu.com's api key
	    public static final String HospGroup_API_KEY = "painiu.com";
		
	    public static final String INBOX_MESSAGES_KEY = "inbox_messages";
	    
	    public static final String API_ENDPOINT = "/api/";
//	    public static final String API_CONTEXT_KEY = "api_context";
	    
	    // system news type
	    public static final int NEWS_BULLETIN = 10;
	    public static final int NEWS_SYSTEM = 0;
	    public static final int NEWS_GROUPS = 1;
	    public static final int NEWS_AD = 2;
	    public static final int NEWS_IMPORTANT = 3;
	    public static final int NEWS_TAG = 4;
	    public static final int NEWS_BLOG = 11;
	    
	    public static final int IMAGE_INDEX = 5;
	    public static final int IMAGE_ACTIVITY = 6;
	    
	    public static final int JS_IMAGE_INDEX = 25;
	    public static final int JS_IMAGE_ACTIVITY = 26;
	    
	    public static final int FJ_IMAGE_INDEX = 35;
	    public static final int FJ_IMAGE_ACTIVITY = 36;
	    
	    public static final int TAG = 7;
	    public static final String TAG_KIND_HOMEPAGE = "home";
	    public static final String TAG_KIND_INDEXPAGE = "index";
	    
	    public static final String PAGE_KIND = "index";
	    
	    public static final int NEWS_TEXT = 0;
	    public static final int NEWS_IMAGE = 1;
	    public static final int NEWS_TEXT_TAG=2;
	    
	    //tags:
	    
	    public static int HOME_TAG = 1;
		public static int USER_HOME_TAG = 2;
		public static int EXPLORE_TAG = 3;
		public static int CURRENT_DAY_TAG = 4;
		public static int HOT_TAG = 5;
		
		//Event
		public static int EVENT = 0; //活动
		public static int THEME = 1; //专题
		public static int COLLECTION = 2; //征集
		
		public static String COLLECTION_NEW = "0"; //新征集
		public static String COLLECTION_LONG = "1"; //长期征集
		public static String COLLECTION_PASS = "2"; //征集
		
		///////////////////////////////////////////////////////////////////////////////
		// Application Configuration
		///////////////////////////////////////////////////////////////////////////////
	    
		public static final String CONFIG_FILE = "configFile";
	    public static final String CONFIG_KEY = "appConfig";
	    public static final String BUNDLE_KEY = "ApplicationResources";
	    
	    // property keys
	    public static final String APP_ROOT_KEY            = "appRoot";
	    public static final String APP_URL_KEY             = "appUrl";
	    public static final String APP_DOMAIN_KEY          = "appDomain";
	    
	    public static final String ENC_ALGORITHM_KEY       = "algorithm";
	    public static final String ENCRYPT_PASSWORD_KEY    = "encryptPassword";
	    public static final String REMEMBER_ME_ENABLED_KEY = "remebermeEnabled";
	    
	    public static final String DOMAIN_FILTER_LIST		= "^(www[0-9]*|photo[0-9]*|mail|blog|painiu|console|admin|manage|send|beta|sender|hdyx|miss|search|group|forum|help|web2|print|star|travel|music|baby|show|rank|worldcup|list|2005|2006|2007|2008|2009|2010|client|city|lomo|blogger|wiki|free|school|news|life|tech|podcast|football|game|tour|auto|sport|mobile|post|chat|digi|test|demo|login|proxy)$";
	    public static final String OUT_LINK_FLASH_SECRET_KEY= "sldfojosfOFJDOjf!@30f";
		
	    public static final String DOMAIN_DEV = "painiu.com";
	    public static final String DOMAIN_RUNTIME = "painiu.com";
	    
//		private static XMLConfiguration config = null;
	    private static Configuration config = null;
	    
	    private static Map props = new HashMap();
	    
//	    private static ConfigManager configManager = null; 
//	    private static Map configCache = Collections.synchronizedMap(new HashMap());
	 
	    public static Object get(String key) {
	    	return props.get(key);
	    }
	    
	    public static void set(String key, Object value) {
	    	props.put(key, value);
	    }

	    /**
	     * Set up Environment
	     * @param servletContext 
	     */
	    public static void setupConfigurations(ServletContext servletContext) throws IOException {
	        String appRoot = servletContext.getRealPath("/");
	        
	        if (appRoot != null) {
	            if (!appRoot.endsWith(FILE_SEPARATOR)) {
	                appRoot += FILE_SEPARATOR;
	            }
	            set(APP_ROOT_KEY, appRoot);
	        }
	        
	        // init configuration
	        String configFile = servletContext.getInitParameter(Painiu.CONFIG_FILE);
	        
	        if (configFile == null) {
	            configFile = "/WEB-INF/painiu.xml";
	        }
	        
	        config = new Configuration();
	        
	        // Create a config object to hold all the app config values
	        InputStream in = null;
	        
	        if (appRoot != null) {
	        	in = new FileInputStream(servletContext.getRealPath(configFile));
	        } else {
	        	URL url = servletContext.getResource(configFile);
	        	in = url.openStream();
	        }
	        
	        try {
	        	config.load(in);
	        } finally {
	        	if (in != null) {
	        		in.close();
	        	}
	        }
	    }

		public static ApplicationConfig getAppConfig() {
			return config.getAppConfig();
		}
		public static CacheConfig getCacheConfig() {
			return config.getCacheConfig();
		}
		public static IconConfig getIconConfig() {
			return config.getIconConfig();
		}
		public static I18NConfig getI18nConfig() {
			return config.getI18nConfig();
		}
		public static MessageConfig getMessageConfig() {
			return config.getMessageConfig();
		}
		public static PhotoConfig getPhotoConfig() {
			return config.getPhotoConfig();
		}
		public static BlacklistConfig getBlacklistConfig() {
			return config.getBlacklistConfig();
		}
		public static WebWorkConfig getWebworkConfig() {
			return config.getWebworkConfig();
		}
	    public static SetConfig getSetConfig() {
	    	return config.getSetConfig();
	    }
	    public static SmsConfig getSmsConfig() {
	    	return config.getSmsConfig();
	    }
	    
	    /**
	     * <p>Gets a System property, defaulting to <code>null</code> if the property
	     * cannot be read.</p>
	     *
	     * <p>If a <code>SecurityException</code> is caught, the return
	     * value is <code>null</code> and a message is written to <code>System.err</code>.</p>
	     * 
	     * @param property the system property name
	     * @return the system property value or <code>null</code> if a security problem occurs
	     */
	    public static String getSystemProperty(String property) {
	        try {
	            return System.getProperty(property);
	        } catch (SecurityException ex) {
	            // we are not allowed to look at this property
	            System.err.println(
	                "Caught a SecurityException reading the system property '" + property 
	                + "'; the SystemUtils property value will default to null."
	            );
	            return null;
	        }
	    }

}
