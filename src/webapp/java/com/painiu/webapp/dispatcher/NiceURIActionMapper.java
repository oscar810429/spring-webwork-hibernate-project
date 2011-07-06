/*
 * @(#)NiceURIActionMapper.java Oct 22 ,2008
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.dispatcher;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.webwork.RequestUtils;
import com.opensymphony.webwork.WebWorkConstants;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.dispatcher.ServletRedirectResult;
import com.opensymphony.webwork.dispatcher.mapper.ActionMapper;
import com.opensymphony.webwork.dispatcher.mapper.ActionMapping;
import com.opensymphony.webwork.util.PrefixTrie;
import com.opensymphony.xwork.config.ConfigurationManager;
import com.opensymphony.xwork.config.entities.ActionConfig;

/**
 * <p>
 * <a href="NiceURIActionMapper.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: NiceURIActionMapper.java 6 2010-05-11 16:20:57Z zhangsf $
 */
public class NiceURIActionMapper implements ActionMapper {

    static final String METHOD_PREFIX = "method:";
    static final String ACTION_PREFIX = "action:";
    static final String REDIRECT_PREFIX = "redirect:";
    static final String REDIRECT_ACTION_PREFIX = "redirect-action:";

    static PrefixTrie prefixTrie = new PrefixTrie() {
        {
            put(METHOD_PREFIX, new ParameterAction() {
                public void execute(String key, ActionMapping mapping) {
                    mapping.setMethod(key.substring(METHOD_PREFIX.length()));
                }
            });

            put(ACTION_PREFIX, new ParameterAction() {
                public void execute(String key, ActionMapping mapping) {
                    String name = key.substring(ACTION_PREFIX.length());
                    int bang = name.indexOf('!');
                    if (bang != -1) {
                        String method = name.substring(bang + 1);
                        mapping.setMethod(method);
                        name = name.substring(0, bang);
                    }
                    
                    mapping.setName(name);
                }
            });

            put(REDIRECT_PREFIX, new ParameterAction() {
                public void execute(String key, ActionMapping mapping) {
                    ServletRedirectResult redirect = new ServletRedirectResult();
                    redirect.setLocation(key.substring(REDIRECT_PREFIX.length()));
                    mapping.setResult(redirect);
                }
            });

            put(REDIRECT_ACTION_PREFIX, new ParameterAction() {
                public void execute(String key, ActionMapping mapping) {
                    String location = key.substring(REDIRECT_ACTION_PREFIX.length());
                    ServletRedirectResult redirect = new ServletRedirectResult();
                    String extension = getDefaultExtension();
                    if (extension != null) {
                        location += "." + extension;
                    }
                    redirect.setLocation(location);
                    mapping.setResult(redirect);
                }
            });
        }
    };

    public ActionMapping getMapping(HttpServletRequest request) {
        ActionMapping mapping = new ActionMapping();
        String uri = getUri(request);
        if (isExcludedUri(uri)) {
        	return null;
        }

        parseNameAndNamespace(uri, mapping, false);

        handleSpecialParameters(request, mapping);

        if (mapping.getName() == null) {
            return null;
        }

        // handle "name!method" convention.
        String name = mapping.getName();
        int exclamation = name.lastIndexOf("!");
        if (exclamation != -1) {
            mapping.setName(name.substring(0, exclamation));
            mapping.setMethod(name.substring(exclamation + 1));
        }
        return mapping;
    }
    
    private boolean isExcludedUri(String uri) {
    	if (uri.startsWith("/swf")) {
    		return true;
    	}
    	
    	return false;
    }

