/*
 * @(#)PagerModel.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.views.freemarker.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.views.freemarker.tags.TagModel;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.painiu.webapp.views.component.Pager;

/**
 * <p>
 * <a href="PagerModel.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: PagerModel.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class PagerModel extends TagModel {
	
    public PagerModel(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        super(stack, req, res);
    }

    /*
     * @see com.opensymphony.webwork.views.freemarker.tags.TagModel#getBean()
     */
    protected Component getBean() {
        return new Pager(stack, req, res);
    }

}
