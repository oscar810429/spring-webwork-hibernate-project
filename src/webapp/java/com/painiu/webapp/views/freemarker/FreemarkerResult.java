/*
 * @(#)FreemarkerResult.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.views.freemarker;

import java.io.IOException;

import com.opensymphony.webwork.ServletActionContext;

import freemarker.template.Template;
import freemarker.template.TemplateModel;

/**
 * <p>
 * <a href="FreemarkerResult.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: FreemarkerResult.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class FreemarkerResult extends
		com.opensymphony.webwork.views.freemarker.FreemarkerResult {
	
    protected boolean preTemplateProcess(Template template, TemplateModel model) throws IOException {
        Object attrContentType = template.getCustomAttribute("content_type");

        if (attrContentType != null) {
            ServletActionContext.getResponse().setContentType(attrContentType.toString());
        } else {
            String contentType = getContentType();

            if (contentType == null) {
                contentType = "text/html";
            }

            // webwork:
//            String encoding = template.getEncoding();
            
            String encoding = template.getOutputEncoding();

            if (encoding != null) {
                contentType = contentType + "; charset=" + encoding;
            }

            ServletActionContext.getResponse().setContentType(contentType);
        }

        return true;
    }
}
