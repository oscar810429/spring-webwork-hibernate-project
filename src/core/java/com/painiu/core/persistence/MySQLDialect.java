/*
 * @(#)MySQLDialect.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.persistence;

import org.hibernate.Hibernate;

/**
 * <p>
 * <a href="MySQLDialect.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: MySQLDialect.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class MySQLDialect extends org.hibernate.dialect.MySQLDialect {
	
	public MySQLDialect() {
		super();
		registerFunction("bitwise_and", new MySQLBitwiseAndSQLFunction("bitwise_and", Hibernate.INTEGER));
	} 
}
