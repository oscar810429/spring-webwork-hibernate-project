package com.painiu.webapp.listener;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.acegisecurity.providers.AuthenticationProvider;
import org.acegisecurity.providers.ProviderManager;
import org.acegisecurity.providers.encoding.Md5PasswordEncoder;
import org.acegisecurity.providers.rememberme.RememberMeAuthenticationProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.painiu.Painiu;
import com.painiu.cache.CacheManager;
import com.painiu.core.service.LookupManager;

/**
 * StartupListener class used to initialize and database settings
 * and populate any application-wide drop-downs.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *
 */
public class StartupListener extends ContextLoaderListener
							implements ServletContextListener {
	
	private static final Log log = LogFactory.getLog(StartupListener.class);
	
	public void contextInitialized(ServletContextEvent event) {
		if (log.isDebugEnabled()) {
			log.debug("Initializing context...");
		}
		
		ServletContext context = event.getServletContext();

		if (log.isDebugEnabled()) {
			log.debug("Loading application configurations......");
		}
		
		try {
			Painiu.setupConfigurations(context);
		} catch (IOException e) {
			log.error("Application startup failed becauseof ConfigurationException: ", e);
			e.printStackTrace();
		}
		
//		context.setAttribute(Painiu.CONFIG_KEY, Painiu.getConfig());
		
		
		if (log.isDebugEnabled()) {
			log.debug("Initializing spring application context...");
		}
		// call Spring's context ContextLoaderListener to initialize
		// all the context files specified in web.xml
		super.contextInitialized(event);
		
		ApplicationContext ctx =
			WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		
		//Painiu.setConfigManager((ConfigManager) ctx.getBean("configManager"));
		
		boolean encryptPassword = false;
		try {
			ProviderManager provider = (ProviderManager) ctx.getBean("authenticationManager");
			for (Iterator it = provider.getProviders().iterator(); it.hasNext();) {
				AuthenticationProvider p = (AuthenticationProvider) it.next();
				if (p instanceof RememberMeAuthenticationProvider) {
					Painiu.set(Painiu.REMEMBER_ME_ENABLED_KEY, Boolean.TRUE);
				}
			}
			
			if (ctx.containsBean("passwordEncoder")) {
				encryptPassword = true;
				Painiu.set(Painiu.ENCRYPT_PASSWORD_KEY, Boolean.TRUE);
				String algorithm = "SHA";
				if (ctx.getBean("passwordEncoder") instanceof Md5PasswordEncoder) {
					algorithm = "MD5";
				}
				Painiu.set(Painiu.ENC_ALGORITHM_KEY, algorithm);
			}
		} catch (NoSuchBeanDefinitionException n) {
			// ignore, should only happen when testing
		}
		
		
		// output the retrieved values for the Init and Context Parameters
		if (log.isDebugEnabled()) {
			log.debug("Remember Me Enabled? " + Painiu.get(Painiu.REMEMBER_ME_ENABLED_KEY));
			log.debug("Encrypt Passwords? " + encryptPassword);
			if (encryptPassword) {
				log.debug("Encryption Algorithm: " + Painiu.get(Painiu.ENC_ALGORITHM_KEY));
			}
			log.debug("Populating drop-downs...");
		}

		setupContext(context);
	}
	
	public static void setupContext(ServletContext context) {
		ApplicationContext ctx = 
			WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		
		LookupManager mgr = (LookupManager) ctx.getBean("lookupManager");
		
		// get list of possible roles
		context.setAttribute(Painiu.AVAILABLE_ROLES, mgr.getAllRoles());
		
		if (log.isDebugEnabled()) {
			log.debug("Drop-down initialization complete [OK]");
		}
		
		// store Node IP
		try {
			String nodeID = getIPAddress();
			
			context.setAttribute(Painiu.CLUSTER_NODE_IP, nodeID);
			
			if (log.isDebugEnabled()) {
				log.debug("Cluster node ID: " + nodeID);
			}
		} catch (SocketException e) {}
	}
	
	/**
	 * Close the root web application context.
	 */
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
		
		CacheManager.shutdown();
	}
	
	private static final String getIPAddress() throws SocketException {
		InetAddress addr = null;
		
		NetworkInterface i = NetworkInterface.getByName("eth0");
		
		if (i != null) {
			Enumeration<InetAddress> ie = i.getInetAddresses();
			if (ie.hasMoreElements()) {
				addr = ie.nextElement();
			}
		}
		
		if (addr == null || addr.isLoopbackAddress()) {
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			
			while (e.hasMoreElements()) {
				i = e.nextElement();
				
				Enumeration<InetAddress> ie = i.getInetAddresses();
				if (ie.hasMoreElements()) {
					addr = ie.nextElement();
					break;
				}

				if (!addr.isLoopbackAddress()) break;
			}
		}
		
		return addr == null ? null : addr.getHostAddress();
	}
}
