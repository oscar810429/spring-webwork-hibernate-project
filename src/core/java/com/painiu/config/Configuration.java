/*
 * @(#)Configuration.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import com.painiu.webapp.image.LocalDiskRepository;

/**
 * <p>
 * <a href="Configuration.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Configuration.java 8 2010-05-11 16:48:01Z zhangsf $
 */
public class Configuration {
	//~ Static fields/initializers =============================================
	
	private static final Log log = LogFactory.getLog(Configuration.class);

	//~ Instance fields ========================================================

	private ApplicationConfig appConfig;
	private CacheConfig cacheConfig;
	private IconConfig iconConfig;
	private I18NConfig i18nConfig;
	private MessageConfig messageConfig;
	private PhotoConfig photoConfig;
	private BlacklistConfig blacklistConfig;
	private WebWorkConfig webworkConfig;
	private SetConfig setConfig;
	private SmsConfig smsConfig;
	//private UploadConfig uploadConfig;
	
	private Digester digester;
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================

	public void load(InputStream in) throws IOException {
		digester = new Digester();
		digester.setValidating(false);
		
		addRules(digester);
		digester.push(this);
		
		try {
			digester.parse(in);
		} catch (SAXException se) {
			log.error("Error parsing input file:" + se.getMessage());
			throw new IOException("SAXException occurred while parsing configuration file.");
		}
	}
	
	private static void addRules(Digester d) {
		// Application configurations
		d.addObjectCreate("config/app", ApplicationConfig.class);
		d.addSetProperties("config/app", 
				new String[] { "media-root", "user-domain-pattern", "flash-domain", "dev-mode" },
				new String[] { "mediaRoot", "userDomainPattern", "flashDomain", "devMode" });
		d.addSetNext("config/app", "setAppConfig");
		
		// Cache configurations
		d.addObjectCreate("config/cache", CacheConfig.class);
		d.addSetProperties("config/cache");
		d.addSetNext("config/cache", "setCacheConfig");
		
		// Message configurations
		d.addObjectCreate("config/message", MessageConfig.class);
		d.addSetProperties("config/message", 
				new String[] { "check-interval" }, 
				new String[] { "checkInterval" } );
		d.addSetNext("config/message", "setMessageConfig");
		
		// Photo configurations
		d.addObjectCreate("config/photo", PhotoConfig.class);
		d.addSetProperties("config/photo", 
				new String[] { "url-pattern", "mail-upload-api-key", "batch-size", "ypfs" },
				new String[] { "urlPattern", "mailUploadApiKey", "batchSize", "ypfs" });
		d.addSetNext("config/photo", "setPhotoConfig");
		
		d.addCallMethod("config/photo/repositories", "setDefaultHost", 1, new String[] { "java.lang.Integer" });
		d.addCallParam("config/photo/repositories", 0, "default");
		d.addObjectCreate("config/photo/repositories/repository", LocalDiskRepository.class);
		d.addSetProperties("config/photo/repositories/repository",
				new String[] { "read-only" }, 
				new String[] { "readOnly" });
		d.addSetNext("config/photo/repositories/repository", "addRepository");
		
		d.addCallMethod("config/photo/image-types/content-type", "addImageType", 0);
		d.addCallMethod("config/photo/zip-types/content-type", "addZipType", 0);
		
		d.addObjectCreate("config/photo/flowmeter", FlowmeterConfig.class);
		d.addSetProperties("config/photo/flowmeter");
		d.addCallMethod("config/photo/flowmeter/maximum", "setMaximum", 2, new String[] { "java.lang.String", "java.lang.Long" });
		d.addCallParam("config/photo/flowmeter/maximum", 0, "role");
		d.addCallParam("config/photo/flowmeter/maximum", 1, "value");
		d.addSetNext("config/photo/flowmeter", "setFlowmeterConfig");
		
		d.addObjectCreate("config/photo/progress", ProgressConfig.class);
		d.addSetProperties("config/photo/progress",
				new String[] { "update-interval", "delay" },
				new String[] { "updateInterval", "delay" });
		d.addSetNext("config/photo/progress", "setProgressConfig");
		
		d.addSetProperties("config/photo/maximum", 
				new String[] { "length", "width", "height" },
				new String[] { "maxLength", "maxWidth", "maxHeight" });
		
		/*d.addObjectCreate("config/photo/filesize", UploadConfig.class);
		d.addSetProperties("config/photo/filesize");
		d.addCallMethod("config/photo/filesize/maximum", "setMaximum", 2, new String[] { "java.lang.String", "java.lang.Long" });
		d.addCallParam("config/photo/filesize/maximum", 0, "role");
		d.addCallParam("config/photo/filesize/maximum", 1, "value");
		d.addSetNext("config/photo/filesize", "setUploadConfig");*/
		
		// Icon configurations
		d.addObjectCreate("config/icon", IconConfig.class);
		d.addSetProperties("config/icon", 
				new String[] { "url-pattern" },
				new String[] { "urlPattern" });
		d.addSetNestedProperties("config/icon");
		d.addSetNext("config/icon", "setIconConfig");
		
		// WebWork configurations
		d.addObjectCreate("config/webwork", WebWorkConfig.class);
		d.addSetProperties("config/webwork");
		d.addSetNext("config/webwork", "setWebworkConfig");
		
		d.addCallMethod("config/webwork/excludes/ext", "addExcludeExtension", 0);
		d.addCallMethod("config/webwork/excludes/dir", "addExcludeDirectory", 0);
		
		// I18N configurations
		d.addObjectCreate("config/i18n", I18NConfig.class);
		d.addSetNext("config/i18n", "setI18nConfig");
		d.addCallMethod("config/i18n/locales/locale", "addLocale", 0);
		
		// Blacklist configurations
		d.addObjectCreate("config/blacklist", BlacklistConfig.class);
		d.addSetProperties("config/blacklist");
		d.addSetNext("config/blacklist", "setBlacklistConfig");
		
		// Set configurations
		d.addObjectCreate("config/set/", SetConfig.class);
		d.addCallMethod("config/set/create-limit/maximum", "setCreateLimit", 2, new String[] { "java.lang.String", "java.lang.Integer"});
		d.addCallParam("config/set/create-limit/maximum", 0, "role");
		d.addCallParam("config/set/create-limit/maximum", 1, "value");
		d.addSetNext("config/set","setSetConfig");
		
		// sms configurations
		d.addObjectCreate("config/sms", SmsConfig.class);
		d.addCallMethod("config/sms/sms-group/smscode", "setSmsCodeGroup", 2, new String[] {"java.lang.String", "java.lang.String"});
		d.addCallParam("config/sms/sms-group/smscode", 0, "code");
		d.addCallParam("config/sms/sms-group/smscode", 1, "group");
		d.addSetProperties("config/sms");
		d.addSetNext("config/sms", "setSmsConfig");
	}
	
