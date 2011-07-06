/*
 * @(#)ConfigValueUserType.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.persistence;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.usertype.EnhancedUserType;

import com.painiu.core.model.Config.Value;

/**
 * <p>
 * <a href="ConfigValueUserType.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: ConfigValueUserType.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class ConfigValueUserType implements EnhancedUserType {

	/*
	 * @see org.hibernate.usertype.EnhancedUserType#objectToSQLString(java.lang.Object)
	 */
	public String objectToSQLString(Object value) {
		return value.toString();
	}

	/*
	 * @see org.hibernate.usertype.EnhancedUserType#toXMLString(java.lang.Object)
	 */
	public String toXMLString(Object value) {
		return objectToSQLString(value);
	}

	/*
	 * @see org.hibernate.usertype.EnhancedUserType#fromXMLString(java.lang.String)
	 */
	public Object fromXMLString(String xmlValue) {
		return Value.fromString(xmlValue);
	}

	/*
	 * @see org.hibernate.usertype.UserType#sqlTypes()
	 */
	public int[] sqlTypes() {
		return new int[] { Hibernate.STRING.sqlType() };
	}

	/*
	 * @see org.hibernate.usertype.UserType#returnedClass()
	 */
	public Class returnedClass() {
		return Value.class;
	}

	/*
	 * @see org.hibernate.usertype.UserType#equals(java.lang.Object, java.lang.Object)
	 */
	public boolean equals(Object x, Object y) throws HibernateException {
		return x.equals(y);
	}

	/*
	 * @see org.hibernate.usertype.UserType#hashCode(java.lang.Object)
	 */
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	/*
	 * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet, java.lang.String[], java.lang.Object)
	 */
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
			throws HibernateException, SQLException {
		String value = rs.getString(names[0]);
        return rs.wasNull() ? null : Value.fromString(value);
	}

	/*
	 * @see org.hibernate.usertype.UserType#nullSafeSet(java.sql.PreparedStatement, java.lang.Object, int)
	 */
	public void nullSafeSet(PreparedStatement st, Object value, int index)
			throws HibernateException, SQLException {
		if (value == null) {
            st.setNull(index, Hibernate.STRING.sqlType());
        }
        else {
            st.setString(index, value.toString());
        }
	}

	/*
	 * @see org.hibernate.usertype.UserType#deepCopy(java.lang.Object)
	 */
	public Object deepCopy(Object value) throws HibernateException {
		return Value.fromString(value.toString());
	}

	/*
	 * @see org.hibernate.usertype.UserType#isMutable()
	 */
	public boolean isMutable() {
		return true;
	}

	/*
	 * @see org.hibernate.usertype.UserType#disassemble(java.lang.Object)
	 */
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) deepCopy(value);
	}

	/*
	 * @see org.hibernate.usertype.UserType#assemble(java.io.Serializable, java.lang.Object)
	 */
	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return deepCopy(cached);
	}

	/*
	 * @see org.hibernate.usertype.UserType#replace(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return deepCopy(original);
	}

}
