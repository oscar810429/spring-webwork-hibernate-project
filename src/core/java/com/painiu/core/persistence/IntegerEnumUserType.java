/*
 * @(#)IntegerEnumUserType.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.persistence;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.usertype.EnhancedUserType;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.util.ReflectHelper;

import com.painiu.core.model.IntegerEnum;
import com.painiu.core.model.Privacy;

/**
 * <p>
 * <a href="IntegerEnumUserType.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: IntegerEnumUserType.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class IntegerEnumUserType implements EnhancedUserType, ParameterizedType {

    private Class enumClass;

    public void setParameterValues(Properties parameters) {
        String enumClassName = parameters.getProperty("enumClassname");
        try {
            enumClass = ReflectHelper.classForName(enumClassName);
        }
        catch (ClassNotFoundException cnfe) {
            throw new HibernateException("Enum class not found", cnfe);
        }
    }


    public Class returnedClass() {
        return enumClass;
    }

    public int[] sqlTypes() {
        return new int[] { Hibernate.INTEGER.sqlType() };
    }

    public boolean isMutable() {
        return false;
    }

    public Object deepCopy(Object value) {
        return value;
    }

    public Serializable disassemble(Object value) {
        return (IntegerEnum) value;
    }

    public Object replace(Object original, Object target, Object owner) {
        return original;
    }

    public Object assemble(Serializable cached, Object owner) {
        return cached;
    }

    public boolean equals(Object x, Object y) {
        return x==y;
    }

    public int hashCode(Object x) {
        return x.hashCode();
    }

    public Object fromXMLString(String xmlValue) {
        return valueOf(Integer.parseInt(xmlValue));
    }

    public String objectToSQLString(Object value) {
        return value.toString();
    }

    public String toXMLString(Object value) {
        return value.toString();
    }

    public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
            throws SQLException {
        int value = rs.getInt(names[0]);
        return rs.wasNull() ? null : valueOf(value);
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index)
            throws SQLException {
        if (value == null) {
            st.setNull(index, Hibernate.INTEGER.sqlType());
        }
        else {
            st.setInt(index, ((IntegerEnum) value).value());
        }
    }

    private Object valueOf(int value) {
//    	try {
//			Method method = enumClass.getMethod("valueOf", new Class[] { int.class });
//			if (Modifier.isStatic(method.getModifiers())) {
//				return method.invoke(null, new Object[] { new Integer(value) });
//			}
//		} catch (SecurityException e) {
//			e.printStackTrace();
//		} catch (NoSuchMethodException e) {
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			e.printStackTrace();
//		}
//		return null;
    	
		if (enumClass == Privacy.class) {
			return Privacy.valueOf(value);
		}
		return IntegerEnum.valueOf(enumClass, value);
		
    }
}

