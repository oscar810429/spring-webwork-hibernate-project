/*
 * @(#)IntegerEnumConverter.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.util;

import java.util.Map;

import com.opensymphony.webwork.util.WebWorkTypeConverter;
import com.painiu.core.model.IntegerEnum;

/**
 * <p>
 * <a href="IntegerEnumConverter.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: IntegerEnumConverter.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class IntegerEnumConverter extends WebWorkTypeConverter {
	
    /*
     * @see com.opensymphony.webwork.util.WebWorkTypeConverter#convertFromString(java.util.Map, java.lang.String[], java.lang.Class)
     */
    public Object convertFromString(Map context, String[] values, Class toClass) {
        int value = Integer.parseInt(values[0]);
        
        return IntegerEnum.valueOf(toClass, value);
    }

    /*
     * @see com.opensymphony.webwork.util.WebWorkTypeConverter#convertToString(java.util.Map, java.lang.Object)
     */
    public String convertToString(Map context, Object o) {
        String result = null;
        
        if (o instanceof IntegerEnum) {
        	IntegerEnum intEnum = (IntegerEnum) o;
            result = String.valueOf(intEnum.value());
        }
        
        return result;
    }
}