    @SuppressWarnings("unchecked")
	public static void handleSpecialParameters(HttpServletRequest request, ActionMapping mapping) {
        // handle special parameter prefixes.
        Map parameterMap = request.getParameterMap();
        for (Iterator iterator = parameterMap.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            ParameterAction parameterAction = (ParameterAction) prefixTrie.get(key);
            if (parameterAction != null) {
                parameterAction.execute(key, mapping);
                break;
            }
        }
    }
    /*
    void parseNameAndNamespace(String uri, ActionMapping mapping) {
        String namespace, name;
        int lastSlash = uri.lastIndexOf("/");
        if (lastSlash == -1) {
            namespace = "";
            name = uri;
        } else if (lastSlash == 0) {
            // ww-1046, assume it is the root namespace, it will fallback to default
            // namespace anyway if not found in root namespace.
            namespace = "/";
            name = uri.substring(lastSlash + 1);
        } else {
            namespace = uri.substring(0, lastSlash);
            name = uri.substring(lastSlash + 1);
        }
        mapping.setNamespace(namespace);
        mapping.setName(dropExtension(name));
    }
    */
    void parseNameAndNamespace(String uri, ActionMapping mapping, boolean lastSlashRemoved) {
        String namespace, name;
        
        int lastSlash = uri.lastIndexOf("/");
        if (lastSlash == -1) {
            namespace = "";
            name = uri;
        } else if (lastSlash == 0) {
            // ww-1046, assume it is the root namespace, it will fallback to default
        	// namespace anyway if not found in root namespace.
        	namespace = "/";
        	name = uri.substring(lastSlash + 1);
        } else {
            namespace = uri.substring(0, lastSlash);
            name = uri.substring(lastSlash + 1);
        }
        
        if (name == null || "".equals(name)) {
        	name = "default";
        } else {
        	name = dropExtension(name);
        }
        
        ActionConfig config = ConfigurationManager.getConfiguration().getRuntimeConfiguration().getActionConfig(namespace, name);
        
        if (config == null && !lastSlashRemoved) {
        	if ("default".equals(name) && uri.length() > 1 && uri.endsWith("/")) {
        		parseNameAndNamespace(uri.substring(0, uri.length() - 1), mapping, true);
        	}
        	return;
        }
        
        mapping.setNamespace(namespace);
        mapping.setName(name);
    }

    String dropExtension(String name) {
    	List<String> extensions = getExtensions();
		if (extensions == null) {
		    return name;
		}
		Iterator<String> it = extensions.iterator();
		while (it.hasNext()) {
			String extension = "." + it.next();
			if (name.endsWith(extension)) {
				name = name.substring(0, name.length() - extension.length());
				return name;
			}
		}
		return null;
    }

    /**
     * Returns null if no extension is specified.
     */
    static String getDefaultExtension() {
        List<String> extensions = getExtensions();
        if (extensions == null) {
        	return null;
        } else {
        	return extensions.get(0);
        }
    }
    
    /**
     * Returns null if no extension is specified.
     */
    static List<String> getExtensions() {
        String extensions = (String) Configuration.get(WebWorkConstants.WEBWORK_ACTION_EXTENSION);
        if (extensions.equals("")) {
        	return null;
        } else {
        	return Arrays.asList(extensions.split(","));
        } 
    }

    String getUri(HttpServletRequest request) {
        // handle http dispatcher includes.
        String uri = (String) request.getAttribute("javax.servlet.include.servlet_path");
        if (uri != null) {
            return uri;
        }

        uri = RequestUtils.getServletPath(request);
        if (uri != null && !"".equals(uri)) {
            return uri;
        }

        uri = request.getRequestURI();
        return uri.substring(request.getContextPath().length());
    }

    public String getUriFromActionMapping(ActionMapping mapping) {
        StringBuffer uri = new StringBuffer();

        uri.append(mapping.getNamespace());
        if(!"/".equals(mapping.getNamespace())) {
            uri.append("/");
        }
        String name = mapping.getName();
        String params = "";
        if ( name.indexOf('?') != -1) {
            params = name.substring(name.indexOf('?'));
            name = name.substring(0, name.indexOf('?'));
        }
        uri.append(name);

        if (null != mapping.getMethod() && !"".equals(mapping.getMethod())) {
            uri.append("!").append(mapping.getMethod());
        }

        String extension = getDefaultExtension();
        if ( extension != null && uri.indexOf( '.' + extension) == -1  ) {
            uri.append(".").append(extension);
            if ( params.length() > 0) {
                uri.append(params);
            }
        }

        return uri.toString();
    }

    interface ParameterAction {
        void execute(String key, ActionMapping mapping);
    }
}
