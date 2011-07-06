/*
 * @(#)MySQLBitwiseAndSQLFunction.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.persistence;

import java.util.List;

import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.Type;

/**
 * <p>
 * <a href="MySQLBitwiseAndSQLFunction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: MySQLBitwiseAndSQLFunction.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class MySQLBitwiseAndSQLFunction extends StandardSQLFunction implements SQLFunction {

	public MySQLBitwiseAndSQLFunction(String name) {
		super(name);
	}
	
	public MySQLBitwiseAndSQLFunction(String name, Type typeValue) {
		super(name, typeValue);
	} 
	
	/*
	 * @see org.hibernate.dialect.function.StandardSQLFunction#render(java.util.List, org.hibernate.engine.SessionFactoryImplementor)
	 */
	public String render(List args, SessionFactoryImplementor factory) {
		if (args.size() != 2) {
			throw new IllegalArgumentException("the function must be passed 2 arguments");
		}
		
		StringBuffer buffer = new StringBuffer(args.get(0).toString());
		buffer.append(" & ").append(args.get(1));
		return buffer.toString();
	}
	
}
