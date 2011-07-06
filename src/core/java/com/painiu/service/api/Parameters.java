/*
 * @(#)Parameters.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.service.api;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * <a href="Parameters.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Parameters.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class Parameters implements Serializable {
	//~ Static fields/initializers =============================================

	private static final DateFormat MYSQL_DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//~ Instance fields ========================================================
	
	private Map params = new HashMap();
	
	//~ Constructors ===========================================================	

	public Parameters() {}
	
	public Parameters(Map paramsMap) {
		for (Iterator i = paramsMap.keySet().iterator(); i.hasNext();) {
			String name = (String) i.next();
			Object value = paramsMap.get(name);
			
			if (value != null && value.getClass().isArray()) {
				if (Array.getLength(value) > 0) {
	                params.put(name, Array.get(value, 0));
	            }
			} else {
				params.put(name, value);
			}
		}
	}
	
	//~ Methods ================================================================
	
	public Object put(String name, Object value) {
		return params.put(name, value);
	}
	
	public Object get(String name) {
		return params.get(name);
	}
	
	public String getString(String name) {
		Object value = params.get(name);
		if (value != null) {
			return value.toString();
		}
		return null;
	}
	
	public int getInteger(String name) {
		return getInteger(name, -1);
	}
	
	public int getInteger(String name, int defaultValue) {
		Object value = get(name);
		
		if (value == null) {
			return defaultValue;
		}
		
		if (value instanceof Number) {
			return ((Number) value).intValue();
		}

		try {
			return Integer.parseInt(value.toString());
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
	
	public int getPositiveInteger(String name, int defaultValue) {
		int value = getInteger(name);
		
		if (value < 0) {
			return defaultValue;
		}
		return value;
	}
	
	public long getLong(String name) {
		return getLong(name, -1);
	}
	
	public long getLong(String name, long defaultValue) {
		Object value = get(name);
		
		if (value == null) {
			return defaultValue;
		}
		
		if (value instanceof Number) {
			return ((Number) value).longValue();
		}

		try {
			return Long.parseLong(value.toString());
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
	
	public long getPositionLong(String name, long defaultValue) {
		long value = getLong(name);
		
		if (value < 0) {
			return defaultValue;
		}
		return value;
	}
	
	public boolean getBoolean(String name) {
		return getBoolean(name, false);
	}
	
	public boolean getBoolean(String name, boolean defaultValue) {
		Object value = get(name);
		
		if (value == null) {
			return defaultValue;
		}
		
		if (value instanceof Boolean) {
			return ((Boolean) value).booleanValue();
		}
		
		String string = value.toString();
		
		return ("true".equalsIgnoreCase(string)) || 
					("1".equalsIgnoreCase(string)) ||
					("yes".equalsIgnoreCase(string));
	}
	
	
	public Date getPostedDate(String name) {
		return getPostedDate(name, null);
	}
	
	public Date getPostedDate(String name, Date defaultValue) {
		Object value = get(name);
		
		if (value == null) {
			return defaultValue;
		}
		
		long longValue = -1L;
		
		if (value instanceof Number) {
			longValue = ((Number) value).longValue();
		}

		try {
			longValue = Long.parseLong(value.toString());
		} catch (NumberFormatException e) {
			return defaultValue;
		}
		
		return new Date(longValue * 1000);
	}
	
	public Date getTakenDate(String name) {
		return getTakenDate(name, null);
	}
	
	public Date getTakenDate(String name, Date defaultValue) {
		Object value = get(name);
		
		if (value == null) {
			return defaultValue;
		}
		
		if (value instanceof Date) {
			return (Date) value;
		}
		
		try {
			return MYSQL_DATETIME_FORMAT.parse(value.toString());
		} catch (ParseException e) {
			return defaultValue;
		}
	}
	
	public Set names() {
		return params.keySet();
	}
	
	public Iterator iterator() {
		return params.entrySet().iterator();
	}
}
