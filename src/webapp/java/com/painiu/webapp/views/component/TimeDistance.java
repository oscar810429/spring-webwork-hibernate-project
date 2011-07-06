/*
 * @(#)TimeDistance.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.views.component;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.painiu.webapp.util.DateUtils;

/**
 * <p>
 * <a href="TimeDistance.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: TimeDistance.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class TimeDistance extends Component {
	//~ Static fields/initializers =============================================

	private static final Log log = LogFactory.getLog(TimeDistance.class);
	
	//~ Instance fields ========================================================

	private String from;
	private String to;
	private String includeSeconds;
	
	private Date fromDate;
	private Date toDate;
	
	//~ Constructors ===========================================================

	public TimeDistance(OgnlValueStack stack) {
        super(stack);
    }
	
	//~ Methods ================================================================

	/**
	 * @param fromTime The from to set.
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	
	/**
	 * @param toTime The to to set.
	 */
	public void setTo(String to) {
		this.to = to;
	}
	
	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	
	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	/**
	 * @param includeSeconds The includeSeconds to set.
	 */
	public void setIncludeSeconds(String includeSeconds) {
		this.includeSeconds = includeSeconds;
	}
	
    public boolean start(Writer writer) {
        boolean result = super.start(writer);

        Date actualFromDate = fromDate != null ? fromDate : (Date) findValue(from, Date.class);
        Date actualToDate = toDate != null ? toDate : to != null ? (Date) findValue(to, Date.class) : new Date();
        
        if (actualFromDate == null) {
        	fieldError("fromTime", "can not find value of 'fromTime'", null);
        }
        if (actualToDate == null) {
        	fieldError("toTime", "can not find value of 'toTime'", null);
        }
        
        boolean countSeconds = true; 
        
        if ("no".equalsIgnoreCase(includeSeconds) || 
        	"false".equalsIgnoreCase(includeSeconds) || 
        	"0".equalsIgnoreCase(includeSeconds)) {
        	countSeconds = false;
        }
        
        String value = DateUtils.distanceInWords(actualFromDate, actualToDate, countSeconds);
        
        try {
            writer.write(value);
        } catch (IOException e) {
            log.info("Could not print out value '" + value + "'", e);
        }

        return result;
    }

	//~ Accessors ==============================================================
}
