/*
 * @(#)IntegerEnum.java  2008-11-13
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * <a href="IntegerEnum.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: IntegerEnum.java 52 2010-06-15 09:28:19Z zhangsf $
 */
public abstract class IntegerEnum extends BaseObject implements Serializable {

	private static final long serialVersionUID = -8742570598876035004L;

	private static final Log log = LogFactory.getLog(IntegerEnum.class);
	
	private final int value;
    
	protected IntegerEnum(int value) {
		this.value = value;
	}

    public int value() {
        return value;
    }

    public static IntegerEnum valueOf(Class clazz, int value) {
    	if (!IntegerEnum.class.isAssignableFrom(clazz)) {
    		throw new IllegalArgumentException("the class must be a subclass of IntegerEnum");
    	}
    	
    	Field[] fields = clazz.getDeclaredFields();
    	for (int i = 0; i < fields.length; i++) {
    		int mod = fields[i].getModifiers();
    		
    		if (clazz == fields[i].getType() && 
    			Modifier.isPublic(mod) && Modifier.isStatic(mod)) {
    			try {
					IntegerEnum field = (IntegerEnum) fields[i].get(null);
					if (field.value() == value) {
						return field;
					}
				} catch (IllegalArgumentException e) {
					log.error("IllegalArgumentException: ", e);
				} catch (IllegalAccessException e) {
					log.error("IllegalAccessException: ", e);
				}
    			
    		}
		}
    	
    	return null;
    }
    
	public String toString() {
		return Integer.toString(value);
	}
	
	/*
	 * @see com.mingda.core.model.BaseObject#equals(java.lang.Object)
	 */
	public final boolean equals(Object o) {
		if (!(o instanceof IntegerEnum)) {
			return false;
		}
		if (!this.getClass().equals(o.getClass())) {
			return false;
		}
		
		return this.value == ((IntegerEnum) o).value();
	}
	
	/*
	 * @see com.mingda.core.model.BaseObject#hashCode()
	 */
	public int hashCode() {
		return this.getClass().hashCode() + value;
	}
}
