/*
 * @(#)DateConverter.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.opensymphony.webwork.util.WebWorkTypeConverter;

/**
 * <p>
 * <a href="DateConverter.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: DateConverter.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class DateConverter extends WebWorkTypeConverter {

    //~ Static fields/initializers =============================================

    public static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    
    //~ Methods ================================================================
    
    /*
     * @see com.opensymphony.webwork.util.WebWorkTypeConverter#convertFromString(java.util.Map, java.lang.String[], java.lang.Class)
     */
    public Object convertFromString(Map context, String[] values, Class toClass) {
        String value = values[0];
        
        try {
            return DATE_TIME_FORMAT.parse(value);
        } catch (ParseException e) {
            return null;
        }
    }

    /*
     * @see com.opensymphony.webwork.util.WebWorkTypeConverter#convertToString(java.util.Map, java.lang.Object)
     */
    public String convertToString(Map context, Object o) {
        if (o instanceof Date) {
            return DATE_TIME_FORMAT.format((Date) o);
        }
        return null;
    }

}
