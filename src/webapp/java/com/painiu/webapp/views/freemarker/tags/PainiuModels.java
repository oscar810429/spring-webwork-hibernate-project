/*
 * @(#)PainiuModels.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.views.freemarker.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * <p>
 * <a href="PainiuModels.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: PainiuModels.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class PainiuModels {
    protected OgnlValueStack stack;
    protected HttpServletRequest req;
    protected HttpServletResponse res;
    
    protected TextFilterModel textFilterModel;
    protected PagerModel pagerModel;
    protected TimeDistanceModel timeDistanceModel;
    protected LocalizedResourceUrlModel localizedResourceUrlModel;
    
    public PainiuModels(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        this.stack = stack;
        this.req = req;
        this.res = res;
    }
    
    /**
     * @return Returns the textFilterModel.
     */
    public TextFilterModel getFilter() {
        if (textFilterModel == null) {
            textFilterModel = new TextFilterModel(stack, req, res);
        }
        return textFilterModel;
    }
    
    /**
	 * @return Returns the pagerModel.
	 */
	public PagerModel getPager() {
		if (pagerModel == null) {
			pagerModel = new PagerModel(stack, req, res);
		}
		return pagerModel;
	}
	
	/**
	 * @return Returns the timeDistanceModel.
	 */
	public TimeDistanceModel getTimeDistance() {
		if (timeDistanceModel == null) {
			timeDistanceModel = new TimeDistanceModel(stack, req, res);
		}
		return timeDistanceModel;
	}
	
	/**
	 * @return Returns the localizedResourceUrlModel.
	 */
	public LocalizedResourceUrlModel getI18nUrl() {
		if (localizedResourceUrlModel == null) {
			localizedResourceUrlModel = new LocalizedResourceUrlModel(stack, req, res);
		}
		return localizedResourceUrlModel;
	}
}
