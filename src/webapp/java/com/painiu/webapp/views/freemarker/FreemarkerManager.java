package com.painiu.webapp.views.freemarker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

//import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.opensymphony.webwork.views.freemarker.ScopesHashModel;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.painiu.Painiu;
import com.painiu.webapp.personality.MultiDomainContextHolder;
import com.painiu.webapp.views.freemarker.tags.PainiuModels;
import com.opensymphony.xwork.ActionSupport;


import freemarker.ext.beans.BeansWrapper;
//import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;

/**
 * <p>
 * <a href="FreemarkerManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: FreemarkerManager.java 156 2010-12-24 00:56:15Z zhangsf $
 */
public class FreemarkerManager extends com.opensymphony.webwork.views.freemarker.FreemarkerManager {
	//~ Static fields/initializers =============================================

	private static final String PARAMS = "params";
	private static final String APP_ROOT = "app_root";
	private static final String APP_DOMAIN = "app_domain";
	private static final String MEDIA_ROOT = "media_root";
	private static final String DEV_MODE = "dev_mode";
	private static final String FLASH_DOMAIN = "flash_domain";
	private static final String PARTNER_NAME = "partner_name";
	
	public static final String MESSAGES       = "pn_messages";
	public static final String ERRORS         = "pn_errors";
	public static final String LOCAL_MESSAGES = "pn_local_messages";
	public static final String LOCAL_ERRORS   = "pn_local_errors";

	

	//~ Instance fields ========================================================

	private Boolean devMode;
	
	//~ Constructors ===========================================================

	public FreemarkerManager() {
		this.devMode = Boolean.valueOf(Painiu.getAppConfig().isDevMode());
	}
	
	//~ Methods ================================================================
	
	public static void populateApplicationContext(SimpleHash model) {
       //model.put(PARAMS, request.getParameterMap());
        /*model.put(APP_ROOT, MultiDomainContextHolder.getContext().getURL());
        model.put(APP_DOMAIN, MultiDomainContextHolder.getContext().getDomain());
        model.put(MEDIA_ROOT, MultiDomainContextHolder.getContext().getMediaRoot());
        model.put(FLASH_DOMAIN, MultiDomainContextHolder.getContext().getFlashDomain());
        model.put(PARTNER_NAME, MultiDomainContextHolder.getContext().getPartnerName());*/
        
        //model.put(PARAMS, request.getParameterMap());
        model.put(APP_ROOT, Painiu.getAppConfig().getURL());
        model.put(APP_DOMAIN, Painiu.getAppConfig().getDomain());
        model.put(MEDIA_ROOT, Painiu.getAppConfig().getMediaRoot());
        model.put(DEV_MODE, Painiu.getAppConfig().isDevMode());
        
        // populate statics
        BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
        TemplateHashModel staticModels = wrapper.getStaticModels();
        try {
        	model.put("PN", staticModels.get("com.painiu.Painiu"));
        	model.put("Privacy", staticModels.get("com.painiu.core.model.Privacy"));
		model.put("PhotoUtils", staticModels.get("com.painiu.webapp.util.PhotoUtils"));
        	model.put("DateUtils", staticModels.get("com.painiu.webapp.util.DateUtils"));
        	model.put("urls", staticModels.get("com.painiu.util.URLUtils"));
		} catch (TemplateModelException e) {
			e.printStackTrace();
		} 
	}
	
    public void populateContext(ScopesHashModel model, OgnlValueStack stack, Object action, HttpServletRequest request, HttpServletResponse response) {
        super.populateContext(model, stack, action, request, response);
        populateApplicationContext(model);
        model.put(PARAMS,       request.getParameterMap());
        model.put("pn", new PainiuModels(stack, request, response));
        populateMessages(model, stack, action, request, response);
     }
    
    /*public SimpleHash buildTemplateModel(OgnlValueStack stack, Object action, ServletContext servletContext, HttpServletRequest request, HttpServletResponse response, ObjectWrapper wrapper) {
        SimpleHash model = super.buildTemplateModel(stack, action, servletContext, request, response, wrapper);
        model.put("pn", new PainiuModels(stack, request, response));
        return model;
    }*/
    
    @SuppressWarnings("unchecked")
    protected void populateMessages(ScopesHashModel model, OgnlValueStack stack, Object action, HttpServletRequest request, HttpServletResponse response) {
		List errors = new ArrayList(2);
        List messages = new ArrayList(2);
        
        if (action != null) {
        	ActionSupport a = (ActionSupport) action;
        	if (a.hasActionErrors()) {
        		for (Iterator i = a.getActionErrors().iterator(); i.hasNext();) {		
					errors.add(i.next());
				}
        	}
        	if (a.hasFieldErrors()) {
        		for (Iterator i = a.getFieldErrors().entrySet().iterator(); i.hasNext();) {
					Entry entry = (Entry) i.next();
					List fieldError = (List) entry.getValue();
					if (fieldError != null && fieldError.size() > 0) {
						errors.add(fieldError.get(0));
					}
				}
        	}
        	if (a.hasActionMessages()) {
        		for (Iterator i = a.getActionMessages().iterator(); i.hasNext();) {
					messages.add(i.next());
				}
        	}
        }
        
        HttpSession session = request.getSession(false);
        if (session != null) {
        	Collection errorsInSession = (Collection) session.getAttribute(ERRORS);
        	if (errorsInSession != null) {
//        		errors.addAll(errorsInSession);
        		request.setAttribute(ERRORS, errorsInSession);
        		session.removeAttribute(ERRORS);
        	}
        	Collection messagesInSession = (Collection) session.getAttribute(MESSAGES);
        	if (messagesInSession != null) {
//        		messages.addAll(messagesInSession);
        		request.setAttribute(MESSAGES, messagesInSession);
        		session.removeAttribute(MESSAGES);
        	}
        	errorsInSession = (Collection) session.getAttribute(LOCAL_ERRORS);
        	if (errorsInSession != null) {
//        		errors.addAll(errorsInSession);
        		request.setAttribute(LOCAL_ERRORS, errorsInSession);
        		session.removeAttribute(LOCAL_ERRORS);
        	}
        	messagesInSession = (Collection) session.getAttribute(LOCAL_MESSAGES);
        	if (messagesInSession != null) {
//        		messages.addAll(messagesInSession);
        		request.setAttribute(LOCAL_MESSAGES, messagesInSession);
        		session.removeAttribute(LOCAL_MESSAGES);
        	}
        }
        
        if (request.getAttribute(ERRORS) != null) {
        	errors.addAll((Collection) request.getAttribute(ERRORS));
        }
        if (request.getAttribute(MESSAGES) != null) {
        	messages.addAll((Collection) request.getAttribute(MESSAGES));
        }
        
        if (errors.size() > 0) {
        	model.put(ERRORS, errors);
        }
        
        if (messages.size() > 0) {
        	model.put(MESSAGES, messages);
        }

        if (request.getAttribute(LOCAL_ERRORS) != null) {
        	model.put(LOCAL_ERRORS, request.getAttribute(LOCAL_ERRORS));
        }
        if (request.getAttribute(LOCAL_MESSAGES) != null) {
        	model.put(LOCAL_MESSAGES, request.getAttribute(LOCAL_MESSAGES));
        }
    }

}