	//~ Accessors ==============================================================
	/**
	 * @return the appConfig
	 */
	public ApplicationConfig getAppConfig() {
		return appConfig;
	}
	/**
	 * @param appConfig the appConfig to set
	 */
	public void setAppConfig(ApplicationConfig appConfig) {
		this.appConfig = appConfig;
	}
	/**
	 * @return the cacheConfig
	 */
	public CacheConfig getCacheConfig() {
		return cacheConfig;
	}
	/**
	 * @param cacheConfig the cacheConfig to set
	 */
	public void setCacheConfig(CacheConfig cacheConfig) {
		this.cacheConfig = cacheConfig;
	}
	/**
	 * @return the iconConfig
	 */
	public IconConfig getIconConfig() {
		return iconConfig;
	}
	/**
	 * @param iconConfig the iconConfig to set
	 */
	public void setIconConfig(IconConfig iconConfig) {
		this.iconConfig = iconConfig;
	}
	/**
	 * @return the i18nConfig
	 */
	public I18NConfig getI18nConfig() {
		return i18nConfig;
	}
	/**
	 * @param config the i18nConfig to set
	 */
	public void setI18nConfig(I18NConfig config) {
		i18nConfig = config;
	}
	/**
	 * @return the messageConfig
	 */
	public MessageConfig getMessageConfig() {
		return messageConfig;
	}
	/**
	 * @param messageConfig the messageConfig to set
	 */
	public void setMessageConfig(MessageConfig messageConfig) {
		this.messageConfig = messageConfig;
	}
	/**
	 * @return the photoConfig
	 */
	public PhotoConfig getPhotoConfig() {
		return photoConfig;
	}
	/**
	 * @param photoConfig the photoConfig to set
	 */
	public void setPhotoConfig(PhotoConfig photoConfig) {
		this.photoConfig = photoConfig;
	}
	/**
	 * @return the blacklistConfig
	 */
	public BlacklistConfig getBlacklistConfig() {
		return blacklistConfig;
	}
	/**
	 * @param blacklistConfig the blacklistConfig to set
	 */
	public void setBlacklistConfig(BlacklistConfig blacklistConfig) {
		this.blacklistConfig = blacklistConfig;
	}
	/**
	 * @return the webworkConfig
	 */
	public WebWorkConfig getWebworkConfig() {
		return webworkConfig;
	}
	/**
	 * @param webworkConfig the webworkConfig to set
	 */
	public void setWebworkConfig(WebWorkConfig webworkConfig) {
		this.webworkConfig = webworkConfig;
	}

	/**
	 * @return the setConfig
	 */
	public SetConfig getSetConfig() {
		return setConfig;
	}

	/**
	 * @param setConfig the setConfig to set
	 */
	public void setSetConfig(SetConfig setConfig) {
		this.setConfig = setConfig;
	}
	
	/**
	 * @return the smsConfig
	 */
	public SmsConfig getSmsConfig() {
		return smsConfig;
	}
	
	/**
	 * @param smsConfig the smsConfig to set
	 */
	public void setSmsConfig(SmsConfig smsConfig) {
		this.smsConfig = smsConfig;
	}
	
	public static void main(String[] args) throws IOException {
		FileInputStream in = new FileInputStream("web/WEB-INF/mingda.xml");
		
		Configuration config = new Configuration();
		
		config.load(in);
		
		System.out.println(config.photoConfig.usePNFS());	
	}

	/**
	 * @return the uploadConfig
	 */
	/*public UploadConfig getUploadConfig() {
		return uploadConfig;
	}*/

	/**
	 * @param uploadConfig the uploadConfig to set
	 */
	/*public void setUploadConfig(UploadConfig uploadConfig) {
		this.uploadConfig = uploadConfig;
	}*/
}
