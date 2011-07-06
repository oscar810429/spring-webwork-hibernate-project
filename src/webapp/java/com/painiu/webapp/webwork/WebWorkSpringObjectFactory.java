/**
 * @(#)WebWorkSpringObjectFactory.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.webwork;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

import com.opensymphony.webwork.WebWorkConstants;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.util.ObjectFactoryInitializable;
import com.opensymphony.xwork.spring.SpringObjectFactory;
import com.painiu.Painiu;

/**
 * <p>
 * <a href="WebWorkSpringObjectFactory.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: WebWorkSpringObjectFactory.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class WebWorkSpringObjectFactory extends SpringObjectFactory implements ObjectFactoryInitializable {
    private static final Logger logger = LoggerFactory.getLogger(WebWorkSpringObjectFactory.class);

    public void init(ServletContext servletContext) {
        logger.info("Initializing WebWork-Spring integration...");
        ApplicationContext appContext = (ApplicationContext) servletContext.getAttribute(Painiu.APPLICATION_CONTEXT_KEY);
        if (appContext == null) {
            // uh oh! looks like the lifecycle listener wasn't installed. Let's inform the user
            String message = "********** FATAL ERROR STARTING UP SPRING-WEBWORK INTEGRATION **********\n" +
                    "Looks like the ApplicationContext has not setup yet!";
            logger.error(message);
            return;
        }
        
        this.setApplicationContext(appContext);

        String autoWire = Configuration.getString(WebWorkConstants.WEBWORK_OBJECTFACTORY_SPRING_AUTOWIRE);
        int type = AutowireCapableBeanFactory.AUTOWIRE_BY_NAME;   // default
        if ("name".equals(autoWire)) {
            type = AutowireCapableBeanFactory.AUTOWIRE_BY_NAME;
        } else if ("type".equals(autoWire)) {
            type = AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE;
        } else if ("auto".equals(autoWire)) {
            type = AutowireCapableBeanFactory.AUTOWIRE_AUTODETECT;
        } else if ("constructor".equals(autoWire)) {
            type = AutowireCapableBeanFactory.AUTOWIRE_CONSTRUCTOR;
        }
        this.setAutowireStrategy(type);

        logger.info("... initialized WebWork-Spring integration successfully");
    }
}
